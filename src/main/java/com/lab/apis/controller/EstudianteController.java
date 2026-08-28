package com.lab.apis.controller;

import com.lab.apis.model.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final List<Estudiante> estudiantes = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public EstudianteController() {
        estudiantes.add(new Estudiante(contador.incrementAndGet(), "Ana", "Perez", "Ingenieria de Sistemas", 20));
        estudiantes.add(new Estudiante(contador.incrementAndGet(), "Carlos", "Gomez", "Administracion", 22));
        estudiantes.add(new Estudiante(contador.incrementAndGet(), "Maria", "Lopez", "Derecho", 21));
        estudiantes.add(new Estudiante(contador.incrementAndGet(), "Jose", "Rodriguez", "Medicina", 23));
        estudiantes.add(new Estudiante(contador.incrementAndGet(), "Lucia", "Martinez", "Psicologia", 19));
    }

    @GetMapping
    public List<Estudiante> obtenerTodos() {
        return estudiantes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante estudiante) {
        estudiante.setId(contador.incrementAndGet());
        estudiantes.add(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante datos) {
        Optional<Estudiante> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Estudiante estudiante = existente.get();
        estudiante.setNombre(datos.getNombre());
        estudiante.setApellido(datos.getApellido());
        estudiante.setCarrera(datos.getCarrera());
        estudiante.setEdad(datos.getEdad());
        return ResponseEntity.ok(estudiante);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarParcial(@PathVariable Long id, @RequestBody Estudiante datos) {
        Optional<Estudiante> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Estudiante estudiante = existente.get();
        if (datos.getNombre() != null) estudiante.setNombre(datos.getNombre());
        if (datos.getApellido() != null) estudiante.setApellido(datos.getApellido());
        if (datos.getCarrera() != null) estudiante.setCarrera(datos.getCarrera());
        if (datos.getEdad() != null) estudiante.setEdad(datos.getEdad());
        return ResponseEntity.ok(estudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Estudiante> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        estudiantes.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Estudiante> buscarPorId(Long id) {
        return estudiantes.stream().filter(e -> e.getId().equals(id)).findFirst();
    }
}
