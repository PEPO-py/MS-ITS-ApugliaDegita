package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.controller;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.Coffee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/V1/coffee")
public class CoffeeController {
    private LocalDateTime FIXED_DATE = LocalDateTime.now();
    @GetMapping("/")
    public Coffee getCoffee(@RequestParam(name = "brand", required = false) String brand,
                            @RequestParam(name = "name", required = false) String name) {
        return new Coffee(name, brand, FIXED_DATE);
    }
}
