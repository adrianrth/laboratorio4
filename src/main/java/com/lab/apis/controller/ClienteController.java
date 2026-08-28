package com.lab.apis.controller;

import com.lab.apis.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final List<Cliente> clientes = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public ClienteController() {
        clientes.add(new Cliente(contador.incrementAndGet(), "Laura", "Jimenez", "laura.jimenez@mail.com", "8091234567"));
        clientes.add(new Cliente(contador.incrementAndGet(), "Miguel", "Santos", "miguel.santos@mail.com", "8092345678"));
        clientes.add(new Cliente(contador.incrementAndGet(), "Elena", "Vargas", "elena.vargas@mail.com", "8093456789"));
        clientes.add(new Cliente(contador.incrementAndGet(), "Ricardo", "Nunez", "ricardo.nunez@mail.com", "8094567890"));
        clientes.add(new Cliente(contador.incrementAndGet(), "Patricia", "Diaz", "patricia.diaz@mail.com", "8095678901"));
    }

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clientes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        cliente.setId(contador.incrementAndGet());
        clientes.add(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente datos) {
        Optional<Cliente> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = existente.get();
        cliente.setNombre(datos.getNombre());
        cliente.setApellido(datos.getApellido());
        cliente.setCorreo(datos.getCorreo());
        cliente.setTelefono(datos.getTelefono());
        return ResponseEntity.ok(cliente);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> actualizarParcial(@PathVariable Long id, @RequestBody Cliente datos) {
        Optional<Cliente> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = existente.get();
        if (datos.getNombre() != null) cliente.setNombre(datos.getNombre());
        if (datos.getApellido() != null) cliente.setApellido(datos.getApellido());
        if (datos.getCorreo() != null) cliente.setCorreo(datos.getCorreo());
        if (datos.getTelefono() != null) cliente.setTelefono(datos.getTelefono());
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Cliente> existente = buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientes.remove(existente.get());
        return ResponseEntity.noContent().build();
    }

    private Optional<Cliente> buscarPorId(Long id) {
        return clientes.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
