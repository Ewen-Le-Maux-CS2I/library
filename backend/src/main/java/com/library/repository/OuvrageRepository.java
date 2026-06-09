package com.library.repository;

import com.library.entities.Ouvrage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {
  List<Ouvrage> findByTitreContainingIgnoreCase(String titre);

  List<Ouvrage> findByGenre(String genre);
}
