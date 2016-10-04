package mg.laiso.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mg.laiso.model.Etudiant;

/**
 * Created by Laiso on 04/10/2016.
 */
public class EtudiantFormDialogController {

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtBourse;

    @FXML
    private Button btnValider;

    @FXML
    private Button btnAnnuler;

    private Stage dialogStage;
    private Etudiant etudiant;
    private boolean validerClicked = false;

    @FXML
    private void initialize(){

    }

    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
    }

    public void setEtudiant(Etudiant etudiant){
        this.etudiant = etudiant;

        this.txtNumero.setText(etudiant.getNumero());
        this.txtNom.setText(etudiant.getNom());
        this.txtPrenom.setText(etudiant.getPrenom());
        this.txtAdresse.setText(etudiant.getAdresse());
        this.txtBourse.setText(String.valueOf(etudiant.getBourse()));
    }

    public boolean isValiderClicked(){
        return validerClicked;
    }

    @FXML
    private void valider(){
        if(isInputValid()){
            etudiant.setNumero(txtNumero.getText());
            etudiant.setNom(txtNom.getText());
            etudiant.setPrenom(txtPrenom.getText());
            etudiant.setAdresse(txtAdresse.getText());
            etudiant.setBourse(Double.parseDouble(txtBourse.getText()));

            validerClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void annuler(){
        dialogStage.close();
    }

    private boolean isInputValid(){
        String message = "";

        if(txtNumero.getText() == null || txtNumero.getText().length() == 0)
            message += "Le champ numéro ne doit pas être vide.\n";

        if(txtNom.getText() == null || txtNom.getText().length() == 0)
            message += "Le champ nom ne doit pas être vide.\n";

        if(txtPrenom.getText() == null || txtPrenom.getText().length() == 0)
            message += "Le champ prénom ne doit pas être vide.\n";

        if(txtAdresse.getText() == null || txtAdresse.getText().length() == 0)
            message += "Le champ adresse ne doit pas être vide.\n";

        if(txtBourse.getText() == null || txtBourse.getText().length() == 0) {
            message += "Le champ prénom ne doit pas être vide.\n";
        }else{
            try{
                Double.parseDouble(txtBourse.getText());
            }catch(NumberFormatException e){
                message += "Bourse non valide.\n";
            }
        }

        if(message.length() == 0)
            return true;
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs non valide");
            alert.setHeaderText("Veuillez vérifier les champs suivants:");
            alert.setContentText(message);

            alert.showAndWait();
        }

        return false;
    }
}
