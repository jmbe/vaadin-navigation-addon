package com.vaadin.addon.navigation.activity.stoppable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DefaultConfirmationDialog extends Window implements NavigationConfirmationDialog {

    private Button cancelButton;
    private Button continueButton;

    public DefaultConfirmationDialog(final String warn) {

        setCaption("Warning");
        VerticalLayout lo = new VerticalLayout();
        setContent(lo);

        lo.setMargin(true);
        lo.setSpacing(true);
        lo.setWidth("400px");

        setModal(true);

        lo.addComponent(new Label(warn));
        lo.addComponent(new Label("If you do not want to navigate away from the current screen, press Cancel."));

        this.cancelButton = new Button("Cancel");
        this.continueButton = new Button("Continue");
        HorizontalLayout h = new HorizontalLayout();
        h.addComponent(cancelButton);
        h.addComponent(continueButton);
        h.setSpacing(true);
        lo.addComponent(h);
        lo.setComponentAlignment(h, "r");

    }

    @Override
    public void setConfirmationDialogHandler(final ConfirmationDialogHandler handler) {
        this.cancelButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                handler.onCancel();
            }
        });

        this.continueButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                handler.onContinue();
            }
        });
    }

}
