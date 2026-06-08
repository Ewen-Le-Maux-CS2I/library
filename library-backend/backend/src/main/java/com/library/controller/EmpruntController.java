package com.library.controller;

import com.library.entities.Emprunt;
import com.library.service.Bibliotheque;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/emprunts")
@RequiredArgsConstructor
public class EmpruntController {

    private final Bibliotheque bibliotheque;

    @PostMapping
    public ResponseEntity<Emprunt> emprunter(@RequestBody Map<String, Long> body) {
        Long exemplaireId = body.get("exemplaireId");
        Emprunt emprunt = bibliotheque.emprunter(exemplaireId);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprunt);
    }

    @PatchMapping("/{id}/retourner")
    public Emprunt retourner(@PathVariable Long id) {
        return bibliotheque.retourner(id);
    }

    @PatchMapping("/{id}/perte")
    public Emprunt declarerPerte(@PathVariable Long id) {
        return bibliotheque.declarerPerte(id);
    }
}
