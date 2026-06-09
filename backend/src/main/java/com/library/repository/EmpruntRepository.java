package com.library.repository;

import com.library.entities.Emprunt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
  List<Emprunt> findByNomEtat(String nomEtat);
}
