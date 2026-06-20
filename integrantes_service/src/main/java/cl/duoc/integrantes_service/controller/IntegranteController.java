package cl.duoc.integrantes_service.controller;

import cl.duoc.integrantes_service.dto.IntegranteRolDTO;
import cl.duoc.integrantes_service.model.Integrante;
import cl.duoc.integrantes_service.service.IntegranteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

@Tag(name = "Integrantes", description = "Operaciones relacionadas con la gestión de integrantes")
@RestController
@RequestMapping("api/v1/integrantes")
public class IntegranteController {

    @Autowired
    private IntegranteService integranteService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprobado"),
            @ApiResponse(responseCode = "404", description = "No se encontraron integrantes")
    })
    public ResponseEntity<?> obtenerIntegrantes() {
        List<Integrante> integrantes = integranteService.obtenerIntegrantes();
        if (integrantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay integrantes");
        }
        return ResponseEntity.ok(integrantes);
    }

    @Operation(summary = "Obtiene un integrante por su id ", description = "Retorna la informacios de un Integrante específico por su ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprobado"),
            @ApiResponse(responseCode = "404", description = "Integrante no encontrado")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crearIntegrante(@Valid @RequestBody Integrante integrante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(integranteService.guardarIntegrante(integrante));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprobado"),
            @ApiResponse(responseCode = "404", description = "Integrante no encontrado")
    })
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Integrante integrante) {
        try {
            return ResponseEntity.ok(integranteService.actualizarIntegrante(id, integrante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un integrante con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "Integrante no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            integranteService.eliminarIntegrante(id);
            return ResponseEntity.ok("integrante eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un integrante con el id " + id);
        }
    }

    @GetMapping("/grupo/{idGrupo}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprobado"),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado")
    })
    public ResponseEntity<List<Integrante>> obtenerPorGrupo(@PathVariable Integer idGrupo) {
        return ResponseEntity.ok(integranteService.obtenerPorGrupo(idGrupo));
    }

    @GetMapping("/{idIntegrante}/con-rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprobado"),
            @ApiResponse(responseCode = "404", description = "Integrante no encontrado")
    })
    public ResponseEntity<IntegranteRolDTO> obtenerConRol(
            @PathVariable Integer idIntegrante) {
        return ResponseEntity.ok(integranteService.obtenerIntegranteConRol(idIntegrante));
    }
}