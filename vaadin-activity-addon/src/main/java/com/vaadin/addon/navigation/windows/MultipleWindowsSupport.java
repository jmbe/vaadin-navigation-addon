package com.vaadin.addon.navigation.windows;

import com.vaadin.Application;
import com.vaadin.addon.navigation.api.NavigableApplication;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Window;

/**
 * Straight from Navigator addon.
 */
public class MultipleWindowsSupport {

    /**
     * Helper for overriding Application.getWindow(String).
     * 
     * <p>
     * This helper makes implementing support for multiple browser tabs or browser windows easy. Just override
     * Application.getWindow(String) in your application like this:
     * </p>
     * 
     * <pre>
     * &#064;Override
     * public Window getWindow(String name) {
     *     return Navigator.getWindow(this, name, super.getWindow(name));
     * }
     * </pre>
     * 
     * @param application
     *            Application instance, which implements Navigator.NavigableApplication interface.
     * @param name
     *            Name parameter from Application.getWindow(String name)
     * @param superGetWindow
     *            The window returned by super.getWindow(name)
     * @return
     */
    public static Window getWindow(final NavigableApplication application, final String name,
            final Window superGetWindow) {

        if (superGetWindow != null) {
            return superGetWindow;
        }

        Window w = application.createNewWindow();
        w.setName(name);
        ((Application) application).addWindow(w);
        w.open(new ExternalResource(w.getURL()));
        return w;
    }
}
