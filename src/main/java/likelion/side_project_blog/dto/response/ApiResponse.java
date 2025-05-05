package likelion.side_project_blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public ApiResponse(boolean success, int code, String message,  T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ApiResponse(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data, String message){
        return new ApiResponse<>(true,200,message,data);
    }
    public static ApiResponse<Void> success(String message){
        return new ApiResponse<>(true,200,message, null);
    }
}