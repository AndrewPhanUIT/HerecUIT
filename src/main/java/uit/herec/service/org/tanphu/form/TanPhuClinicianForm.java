package uit.herec.service.org.tanphu.form;

import java.io.Serializable;

public class TanPhuClinicianForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6392771436237979346L;
    private String id;
    private String name;

    public TanPhuClinicianForm(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public TanPhuClinicianForm() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name + "@" + this.id;
    }

}
