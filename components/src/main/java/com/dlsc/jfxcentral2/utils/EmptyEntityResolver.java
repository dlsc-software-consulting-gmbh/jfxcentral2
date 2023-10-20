package com.dlsc.jfxcentral2.utils;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.StringReader;
/**
 * EmptyEntityResolver is a singleton implementation of the EntityResolver interface.
 * Its main purpose is to prevent the XML parser from trying to access external DTDs or schemas
 * when parsing an XML document. This can be particularly useful to avoid unnecessary network
 * requests or potential blocking issues, such as getting HTTP 429 response codes.
 *
 * By providing an empty InputSource, we essentially tell the XML parser that there are no external
 * entities to resolve, and it should continue parsing without trying to fetch them.
 *
 * Typical use case:
 * When parsing SVG or any XML documents that reference external DTDs or schemas,
 * you can use this resolver to speed up parsing and avoid potential network-related issues.
 *
 */
public enum EmptyEntityResolver implements EntityResolver {
    INSTANCE;

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        return new InputSource(new StringReader(""));
    }
}
