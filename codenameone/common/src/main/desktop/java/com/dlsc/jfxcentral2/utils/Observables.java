package com.dlsc.jfxcentral2.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;

import java.util.function.Function;

/// `ObservableValue.map` (JavaFX 19), which the Codename One JavaFX layer does
/// not have, as a binding.
public final class Observables {

    private Observables() {
    }

    public static <T, R> ObjectBinding<R> map(ObservableValue<T> value, Function<? super T, ? extends R> mapper) {
        return Bindings.createObjectBinding(() -> mapper.apply(value.getValue()), value);
    }
}
