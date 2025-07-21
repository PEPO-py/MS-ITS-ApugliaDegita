package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.controller;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dao.CorsoDAO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.CorsoAllDataDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.Corso;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.CorsoStudente;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service.CorsoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1/corso")
public class CorsoController {
    @Autowired
    private CorsoService corsoService;

    @GetMapping
    public ResponseEntity<List<Corso>> corsoList() {
        return ResponseEntity.status(HttpStatus.OK).body(corsoService.getAllCorso());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CorsoAllDataDTO>> getAllInfo() {
        return new ResponseEntity<>(corsoService.getAllCorsoInfo(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corso> getCorso(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(corsoService.getCorsoByID(id));
    }

    @PostMapping
    public ResponseEntity<Corso> craeteNewCorso(@Valid @RequestBody Corso newCorso, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body(corsoService.createCorso(newCorso, result));
    }

    @PatchMapping("/add-studenti-to-corso")
    public ResponseEntity<CorsoStudente> addStudentToCorso(@Valid @RequestBody CorsoStudente corsoStudente, BindingResult result) {
        return ResponseEntity.status(HttpStatus.OK).body(corsoService.addStudentiToCorso(corsoStudente, result));
    }

}
