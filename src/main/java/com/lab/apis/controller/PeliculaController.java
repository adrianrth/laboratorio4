package com.lab.apis.controller;

import com.lab.apis.model.Pelicula;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final List<Pelicula> peliculas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public PeliculaController() {
        peliculas.add(new Pelicula(contador.incrementAndGet(), "El Padrino", "Francis Ford Coppola", "Drama", 1972));
        peliculas.add(new Pelicula(contador.incrementAndGet(), "Inception", "Christopher Nolan", "Ciencia Ficcion", 2010));
        peliculas.add(new Pelicula(contador.incrementAndGet(), "Pulp Fiction", "Quentin Tarantino", "Crimen", 1994));
        peliculas.add(new Pelicula(contador.incrementAndGet(), "Coco", "Lee Unkrich", "Animacion", 2017));
        peliculas.add(new Pelicula(contador.incrementAndGet(), "Parasite", "Bong Joon-ho", "Thriller", 2019));
    }

    @GetMapping
    public List<Pelicula> obtenerTodos() {
        return peliculas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pelicula> crear(@RequestBody Pelicula pelicula) {
        pelicula.setId(contador.incrementAndGet());
        peliculas.add(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(pelicula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizar(@PathVariable Long id, @RequestBody Pelicula datos) {
        Optional<Pelicula> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pelicula pelicula = existente.get();
        pelicula.setTitulo(datos.getTitulo());
        pelicula.setDirector(datos.getDirector());
        pelicula.setGenero(datos.getGenero());
        pelicula.setAnio(datos.getAnio());
        return ResponseEntity.ok(pelicula);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarParcial(@PathVariable Long id, @RequestBody Pelicula datos) {
        Optional<Pelicula> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pelicula pelicula = existente.get();
        if (datos.getTitulo() != null) pelicula.setTitulo(datos.getTitulo());
        if (datos.getDirector() != null) pelicula.setDirector(datos.getDirector());
        if (datos.getGenero() != null) pelicula.setGenero(datos.getGenero());
        if (datos.getAnio() != null) pelicula.setAnio(datos.getAnio());
        return ResponseEntity.ok(pelicula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Pelicula> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        peliculas.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Pelicula> buscarPorId(Long id) {
        return peliculas.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
