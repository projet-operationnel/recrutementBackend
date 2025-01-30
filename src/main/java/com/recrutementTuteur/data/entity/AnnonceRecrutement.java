/*
package com.recrutementTuteur.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "annonce_recrutements")
public class AnnonceRecrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private LocalDate datePublication;
    private boolean estActive;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;
}
*/

package com.recrutementTuteur.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/*
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "annonce_recrutements")
public class AnnonceRecrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Column(name = "est_active", nullable = false)
    private boolean estActive;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;
}*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "annonce_recrutements")
public class AnnonceRecrutement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Column(name = "est_active", nullable = false)
    private boolean estActive;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;
}