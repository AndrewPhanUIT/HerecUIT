package uit.herec.service.org.tanphu.form;

import java.io.Serializable;

public class TanPhuDiagnosisFormAllergy implements Serializable {

    private static final long serialVersionUID = 8405312284498834400L;
    private String name;
    private String status;
    private String reaction;

    public TanPhuDiagnosisFormAllergy(String name, String status, String reaction) {
        super();
        this.name = name;
        this.status = status;
        this.reaction = reaction;
    }

    public TanPhuDiagnosisFormAllergy() {
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
