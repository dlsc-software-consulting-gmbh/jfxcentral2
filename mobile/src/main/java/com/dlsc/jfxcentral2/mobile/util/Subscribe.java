package com.dlsc.jfxcentral2.mobile.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as an event handler.
 * Methods annotated with @Subscribe will be registered with the EventBusFX
 * and will handle events of the specified type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
}
