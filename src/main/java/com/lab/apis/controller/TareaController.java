package com.lab.apis.controller;

import com.lab.apis.model.Tarea;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public TareaController() {
        tareas.add(new Tarea(contador.incrementAndGet(), "Preparar informe mensual", "Consolidar cifras de ventas", "ALTA", false));
        tareas.add(new Tarea(contador.incrementAndGet(), "Revisar correos", "Responder pendientes del cliente", "MEDIA", true));
        tareas.add(new Tarea(contador.incrementAndGet(), "Actualizar documentacion", "Documentar nuevos endpoints", "BAJA", false));
        tareas.add(new Tarea(contador.incrementAndGet(), "Reunion de equipo", "Sprint planning semanal", "ALTA", false));
        tareas.add(new Tarea(contador.incrementAndGet(), "Backup de base de datos", "Respaldo semanal programado", "MEDIA", true));
    }

    @GetMapping
    public List<Tarea> obtenerTodos() {
        return tareas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tarea.setId(contador.incrementAndGet());
        tareas.add(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea datos) {
        Optional<Tarea> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tarea tarea = existente.get();
        tarea.setTitulo(datos.getTitulo());
        tarea.setDescripcion(datos.getDescripcion());
        tarea.setPrioridad(datos.getPrioridad());
        tarea.setCompletada(datos.getCompletada());
        return ResponseEntity.ok(tarea);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> actualizarParcial(@PathVariable Long id, @RequestBody Tarea datos) {
        Optional<Tarea> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tarea tarea = existente.get();
        if (datos.getTitulo() != null) tarea.setTitulo(datos.getTitulo());
        if (datos.getDescripcion() != null) tarea.setDescripcion(datos.getDescripcion());
        if (datos.getPrioridad() != null) tarea.setPrioridad(datos.getPrioridad());
        if (datos.getCompletada() != null) tarea.setCompletada(datos.getCompletada());
        return ResponseEntity.ok(tarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Tarea> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tareas.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Tarea> buscarPorId(Long id) {
        return tareas.stream().filter(t -> t.getId().equals(id)).findFirst();
    }
}
