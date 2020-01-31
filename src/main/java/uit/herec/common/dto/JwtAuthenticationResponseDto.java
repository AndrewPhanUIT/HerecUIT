package uit.herec.common.dto;

import java.io.Serializable;

import uit.herec.common.Constant;

public class JwtAuthenticationResponseDto implements Serializable{

    private static final long serialVersionUID = -5629360776903189828L;
    private String accessToken;
    private final String tokenType = Constant.TOKEN_TYPE;
    private String hyperledgerName;

    public JwtAuthenticationResponseDto(String accessToken, String hyperledgerName) {
        super();
        this.accessToken = accessToken;
        this.hyperledgerName = hyperledgerName;
    }

    public JwtAuthenticationResponseDto() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getHyperledgerName() {
        return hyperledgerName;
    }

    public void setHyperledgerName(String hyperledgerName) {
        this.hyperledgerName = hyperledgerName;
    }

}
