package com.recrutementTuteur.data.repository;

import com.recrutementTuteur.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTelephone(String telephone);
}