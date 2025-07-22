package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private Set<UserRole> userRole;

    public String marica (String qualsiasi) {
        return qualsiasi;
    }

}
