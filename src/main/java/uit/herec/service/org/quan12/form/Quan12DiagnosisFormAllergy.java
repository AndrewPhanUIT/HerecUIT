package uit.herec.service.org.quan12.form;

import java.io.Serializable;

public class Quan12DiagnosisFormAllergy implements Serializable {

    private static final long serialVersionUID = -6684865544916877251L;
    private String name;
    private String status;
    private String reaction;

    public Quan12DiagnosisFormAllergy(String name, String status, String reaction) {
        super();
        this.name = name;
        this.status = status;
        this.reaction = reaction;
    }

    public Quan12DiagnosisFormAllergy() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

}
