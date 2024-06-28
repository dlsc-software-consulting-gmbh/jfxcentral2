package com.dlsc.jfxcentral2.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the execution thread for event handling methods.
 * This annotation is used in conjunction with the @Subscribe annotation
 * to define on which thread the event handler should be executed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunOn {

    /**
     * The execution thread on which the annotated method should be run.
     * Defaults to {@link ThreadType#POSTING}.
     *
     * @return the execution thread
     */
    ThreadType value() default ThreadType.POSTING;
}
