package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.util.StringUtils;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// Renders the repository's Markdown as plain JavaFX nodes. The desktop app used
/// JPro's mdfx, which needs a WebView for code and video; on a phone a small
/// renderer covering what the repository's readmes use (headings, paragraphs,
/// lists, quotes, fenced code, images and links) is enough.
public class CustomMarkdownView extends VBox {

    public CustomMarkdownView() {
        getStyleClass().addAll("markdown-view", "custom-markdown-view");
        // mdfx's view attached its own stylesheet; the rules for headings,
        // links and code are in it.
        getStylesheets().add(CustomMarkdownView.class.getResource("markdown.css").toExternalForm());
        mdString.addListener(it -> render());
        baseURL.addListener(it -> render());
    }

    public CustomMarkdownView(String mdString) {
        this();
        setMdString(mdString);
    }

    private final StringProperty mdString = new SimpleStringProperty(this, "mdString", "");

    public String getMdString() {
        return mdString.get();
    }

    public StringProperty mdStringProperty() {
        return mdString;
    }

    public void setMdString(String mdString) {
        this.mdString.set(mdString);
    }

    private void render() {
        List<Node> nodes = new ArrayList<>();
        String md = getMdString();
        if (md != null) {
            String[] lines = md.replace("\r", "").replace("\t", "    ").split("\n");
            StringBuilder para = new StringBuilder();
            String kind = "markdown-paragraph";
            StringBuilder code = null;
            for (String line : lines) {
                if (line.trim().startsWith("```")) {
                    if (code == null) {
                        flush(para, nodes, kind);
                        code = new StringBuilder();
                    } else {
                        Label label = new Label(code.toString());
                        label.getStyleClass().addAll("markdown-code", "markdown-code-block");
                        label.setWrapText(true);
                        nodes.add(label);
                        code = null;
                    }
                    continue;
                }
                if (code != null) {
                    code.append(code.length() == 0 ? "" : "\n").append(line);
                    continue;
                }
                String t = line.trim();
                boolean indented = line.startsWith("  ");
                if (t.isEmpty()) {
                    flush(para, nodes, kind);
                } else if (t.startsWith("#")) {
                    flush(para, nodes, kind);
                    int level = 0;
                    while (level < t.length() && t.charAt(level) == '#') {
                        level++;
                    }
                    TextFlow flow = inline(t.substring(level).replaceAll("#+$", "").trim());
                    flow.getStyleClass().addAll("markdown-heading", "markdown-heading-" + Math.min(level, 5));
                    nodes.add(flow);
                } else if (t.startsWith("- ") || t.startsWith("* ") || t.startsWith("+ ") || t.matches("\\d+[.)] .*")) {
                    flush(para, nodes, kind);
                    String bullet = Character.isDigit(t.charAt(0)) ? t.substring(0, t.indexOf(' ')) : "\u2022";
                    para.append(indented ? "    " : "").append(bullet).append(' ').append(t.substring(t.indexOf(' ') + 1).trim());
                    kind = "markdown-list-item";
                } else if (t.startsWith(">")) {
                    if (!"markdown-normal-block-quote".equals(kind)) {
                        flush(para, nodes, kind);
                        kind = "markdown-normal-block-quote";
                    }
                    String q = t.substring(1).trim();
                    para.append(para.length() == 0 || q.isEmpty() ? "" : " ").append(q);
                } else if (t.startsWith("|")) {
                    // A table row: the cells side by side, the separator row dropped.
                    flush(para, nodes, kind);
                    if (!t.matches("[|:\\- ]+")) {
                        StringBuilder row = new StringBuilder();
                        for (String cell : t.substring(1).split("\\|")) {
                            if (!cell.trim().isEmpty()) {
                                row.append(row.length() == 0 ? "" : "  \u00b7  ").append(cell.trim());
                            }
                        }
                        para.append(row);
                        flush(para, nodes, "markdown-table-row");
                    }
                } else if (t.matches("</?(div|p|br|hr|table|tr|td|th|center|details|summary)\\b[^>]*>") || t.matches("-{3,}|\\*{3,}|_{3,}")) {
                    // Block HTML wrappers and rules carry no text of their own.
                    flush(para, nodes, kind);
                } else if (t.startsWith("![") && t.endsWith(")") && t.indexOf("](") > 0 && t.indexOf("](") == t.lastIndexOf("](")) {
                    flush(para, nodes, kind);
                    if (isShowImages()) {
                        nodes.add(image(destination(t.substring(t.indexOf("](") + 2, t.length() - 1))));
                    }
                } else if ("markdown-list-item".equals(kind) && para.length() > 0 && indented) {
                    // A list item continued on the next, indented line.
                    para.append(' ').append(t);
                } else {
                    // A quote takes lazy continuation lines; anything else is a paragraph.
                    if (para.length() == 0 || !"markdown-normal-block-quote".equals(kind)) {
                        if (!"markdown-paragraph".equals(kind)) {
                            flush(para, nodes, kind);
                        }
                        kind = "markdown-paragraph";
                    }
                    para.append(para.length() == 0 ? "" : " ").append(t);
                }
            }
            flush(para, nodes, kind);
        }
        getChildren().setAll(nodes);
    }

    private void flush(StringBuilder para, List<Node> nodes, String styleClass) {
        if (para.length() > 0) {
            TextFlow flow = inline(para.toString());
            flow.getStyleClass().add(styleClass);
            nodes.add(flow);
            para.setLength(0);
        }
    }

    /// Splits a paragraph into text runs and links. Emphasis markers, code ticks
    /// and inline HTML tags are dropped, keeping their text; `[text](url)`,
    /// `[![alt](img)](url)` and `<https://...>` become links. Brackets and
    /// parentheses are matched by depth, so a URL holding `(...)` stays whole.
    private TextFlow inline(String s) {
        TextFlow flow = new TextFlow();
        StringBuilder text = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            boolean image = c == '!' && i + 1 < s.length() && s.charAt(i + 1) == '[';
            if (c == '[' || image) {
                int open = image ? i + 1 : i;
                int close = matching(s, open, '[', ']');
                if (close > 0 && close + 1 < s.length() && s.charAt(close + 1) == '(') {
                    int end = matching(s, close + 1, '(', ')');
                    if (end > 0) {
                        String label = s.substring(open + 1, close);
                        String target = destination(s.substring(close + 2, end));
                        addText(flow, text);
                        if (!image) {
                            addLink(flow, linkLabel(label, target), target);
                        }
                        i = end + 1;
                        continue;
                    }
                }
            } else if (c == '<') {
                int end = s.indexOf('>', i);
                if (end > 0) {
                    String tag = s.substring(i + 1, end);
                    if (tag.startsWith("http://") || tag.startsWith("https://")) {
                        addText(flow, text);
                        addLink(flow, tag, tag);
                        i = end + 1;
                        continue;
                    }
                    if (tag.matches("/?[A-Za-z][A-Za-z0-9]*(\\s[^<>]*)?/?")) {
                        // Inline HTML: keep the text around it, drop the tag.
                        i = end + 1;
                        continue;
                    }
                }
            } else if (c == '\\' && i + 1 < s.length()) {
                text.append(s.charAt(i + 1));
                i += 2;
                continue;
            }
            text.append(c);
            i++;
        }
        addText(flow, text);
        return flow;
    }

    /// The text a link shows: its label, an image label's alt text, or the URL.
    private static String linkLabel(String label, String target) {
        String l = label.trim();
        if (l.startsWith("![")) {
            int close = matching(l, 1, '[', ']');
            l = close > 0 ? l.substring(2, close) : "";
        }
        l = strip(l).trim();
        return l.isEmpty() ? target : l;
    }

    /// A link destination without its optional `"title"` and angle brackets.
    private static String destination(String raw) {
        String d = raw.trim();
        int space = d.indexOf(' ');
        if (space > 0) {
            d = d.substring(0, space);
        }
        if (d.startsWith("<") && d.endsWith(">")) {
            d = d.substring(1, d.length() - 1);
        }
        return d;
    }

    /// The index of the bracket that closes the one at `open`, or -1.
    private static int matching(String s, int open, char o, char c) {
        int depth = 0;
        for (int i = open; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\\') {
                i++;
            } else if (ch == o) {
                depth++;
            } else if (ch == c && --depth == 0) {
                return i;
            }
        }
        return -1;
    }

    private void addLink(TextFlow flow, String label, String target) {
        String url = resolve(target);
        for (Text word : words(label)) {
            if (url != null) {
                // An in-page anchor or a scheme we do not open stays plain text.
                word.getStyleClass().add("markdown-link");
                ExternalLinkUtil.setExternalLink(word, url, label);
            }
            flow.getChildren().add(word);
        }
    }

    private static void addText(TextFlow flow, StringBuilder text) {
        if (text.length() > 0) {
            flow.getChildren().addAll(words(strip(text.toString())));
            text.setLength(0);
        }
    }

    /// One `Text` per word, each with the spaces after it. Codename One
    /// 7.0.280's `TextFlow` breaks lines between its children but never inside
    /// one, so a paragraph handed over as a single run ran off the screen. A
    /// link is words like any other, as in mdfx: a `Hyperlink` control would
    /// be a native button with the theme's font and padding.
    private static List<Text> words(String s) {
        List<Text> out = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int j = i;
            while (j < s.length() && s.charAt(j) != ' ') {
                j++;
            }
            while (j < s.length() && s.charAt(j) == ' ') {
                j++;
            }
            Text t = new Text(s.substring(i, j));
            t.getStyleClass().add("markdown-text");
            out.add(t);
            i = j;
        }
        return out;
    }

    /// Drops emphasis and code markers: `**`, `__`, backticks, and a single `*`
    /// or `_` anywhere but between two letters (so `snake_case` keeps its underscore).
    private static String strip(String s) {
        String r = StringUtils.replace(StringUtils.replace(StringUtils.replace(s, "**", ""), "`", ""), "__", "");
        StringBuilder b = new StringBuilder(r.length());
        for (int i = 0; i < r.length(); i++) {
            char ch = r.charAt(i);
            if (ch == '*' || ch == '_') {
                boolean before = i > 0 && Character.isLetterOrDigit(r.charAt(i - 1));
                boolean after = i + 1 < r.length() && Character.isLetterOrDigit(r.charAt(i + 1));
                if (!(before && after)) {
                    continue;
                }
            }
            b.append(ch);
        }
        return b.toString();
    }

    /// The absolute URL a link or image opens, or null when it must not be
    /// opened: in-page anchors, and every scheme but http, https and mailto
    /// (a readme is third-party content, and `CN.execute` would hand a
    /// `file:` or custom-scheme URL straight to the platform).
    private String resolve(String url) {
        if (url == null || url.isEmpty() || url.startsWith("#")) {
            return null;
        }
        int colon = url.indexOf(':');
        int slash = url.indexOf('/');
        if (colon > 0 && (slash < 0 || colon < slash)) {
            String scheme = url.substring(0, colon);
            return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")
                    || scheme.equalsIgnoreCase("mailto") ? url : null;
        }
        String base = getBaseURL();
        if (base == null || base.isEmpty()) {
            return null;
        }
        if (url.startsWith("//")) {
            return "https:" + url;
        }
        if (url.startsWith("/")) {
            int host = base.indexOf("//");
            int path = host < 0 ? -1 : base.indexOf('/', host + 2);
            return (path < 0 ? base : base.substring(0, path)) + url;
        }
        String rel = url.startsWith("./") ? url.substring(2) : url;
        return (base.endsWith("/") ? base : base + "/") + rel;
    }

    private Node image(String url) {
        ImageView imageView = new ImageView();
        String resolved = resolve(url);
        if (resolved == null || resolved.startsWith("mailto:")) {
            return imageView;
        }
        imageView.imageProperty().bind(ImageManager.getInstance().remoteImageProperty(resolved, null));
        imageView.setPreserveRatio(true);
        FitImage fit = new FitImage(imageView);
        fit.setOnMouseClicked(evt -> {
            Consumer<Image> onImageClick = getOnImageClick();
            if (onImageClick != null && imageView.getImage() != null) {
                onImageClick.accept(imageView.getImage());
            }
        });
        return fit;
    }

    /// Shows a picture at the width it is given, keeping its shape, and never
    /// wider than the picture itself: a badge or an icon stays sharp.
    ///
    /// The picture used to take its fit width from this view's width by a
    /// binding. That width is only known once the page lays this view out,
    /// after the page has asked this view its height, so the page placed
    /// the sections below for pictures that had no size yet and they were
    /// drawn over the text. Here the height follows from the width asked
    /// about, so the page gets the right answer the first time.
    private static final class FitImage extends Region {
        private final ImageView view;

        FitImage(ImageView view) {
            this.view = view;
            getChildren().add(view);
            view.imageProperty().addListener(obs -> requestLayout());
        }

        @Override
        public Orientation getContentBias() {
            return Orientation.HORIZONTAL;
        }

        @Override
        protected double computeMinWidth(double height) {
            return 0;
        }

        @Override
        protected double computePrefWidth(double height) {
            Image image = view.getImage();
            return image == null ? 0 : image.getWidth();
        }

        private double shownWidth(double width) {
            Image image = view.getImage();
            double natural = image == null ? 0 : image.getWidth();
            return width > 0 ? Math.min(width, natural) : natural;
        }

        @Override
        protected double computeMinHeight(double width) {
            return computePrefHeight(width);
        }

        @Override
        protected double computePrefHeight(double width) {
            Image image = view.getImage();
            if (image == null || image.getWidth() <= 0) {
                return 0;
            }
            return shownWidth(width) * image.getHeight() / image.getWidth();
        }

        @Override
        protected void layoutChildren() {
            view.setFitWidth(Math.max(1, shownWidth(getWidth())));
            view.relocate(0, 0);
        }
    }

    public void setLink(Node node, String link, String description) {
        if (StringUtils.isNotBlank(link)) {
            ExternalLinkUtil.setExternalLink(node, link, description);
        }
    }

    private final ObjectProperty<Consumer<Image>> onImageClick = new SimpleObjectProperty<>(this, "onImageClick");

    public Consumer<Image> getOnImageClick() {
        return onImageClick.get();
    }

    public ObjectProperty<Consumer<Image>> onImageClickProperty() {
        return onImageClick;
    }

    public void setOnImageClick(Consumer<Image> onImageClick) {
        this.onImageClick.set(onImageClick);
    }

    private final StringProperty baseURL = new SimpleStringProperty(this, "baseURL");

    public String getBaseURL() {
        return baseURL.get();
    }

    public StringProperty baseURLProperty() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL.set(baseURL);
    }

    private final BooleanProperty showImages = new SimpleBooleanProperty(this, "showImages", true);

    public boolean isShowImages() {
        return showImages.get();
    }

    public BooleanProperty showImagesProperty() {
        return showImages;
    }

    public void setShowImages(boolean showImages) {
        this.showImages.set(showImages);
    }
}
