package com.vaadin.example.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.addon.navigation.activity.Activity;
import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.place.Place;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class ProductView extends Label implements Activity {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(ProductView.class);

    @Override
    public void init(final NavigableWindow window) {
        setCaption("");
    }

    @Override
    public void navigateTo(final Place place) {
        log.info("Navigating to products");

        if (place instanceof ProductPlace) {
            ProductPlace productPlace = (ProductPlace) place;
            log.info("Showing product {}.", productPlace.getProductId());
            setCaption("Info about product " + productPlace.getProductId());
        }
    }

    @Override
    public Component getView() {
        return this;
    }
}
