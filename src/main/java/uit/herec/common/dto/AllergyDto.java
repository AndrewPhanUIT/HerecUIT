package uit.herec.common.dto;


public class AllergyDto {
    private String name;
    private String status;
    private String reaction;
    public AllergyDto(String name, String status, String reaction) {
        super();
        this.name = name;
        this.status = status;
        this.reaction = reaction;
    }
    public AllergyDto() {
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
