package com.vaadin.example.navigation;

import java.util.UUID;

import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.core.ActivityManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class VaadinNavigationExampleMainWindow extends NavigableWindow {

    public VaadinNavigationExampleMainWindow(final ActivityManager activityManager) {
        setCaption("Vaadin Activities Addon Example");

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        setContent(rootLayout);

        addComponent(createMenu());
        addComponent(activityManager);
        /* Allocate all available extra space to activity manager */
        rootLayout.setExpandRatio(activityManager, 1);
    }

    private Component createMenu() {
        HorizontalLayout layout = new HorizontalLayout();

        Button welcomeButton = new Button("Welcome");
        welcomeButton.addListener(new Button.ClickListener() {
            public void buttonClick(final ClickEvent event) {
                goTo(new WelcomePlace());

            }
        });

        layout.addComponent(welcomeButton);

        Button productsButton = new Button("Products");
        productsButton.addListener(new Button.ClickListener() {

            public void buttonClick(final ClickEvent event) {
                ProductPlace place = new ProductPlace().withProductId(UUID.randomUUID().toString());
                goTo(place);
            }
        });

        layout.addComponent(productsButton);

        return layout;
    }

}
