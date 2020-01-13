package uit.dessertation.diagnosis;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class Medication {
    @Property()
    private final int quantity;
    @Property()
    private final String doseQuantity;
    @Property()
    private final String name;
    @Property()
    private final String note;
    @Property()
    private final String endDate;
    @Property()
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
