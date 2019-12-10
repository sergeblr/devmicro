package com.epam.summer19.webappvdn8.components;

import com.epam.summer19.webappvdn8.ItemsView;
import com.epam.summer19.webappvdn8.OrdersView;
import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

@SpringView
public class HeaderMenuBar extends HorizontalLayout implements View {

    private final Label leftSpace;
    private final Image cmLogo;
    private final Label cmName;
    private final MenuBar menuBar;

    public HeaderMenuBar() {

        // Space left from logo
        leftSpace = new Label(" ");

        // Logo
        cmLogo = new Image("CM", new ThemeResource("images/cmlogo.png"));
        cmLogo.setWidth("32px");
        cmLogo.setHeight("26px");

        // Name
        cmName = new Label("Cafe Menu");
        cmName.setStyleName(ValoTheme.LABEL_BOLD);
        //logoName.setStyleName();

        // MenuBar
        menuBar = new MenuBar();
        //menuBar.addThemeVariants(MenuBarVariant.MATERIAL_OUTLINED);
        menuBar.addItem("Main", e -> getUI().getNavigator().navigateTo(""));
        menuBar.addItem("Orders", e -> getUI().getNavigator().navigateTo(OrdersView.VIEW_NAME));
        menuBar.addItem("Items", e -> getUI().getNavigator().navigateTo(ItemsView.VIEW_NAME));
        //menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        //menuBar.setWidth("350px");

        // -->> Adding all to layout + alignment
        addComponents(leftSpace, cmLogo, cmName, menuBar);
        setComponentAlignment(menuBar, Alignment.MIDDLE_LEFT);
        setComponentAlignment(cmLogo, Alignment.MIDDLE_CENTER);
        setComponentAlignment(cmName, Alignment.MIDDLE_CENTER);
    }

}