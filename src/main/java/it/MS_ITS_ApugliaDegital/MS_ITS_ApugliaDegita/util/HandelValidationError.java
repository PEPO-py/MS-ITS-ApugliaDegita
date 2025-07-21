package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.util;

import org.springframework.validation.BindingResult;

public abstract class HandelValidationError {
    public static String getAllErrors(BindingResult result) {
        String errors = "";

        for (int i = 0; i < result.getAllErrors().size(); i++) {
            errors += result.getFieldErrors().get(i).getDefaultMessage()
                    .formatted(result.getFieldErrors().get(i).getField()) + "\n";
        }

        return errors;
    }

    public static String getErrorsOfField(BindingResult result, String fieldName) {
        String errors = "";
        for (int i = 0; i < result.getFieldErrors(fieldName).size(); i++) {
            errors = result.getFieldErrors(fieldName).get(i).getDefaultMessage().formatted(fieldName);
        }

        return errors;
    }
}
