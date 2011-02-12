package com.vaadin.example.navigation;

import com.google.common.base.Strings;
import com.vaadin.addon.navigation.api.UriState;
import com.vaadin.addon.navigation.place.Place;

public class ProductPlace implements Place {

    private String productId;

    public ProductPlace withProductId(final String productId) {
        this.productId = Strings.nullToEmpty(productId);
        return this;
    }

    @Override
    public UriState serializeState() {
        if (Strings.isNullOrEmpty(this.productId)) {
            return new UriState();
        }

        return new UriState(productId);
    }

    @Override
    public void bind(final UriState state) {
        if (state.isEmpty()) {
            return;
        }

        this.productId = state.getState();
    }

    public String getProductId() {
        return this.productId;
    }

}
