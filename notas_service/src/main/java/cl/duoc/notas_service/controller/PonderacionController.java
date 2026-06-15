package cl.duoc.notas_service.controller;

import cl.duoc.notas_service.model.Nota;
import cl.duoc.notas_service.model.Ponderacion;
import cl.duoc.notas_service.service.PonderacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ponderaciones")
public class PonderacionController {

    @Autowired
    private PonderacionService ponderacionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPonderacion(@PathVariable Integer id) {
        Optional<Ponderacion> ponderacionOptional = ponderacionService.buscarPonderacionPorId(id);

        if (ponderacionOptional.isPresent()) {
            return ResponseEntity.ok(ponderacionOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro una ponderacion con el ID: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Ponderacion> ponderaciones = ponderacionService.obtenerPonderacion();
        if (ponderaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ponderaciones");
        }
        return ResponseEntity.ok(ponderaciones);
    }

    @PostMapping
    public ResponseEntity<Ponderacion> guardarPonderacion(@RequestBody @Valid Ponderacion ponderacion) {
        return ResponseEntity.ok(ponderacionService.guardarPonderacion(ponderacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Ponderacion ponderacion) {
        try {
            return ResponseEntity.ok(ponderacionService.actualizarPonderacion(id, ponderacion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una ponderacion con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            ponderacionService.eliminarPonderacion(id);
            return ResponseEntity.ok("ponderacion eliminada con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe una ponderacion con el id " + id);
        }
    }
}
