package mg.laiso.controller;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import mg.laiso.MainApp;
import mg.laiso.model.Etudiant;
import mg.laiso.view.ConfirmBox;

/**
 * Created by Laiso on 04/10/2016.
 */
public class EtudiantController {
    @FXML
    private TableView<Etudiant> tblEtudiant;

    @FXML
    private TableColumn<Etudiant, String> colNumero;

    @FXML
    private TableColumn<Etudiant, String> colNom;

    @FXML
    private TableColumn<Etudiant, String> colPrenom;


    @FXML
    private Label lblNumero;

    @FXML
    private Label lblNom;

    @FXML
    private Label lblPrenom;

    @FXML
    private Label lblAdresse;

    @FXML
    private Label lblBourse;

    @FXML
    private Button btnNouveau;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    public EtudiantController() {
    }

    private MainApp mainApp;

    @FXML
    public void initialize(){
        colNumero.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());

        showEtudiantDetails(null);

        tblEtudiant.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEtudiantDetails(newValue)
        );

        btnNouveau.setTooltip(new Tooltip("Ajouter un nouveau étudiant"));
        btnModifier.setTooltip(new Tooltip("Modifier l'étudiant selectionné"));
        btnSupprimer.setTooltip(new Tooltip("Supprimer l'étudiant selectionné"));
    }

    @FXML
    private void supprimerEtudiant(){
        int selected = tblEtudiant.getSelectionModel().getSelectedIndex();
        if(selected >= 0) {
            if(ConfirmBox.display("Confirmer la suppression", "Voulez-vous vraiment supprimer l'étudiant selectionné?")) {
                tblEtudiant.getItems().remove(selected);

                Notification notification = Notifications.INFORMATION;
                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setTitle("Gestion d'étudiants App");
                trayNotification.setMessage("L'etudiant a été supprimé avec succès.");
                trayNotification.setNotification(notification);
                trayNotification.setAnimation(Animations.FADE);
                trayNotification.showAndDismiss(Duration.seconds(3));
                trayNotification.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Aucune séléction");
            alert.setHeaderText("Aucun étudiant selectionné");
            alert.setContentText("Selectionner au moins un étudiant");

            alert.showAndWait();
        }
    }

    @FXML
    private void nouveauEtudiant(){
        Etudiant temp = new Etudiant();
        boolean validerClicked = mainApp.showEtudiantEditDialog(temp);
        if(validerClicked) {
            mainApp.getEtudiants().add(temp);

            Notification notification = Notifications.SUCCESS;
            TrayNotification trayNotification = new TrayNotification();
            trayNotification.setTitle("Gestion d'étudiants App");
            trayNotification.setMessage("Etudiant " + temp.getNom() + " " + temp.getPrenom() + " ajouté avec succès.");
            trayNotification.setNotification(notification);
            trayNotification.setAnimation(Animations.FADE);
            trayNotification.showAndDismiss(Duration.seconds(3));
            trayNotification.showAndWait();
        }
    }

    @FXML
    private void modifierEtudiant(){
        Etudiant selected = tblEtudiant.getSelectionModel().getSelectedItem();
        if(selected != null){
            boolean validerClicked = mainApp.showEtudiantEditDialog(selected);
            if(validerClicked) {
                showEtudiantDetails(selected);

                Notification notification = Notifications.INFORMATION;
                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setTitle("Gestion d'étudiants App");
                trayNotification.setMessage("Etudiant " + selected.getNom() + " " + selected.getPrenom() + " mis à jour.");
                trayNotification.setNotification(notification);
                trayNotification.setAnimation(Animations.FADE);
                trayNotification.showAndDismiss(Duration.seconds(3));
                trayNotification.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune séléction");
            alert.setHeaderText("Aucun étudiant n'est selectionné");
            alert.setContentText("Veuillez selectionner un étudiant");

            alert.showAndWait();
        }
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        tblEtudiant.setItems(mainApp.getEtudiants());
    }

    private void showEtudiantDetails(Etudiant etudiant){
        if(etudiant != null){
            lblNumero.setText(etudiant.getNumero());
            lblNom.setText(etudiant.getNom());
            lblPrenom.setText(etudiant.getPrenom());
            lblAdresse.setText(etudiant.getAdresse());
            lblBourse.setText(String.valueOf(etudiant.getBourse()));
        }else{
            lblNumero.setText("");
            lblNom.setText("");
            lblPrenom.setText("");
            lblAdresse.setText("");
            lblBourse.setText("");
        }
    }
}
