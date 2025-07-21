package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CorsoAllDataDTO {
    private String name;

    private List<UserDTO> studenti;
}
