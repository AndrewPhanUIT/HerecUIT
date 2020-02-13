package uit.herec.common.dto;

import java.io.Serializable;

public class PermissionDto implements Serializable {
    private static final long serialVersionUID = 8084790829478719698L;
    private String name;
    private String orgName;
    private boolean isPermissioned;

    public PermissionDto(String name, String orgName, boolean isPermissioned) {
        super();
        this.name = name;
        this.orgName = orgName;
        this.isPermissioned = isPermissioned;
    }

    public PermissionDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean isPermissioned() {
        return isPermissioned;
    }

    public void setPermissioned(boolean isPermissioned) {
        this.isPermissioned = isPermissioned;
    }

}
