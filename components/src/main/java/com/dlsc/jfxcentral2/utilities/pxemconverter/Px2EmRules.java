package com.dlsc.jfxcentral2.utilities.pxemconverter;

import java.util.List;

/**
 * convertibleProps: properties that can be converted from px to em;
 * ignoredProps: properties that should be ignored when converting from px to em.
 */
public record Px2EmRules(List<String> convertibleProps, List<String> ignoredProps) {
}
