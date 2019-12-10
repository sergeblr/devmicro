package com.epam.summer19.webappvdn8;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.List;

@SpringView(name = ItemsView.VIEW_NAME)
public class ItemsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "items";

    @Autowired
    private ItemService itemService;

    // Objects
    private List<Item> items;
    private Item newItem;
    private Item editableItem;
    private Grid<Item> itemsGrid;
    private Binder<Item> itemBinder;

    // Buttons & registrations, textFields, layout for them
    private HorizontalLayout itemFieldLayout;
    private Button addItemButton;
    private Button itemFieldOKButton;
    private Button itemFieldDeleteButton;
    private Button itemFieldCancelButton;
    private Registration itemFieldOKButtonRegistration;
    private Registration itemsGridSelectionRegistration;
    private Registration itemFieldDeleteButtonRegistration;
    private TextField itemFieldName;
    private TextField itemFieldPrice;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
        // This view is constructed in the init() method()
    }

    void init() {
        // Create grid, Add button
        itemsGrid = new Grid<>(Item.class);
        itemBinder = new Binder<>();
        itemsGrid.setSizeFull();
        addItemButton = new Button("Add item", e -> {
            itemFieldCreateMode();
            itemFieldLayout.setVisible(true);
        });

        // Setup grid, header Field Form
        itemsGridSelectionRegistration = () -> {};
        setupItemsGrid();
        initItemFieldForm();
        updateItemsList();

        // Adding components to ItemsView layout
        addComponent(addItemButton);
        addComponent(itemFieldLayout);
        addComponent(itemsGrid);

        // Set ExpandRations and SizeFull
        setExpandRatio(addItemButton, 1);
        setExpandRatio(itemFieldLayout, 2);
        setExpandRatio(itemsGrid, 15);
        setSizeFull();
    }

    // Setup items grid (reInit)
    private void setupItemsGrid() {
        // Grid Select Listener & past to EDIT field
        itemsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        itemsGrid.setColumnOrder("itemId", "itemName", "itemPrice");
        itemsGrid.setWidth("900px");
        itemsGrid.getColumn("itemId").setExpandRatio(1);
        itemsGrid.getColumn("itemName").setExpandRatio(5);
        itemsGrid.getColumn("itemPrice").setExpandRatio(2);
        
        itemsGridSelectionRegistration.remove();
        itemsGridSelectionRegistration = itemsGrid.addSelectionListener(e ->
        {
            if(e.getFirstSelectedItem().isPresent()) {
                editableItem = e.getFirstSelectedItem().get();
                itemFieldEditMode();
            }
            itemFieldLayout.setVisible(true);
        });
    }

    // Update items grid
    private void updateItemsList() {
        items = itemService.findAll();
        itemsGrid.setItems(items);
        itemsGrid.getDataProvider().refreshAll();
    }

    // Item subForm -> creating (newItem) mode
    private void itemFieldCreateMode() {
        itemFieldOKButton.setCaption("Create new item");
        itemFieldDeleteButton.setVisible(false);
        itemBinder.readBean(null);
        editableItem = null;

        itemFieldOKButtonRegistration.remove();
        itemFieldOKButtonRegistration = itemFieldOKButton.addClickListener(e ->
        {
            try {
                itemBinder.writeBean(newItem);
                itemService.add(newItem);
                itemFieldLayout.setVisible(false);
                updateItemsList();
                setupItemsGrid();
                Notification.show("Item "+newItem.getItemName()+" succsessfully created.", Notification.Type.TRAY_NOTIFICATION);
            }
            catch (ValidationException exc) {
                Notification.show("Item could not be saved, check errors.");
            }
        });
    }

    // Item subForm -> editing mode
    private void itemFieldEditMode() {
        itemFieldOKButton.setCaption("Confirm edited");
        itemFieldDeleteButton.setVisible(true);
        itemBinder.readBean(editableItem);

        itemFieldOKButtonRegistration.remove();
        itemFieldOKButtonRegistration = itemFieldOKButton.addClickListener(e ->
        {
            try {
                itemBinder.writeBean(editableItem);
                itemService.update(editableItem);
                itemFieldLayout.setVisible(false);
                setupItemsGrid();
                updateItemsList();
                Notification.show("Item #"+editableItem.getItemId()+" succsessfully changed.", Notification.Type.TRAY_NOTIFICATION);
            }
            catch (ValidationException exc) {
                Notification.show("Item could not be saved, check errors.");
            }
        });

        itemFieldDeleteButtonRegistration.remove();
        itemFieldDeleteButtonRegistration = itemFieldDeleteButton.addClickListener(e ->
            ConfirmDialog.show(
                    UI.getCurrent(),
                    "Delete confirmation",
                    "Delete item: " + editableItem.getItemName() +"?",
                    "Delete",
                    "Cancel",
                    new ConfirmDialog.Listener() {
                @Override
                public void onClose(ConfirmDialog confirmDialog) {
                    if (confirmDialog.isConfirmed()) {
                        itemService.delete(editableItem.getItemId());
                        updateItemsList();
                        setupItemsGrid();
                        Notification.show(editableItem.getItemName()+" deleted",
                                Notification.Type.WARNING_MESSAGE);
                    } else { // else?
                        }
            }
        }));

    }

    // Init/reinit binder for new/editable item
    private void itemBinder() {
        itemBinder.forField(itemFieldName)
                .withNullRepresentation("")
                .withValidator(str -> str.length() > 1, "Length must be more than 0")
                .withValidator(str ->
                                itemService.findItemByName(str.toString()) == null ||
                                editableItem.getItemId() == itemService.findItemByName(str.toString()).getItemId(),
                        "Item already in DB")
                .bind(Item::getItemName, Item::setItemName);

        itemBinder.forField(itemFieldPrice)
                .withNullRepresentation("")
                .withValidator(str -> str.length() > 1, "Please fill price field")
                .withConverter(new StringToBigDecimalConverter("Price must be in 0,00 format"))
                .bind(Item::getItemPrice, Item::setItemPrice);
    }


    // init Item create/edit subForm
    private void initItemFieldForm() {
        // Setup Item Field Layout
        itemFieldLayout = new HorizontalLayout();
        itemFieldLayout.setMargin(false);
        itemFieldLayout.setSpacing(true);
        itemFieldLayout.setVisible(false);

        //Show Item Name & price fields
        itemFieldName = new TextField();
        itemFieldName.setCaption("Item Name:");
        itemFieldName.setPlaceholder("Item name");

        itemFieldPrice = new TextField();
        itemFieldPrice.setCaption("Item Price:");
        itemFieldPrice.setPlaceholder("0,00");

        // Binders (init item for binder, prepare for read/write + !Validators! Vaadin 8)
        editableItem = new Item();
        newItem = new Item();
        itemBinder();

        // Create new item button & Cancel button & Delete button
        // Implementation of itemFieldOKButton is: itemFieldEditMode() & itemFieldCreateMode()
        itemFieldOKButton = new Button();
        itemFieldOKButtonRegistration = () -> {};
        itemFieldOKButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        itemFieldDeleteButton = new Button("Delete");
        itemFieldDeleteButtonRegistration = () -> {};
        itemFieldDeleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        itemFieldCancelButton = new Button("Cancel", e -> {
            itemFieldLayout.setVisible(false);
        });

        // Adding components to subform: new Item Form + alignment
        itemFieldLayout.addComponents(itemFieldName, itemFieldPrice, itemFieldOKButton, itemFieldDeleteButton, itemFieldCancelButton);
        itemFieldLayout.setComponentAlignment(itemFieldOKButton, Alignment.BOTTOM_LEFT);
        itemFieldLayout.setComponentAlignment(itemFieldDeleteButton, Alignment.BOTTOM_LEFT);
        itemFieldLayout.setComponentAlignment(itemFieldCancelButton, Alignment.BOTTOM_LEFT);
    }


}