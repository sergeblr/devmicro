package com.epam.summer19.webappvdn8;

import com.epam.summer19.dto.OrderDTO;
import com.epam.summer19.model.Item;
import com.epam.summer19.model.Order;
import com.epam.summer19.service.OrderService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringView(name = OrdersView.VIEW_NAME)
public class OrdersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "orders";

    @Autowired
    private OrderService orderService;

    // Objects
    private List<OrderDTO> orders;
    private Order newOrder;
    private Order editableOrder;
    private OrderDTO editableOrderDTO;
    private Grid<OrderDTO> ordersGrid;
    Binder<Order> orderBinder;

    // Header field with buttons and registrations, employeeId field
    private HorizontalLayout orderFieldLayout;
    private Button addOrderButton;
    private Button orderFieldOKButton;
    private Button orderFieldEditItemsButton;
    private Button orderFieldDeleteButton;
    private Button orderFieldCancelButton;
    private Registration ordersGridSelectionRegistration;
    private Registration orderFieldOKButtonRegistration;
    private Registration orderFieldEditItemsButtonRegistration;
    private Registration orderFieldDeleteButtonRegistration;
    private TextField orderFieldEmployeeId;

    //Filter elements
    private HorizontalLayout headerButtonFilterLayout;
    private DateTimeField startDateTime;
    private DateTimeField endDateTime;

    // Main enter method
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
        // This view is constructed in the init() method()
    }

    // Main init method
    void init() {
        ordersGrid = new Grid<>(OrderDTO.class);
        orderBinder = new Binder<>();
        ordersGrid.setSizeFull();

        // Setup AddOrder button, DateTime filter components
        headerButtonFilterLayout = new HorizontalLayout();
        addOrderButton = new Button("Add order", e -> {
            orderFieldCreateMode();
            orderFieldLayout.setVisible(true);
        });
        initFilterForm();
        headerButtonFilterLayout.addComponents(addOrderButton, startDateTime, endDateTime);

        // Setup Header field, Grid
        setupOrdersGrid();
        initOrderFieldForm();
        updateOrdersList();

        // Adding all components to ItemsView layout
        addComponent(headerButtonFilterLayout);
        addComponent(orderFieldLayout);
        addComponent(ordersGrid);

        // Set ExpandRations and SizeFull
        setExpandRatio(headerButtonFilterLayout, 1);
        setExpandRatio(orderFieldLayout, 2);
        setExpandRatio(ordersGrid, 15);
        setSizeFull();
    }

    // Filter - datetime START & END init
    private void initFilterForm() {
        startDateTime = new DateTimeField();
        endDateTime = new DateTimeField();

        startDateTime.setValue(LocalDateTime.now().minusYears(1).truncatedTo(ChronoUnit.MINUTES));
        endDateTime.setValue(LocalDateTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES));

        startDateTime.addValueChangeListener(e -> {
            updateOrdersList();
            setupOrdersGrid();
        });
        endDateTime.addValueChangeListener(e -> {
            updateOrdersList();
            setupOrdersGrid();
        });
    }

    // Setup orders grid (reInit)
    private void setupOrdersGrid() {
        ordersGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        ordersGridSelectionRegistration = () -> {};
        ordersGrid.setColumnOrder("orderId", "employeeId", "orderDateTime", "summaryPrice", "itemsQuantity");
        ordersGrid.setWidth("900px");
        ordersGrid.getColumn("orderId").setExpandRatio(1);
        ordersGrid.getColumn("employeeId").setExpandRatio(1);
        ordersGrid.getColumn("orderDateTime").setExpandRatio(5);
        ordersGrid.getColumn("summaryPrice").setExpandRatio(2);
        ordersGrid.getColumn("itemsQuantity").setExpandRatio(2);

        ordersGridSelectionRegistration.remove();
        ordersGridSelectionRegistration = ordersGrid.addSelectionListener(e ->
        {
            if(e.getFirstSelectedItem().isPresent()) {
                editableOrderDTO = e.getFirstSelectedItem().get();
                orderFieldEditMode();
            }
            orderFieldLayout.setVisible(true);
        });
    }

    // Update orders grid
    private void updateOrdersList() {
        orders = orderService.findOrdersDTOByDateTime(startDateTime.getValue(), endDateTime.getValue());
        ordersGrid.setItems(orders);
        ordersGrid.getDataProvider().refreshAll();
    }

    // Order subForm -> creating mode
    private void orderFieldCreateMode() {
        orderFieldOKButton.setCaption("Create new order");
        orderFieldDeleteButton.setVisible(false);
        orderFieldEditItemsButton.setVisible(false);
        orderBinder.readBean(null);

        // Create new order button
        orderFieldOKButtonRegistration.remove();
        orderFieldOKButtonRegistration = orderFieldOKButton.addClickListener(e ->
        {
            try {
                orderBinder.writeBean(newOrder);
                newOrder = orderService.add(newOrder);
                orderFieldLayout.setVisible(false);
                endDateTime.setValue(LocalDateTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES));
                updateOrdersList();
                setupOrdersGrid();
                Notification.show("Order "+newOrder.getOrderId()+" succsessfully created.", Notification.Type.WARNING_MESSAGE);
            }
            catch (ValidationException exc) {
                Notification.show("Order could not be created, check errors.");
            }
        });

    }

    // Order subForm -> editing mode
    private void orderFieldEditMode() {
        orderFieldOKButton.setCaption("Confirm edited");
        orderFieldEditItemsButton.setCaption("Edit items...");
        orderFieldEditItemsButton.setVisible(true);
        orderFieldDeleteButton.setVisible(true);
        editableOrder.setOrderEmployeeId(editableOrderDTO.getEmployeeId());
        editableOrder.setOrderId(editableOrderDTO.getOrderId());
        orderBinder.readBean(editableOrder);

        // Edit order confirmation button
        orderFieldOKButtonRegistration.remove();
        orderFieldOKButtonRegistration = orderFieldOKButton.addClickListener(e ->
        {
            try {
                orderBinder.writeBean(editableOrder);
                orderService.update(editableOrder);
                orderFieldLayout.setVisible(false);
                setupOrdersGrid();
                updateOrdersList();
                Notification.show("Order #"+editableOrder.getOrderId()+" succsessfully changed.", Notification.Type.WARNING_MESSAGE);
            }
            catch (ValidationException exc) {
                Notification.show("Order could not be saved, check errors.");
            }
        });

        // Delete order button
        orderFieldDeleteButtonRegistration.remove();
        orderFieldDeleteButtonRegistration = orderFieldDeleteButton.addClickListener(e ->
                ConfirmDialog.show(
                        UI.getCurrent(),
                        "Delete confirmation",
                        "Delete order: " + editableOrder.getOrderId() +"?",
                        "Delete",
                        "Cancel",
                        new ConfirmDialog.Listener() {
                            @Override
                            public void onClose(ConfirmDialog confirmDialog) {
                                if (confirmDialog.isConfirmed()) {
                                    orderService.delete(editableOrder.getOrderId());
                                    updateOrdersList();
                                    setupOrdersGrid();
                                    Notification.show("Order #" + editableOrder.getOrderId()+" deleted",
                                            Notification.Type.WARNING_MESSAGE);
                                } else { // else?
                                }
                            }
                        }));

        // Add items to THIS order button
        orderFieldEditItemsButtonRegistration.remove();
        orderFieldEditItemsButtonRegistration = orderFieldEditItemsButton.addClickListener(e ->
            getUI().getNavigator().navigateTo(ItemInOrdersView.VIEW_NAME + "/" + editableOrder.getOrderId())
        );
    }


    // Init/reinit binder for new/editable order objects
    private void orderBinder() {
        orderBinder.forField(orderFieldEmployeeId)
                .withNullRepresentation("")
                .withValidator(str -> str.length() > 1, "Length must be more than 0")
                .withConverter(new StringToIntegerConverter("Price must be in Integer format"))
                .bind(Order::getOrderEmployeeId, Order::setOrderEmployeeId);
    }


    // init Order create/edit subForm
    private void initOrderFieldForm() {
        // Setup Order Field Layout
        orderFieldLayout = new HorizontalLayout();
        orderFieldLayout.setMargin(false);
        orderFieldLayout.setSpacing(true);
        orderFieldLayout.setVisible(false);

        // Setup Order Name & price fields
        orderFieldEmployeeId = new TextField();
        orderFieldEmployeeId.setCaption("Employee ID:");
        orderFieldEmployeeId.setPlaceholder("ID");

        // Binders (init order for binder, prepare for read/write + !Validators! Vaadin 8)
        editableOrder = new Order();
        editableOrderDTO = new OrderDTO();
        newOrder = new Order();

        orderBinder();

        // Create buttons: New order & Edit & Cancel & Delete
        // Implementation of orderFieldOKButton is: orderFieldEditMode() & orderFieldCreateMode()
        orderFieldOKButton = new Button();
        orderFieldOKButtonRegistration = () -> {};
        orderFieldOKButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        orderFieldEditItemsButton = new Button();
        orderFieldEditItemsButtonRegistration = () -> {};
        orderFieldEditItemsButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        orderFieldDeleteButton = new Button("Delete");
        orderFieldDeleteButtonRegistration = () -> {};
        orderFieldDeleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        orderFieldCancelButton = new Button("Cancel", e -> {
            orderFieldLayout.setVisible(false);
        });

        // Adding components to subform: new Order Form + alignment
        orderFieldLayout.addComponents(orderFieldEmployeeId, orderFieldOKButton, orderFieldEditItemsButton, orderFieldDeleteButton, orderFieldCancelButton);
        orderFieldLayout.setComponentAlignment(orderFieldOKButton, Alignment.BOTTOM_LEFT);
        orderFieldLayout.setComponentAlignment(orderFieldEditItemsButton, Alignment.BOTTOM_LEFT);
        orderFieldLayout.setComponentAlignment(orderFieldDeleteButton, Alignment.BOTTOM_LEFT);
        orderFieldLayout.setComponentAlignment(orderFieldCancelButton, Alignment.BOTTOM_LEFT);
    }

}
