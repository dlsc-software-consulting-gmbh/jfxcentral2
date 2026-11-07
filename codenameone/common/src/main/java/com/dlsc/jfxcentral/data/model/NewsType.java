package com.dlsc.jfxcentral.data.model;

// Top level rather than nested in News: the Codename One mapper generator
// names a nested type by its binary name (Outer$Inner), which does not compile.
public enum NewsType {
    RELEASE,
    LINKS,
    EVENT,
    TIPS,
    NOTEWORTHY,
    MISC
}
