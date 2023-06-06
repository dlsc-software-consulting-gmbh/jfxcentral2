package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.components.DownloadsBox;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HelloDownloadsBox extends JFXCentralSampleBase {

    private DownloadsBox downloadsBox;

    @Override
    protected Region createControl() {
        Download download = createDownload();
        downloadsBox = new DownloadsBox(download);
        return new StackPane(downloadsBox);
    }

    private Download createDownload() {
        Download download = new Download();
        download.setName("CalendarFX");
        download.setFiles(List.of(
                createDownloadFile(Download.FileType.EXE),
                createDownloadFile(Download.FileType.DMG),
                createDownloadFile(Download.FileType.JAR)
        ));
        return download;
    }

    private Download.DownloadFile createDownloadFile(Download.FileType fileType) {
        Download.DownloadFile downloadFile = new Download.DownloadFile();
        downloadFile.setFileType(fileType);
        downloadFile.setUrl("url ...");
        return downloadFile;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        downloadsBox.sizeProperty().bind(sizeComboBox.valueProperty());

        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "DownloadsBox";
    }
}
