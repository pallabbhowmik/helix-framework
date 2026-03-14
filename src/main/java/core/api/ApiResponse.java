package core.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Generic API response wrapper for paginated/list endpoints.
 * Maps JSON responses with a "data" array field.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private List<T> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{success=" + success + ", status=" + status +
                ", message='" + message + "', dataSize=" + (data != null ? data.size() : 0) + "}";
    }
}
