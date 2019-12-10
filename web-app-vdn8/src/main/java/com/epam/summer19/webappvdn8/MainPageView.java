package com.epam.summer19.webappvdn8;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import javax.swing.text.html.HTML;

@SpringView(name = MainPageView.VIEW_NAME)
public class MainPageView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "";

    private Label mainPageHeader;
    private Label mainPageDescription;

    @PostConstruct
    void init() {
        // Header & descr text
        mainPageHeader = new Label("Welcome to CafeMenu Web application.");
        mainPageDescription = new Label(
                "<h5><u>Cafemenu</u> - application for cafe employees to control of making orders (adding/editing items)," +
                        " filter them by date&time, editing and summarize by items price inside & item quantity.</h5>", ContentMode.HTML);

        addComponents(mainPageHeader, mainPageDescription);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}