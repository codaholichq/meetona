package meetona.shared.response;

import lombok.Data;

@Data
public class ServiceResponse<T> {

    private T data;
    private Boolean success;

    public ServiceResponse() {
    }

    public ServiceResponse(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }
}
