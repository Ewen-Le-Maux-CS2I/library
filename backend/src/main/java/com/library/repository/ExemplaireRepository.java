package com.library.repository;

import com.library.entities.Exemplaire;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
  List<Exemplaire> findByOuvrageId(Long ouvrageId);

  List<Exemplaire> findByDisponibleTrue();
}
