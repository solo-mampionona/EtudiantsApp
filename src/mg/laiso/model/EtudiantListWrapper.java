package mg.laiso.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Laiso on 04/10/2016.
 */

@XmlRootElement(name = "etudiants")
public class EtudiantListWrapper {

    private List<Etudiant> etudiants;

    @XmlElement(name = "etudiant")
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
}
