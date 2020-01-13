package uit.dessertation.diagnosis;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class Allergy {

    @Property()
    private final String name;
    @Property()
    private final String status;
    @Property()
    private final String reaction;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getReaction() {
        return reaction;
    }

    public Allergy(@JsonProperty("name") String name, @JsonProperty("status") String status,
            @JsonProperty("reaction") String reaction) {
        super();
        this.name = name;
        this.status = status;
        this.reaction = reaction;
    }

}
