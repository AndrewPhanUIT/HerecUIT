package uit.herec.common.dto;

public class MedicationDto {
    private int quantity;
    private String doseQuantity;
    private String name;
    private String note;
    private String endDate;
    private String startDate;

    public MedicationDto(int quantity, String doseQuantity, String name, String note, String endDate,
            String startDate) {
        super();
        this.quantity = quantity;
        this.doseQuantity = doseQuantity;
        this.name = name;
        this.note = note;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public MedicationDto() {
        super();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
