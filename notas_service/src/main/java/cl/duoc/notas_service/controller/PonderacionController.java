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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;

@Tag(name = "Ponderaciones", description = "Operaciones relacionadas con la gestión de ponderaciones")
@RestController
@RequestMapping("/api/v1/ponderaciones")
public class PonderacionController {

    @Autowired
    private PonderacionService ponderacionService;

    /*@GetMapping("/{id}")
    public ResponseEntity<?> obtenerPonderacion(@PathVariable Integer id) {
        Optional<Ponderacion> ponderacionOptional = ponderacionService.buscarPonderacionPorId(id);

        if (ponderacionOptional.isPresent()) {
            return ResponseEntity.ok(ponderacionOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro una ponderacion con el ID: " + id);
        }
    }*/

    @Operation(summary = "Obtiene un integrante por su id ", description = "Retorna la informacios de un Integrante específico por su ID")
    @GetMapping("/{id}")
    public EntityModel<Ponderacion> obtenerPonderacion(@PathVariable Integer id){
        Ponderacion ponderacion = ponderacionService.buscarPonderacionPorId(id).orElseThrow();
        EntityModel<Ponderacion> model = EntityModel.of(ponderacion);

        model.add(
                linkTo(
                        methodOn(PonderacionController.class).obtenerPonderacion(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8083" +
                        "/api/v1/ponderaciones" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(PonderacionController.class)
                                .obtenerPonderaciones()
                ).withRel("todos-los ponderaciones")
        );
        return model;
    }

    @GetMapping
    public ResponseEntity<?> obtenerPonderaciones() {
        List<Ponderacion> ponderaciones = ponderacionService.obtenerPonderacion();
        if (ponderaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ponderaciones");
        }
        return ResponseEntity.ok(ponderaciones);
    }

    @PostMapping
    public ResponseEntity<Ponderacion> crearPonderacion(@RequestBody @Valid Ponderacion ponderacion) {
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
