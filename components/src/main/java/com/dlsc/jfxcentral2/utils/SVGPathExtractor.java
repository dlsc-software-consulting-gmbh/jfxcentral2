package com.dlsc.jfxcentral2.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * A utility tool used to extract paths from SVG files.
 */
public class SVGPathExtractor {
    private static final Logger LOGGER = LogManager.getLogger(SVGPathExtractor.class);

    private SVGPathExtractor() {
    }

    public static String extractPaths(File svgFile) {
        return extractPaths(svgFile.getAbsolutePath());
    }

    public static String extractPaths(String svgFilePath) {
        if (!new File(svgFilePath).exists()) {
            LOGGER.error("SVG file {} does not exist.", svgFilePath);
            return "";
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // Disabling DTD validation can speed up XML parsing but might overlook some errors in the XML document
        dbFactory.setValidating(false);

        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error("Error while creating DocumentBuilder {}.",svgFilePath, e);
            return  "";
        }

        Document doc;
        try {
            doc = dBuilder.parse(new File(svgFilePath));
        } catch (SAXException | IOException e) {
            LOGGER.error("Error while parsing SVG file {}", svgFilePath, e);
            return "";
        }
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("path");

        if (nodeList.getLength() == 0) {
            LOGGER.warn("No paths found in SVG file {}", svgFilePath);
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                result.append(element.getAttribute("d").trim());
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

}
