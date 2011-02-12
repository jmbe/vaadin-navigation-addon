package com.vaadin.addon.navigation.activity.stoppable;

import com.vaadin.ui.Component;

/**
 * Expected to be a Window component (no interface for that).
 */
public interface NavigationConfirmationDialog extends Component {

    void setConfirmationDialogHandler(ConfirmationDialogHandler handler);
}
