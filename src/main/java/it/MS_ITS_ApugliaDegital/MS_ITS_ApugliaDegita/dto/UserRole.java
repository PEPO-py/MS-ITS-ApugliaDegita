package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole {
    private long id;
    private String name;
    private String description;
    private String roleCode;
}
