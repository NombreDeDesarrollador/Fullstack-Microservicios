package cl.duoc.integrantes_service.controller;

import cl.duoc.integrantes_service.dto.IntegranteRolDTO;
import cl.duoc.integrantes_service.model.Integrante;
import cl.duoc.integrantes_service.service.IntegranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.Optional;
@Tag(name = "Integrantes", description = "Operaciones relacionadas con la gestión de integrantes")
@RestController
@RequestMapping("api/v1/integrantes")
public class IntegranteController {

    @Autowired
    private IntegranteService integranteService;

    @GetMapping
    public ResponseEntity<?> obtenerIntegrantes() {
        List<Integrante> integrantes = integranteService.obtenerIntegrantes();
        if (integrantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay integrantes");
        }
        return ResponseEntity.ok(integrantes);
    }

    @Operation(summary = "Obtiene un integrante por su id ", description = "Retorna la informacios de un Integrante específico por su ID")
    @GetMapping("/{id}")
    public EntityModel<Integrante> obtenerIntegrante(@PathVariable Integer id){
        Integrante integrante = integranteService.buscarIntegrantePorId(id).orElseThrow();
        EntityModel<Integrante> model = EntityModel.of(integrante);

        model.add(
                linkTo(
                        methodOn(IntegranteController.class).obtenerIntegrante(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8087" +
                        "/api/v1/integrantes" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(IntegranteController.class)
                                .obtenerIntegrantes()
                ).withRel("todos-los integrantes")
        );
        return model;
    }

    //public ResponseEntity<?> buscarIntegrantePorId(@PathVariable Integer id) {
      //  Optional<Integrante> integrante = integranteService.buscarIntegrantePorId(id);
        //if (integrante.isPresent()) {
          //  return ResponseEntity.ok(integrante.get());
        //}
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un integrante con el ID " + id);
    //}

    @PostMapping
    public Integrante crearIntegrante(@RequestBody Integrante integrante) {
        return integranteService.guardarIntegrante(integrante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Integrante integrante) {
        try {
            return ResponseEntity.ok(integranteService.actualizarIntegrante(id, integrante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un integrante con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            integranteService.eliminarIntegrante(id);
            return ResponseEntity.ok("integrante eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un integrante con el id " + id);
        }
    }

    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<List<Integrante>> obtenerPorGrupo(@PathVariable Integer idGrupo) {
        return ResponseEntity.ok(integranteService.obtenerPorGrupo(idGrupo));
    }

    @GetMapping("/{idIntegrante}/con-rol")
    public ResponseEntity<IntegranteRolDTO> obtenerConRol(
            @PathVariable Integer idIntegrante) {
        return ResponseEntity.ok(integranteService.obtenerIntegranteConRol(idIntegrante));
    }
}