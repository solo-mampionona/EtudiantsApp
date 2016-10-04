package mg.laiso.model;

import javafx.beans.property.*;

/**
 * Created by Laiso on 04/10/2016.
 */
public class Etudiant {
    private final StringProperty Numero;
    private final StringProperty Nom;
    private final StringProperty Prenom;
    private final StringProperty Adresse;
    private final DoubleProperty Bourse;

    public Etudiant() {
        this.Numero = new SimpleStringProperty("");
        this.Nom = new SimpleStringProperty("");
        this.Prenom = new SimpleStringProperty("");
        this.Adresse = new SimpleStringProperty("");
        this.Bourse = new SimpleDoubleProperty(0.00);
    }

    public Etudiant(String numero, String nom, String prenom, String adresse, Double bourse) {
        this.Numero = new SimpleStringProperty(numero);
        this.Nom = new SimpleStringProperty(nom);
        this.Prenom = new SimpleStringProperty(prenom);
        this.Adresse = new SimpleStringProperty(adresse);
        this.Bourse = new SimpleDoubleProperty(bourse);
    }

    public String getNumero() {
        return Numero.get();
    }

    public StringProperty numeroProperty() {
        return Numero;
    }

    public void setNumero(String numero) {
        this.Numero.set(numero);
    }

    public String getNom() {
        return Nom.get();
    }

    public StringProperty nomProperty() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom.set(nom);
    }

    public String getPrenom() {
        return Prenom.get();
    }

    public StringProperty prenomProperty() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom.set(prenom);
    }

    public String getAdresse() {
        return Adresse.get();
    }

    public StringProperty adresseProperty() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        this.Adresse.set(adresse);
    }

    public double getBourse() {
        return Bourse.get();
    }

    public DoubleProperty bourseProperty() {
        return Bourse;
    }

    public void setBourse(double bourse) {
        this.Bourse.set(bourse);
    }
}
