package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for CachingSupplier. CachingSupplier is a utility class that caches the value returned by the delegate supplier.
 * The cache can be invalidated using the invalidateCache method.
 */
public class CachingSupplierTest {

    @Test
    public void testGet() {
        AtomicInteger atomicInteger = new AtomicInteger();
        CachingSupplier<Integer> cachingSupplier = new CachingSupplier<>(atomicInteger::incrementAndGet);
        
        // Test that the supplier is caching the value
        int firstValue = cachingSupplier.get();
        assertEquals(1, firstValue);
        int secondValue = cachingSupplier.get();
        assertEquals(firstValue, secondValue);

        // Test invalidating the cache
        cachingSupplier.invalidateCache();
        int thirdValue = cachingSupplier.get();
        assertEquals(2, thirdValue);
    }
}