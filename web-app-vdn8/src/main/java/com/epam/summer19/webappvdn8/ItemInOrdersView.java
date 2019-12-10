package com.epam.summer19.webappvdn8;

import com.epam.summer19.model.Item;
import com.epam.summer19.model.ItemInOrder;
import com.epam.summer19.service.ItemInOrderService;
import com.epam.summer19.service.ItemService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = ItemInOrdersView.VIEW_NAME)
public class ItemInOrdersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "iteminorders";

    @Autowired
    private ItemInOrderService itemInOrderService;

    @Autowired
    private ItemService itemService;

    // Objects
    private List<ItemInOrder> iios;
    private List<ItemInOrder> iiosToBeAdded;
    private ItemInOrder iioToBeAdded;
    private ItemInOrder newIio;
    private ItemInOrder editableIio;
    private Grid<ItemInOrder> iiosGrid;
    private Integer currentOrderId;

    // Buttons & registrations, layout for them, order label
    private HorizontalLayout iioFieldLayout;
    private Label currentOrderLabel;
    private Button iioFieldSaveButton;
    private Button iioFieldCancelButton;
    private Button iioFieldAddItemButton;
    private Button iioFieldDeleteButton;
    private Registration iioFieldSaveButtonRegistration;
    private Registration iioFieldCancelButtonRegistration;
    private Registration iioFieldAddItemButtonRegistration;
    private Registration iiosGridSelectionRegistration;
    private Registration iioFieldDeleteButtonRegistration;

    //for ItemsGrid:
    private Window itemsWindow;
    private Grid<Item> itemsGrid;
    private Registration itemsGridSelectionRegistration;
    private Item selectedItem;
    private VerticalLayout itemsLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            currentOrderId = Integer.parseInt(event.getParameters());
        }
        // This view is constructed in the init() method() /**event.getParameters()**/
        init();
    }

    // Main init method (invoked from enter() )
    void init() {
        
        currentOrderLabel = new Label("Order #" + currentOrderId);
        currentOrderLabel.setStyleName(ValoTheme.LABEL_BOLD);
        iiosGrid = new Grid<>(ItemInOrder.class);
        iiosGrid.setSizeFull();

        // Main inits and setups
        iiosGridSelectionRegistration = () -> {};
        setupIiosGrid();
        initIioFieldForm();
        iioFieldButtonsInit();
        updateIiosList();
        
        // Adding components to ItemsView layout
        addComponent(currentOrderLabel);
        addComponent(iioFieldLayout);
        addComponent(iiosGrid);

        // Set ExpandRations and SizeFull for THIS view
        setExpandRatio(iioFieldLayout, 1);
        setExpandRatio(iiosGrid, 15);
        addStyleName("backColorGrey");
        setSizeFull();
    }

    // Setup items grid (reInit)
    private void setupIiosGrid() {
        iiosGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        iiosGridSelectionRegistration.remove();
        iiosGridSelectionRegistration = iiosGrid.addSelectionListener(e ->
        {
            if(e.getFirstSelectedItem().isPresent()) {
                editableIio = e.getFirstSelectedItem().get();
                iioFieldDeleteButtonUpdate();
            }
        });
    }

    // Update iios grid
    private void updateIiosList() {
        iios = itemInOrderService.findIioByOrderId(currentOrderId);
        iiosGrid.setItems(iios);
        iiosGrid.getDataProvider().refreshAll();
    }

    // Init buttons actions: Save, Cancel, AddItem
    private void iioFieldButtonsInit() {

        // Save button
        iioFieldSaveButtonRegistration.remove();
        iioFieldSaveButtonRegistration = iioFieldSaveButton.addClickListener(e ->
        {
                updateIiosList();
                setupIiosGrid();
                Notification.show("Order #" + currentOrderId +" saved",
                        Notification.Type.WARNING_MESSAGE);
                getUI().getNavigator().navigateTo(OrdersView.VIEW_NAME);
        });

        // AddItem button
        iioFieldAddItemButtonRegistration.remove();
        iioFieldAddItemButtonRegistration = iioFieldAddItemButton.addClickListener(e ->
        {
            // Show items list dialog to choose
            List<Item> items = itemService.findAll();
            itemsGrid.setItems(items);
            itemsGrid.getDataProvider().refreshAll();
            itemsGridSelectionListener();       // Grid Selection Listener
            UI.getCurrent().addWindow(itemsWindow);


        });

    }

    // Items Grid selection listener
    private void itemsGridSelectionListener() {

        if(itemsGridSelectionRegistration != null) itemsGridSelectionRegistration.remove();
        itemsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        itemsGridSelectionRegistration = itemsGrid.addItemClickListener(ev ->
        {
            selectedItem = ev.getItem();
            iioToBeAdded.setIioOrderId(currentOrderId);
            iioToBeAdded.setIioItemId(selectedItem.getItemId());
            iioToBeAdded.setIioItemName(selectedItem.getItemName());
            iioToBeAdded.setIioItemPrice(selectedItem.getItemPrice());
            ItemInOrder currentIio = itemInOrderService.findIioByOrderItemId(currentOrderId, selectedItem.getItemId());
            if(currentIio != null) {
                iioToBeAdded.setIioItemCount(currentIio.getIioItemCount() + 1);
                itemInOrderService.update(iioToBeAdded);
            }
            else {
                iioToBeAdded.setIioItemCount(1);
                itemInOrderService.add(iioToBeAdded);
            }

            Notification.show(selectedItem.getItemName()+" added to order #"+currentOrderId,
                    Notification.Type.TRAY_NOTIFICATION);
        });
    }
    
    // Init button action: Delete
    private void iioFieldDeleteButtonUpdate() {
        
        iioFieldDeleteButtonRegistration.remove();
        iioFieldDeleteButtonRegistration = iioFieldDeleteButton.addClickListener(e ->
            ConfirmDialog.show(
                    UI.getCurrent(),
                    "Delete item from order",
                    "Delete item: " + editableIio.getIioItemName() +"?",
                    "Delete",
                    "Cancel",
                    new ConfirmDialog.Listener() {
                @Override
                public void onClose(ConfirmDialog confirmDialog) {
                    if (confirmDialog.isConfirmed()) {
                        itemInOrderService.delete(currentOrderId, editableIio.getIioItemId());
                        updateIiosList();
                        setupIiosGrid();
                    } else { // else?
                        }
            }
        }));

    }

    // init ItemInOrder create/edit subForm
    private void initIioFieldForm() {
        // Setup ItemInOrder Field Layout
        iioFieldLayout = new HorizontalLayout();
        iioFieldLayout.setMargin(false);
        iioFieldLayout.setSpacing(true);

        // Init components
        editableIio = new ItemInOrder();
        newIio = new ItemInOrder();
        iiosToBeAdded = new ArrayList<ItemInOrder>();
        iioToBeAdded = new ItemInOrder();
        itemsWindow = new Window();
        itemsLayout = new VerticalLayout();
        itemsGrid = new Grid<>(Item.class);
        selectedItem = new Item();

        //Init and setup ItemInOrders grid
        iiosGrid.setColumnOrder("iioItemId", "iioItemName", "iioItemPrice", "iioItemCount");
        iiosGrid.removeColumn("iioOrderId");
        iiosGrid.setWidth("900px");
        iiosGrid.getColumn("iioItemId").setExpandRatio(1);
        iiosGrid.getColumn("iioItemName").setExpandRatio(7);
        iiosGrid.getColumn("iioItemPrice").setExpandRatio(2);
        iiosGrid.getColumn("iioItemCount").setExpandRatio(2);

        // Init items window
        itemsGrid.setSizeFull();
        itemsGridSelectionRegistration = () -> {};
        itemsLayout.addComponent(itemsGrid);
        itemsLayout.setSizeFull();
        itemsWindow.setContent(itemsLayout);
        itemsWindow.setClosable(true);
        itemsWindow.setWindowMode(WindowMode.NORMAL);
        itemsWindow.setWidth("60%");
        itemsWindow.setHeight("60%");
        itemsWindow.center();
        itemsWindow.addCloseListener(evc -> {
            updateIiosList();
        });

        // Create buttons: Save, Cancel, AddItem, Delete
        // Implementation of itemFieldOKButton is: itemFieldEditMode()
        iioFieldSaveButton = new Button("Save order & return");
        iioFieldSaveButtonRegistration = () -> {};
        iioFieldSaveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        iioFieldCancelButton = new Button("Cancel");
        iioFieldCancelButtonRegistration = iioFieldCancelButton.addClickListener(e ->
        { getUI().getNavigator().navigateTo(OrdersView.VIEW_NAME); });

        iioFieldAddItemButton = new Button("Add item to order");
        iioFieldAddItemButtonRegistration = () -> {};
        iioFieldAddItemButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        iioFieldDeleteButton = new Button("Delete selected from order");
        iioFieldDeleteButtonRegistration = () -> {};
        iioFieldDeleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        // Adding components to subform: Buttons + alignment
        iioFieldLayout.addComponents(iioFieldSaveButton, iioFieldCancelButton, iioFieldAddItemButton, iioFieldDeleteButton);
        iioFieldLayout.setComponentAlignment(iioFieldSaveButton, Alignment.BOTTOM_LEFT);
        iioFieldLayout.setComponentAlignment(iioFieldCancelButton, Alignment.BOTTOM_LEFT);
        iioFieldLayout.setComponentAlignment(iioFieldAddItemButton, Alignment.BOTTOM_LEFT);
        iioFieldLayout.setComponentAlignment(iioFieldDeleteButton, Alignment.BOTTOM_LEFT);
    }

}