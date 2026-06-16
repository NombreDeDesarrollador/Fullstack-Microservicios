package cl.duoc.asignaturas_service.controller;

import cl.duoc.asignaturas_service.dto.AsignaturaDTO;
import cl.duoc.asignaturas_service.dto.AsignaturaSeccionDTO;
import cl.duoc.asignaturas_service.model.Asignatura;
import cl.duoc.asignaturas_service.service.AsignaturaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Asignaturas", description = "Operaciones relacionadas con la gestión de asignaturas")
@RestController
@RequestMapping("/api/v1/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public ResponseEntity<List<Asignatura>> obtenerAsignaturas(){
        List<Asignatura> asignaturas = asignaturaService.obtenerAsignaturas();

        if (asignaturas.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(asignaturas);
    }

    @Operation (summary = "Obtiene una asignatura por su id ", description = "Retorna la informacion de una Integrante específico por su ID")
    @GetMapping("/{id}")
    public EntityModel<Asignatura> obtenerAsignatura(@PathVariable Integer id){
        Asignatura asignatura = asignaturaService.buscarAsignaturaPorId(id).orElseThrow();
        EntityModel<Asignatura> model = EntityModel.of(asignatura);

        model.add(
                linkTo(
                        methodOn(AsignaturaController.class).obtenerAsignatura(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8084" +
                        "/api/v1/asignaturas" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(AsignaturaController.class)
                                .obtenerAsignaturas()
                ).withRel("todos-las asignaturas")
        );
        return model;
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Asignatura> asignatura = asignaturaService.buscarAsignaturaPorId(id);

        if (asignatura.isPresent()){
            return ResponseEntity.ok(asignatura.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una Asignatura con el id " + id);
    }
*/
    @PostMapping
    public ResponseEntity<Asignatura> guardarAsignatura(@RequestBody @Valid Asignatura asignatura) {
        return ResponseEntity.ok(asignaturaService.guardarAsignatura(asignatura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Asignatura asignatura) {
        try {
            return ResponseEntity.ok(asignaturaService.actualizarAsignatura(id, asignatura));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una asignatura con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            asignaturaService.eliminarAsignatura(id);
            return ResponseEntity.ok("asignatura eliminada con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe una asignatura con el id " + id);
        }
    }

    @GetMapping("/{idAsignatura}/con-secciones")
    public ResponseEntity<AsignaturaSeccionDTO> obtenerConSecciones(
            @PathVariable Integer idAsignatura) {
        return ResponseEntity.ok(asignaturaService.obtenerAsignaturaConSecciones(idAsignatura));
    }
}