package com.vaadin.addon.navigation.api;

/**
 * From Navigator addon.
 */
public interface NavigableApplication {

    /**
     * Create a new browser window.
     * 
     * This method must construct a new window that could be used as a main window for the application. Each call to
     * this method must create a new instance and your application should work when there are multiple instances of
     * concurrently. Each window can contain anything you like, but at least they should contain a new Navigator
     * instance for controlling navigation within the window. Typically one also adds some kind of menu for commanding
     * navigator.
     * 
     * @return New window.
     */
    public NavigableWindow createNewWindow();

}
