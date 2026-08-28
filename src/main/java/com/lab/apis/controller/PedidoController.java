package com.lab.apis.controller;

import com.lab.apis.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final List<Pedido> pedidos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public PedidoController() {
        pedidos.add(new Pedido(contador.incrementAndGet(), "Laura Jimenez", "Laptop Lenovo", 1, 750.00, "PENDIENTE"));
        pedidos.add(new Pedido(contador.incrementAndGet(), "Miguel Santos", "Mouse Inalambrico", 2, 31.00, "ENVIADO"));
        pedidos.add(new Pedido(contador.incrementAndGet(), "Elena Vargas", "Silla Ergonomica", 1, 120.00, "ENTREGADO"));
        pedidos.add(new Pedido(contador.incrementAndGet(), "Ricardo Nunez", "Cafetera", 3, 135.00, "PENDIENTE"));
        pedidos.add(new Pedido(contador.incrementAndGet(), "Patricia Diaz", "Cuaderno A4", 10, 32.00, "CANCELADO"));
    }

    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        pedido.setId(contador.incrementAndGet());
        pedidos.add(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido datos) {
        Optional<Pedido> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pedido pedido = existente.get();
        pedido.setCliente(datos.getCliente());
        pedido.setProducto(datos.getProducto());
        pedido.setCantidad(datos.getCantidad());
        pedido.setTotal(datos.getTotal());
        pedido.setEstado(datos.getEstado());
        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> actualizarParcial(@PathVariable Long id, @RequestBody Pedido datos) {
        Optional<Pedido> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pedido pedido = existente.get();
        if (datos.getCliente() != null) pedido.setCliente(datos.getCliente());
        if (datos.getProducto() != null) pedido.setProducto(datos.getProducto());
        if (datos.getCantidad() != null) pedido.setCantidad(datos.getCantidad());
        if (datos.getTotal() != null) pedido.setTotal(datos.getTotal());
        if (datos.getEstado() != null) pedido.setEstado(datos.getEstado());
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Pedido> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pedidos.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Pedido> buscarPorId(Long id) {
        return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
