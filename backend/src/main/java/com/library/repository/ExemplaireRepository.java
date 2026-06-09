package com.library.repository;

import com.library.entities.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    List<Exemplaire> findByOuvrageId(Long ouvrageId);
    List<Exemplaire> findByDisponibleTrue();
}
