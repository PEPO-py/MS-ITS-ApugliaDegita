package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CorsoStudente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Positive(message = "must be an long integer id")
    private long corsoId;
    @Positive(message = "must be an long integer id")
    private long studenteId;
}
