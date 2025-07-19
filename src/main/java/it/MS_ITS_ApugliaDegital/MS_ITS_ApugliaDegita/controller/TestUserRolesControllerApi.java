package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.controller;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.UserRole;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service.UserRoleTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/V1/roles-from-users")
public class TestUserRolesControllerApi {
    @Autowired
    private UserRoleTestService userRoleTestService;

    @GetMapping
    public ResponseEntity<List<UserRole>> getUserRolesFromUserAPI() {
        return ResponseEntity.status(HttpStatus.OK).body(userRoleTestService.getUserRolesFromAPI());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRolesFromApi(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userRoleTestService.getUserRoleByIdFromAPI(id));
    }
}
