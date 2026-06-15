package cl.duoc.roles_service.controller;

import cl.duoc.roles_service.model.Rol;
import cl.duoc.roles_service.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Rol> roles = rolService.listarTodos();
        if (roles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay roles");
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable int id) {
        Optional<Rol> rol = rolService.buscarPorId(id);
        if (rol.isPresent()) {
            return ResponseEntity.ok(rol.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un rol con el id " + id);
    }

    @PostMapping
    public Rol crearRol(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable int id, @RequestBody Rol rol) {
        try {
            return ResponseEntity.ok(rolService.actualizar(id, rol));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe rol con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable Integer id) {
        try {
            rolService.eliminarRol(id);
            return ResponseEntity.ok("Rol eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un rol con el id " + id);
        }
    }
}