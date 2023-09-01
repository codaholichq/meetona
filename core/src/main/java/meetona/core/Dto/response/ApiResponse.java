package meetona.core.Dto.response;

import lombok.*;

@Data
public class ApiResponse {

    private String data;
    private Boolean success;

    public ApiResponse() {
    }

    public ApiResponse(String data, Boolean success) {
        this.data = data;
        this.success = success;
    }
}
