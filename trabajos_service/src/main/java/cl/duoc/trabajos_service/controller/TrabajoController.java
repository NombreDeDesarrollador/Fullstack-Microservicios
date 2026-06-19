package cl.duoc.trabajos_service.controller;

import cl.duoc.trabajos_service.model.Trabajo;
import cl.duoc.trabajos_service.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;

@Tag(name = "Trabajos", description = "Operaciones relacionadas con la gestión de trabajos")
@RestController
@RequestMapping("api/v1/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    @GetMapping
    public ResponseEntity<?> obtenerTrabajos() {
        List<Trabajo> trabajos = trabajoService.obtenerTrabajos();
        if (trabajos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay trabajos");
        }
        return ResponseEntity.ok(trabajos);
    }

    @Operation(summary = "Obtiene un trabajo por su id ", description = "Retorna la informacion de un trabajo específico por su ID")
    @GetMapping("/{id}")
    public EntityModel<Trabajo> obtenerTrabajo(@PathVariable Integer id){
        Trabajo trabajo = trabajoService.buscarTrabajoPorId(id).orElseThrow();
        EntityModel<Trabajo> model = EntityModel.of(trabajo);

        model.add(
                linkTo(
                        methodOn(TrabajoController.class).obtenerTrabajo(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8090" +
                        "/api/v1/trabajos" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(TrabajoController.class)
                                .obtenerTrabajos()
                ).withRel("todos-los trabajos")
        );
        return model;
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        Optional<Trabajo> trabajo = trabajoService.buscarTrabajoPorId(id);
        if (trabajo.isPresent()) {
            return ResponseEntity.ok(trabajo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un trabajo con el id " + id);
    }*/

    @PostMapping
    public Trabajo crear(@RequestBody Trabajo trabajo) {
        return trabajoService.guardarTrabajo(trabajo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Trabajo trabajo) {
        try {
            return ResponseEntity.ok(trabajoService.actualizarTrabajo(id, trabajo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe trabajo con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            trabajoService.eliminarTrabajo(id);
            return ResponseEntity.ok("Trabajo eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un trabajo con el id " + id);
        }
    }

    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<Trabajo> obtenerPorGrupo(@PathVariable Integer idGrupo) {
        return ResponseEntity.ok(trabajoService.obtenerPorGrupo(idGrupo));
    }
}