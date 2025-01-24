package com.recrutementTuteur.data.entity;

import com.recrutementTuteur.data.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Stockage du chemin de la photo (nullable)
/*    private String photoUrl;

    // Métadonnées de la photo (nullable)
    private String photoNom;
    private String photoType;

    @Lob
    @Column(name = "photo_data", columnDefinition = "BYTEA")
    private byte[] photoData;*/
}