package com.dlsc.jfxcentral2.utils;

import java.util.function.Supplier;

/**
 * A supplier that caches the value returned by the delegate supplier.
 */
public class CachingSupplier<T> implements Supplier<T> {

    private final Supplier<T> delegate;
    private boolean valueCached;
    private T cachedValue;

    public CachingSupplier(Supplier<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T get() {
        if (valueCached) {
            return cachedValue;
        }
        cachedValue = delegate.get();
        valueCached = true;
        return cachedValue;
    }

    public void invalidateCache() {
        valueCached = false;
    }
}

