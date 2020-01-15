package uit.dessertation.diagnosis;

import com.owlike.genson.annotation.JsonProperty;

public class Allergy {
    private final String name;
    private final String status;
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
