package mg.laiso.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import mg.laiso.MainApp;

import java.io.File;

/**
 * Created by Laiso on 04/10/2016.
 */
public class RootController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void nouveau(){
        mainApp.getEtudiants().clear();
        mainApp.setEtudiantFilePath(null);
    }

    @FXML
    private void ouvrir(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml"
        );

        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if(file != null){
            mainApp.loadDataFromFile(file);
        }
    }

    @FXML
    private void enregistrer(){
        File file = mainApp.getEtudiantFilePath();
        if(file != null)
            mainApp.saveDataToFile(file);
        else
            enregistrerSous();
    }

    @FXML
    private void enregistrerSous(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml"
        );

        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if(file != null) {
            if (!file.getName().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveDataToFile(file);
        }
    }

    @FXML
    private void quitter(){
        System.exit(0);
    }

    @FXML
    private void aPropos(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gestion d'étudiant");
        alert.setHeaderText("A propos de l'application");
        alert.setContentText("Projet JavaFX - Socket - M1 Hybride 2016 \n Auteur: Laiso - Fehizoro - Christine");

        alert.showAndWait();
    }

    @FXML
    private void parametres(){
        System.out.println("Paramètres");
        mainApp.showParametres();
    }

    @FXML
    private void synchroniser(){
        System.out.println("synchronisation");
    }
}
