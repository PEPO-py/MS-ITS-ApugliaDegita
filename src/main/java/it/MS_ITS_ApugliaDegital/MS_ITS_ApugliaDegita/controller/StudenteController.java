package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.controller;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.StudenteCorsiDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service.CorsoService;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/V1/studente")
public class StudenteController {
    @Autowired
    private CorsoService corsoService;

    @Autowired
    private StudenteService studenteService;

    @GetMapping("/{studente_id}")
    public ResponseEntity<StudenteCorsiDTO> getCorsiOfStudente(@PathVariable long studente_id){
        return ResponseEntity.status(HttpStatus.OK).body(corsoService.getCorsiOfStudente(studente_id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllStudenti() {
        return ResponseEntity.status(HttpStatus.OK).body(studenteService.getAllUserOfStudente());
    }
}
