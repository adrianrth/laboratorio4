package com.lab.apis.controller;

import com.lab.apis.model.Libro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final List<Libro> libros = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public LibroController() {
        libros.add(new Libro(contador.incrementAndGet(), "Cien Anios de Soledad", "Gabriel Garcia Marquez", "Realismo Magico", 18.50));
        libros.add(new Libro(contador.incrementAndGet(), "1984", "George Orwell", "Distopia", 15.00));
        libros.add(new Libro(contador.incrementAndGet(), "El Principito", "Antoine de Saint-Exupery", "Fabula", 10.00));
        libros.add(new Libro(contador.incrementAndGet(), "Don Quijote de la Mancha", "Miguel de Cervantes", "Clasico", 22.00));
        libros.add(new Libro(contador.incrementAndGet(), "Rayuela", "Julio Cortazar", "Novela", 17.30));
    }

    @GetMapping
    public List<Libro> obtenerTodos() {
        return libros;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        libro.setId(contador.incrementAndGet());
        libros.add(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro datos) {
        Optional<Libro> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Libro libro = existente.get();
        libro.setTitulo(datos.getTitulo());
        libro.setAutor(datos.getAutor());
        libro.setGenero(datos.getGenero());
        libro.setPrecio(datos.getPrecio());
        return ResponseEntity.ok(libro);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Libro> actualizarParcial(@PathVariable Long id, @RequestBody Libro datos) {
        Optional<Libro> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Libro libro = existente.get();
        if (datos.getTitulo() != null) libro.setTitulo(datos.getTitulo());
        if (datos.getAutor() != null) libro.setAutor(datos.getAutor());
        if (datos.getGenero() != null) libro.setGenero(datos.getGenero());
        if (datos.getPrecio() != null) libro.setPrecio(datos.getPrecio());
        return ResponseEntity.ok(libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Libro> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        libros.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Libro> buscarPorId(Long id) {
        return libros.stream().filter(l -> l.getId().equals(id)).findFirst();
    }
}
