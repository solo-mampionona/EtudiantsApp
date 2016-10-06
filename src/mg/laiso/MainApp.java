package mg.laiso;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import mg.laiso.controller.EtudiantController;
import mg.laiso.controller.EtudiantFormDialogController;
import mg.laiso.controller.ParametresController;
import mg.laiso.controller.RootController;
import mg.laiso.model.Etudiant;
import mg.laiso.model.EtudiantListWrapper;
import mg.laiso.view.ConfirmBox;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by Laiso on 04/10/2016.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private String adresseIP;
    private int port;

    private ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();

    public MainApp() {
        /*etudiants.add(new Etudiant("H001", "Razafindradimy", "Solomampionona Dieudonné", "Tanambao", 44000.00));
        etudiants.add(new Etudiant("H002", "Ambinintsoa", "Fanantenana", "Soatsihadino", 44000.00));
        etudiants.add(new Etudiant("H003", "Randrianarisaona", "Mahery Haja", "Ivory Atsimo", 44000.00));
        etudiants.add(new Etudiant("H004", "Rapatsalahy", "Miary Andrianjaka", "Antarandolo", 44000.00));
        etudiants.add(new Etudiant("H005", "Randriatahirison", "Valisoa", "Ivory", 44000.00));
        etudiants.add(new Etudiant("H006", "Rakotonirainy", "Lalamampionona Joelisolo", "Tanambao", 44000.00));*/
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestion d'étudiant");

        this.primaryStage.setOnCloseRequest(e -> {
            closeProgram(); e.consume();
        });

        initRootLayout();

        showEtudiant();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Root.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Cannot load Root FXML resources");
        }

        File file = getEtudiantFilePath();
        if (file != null)
            loadDataFromFile(file);
    }

    public void showEtudiant() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Etudiant.fxml"));
            AnchorPane etudiantOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(etudiantOverview);

            EtudiantController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            System.err.println("Cannot load Etudiant FXML resources");
        }
    }

    public boolean showEtudiantEditDialog(Etudiant etudiant) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EtudiantFormDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier l'étudiant");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EtudiantFormDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEtudiant(etudiant);

            dialogStage.showAndWait();
            return controller.isValiderClicked();
        } catch (IOException e) {
            System.err.println("Cannot load EtudiantFormDialog FXML resource");
            return false;
        }
    }

    public void showParametres(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Parametres.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            ParametresController controller = (ParametresController) loader.getController();
            controller.setMainApp(this);

            Stage parametreStage = new Stage();
            parametreStage.setTitle("Paramètres");
            parametreStage.initModality(Modality.APPLICATION_MODAL);
            parametreStage.initOwner(primaryStage);

            controller.setParametreStage(parametreStage);

            Scene scene = new Scene(page);
            parametreStage.setScene(scene);

            parametreStage.setResizable(false);

            parametreStage.showAndWait();
        }catch (IOException e){
            System.err.println("Cannot load Parametres FXML resource");
        }
    }

    public File getEtudiantFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null)
            return new File(filePath);
        return null;
    }

    public void setEtudiantFilePath(File file) {
        Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            preferences.put("filePath", file.getPath());

            primaryStage.setTitle("Gestion d'étudiant - " + file.getName());
        } else {
            preferences.remove("filePath");

            primaryStage.setTitle("Gestion d'étudiants");
        }
    }

    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(EtudiantListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            EtudiantListWrapper wrapper = (EtudiantListWrapper) um.unmarshal(file);
            etudiants.clear();
            etudiants.addAll(wrapper.getEtudiants());

            // Registre
            setEtudiantFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement des données");
            alert.setContentText("Impossible de charger les données à partir du fichier " + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(EtudiantListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            EtudiantListWrapper wrapper = new EtudiantListWrapper();
            wrapper.setEtudiants(etudiants);

            m.marshal(wrapper, file);

            setEtudiantFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'éctirure des données");
            alert.setContentText("Impossible d'écrire les données sur le fichier " + file.getPath());

            alert.showAndWait();
        }
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Confirmer", "Voulez-vous vraiment fermer l'application?");
        if (answer) {
            File file = getEtudiantFilePath();
            if (file != null) {
                System.out.println("Saving file");
                saveDataToFile(file);
            }
            primaryStage.close();
        }
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public ObservableList<Etudiant> getEtudiants() {
        return this.etudiants;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
