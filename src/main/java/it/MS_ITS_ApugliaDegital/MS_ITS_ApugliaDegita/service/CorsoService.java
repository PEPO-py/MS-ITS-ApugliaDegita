package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.CorsoAllDataDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.StudenteCorsiDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserRole;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.Corso;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.CorsoStudente;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.exception.DataValidationException;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.exception.NotFoundException;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.repository.CorsoRepository;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.util.HandelValidationError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CorsoService {


    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoStudenteService corsoStudenteService;

    @Autowired
    private RestTemplate restTemplate;

    private final String apiBaseURl;

    public CorsoService (RestTemplateBuilder builder, @Value("${users.api.url}") String apiBaseURl) {
        this.restTemplate = builder.build();
        this.apiBaseURl = apiBaseURl;
    }

    @Transactional(readOnly = true)
    public List<Corso> getAllCorso() {
        return corsoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Corso getCorsoByID(long id) {
        return corsoRepository.getCorsoById(id).
                orElseThrow(()-> new NotFoundException("No corso found with that id (%d)".formatted(id)));
    }

    @Transactional
    public Corso createCorso(@Valid Corso newCorso, BindingResult result) {
        if (result.hasErrors()) throw new DataValidationException(HandelValidationError.getAllErrors(result));
        else {
            return corsoRepository.save(newCorso);
        }
    }

    @Transactional
    public CorsoStudente addStudentiToCorso(@Valid CorsoStudente corsoStudente, BindingResult result) {
        String url = this.apiBaseURl + "/users/" +  corsoStudente.getStudenteId();
        Corso getCorso = corsoRepository.getCorsoById(corsoStudente.getCorsoId()).orElseThrow(() -> new NotFoundException("No corso found with that id (%d)".formatted(corsoStudente.getCorsoId())));
        if (result.hasErrors()) throw new DataValidationException(HandelValidationError.getAllErrors(result));
        else {
            try {
                UserDTO userFromAI = restTemplate.getForObject(url, UserDTO.class);
                List<UserRole> userRole = userFromAI.getUserRole().stream().toList();
                if (userRole.get(0).getId() != 1) {
                    throw new DataValidationException("The following id (%d) is not a student id".formatted(userRole.get(0).getId()));
                } else {
                    return corsoStudenteService.createCorsoStudente(corsoStudente);
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) throw new NotFoundException("Student not found with this id (%d)".formatted(corsoStudente.getStudenteId()));
                return corsoStudente;
            }

        }

    }

    @Transactional(readOnly = true)
    public List<CorsoAllDataDTO> getAllCorsoInfo() {
        List<Corso> getAllCorsi = getAllCorso();

        List<CorsoAllDataDTO> allCorsoInfo = new ArrayList<>();

        CorsoAllDataDTO newCorsoInfo = new CorsoAllDataDTO();

        for (int i=0; i < getAllCorsi.size(); i++) {
            newCorsoInfo.setName(getAllCorsi.get(i).getName());
            List<CorsoStudente> getStudentiOfCorso = corsoStudenteService.getStudentiOfCorso(getAllCorsi.get(i).getId());
            List<UserDTO> listOfStudenti = new ArrayList<>();

            for (int y=0; y < getStudentiOfCorso.size(); y++) {
                String url = this.apiBaseURl + "/users/" +  getStudentiOfCorso.get(i).getStudenteId();
                listOfStudenti.add(restTemplate.getForObject(url, UserDTO.class));
            }
            newCorsoInfo.setStudenti(listOfStudenti);
        }
        allCorsoInfo.add(newCorsoInfo);
        return allCorsoInfo;
    }

    @Transactional(readOnly = true)
    public StudenteCorsiDTO getCorsiOfStudente(long student_id) {
        StudenteCorsiDTO studenteCorsi = new StudenteCorsiDTO();
        studenteCorsi.setUtente(getOnlyUserStudentById(student_id).get());
        List<CorsoStudente> corsiOfStudente = corsoStudenteService.getCorsiOfStudente(studenteCorsi.getUtente().getId());
        List<Corso> studenteListOfCorsi = new ArrayList<>();
        for (int i=0; i<corsiOfStudente.size(); i++) {
            studenteListOfCorsi.add(getCorsoByID(corsiOfStudente.get(i).getCorsoId()));

        }
        studenteCorsi.setCorsi(studenteListOfCorsi);
        return studenteCorsi;

    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getOnlyUserStudentById(long student_id) {
        String urlStudente = this.apiBaseURl + "/users/" + student_id;
        try {
            UserDTO student = restTemplate.getForObject(urlStudente, UserDTO.class);
            List<UserRole> userRole  = student.getUserRole().stream().toList();
            if (userRole.get(0).getId() != 1) throw new DataValidationException("The id (%d) used is not a student id".formatted(student_id));
            else {
                return Optional.of(student);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) throw new NotFoundException("Student not found with that id(%d)".formatted(student_id));
            else return null;
        }
    }

}
