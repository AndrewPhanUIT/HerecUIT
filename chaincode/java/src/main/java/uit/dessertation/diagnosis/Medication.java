package uit.dessertation.diagnosis;

import com.owlike.genson.annotation.JsonProperty;

public class Medication {
    private final int quantity;
    private final String doseQuantity;
    private final String name;
    private final String note;
    private final String endDate;
    private final String startDate;

    public int getQuantity() {
        return quantity;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public Medication(@JsonProperty("quantity") int quantity, @JsonProperty("doseQuantity") String doseQuantity,
            @JsonProperty("name") String name, @JsonProperty("note") String note,
            @JsonProperty("endDate") String endDate, @JsonProperty("startDate") String startDate) {
        super();
        this.quantity = quantity;
        this.doseQuantity = doseQuantity;
        this.name = name;
        this.note = note;
        this.endDate = endDate;
        this.startDate = startDate;
    }

}
