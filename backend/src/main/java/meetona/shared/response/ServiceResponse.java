package meetona.shared.response;

import lombok.Data;

@Data
public class ServiceResponse<T> {

    private T data;
    private boolean success = false;

    public ServiceResponse() {
    }

    public ServiceResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public void setData(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

}
