package uit.herec.common.dto;

import java.util.List;

public class ChaincodeScript {
    private String function;
    private List<Object> Args;

    public ChaincodeScript(String function, List<Object> args) {
        super();
        this.function = function;
        Args = args;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<Object> getArgs() {
        return Args;
    }

    public void setArgs(List<Object> args) {
        Args = args;
    }
}
