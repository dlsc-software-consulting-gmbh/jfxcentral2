package com.dlsc.jfxcentral2.devtools.pathextractor;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.attributes.ViewBox;
import com.github.weisj.jsvg.geometry.size.FloatSize;
import com.github.weisj.jsvg.parser.SVGLoader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class SvgToImageUtil {

    public record ImageResult(Image image, int originalWidth, int originalHeight, File file) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImageResult result = (ImageResult) o;
            return Objects.equals(file, result.file);
        }

        @Override
        public int hashCode() {
            return Objects.hash(file);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger(SvgToImageUtil.class);

    private SvgToImageUtil() {
    }

    public static ImageResult parserSVG(String svgFilePath) {
        return parserSVG(new File(svgFilePath));
    }

    public static ImageResult parserSVG(File svgFile) {
        return parserSVG(svgFile, -1, -1);
    }

    /**
     * Parses an SVG file to generate an ImageResult, which contains a WritableImage alongside the dimensions of the image.
     *
     * @param svgFile    The SVG file to be parsed.
     * @param prefWidth  The desired maximum width for the output image. If set to -1 along with prefHeight being -1, the original dimensions of the SVG will be maintained. Any other negative number or zero will throw an IllegalArgumentException.
     * @param prefHeight The desired maximum height for the output image. If set to -1 along with prefWidth being -1, the original dimensions of the SVG will be maintained. Any other negative number or zero will throw an IllegalArgumentException.
     * @return An ImageResult object containing the WritableImage generated from the SVG file and its dimensions, or null if the SVG document could not be loaded successfully.
     * @throws IllegalArgumentException If prefWidth or prefHeight are zero or negative (except -1 to maintain original size).
     */
    public static ImageResult parserSVG(File svgFile, double prefWidth, double prefHeight) {
        if (prefWidth < -1 || prefHeight < -1 || prefWidth == 0 || prefHeight == 0) {
            throw new IllegalArgumentException("Max originalWidth and originalHeight must be greater than 0 or equal to -1 to maintain the original size.");
        }

        if ((prefWidth == -1 && prefHeight != -1) || (prefHeight == -1 && prefWidth != -1)) {
            throw new IllegalArgumentException("Both prefWidth and prefHeight should be -1 to maintain the original size, or both should have a specific value.");
        }

        SVGDocument svgDocument = loadSVGDocument(svgFile);
        if (svgDocument == null) {
            return null;
        }

        FloatSize size = svgDocument.size();
        double width = size.width;
        double height = size.height;

        if (prefWidth != -1) {
            double widthRatio = prefWidth / width;
            double heightRatio = prefHeight / height;
            double maxRatio = Math.max(widthRatio, heightRatio);

            width *= maxRatio;
            height *= maxRatio;
        }

        BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        svgDocument.render(null, g2d, new ViewBox(0, 0, (float) width, (float) height));
        g2d.dispose();

        WritableImage fxImage = SwingFXUtils.toFXImage(image, null);
        return new ImageResult(fxImage, (int) size.width, (int) size.height, svgFile);
    }

    private static SVGDocument loadSVGDocument(File svgFile) {
        URL svgUrl;
        try {
            svgUrl = svgFile.toURI().toURL();
        } catch (MalformedURLException e) {
            LOGGER.error("Error while converting SVG file to URL.", e);
            return null;
        }
        return loadSVGDocument(svgUrl);
    }

    private static SVGDocument loadSVGDocument(InputStream is) {
        SVGLoader loader = new SVGLoader();
        return loader.load(is);
    }

    private static SVGDocument loadSVGDocument(URL url) {
        SVGLoader loader = new SVGLoader();
        return loader.load(url);
    }

    public static Image toImage(InputStream is, double requestedWidth, double requestedHeight, double outputScaleX, double outputScaleY, boolean preserveRatio) {
        SVGDocument svgDocument = loadSVGDocument(is);
        if (svgDocument == null) {
            return null;
        }
        BufferedImage image = renderImage(requestedWidth, requestedHeight, outputScaleX, outputScaleY, preserveRatio, svgDocument);
        return SwingFXUtils.toFXImage(image, null);
    }

    private static BufferedImage renderImage(double requestedWidth, double requestedHeight, double outputScaleX, double outputScaleY, boolean preserveRatio, SVGDocument svgDocument) {
        FloatSize size = svgDocument.size();
        double width = size.width;
        double height = size.height;

        if (preserveRatio) {
            double aspectRatio = width / height;
            if (requestedWidth > 0) {
                requestedHeight = requestedWidth / aspectRatio;
            } else if (requestedHeight > 0) {
                requestedWidth = requestedHeight * aspectRatio;
            }
        }

        width = requestedWidth > 0 ? requestedWidth : width * outputScaleX;
        height = requestedHeight > 0 ? requestedHeight : height * outputScaleY;

        BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        svgDocument.render(null, g2d, new ViewBox(0, 0, (float) width, (float) height));
        g2d.dispose();
        return image;
    }

    public static Image toImage(URL url, double requestedWidth, double requestedHeight, double outputScaleX, double outputScaleY, boolean preserveRatio) {
        SVGDocument svgDocument = loadSVGDocument(url);
        if (svgDocument == null) {
            return null;
        }
        BufferedImage image = renderImage(requestedWidth, requestedHeight, outputScaleX, outputScaleY, preserveRatio, svgDocument);
        return SwingFXUtils.toFXImage(image, null);
    }

    public static Image toImage(String url, double requestedWidth, double requestedHeight, double outputScaleX, double outputScaleY, boolean preserveRatio) {
        try {
            return toImage(new URL(url), requestedWidth, requestedHeight, outputScaleX, outputScaleY, preserveRatio);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image toImage(File svgFile, double requestedWidth, double requestedHeight, double outputScaleX, double outputScaleY, boolean preserveRatio) {
        SVGDocument svgDocument = loadSVGDocument(svgFile);
        if (svgDocument == null) {
            return null;
        }
        BufferedImage image = renderImage(requestedWidth, requestedHeight, outputScaleX, outputScaleY, preserveRatio, svgDocument);
        return SwingFXUtils.toFXImage(image, null);
    }
}
