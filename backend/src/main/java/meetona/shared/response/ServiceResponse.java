package meetona.shared.response;

import lombok.Data;

@Data
public class ServiceResponse<T> {

    private T data;
    private String error;
    private Boolean success = false;

    public ServiceResponse() {
    }

    public ServiceResponse(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public void setData(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
        this.success = false;
    }
}
