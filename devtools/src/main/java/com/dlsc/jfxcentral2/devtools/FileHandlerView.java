package com.dlsc.jfxcentral2.devtools;

import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.logging.log4j.core.util.FileUtils;
import org.kordamp.ikonli.icomoon.Icomoon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileHandlerView extends StackPane {
    private WebAPI.FileUploader fileHandler;
    private ProgressIndicator progressIndicator;
    private final boolean useProgressIndicator;
    private final boolean isMultipleFiles;
    private final boolean rememberLastDirectory;
    private final SimpleBooleanProperty progressingProperty = new SimpleBooleanProperty(this, "progressVisible");
    public FileHandlerView() {
        this(true, false,true);
    }

    public FileHandlerView(boolean useProgressIndicator, boolean isMultipleFiles , boolean rememberLastDirectory) {
        getStyleClass().add("file-handler-view");

        this.useProgressIndicator = useProgressIndicator;
        this.isMultipleFiles = isMultipleFiles;
        this.rememberLastDirectory = rememberLastDirectory;

        // innerLabel to show text and graphic
        Label innerTipsLabel = new Label();
        innerTipsLabel.getStyleClass().add("inner-label");
        innerTipsLabel.textProperty().bind(textProperty());
        innerTipsLabel.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            if (WebAPI.isBrowser()) {
                return progressingProperty.get() ? progressIndicator : getGraphic();
            }
            return getGraphic();
        }, graphicProperty(), progressingProperty));
        innerTipsLabel.setWrapText(true);
        innerTipsLabel.managedProperty().bind(innerTipsLabel.visibleProperty());
        innerTipsLabel.visibleProperty().bind(showTipsProperty());

        getChildren().add(innerTipsLabel);

        if (WebAPI.isBrowser()) {
            initFileUploader(isMultipleFiles);
        } else {
            initFileChoose(isMultipleFiles);
        }

    }

    /**
     * For desktop only
     */
    private void initFileChoose(boolean multipleFiles) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        // convert supportedExtensions to FileChooser.ExtensionFilter
        supportedExtensions.addListener((ListChangeListener.Change<? extends String> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (String addedItem : c.getAddedSubList()) {
                        if (addedItem.startsWith(".")) {
                            addedItem = addedItem.substring(1);
                        }
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(addedItem, "*." + addedItem));
                    }
                }
                if (c.wasRemoved()) {
                    for (String removedItem : c.getRemoved()) {
                        fileChooser.getExtensionFilters().removeIf(filter -> filter.getDescription().equals(removedItem));
                    }
                }
            }
        });

        setOnMousePressed(event -> {
            Window window = getScene().getWindow();
            File lastDirectory = getLastDirectory();
            if (rememberLastDirectory && lastDirectory != null) {
                if (this.lastDirectory != null) {
                    fileChooser.setInitialDirectory(lastDirectory);
                }
            }
            if (multipleFiles) {
                List<File> files = fileChooser.showOpenMultipleDialog(window);
                onUploadFileHandler(files);
            } else {
                File file = fileChooser.showOpenDialog(window);
                if (file != null) {
                    onUploadFileHandler(List.of(file));
                }
            }
        });

        setOnDragEntered(evt -> getStyleClass().add("active"));
        setOnDragExited(evt -> getStyleClass().remove("active"));

        setOnDragOver(evt -> {
            if (evt.getDragboard().hasFiles()) {
                boolean hasSupportedExtension = evt.getDragboard().getFiles().stream()
                        .anyMatch(file -> getSupportedExtensions().stream()
                                .anyMatch(extension -> file.getName().endsWith(extension)));
                if (hasSupportedExtension) {
                    evt.acceptTransferModes(TransferMode.ANY);
                }
            }
        });


        setOnDragDropped(evt -> {
            if (evt.getDragboard().hasFiles()) {
                if (isMultipleFiles) {
                    List<File> supportedFiles = evt.getDragboard().getFiles().stream()
                            .filter(file -> getSupportedExtensions().stream()
                                    .anyMatch(extension -> file.getName().endsWith(extension)))
                            .collect(Collectors.toList());
                    onUploadFileHandler(supportedFiles);
                }else {
                    evt.getDragboard().getFiles().stream()
                            .filter(file -> getSupportedExtensions().stream()
                                    .anyMatch(extension -> file.getName().endsWith(extension)))
                            .findFirst().ifPresent(supportedFile -> onUploadFileHandler(List.of(supportedFile)));
                }
            }
        });

    }

    /**
     * For browser only
     */
    private void initFileUploader(boolean multipleFiles) {
        fileHandler = multipleFiles ? WebAPI.makeMultiFileUploadNodeStatic(this) : WebAPI.makeFileUploadNodeStatic(this);
        Bindings.bindContent(fileHandler.supportedExtensions(), getSupportedExtensions());
        fileHandler.setSelectFileOnClick(true);
        fileHandler.setSelectFileOnDrop(true);

        fileHandler.fileDragOverProperty().addListener((ob, ov, nv) -> {
            if (nv) {
                getStyleClass().add("active");
            } else {
                getStyleClass().remove("active");
            }
        });

        // File filtering for drag-and-drop uploads is possible, filtering out unsupported file types.
        fileHandler.setOnFileSelected((file) -> {
            if (file == null) {
                return;
            }
            boolean isValid = supportedExtensions.stream().anyMatch(file::endsWith);
            if (isValid) {
                FileUtils.getFileExtension(new File(file));
                updateProgressDisplay();
                fileHandler.uploadFile();
            }
        });

        fileHandler.uploadedFileProperty().addListener((ob, ov, file) -> {
            if (file != null ) {
                boolean isValid = supportedExtensions.stream()
                        .anyMatch(extension -> file.getName().endsWith(extension));

                if (isValid) {
                    onUploadFileHandler(List.of(file));
                }
            }
        });

        fileHandler.progressProperty().addListener((obs, oldV, newV) -> {
            updateProgressDisplay();
        });
    }

    /**
     * protected: maybe override to customize progress display
     */
    protected void updateProgressDisplay() {
        progressingProperty.set(fileHandler.getProgress() < 1.0);
        double progress = fileHandler.getProgress();
        setText((int) (progress * 100) + "%");
        if (useProgressIndicator) {
            if (progressIndicator == null) {
                progressIndicator = new ProgressIndicator();
            }
            progressIndicator.setProgress(progress);
        }
    }

    private void onUploadFileHandler(List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        if (rememberLastDirectory) {
            setLastDirectory(files.get(0).getParentFile());
        }
        Consumer<File> fileConsumer = getOnUploadedFile();
        if (fileConsumer != null) {
            files.forEach(fileConsumer);
        }
    }

    private final ObjectProperty<Consumer<File>> onUploadedFile = new SimpleObjectProperty<>(this, "onUploadedFile");

    public Consumer<File> getOnUploadedFile() {
        return onUploadedFile.get();
    }

    public ObjectProperty<Consumer<File>> onUploadedFileProperty() {
        return onUploadedFile;
    }

    public void setOnUploadedFile(Consumer<File> onUploadedFile) {
        this.onUploadedFile.set(onUploadedFile);
    }

    /**
     * Each entry should begin with char '.' such as: '.jpg' , '.svg'
     */
    private final ObservableList<String> supportedExtensions = FXCollections.observableArrayList();

    public ObservableList<String> getSupportedExtensions() {
        return supportedExtensions;
    }

    private final StringProperty text = new SimpleStringProperty(this, "text", "Click or Drop File");

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic", new FontIcon(Icomoon.ICM_UPLOAD));

    public Node getGraphic() {
        return graphic.get();
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic.set(graphic);
    }

    private final BooleanProperty showTips = new SimpleBooleanProperty(this, "showTips", true);

    public boolean isShowTips() {
        return showTips.get();
    }

    public BooleanProperty showTipsProperty() {
        return showTips;
    }

    public void setShowTips(boolean showTips) {
        this.showTips.set(showTips);
    }

    private final ReadOnlyObjectWrapper<File> lastDirectory = new ReadOnlyObjectWrapper<>(this, "lastDirectory");

    public File getLastDirectory() {
        return lastDirectory.get();
    }

    public ReadOnlyObjectProperty<File> lastDirectoryProperty() {
        return lastDirectory.getReadOnlyProperty();
    }

    private void setLastDirectory(File lastDirectory) {
        this.lastDirectory.set(lastDirectory);
    }
}
