package com.lab.apis.controller;

import com.lab.apis.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final List<Curso> cursos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public CursoController() {
        cursos.add(new Curso(contador.incrementAndGet(), "Programacion I", "Fundamentos de programacion", 4, "Presencial"));
        cursos.add(new Curso(contador.incrementAndGet(), "Bases de Datos", "Modelado y SQL", 3, "Virtual"));
        cursos.add(new Curso(contador.incrementAndGet(), "Estructuras de Datos", "Listas, pilas, colas y arboles", 4, "Presencial"));
        cursos.add(new Curso(contador.incrementAndGet(), "Redes de Computadoras", "Fundamentos de redes", 3, "Hibrido"));
        cursos.add(new Curso(contador.incrementAndGet(), "Ingenieria de Software", "Metodologias agiles", 4, "Virtual"));
    }

    @GetMapping
    public List<Curso> obtenerTodos() {
        return cursos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        curso.setId(contador.incrementAndGet());
        cursos.add(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso datos) {
        Optional<Curso> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = existente.get();
        curso.setNombre(datos.getNombre());
        curso.setDescripcion(datos.getDescripcion());
        curso.setCreditos(datos.getCreditos());
        curso.setModalidad(datos.getModalidad());
        return ResponseEntity.ok(curso);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Curso> actualizarParcial(@PathVariable Long id, @RequestBody Curso datos) {
        Optional<Curso> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = existente.get();
        if (datos.getNombre() != null) curso.setNombre(datos.getNombre());
        if (datos.getDescripcion() != null) curso.setDescripcion(datos.getDescripcion());
        if (datos.getCreditos() != null) curso.setCreditos(datos.getCreditos());
        if (datos.getModalidad() != null) curso.setModalidad(datos.getModalidad());
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Curso> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cursos.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Curso> buscarPorId(Long id) {
        return cursos.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
