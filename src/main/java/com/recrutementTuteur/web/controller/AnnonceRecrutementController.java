package com.recrutementTuteur.web.controller;

import com.recrutementTuteur.services.interfaces.IAnnonceRecrutementService;
import com.recrutementTuteur.web.dto.requests.CreateAnnonceRequest;
import com.recrutementTuteur.web.dto.response.AnnonceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/annonces")
@RequiredArgsConstructor
public class AnnonceRecrutementController {

    private final IAnnonceRecrutementService annonceService;

    @PostMapping
   @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AnnonceResponse> creer(@RequestBody CreateAnnonceRequest request) {
        AnnonceResponse response = annonceService.creer(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> lister() {
        return ResponseEntity.ok(annonceService.listerTout());
    }

    @GetMapping("/annee/{anneeId}")
    public ResponseEntity<List<AnnonceResponse>> listerParAnnee(@PathVariable Long anneeId) {
        return ResponseEntity.ok(annonceService.listerParAnnee(anneeId));
    }

    @PutMapping("/{id}")
   @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AnnonceResponse> modifier(@PathVariable Long id, @RequestBody CreateAnnonceRequest request) {
        AnnonceResponse response = annonceService.modifier(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AnnonceResponse> supprimer(@PathVariable Long id) {
        AnnonceResponse response = annonceService.supprimer(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}