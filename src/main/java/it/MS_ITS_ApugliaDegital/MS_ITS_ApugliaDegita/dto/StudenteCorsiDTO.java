package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.Corso;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudenteCorsiDTO {
    private UserDTO utente;
    private List<Corso> corsi;
}
