package uit.herec.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiResponseDto implements Serializable {
    private static final long serialVersionUID = -1622583633503083663L;

    private boolean success;
    private List<Object> datas = new ArrayList<>();
    private int size = 1;
    private String message;

    public ApiResponseDto(boolean success, List<Object> datas, String message) {
        super();
        this.success = success;
        this.datas = datas;
        this.size = this.calculateSize();
        this.message = message;
    }

    public ApiResponseDto() {
        super();
    }

    public ApiResponseDto(boolean success, List<Object> datas, int size, String message) {
        super();
        this.success = success;
        this.datas = datas;
        this.size = size;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public int getSize() {
        return size;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int calculateSize() {
        return this.datas.size();
    }
}
