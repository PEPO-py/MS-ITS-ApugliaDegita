package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coffee {

    private String name;
    private String brand;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime date;

    //getters and setters
}