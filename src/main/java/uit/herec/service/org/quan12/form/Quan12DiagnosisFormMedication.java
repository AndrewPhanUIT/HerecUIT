package uit.herec.service.org.quan12.form;

import java.io.Serializable;

public class Quan12DiagnosisFormMedication implements Serializable {

    private static final long serialVersionUID = 3277035496123185872L;
    private String quantity;
    private String doseQuantity;
    private String name;
    private String note;
    private String endDate;
    private String startDate;

    public Quan12DiagnosisFormMedication(String quantity, String doseQuantity, String name, String note, String endDate,
            String startDate) {
        super();
        this.quantity = quantity;
        this.doseQuantity = doseQuantity;
        this.name = name;
        this.note = note;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public Quan12DiagnosisFormMedication() {
        super();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(String doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}
