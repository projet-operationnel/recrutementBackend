package com.recrutementTuteur.web.controller;

import com.recrutementTuteur.services.interfaces.IAnneeAcademiqueService;
import com.recrutementTuteur.web.dto.requests.CreateAnneeRequest;
import com.recrutementTuteur.web.dto.response.AnneeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/annee")
@RequiredArgsConstructor
public class AnneeAcademiqueController {

    private final IAnneeAcademiqueService anneeService;

    @PostMapping
/*    @PreAuthorize("isAuthenticated()")*/
    public ResponseEntity<AnneeResponse> creer(@RequestBody CreateAnneeRequest request) {
        AnneeResponse response = anneeService.creer(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{id}/activer")
/*    @PreAuthorize("isAuthenticated()")*/
    public ResponseEntity<AnneeResponse> activer(@PathVariable Long id) {
        AnneeResponse response = anneeService.activer(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnneeResponse>> lister() {
        return ResponseEntity.ok(anneeService.listerTout());
    }

    @PutMapping("/{id}")
/*    @PreAuthorize("isAuthenticated()")*/
    public ResponseEntity<AnneeResponse> modifier(@PathVariable Long id, @RequestBody CreateAnneeRequest request) {
        AnneeResponse response = anneeService.modifier(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
/*    @PreAuthorize("isAuthenticated()")*/
    public ResponseEntity<AnneeResponse> supprimer(@PathVariable Long id) {
        AnneeResponse response = anneeService.supprimer(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}