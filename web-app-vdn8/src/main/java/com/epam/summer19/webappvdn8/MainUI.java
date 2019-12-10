package com.epam.summer19.webappvdn8;

import com.epam.summer19.webappvdn8.components.HeaderMenuBar;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;


@SpringUI
@SpringViewDisplay
@Theme("cmthemex")
public class MainUI extends UI implements ViewDisplay {

    private VerticalLayout mainLayout;

    private Panel viewDisplay;
    private HeaderMenuBar headerMenuBar;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeaderMenu();
        addBody();
        setExpandRatios();
    }

    private void setupLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        mainLayout.setSpacing(false);
        mainLayout.setMargin(false);
        mainLayout.setSizeFull();
        setContent(mainLayout);
    }

    private void addHeaderMenu() {
        headerMenuBar = new HeaderMenuBar();
        mainLayout.addComponent(headerMenuBar);
    }

    private void addBody() {
        viewDisplay = new Panel();
        viewDisplay.setSizeFull();
        mainLayout.addComponent(viewDisplay);
    }

    private void setExpandRatios() {
        mainLayout.setExpandRatio(headerMenuBar, 1);
        mainLayout.setExpandRatio(viewDisplay, 15);
    }

    @Override
    public void showView(View view) {
        viewDisplay.setContent((Component) view);
    }

}
