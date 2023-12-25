package uz.todoapp.web.utility;

import org.springframework.http.ResponseEntity;

public class ApiUtils {

    public static <T> ResponseEntity<T> successResponse(T data) {
        return ResponseEntity.ok(data);
    }

    public static ResponseEntity<?> errorResponse(String message, int statusCode) {
        // Build and return error response
        return null;
    }

    // Other API utility methods
}

