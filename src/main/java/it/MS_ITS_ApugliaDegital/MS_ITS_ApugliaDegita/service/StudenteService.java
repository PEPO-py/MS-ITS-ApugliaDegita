package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserDTO;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudenteService {
    @Autowired
    private final RestTemplate restTemplate;
    private final String apiBaseURl;

    public StudenteService (RestTemplateBuilder builder, @Value("${users.api.url}") String apiBaseURl) {
        this.restTemplate = builder.build();
        this.apiBaseURl = apiBaseURl;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUserOfStudente() {
        String url = this.apiBaseURl + "/users";
        List<UserDTO> listOfStudenti = new ArrayList<>();
        try {
            List<UserDTO> listOfUsers = restTemplate.
                    exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {}).getBody();
            for (int i=0; i < listOfUsers.size(); i++) {
                List<UserRole> userRole = listOfUsers.get(i).getUserRole().stream().toList();
                if (userRole.get(0).getId() == 1) {
                    listOfStudenti.add(listOfUsers.get(i));
                }
            }
            return listOfStudenti;
        } catch (HttpClientErrorException e) {
            return listOfStudenti;
        }

    }
}
