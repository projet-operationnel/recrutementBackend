package com.recrutementTuteur.data.entity;

import com.recrutementTuteur.data.enums.StatutCandidature;
import com.recrutementTuteur.data.enums.StatutProfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidatures")
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatutCandidature statutCandidature;

    private String cvDocument;
    private String motivationDocument;

    @Enumerated(EnumType.STRING)
    private StatutProfil statutProfil;

    @ManyToOne
    @JoinColumn(name = "annonce_recrutement_id")
    private AnnonceRecrutement annonceRecrutement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}