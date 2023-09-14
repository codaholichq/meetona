package meetona.core.payload.response;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private T data;
    private Boolean success;

    public ApiResponse() {
    }

    public ApiResponse(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }
}
