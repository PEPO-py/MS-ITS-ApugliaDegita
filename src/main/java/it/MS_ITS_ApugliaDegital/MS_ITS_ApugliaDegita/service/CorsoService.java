package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dao.CorsoDAO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.CorsoAllDataDTO;
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
        String url = this.apiBaseURl + "/users/" +  corsoStudente.getStudente_id();
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
                if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) throw new NotFoundException("Student not found with this id (%d)".formatted(corsoStudente.getStudente_id()));
                return corsoStudente;
            }

        }

    }

    @Transactional(readOnly = true)
    public List<CorsoAllDataDTO> getAllCorsoInfo() {
        List<Corso> getAllCorsi = getAllCorso();
        List<CorsoAllDataDTO> allCorsoInfo = new ArrayList<>();
        CorsoAllDataDTO newCorsoInfo = new CorsoAllDataDTO();
        for (int i=0; i<getAllCorsi.size(); i++) {
            newCorsoInfo.setName(getAllCorsi.get(i).getName());
            List<CorsoStudente> getStudentiOfCorso = corsoStudenteService.getStudentiOfCoroso(getAllCorsi.get(i).getId());
            List<UserDTO> listOfStudenti = new ArrayList<>();
            for (int y=0; y < getStudentiOfCorso.size(); y++) {
                String url = this.apiBaseURl + "/users/" +  getStudentiOfCorso.get(i).getStudente_id();
                listOfStudenti.add(restTemplate.getForObject(url, UserDTO.class));
            }
            newCorsoInfo.setStudenti(listOfStudenti);
        }
        allCorsoInfo.add(newCorsoInfo);
        return allCorsoInfo;
    }
//    public Corso addStudentiToCorso(long corso_id, long studenti_id) {
//        Corso getCorso = corsoRepository.getCorsoById(corso_id).orElseThrow(()-> new DataValidationException("Corso not found with that id (%d)".formatted(corso_id)));
//        String url = this.apiBaseURl + "/users/" +  studenti_id;
//        System.out.println("corso geted: " + getCorso);
//        // get studenti by id
//        try {
//            UserDTO getStudent = restTemplate.getForObject(url, UserDTO.class);
//            System.out.println("Student geted: " + getStudent);
//            List<UserRole> getRoles = getStudent.getUserRole().stream().toList();
//            if (getRoles.get(0).getId() != 1) {
//                throw new DataValidationException("The id (%d) is not an student id".formatted(studenti_id));
//            } else {
//                Set<UserDTO> users = new HashSet<>();
//                UserDTO addUser = new UserDTO();
//                addUser.setId(getStudent.getId());
//                addUser.setFirstName(getStudent.getFirstName());
//                addUser.setLastName(getStudent.getLastName());
//                addUser.setUserRole(getStudent.getUserRole());
//                users.add(addUser);
//                UserDTO saveUser = new UserDTO();
//                saveUser.setId(addUser.getId());
//                userDTORespository.save(saveUser);
//                getCorso.setStudenti(users);
//                return corsoRepository.save(getCorso);
//            }
//        } catch (HttpClientErrorException e) {
//            if(e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) throw new NotFoundException("Studenti not found with that id (%)".formatted(studenti_id));
//            else return getCorso;
//        }
//    }
}
