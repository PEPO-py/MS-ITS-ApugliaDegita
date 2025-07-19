package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserRole;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.exception.NotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserRoleTestService {
    @Autowired
    private final RestTemplate restTemplate;
    private final String apiBaseURl;

    public UserRoleTestService (RestTemplateBuilder builder, @Value("${users.api.url}") String apiBaseURl) {
        this.restTemplate = builder.build();
        this.apiBaseURl = apiBaseURl;
    }

    public List<UserRole> getUserRolesFromAPI () {
        String url = this.apiBaseURl + "/roles";
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserRole>>() {}).getBody();
    }

    public UserRole getUserRoleByIdFromAPI(Long id) {
        String url = this.apiBaseURl + "/roles/" +  id.toString();
        // boolean error404 = false;
        try {
            //HttpStatusCode status = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<UserRole>() {}).getStatusCode();
            // if (status.isSameCodeAs(HttpStatus.NOT_FOUND)) error404 = true;
            return restTemplate.getForObject(url, UserRole.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND))
                throw new NotFoundException("User role not found with that id (%d)".formatted(id));
            else throw new InternalError("Error");
        }
    }

}
