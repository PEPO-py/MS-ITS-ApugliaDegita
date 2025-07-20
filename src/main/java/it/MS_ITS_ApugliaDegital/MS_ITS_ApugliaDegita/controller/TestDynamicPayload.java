package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.controller;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto.DynamicPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/V1/dynamic")
public class TestDynamicPayload {
    @PostMapping
    public ResponseEntity<DynamicPayload> getDynamic(@RequestBody DynamicPayload new_dynamic) {
        System.out.println(new_dynamic.toString());
        int num_attr = new_dynamic.getRawDynamicAttributesMap().size();
        new_dynamic.addDynamicAttribute("num_attr", num_attr);
        return new ResponseEntity<>(new_dynamic, HttpStatus.CREATED);
    }
}
