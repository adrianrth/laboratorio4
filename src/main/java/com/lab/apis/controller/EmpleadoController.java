package com.lab.apis.controller;

import com.lab.apis.model.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final List<Empleado> empleados = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public EmpleadoController() {
        empleados.add(new Empleado(contador.incrementAndGet(), "Pedro Ramirez", "Desarrollador", 1800.00, "Tecnologia"));
        empleados.add(new Empleado(contador.incrementAndGet(), "Sofia Torres", "Analista de RRHH", 1500.00, "Recursos Humanos"));
        empleados.add(new Empleado(contador.incrementAndGet(), "Diego Fernandez", "Contador", 1600.00, "Finanzas"));
        empleados.add(new Empleado(contador.incrementAndGet(), "Valeria Castro", "Gerente de Ventas", 2200.00, "Ventas"));
        empleados.add(new Empleado(contador.incrementAndGet(), "Andres Morales", "Soporte Tecnico", 1200.00, "Tecnologia"));
    }

    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleados;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado empleado) {
        empleado.setId(contador.incrementAndGet());
        empleados.add(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado datos) {
        Optional<Empleado> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = existente.get();
        empleado.setNombre(datos.getNombre());
        empleado.setPuesto(datos.getPuesto());
        empleado.setSalario(datos.getSalario());
        empleado.setDepartamento(datos.getDepartamento());
        return ResponseEntity.ok(empleado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empleado> actualizarParcial(@PathVariable Long id, @RequestBody Empleado datos) {
        Optional<Empleado> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = existente.get();
        if (datos.getNombre() != null) empleado.setNombre(datos.getNombre());
        if (datos.getPuesto() != null) empleado.setPuesto(datos.getPuesto());
        if (datos.getSalario() != null) empleado.setSalario(datos.getSalario());
        if (datos.getDepartamento() != null) empleado.setDepartamento(datos.getDepartamento());
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Empleado> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        empleados.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Empleado> buscarPorId(Long id) {
        return empleados.stream().filter(e -> e.getId().equals(id)).findFirst();
    }
}
