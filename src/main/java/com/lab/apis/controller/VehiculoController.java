package com.lab.apis.controller;

import com.lab.apis.model.Vehiculo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final List<Vehiculo> vehiculos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public VehiculoController() {
        vehiculos.add(new Vehiculo(contador.incrementAndGet(), "Toyota", "Corolla", 2022, 21000.00));
        vehiculos.add(new Vehiculo(contador.incrementAndGet(), "Honda", "Civic", 2021, 20500.00));
        vehiculos.add(new Vehiculo(contador.incrementAndGet(), "Chevrolet", "Onix", 2023, 18000.00));
        vehiculos.add(new Vehiculo(contador.incrementAndGet(), "Nissan", "Sentra", 2020, 17500.00));
        vehiculos.add(new Vehiculo(contador.incrementAndGet(), "Mazda", "CX-5", 2022, 27000.00));
    }

    @GetMapping
    public List<Vehiculo> obtenerTodos() {
        return vehiculos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo vehiculo) {
        vehiculo.setId(contador.incrementAndGet());
        vehiculos.add(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo datos) {
        Optional<Vehiculo> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vehiculo vehiculo = existente.get();
        vehiculo.setMarca(datos.getMarca());
        vehiculo.setModelo(datos.getModelo());
        vehiculo.setAnio(datos.getAnio());
        vehiculo.setPrecio(datos.getPrecio());
        return ResponseEntity.ok(vehiculo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarParcial(@PathVariable Long id, @RequestBody Vehiculo datos) {
        Optional<Vehiculo> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vehiculo vehiculo = existente.get();
        if (datos.getMarca() != null) vehiculo.setMarca(datos.getMarca());
        if (datos.getModelo() != null) vehiculo.setModelo(datos.getModelo());
        if (datos.getAnio() != null) vehiculo.setAnio(datos.getAnio());
        if (datos.getPrecio() != null) vehiculo.setPrecio(datos.getPrecio());
        return ResponseEntity.ok(vehiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Vehiculo> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehiculos.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculos.stream().filter(v -> v.getId().equals(id)).findFirst();
    }
}
