package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Esame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private float durata;

    private String data;

    @ManyToOne // Or @ManyToMan
    @JoinColumn(name = "corso_id") // Define the foreign key column name in the 'esame' table
    private Corso corso; // <--- Add this field with the name 'corso'


}
