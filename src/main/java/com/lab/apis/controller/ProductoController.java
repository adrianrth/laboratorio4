package com.lab.apis.controller;

import com.lab.apis.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public ProductoController() {
        productos.add(new Producto(contador.incrementAndGet(), "Laptop Lenovo", 750.00, "Tecnologia"));
        productos.add(new Producto(contador.incrementAndGet(), "Mouse Inalambrico", 15.50, "Tecnologia"));
        productos.add(new Producto(contador.incrementAndGet(), "Silla Ergonomica", 120.00, "Muebles"));
        productos.add(new Producto(contador.incrementAndGet(), "Cafetera", 45.00, "Electrodomesticos"));
        productos.add(new Producto(contador.incrementAndGet(), "Cuaderno A4", 3.20, "Papeleria"));
    }

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        producto.setId(contador.incrementAndGet());
        productos.add(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datos) {
        Optional<Producto> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Producto producto = existente.get();
        producto.setNombre(datos.getNombre());
        producto.setPrecio(datos.getPrecio());
        producto.setCategoria(datos.getCategoria());
        return ResponseEntity.ok(producto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(@PathVariable Long id, @RequestBody Producto datos) {
        Optional<Producto> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Producto producto = existente.get();
        if (datos.getNombre() != null) producto.setNombre(datos.getNombre());
        if (datos.getPrecio() != null) producto.setPrecio(datos.getPrecio());
        if (datos.getCategoria() != null) producto.setCategoria(datos.getCategoria());
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Producto> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productos.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Producto> buscarPorId(Long id) {
        return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
