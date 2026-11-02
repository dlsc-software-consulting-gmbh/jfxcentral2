package com.dlsc.jfxcentral2.utils;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.Stack;

public class Navigator<T> {

    private final Stack<T> backStack = new Stack<>();
    private final Stack<T> forwardStack = new Stack<>();

    public Navigator() {
    }

    /**
     * Records a new page visit in the navigation history.
     * <p>
     * <b>Tips: </b>
     * Use this method when navigating to a new page that represents a significant change in the application's view or data.
     * Avoid using it for back or forward navigation actions, or for refresh operations, as it's designed to track progressions
     * to new states rather than movements within the existing navigation history.
     */
    public void visit(T page) {
        if (getCurrent() != null) {
            backStack.push(getCurrent());
            setCanGoBack(true);
        }
        setCurrent(page);
        forwardStack.clear();
        setCanGoForward(false);
    }

    public T goBack() {
        if (isCanGoBack()) {
            forwardStack.push(getCurrent());
            setCurrent(backStack.pop());
            setCanGoForward(true);
            setCanGoBack(!backStack.isEmpty());
        }
        return getCurrent();
    }

    public T goForward() {
        if (isCanGoForward()) {
            backStack.push(getCurrent());
            setCurrent(forwardStack.pop());
            setCanGoBack(true);
            setCanGoForward(!forwardStack.isEmpty());
        }
        return getCurrent();
    }

    private final ReadOnlyObjectWrapper<T> current = new ReadOnlyObjectWrapper<>(this, "current");

    public final ReadOnlyObjectProperty<T> currentProperty() {
        return current.getReadOnlyProperty();
    }

    public final T getCurrent() {
        return current.get();
    }

    private void setCurrent(T current) {
        this.current.set(current);
    }

    private final ReadOnlyBooleanWrapper canGoBack = new ReadOnlyBooleanWrapper(this, "canGoBack");

    public final ReadOnlyBooleanProperty canGoBackProperty() {
        return canGoBack.getReadOnlyProperty();
    }

    public final boolean isCanGoBack() {
        return canGoBack.get();
    }

    private void setCanGoBack(boolean canGoBack) {
        this.canGoBack.set(canGoBack);
    }

    private final ReadOnlyBooleanWrapper canGoForward = new ReadOnlyBooleanWrapper(this, "canGoForward");

    public final ReadOnlyBooleanProperty canGoForwardProperty() {
        return canGoForward.getReadOnlyProperty();
    }

    public final boolean isCanGoForward() {
        return canGoForward.get();
    }

    private void setCanGoForward(boolean canGoForward) {
        this.canGoForward.set(canGoForward);
    }

    public final void reset() {
        backStack.clear();
        forwardStack.clear();
        setCurrent(null);
        setCanGoBack(false);
        setCanGoForward(false);
    }

}
