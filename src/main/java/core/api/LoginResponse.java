package core.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for login API response deserialization.
 * Demonstrates proper response modeling for type-safe API testing.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    private boolean success;
    private int status;
    private String message;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{success=" + success + ", status=" + status +
                ", message='" + message + "'}";
    }
}
