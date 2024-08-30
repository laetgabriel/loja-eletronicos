package org.acgprojeto.util;

import javafx.stage.FileChooser;

public class FileChooserUtil {

    public static FileChooser gerarFileChooser(String nomeDoArquivo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(nomeDoArquivo);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }
}
