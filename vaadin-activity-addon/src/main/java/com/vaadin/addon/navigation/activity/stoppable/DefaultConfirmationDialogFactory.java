package com.vaadin.addon.navigation.activity.stoppable;


public class DefaultConfirmationDialogFactory implements NavigationConfirmationDialogFactory {

    @Override
    public NavigationConfirmationDialog create(final String warn) {
        return new DefaultConfirmationDialog(warn);
    }

}
