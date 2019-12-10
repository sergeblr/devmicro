package com.epam.summer19.webappvdn8;

import com.epam.summer19.service.ItemInOrderService;
import com.epam.summer19.service.ItemService;
import com.epam.summer19.service.OrderService;
import com.epam.summer19.webappvdn8.components.HeaderMenuBar;
import com.github.mvysny.kaributesting.v8.MockVaadin;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.mvysny.kaributesting.v8.LocatorJ._get;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = WebAppVdn8ApplicationTests.class)
class WebAppVdn8ApplicationTests {

   /** @Autowired
    private ItemService itemService;

    @Autowired
    private ItemInOrderService itemInOrderService;

    @Autowired
    private OrderService orderService;
**/
    @BeforeEach
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {
        MockVaadin.tearDown();
    }

    @Test
    public void mainUITest() {
        MockVaadin.setup(MainUI::new);

        final VerticalLayout layout = (VerticalLayout) UI.getCurrent().getContent();

        assertEquals(2, layout.getComponentCount());
        //assertEquals("Cafe Menu", _get(Label.class, spec -> spec.withCaption("Cafe Menu")).getValue());
    }

    @Test
    public void headerMenuBarTest() {
        MockVaadin.setup(MainUI::new);

        final VerticalLayout layout = (VerticalLayout) UI.getCurrent().getContent();
        final HorizontalLayout headerMenuBarLayout = (HorizontalLayout) layout.getComponent(1).getUI().getContent();

        assertEquals("Cafe Menu", _get(Label.class, spec -> spec.withCaption("Cafe Menu")));
    }

}
