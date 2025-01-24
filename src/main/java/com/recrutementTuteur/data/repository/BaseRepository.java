package com.recrutementTuteur.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// Add NoRepositoryBean annotation
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}
