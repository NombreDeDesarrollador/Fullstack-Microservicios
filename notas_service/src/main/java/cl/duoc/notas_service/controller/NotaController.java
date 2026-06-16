package cl.duoc.notas_service.controller;

import cl.duoc.notas_service.dto.NotasIntegranteDTO;
import cl.duoc.notas_service.model.Nota;
import cl.duoc.notas_service.service.NotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.Link;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;
@Tag(name = "Notas", description = "Operaciones relacionadas con la gestión de notas")
@RestController
@RequestMapping("/api/v1/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;


    @GetMapping
    public ResponseEntity<?> obtenerNotas() {
        List<Nota> notas = notaService.obtenerNotas();
        if (notas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay notas");
        }
        return ResponseEntity.ok(notas);
    }

    @Operation(summary = "Obtiene una nota por su id ", description = "Retorna la informacios de una nota específica por su ID")
    @GetMapping("/{id}")
    public EntityModel<Nota> obtenerNota(@PathVariable Integer id){
        Nota nota = notaService.buscarNotaPorId(id).orElseThrow();
        EntityModel<Nota> model = EntityModel.of(nota);

        model.add(
                linkTo(
                        methodOn(NotaController.class).obtenerNota(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8083" +
                        "/api/v1/notas" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(NotaController.class)
                                .obtenerNotas()
                ).withRel("todos-los integrantes")
        );
        return model;
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> obtenerNotaPorId(@PathVariable Integer id) {
        Optional<Nota> notaOptional = notaService.buscarNotaPorId(id);

        if (notaOptional.isPresent()) {
            return ResponseEntity.ok(notaOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una nota con el ID: " + id);
        }
    }*/


    @PostMapping
    public ResponseEntity<Nota> guardarNota(@RequestBody @Valid Nota nota) {
        return ResponseEntity.ok(notaService.guardarNota(nota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Nota nota) {
        try {
            return ResponseEntity.ok(notaService.actualizarNota(id, nota));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una nota con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            notaService.eliminarNota(id);
            return ResponseEntity.ok("nota eliminada con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe una nota con el id " + id);
        }
    }

    @GetMapping("{idIntegrante}/con-integrante")
    public ResponseEntity<NotasIntegranteDTO> getNotasConIntegrante(@PathVariable Integer idIntegrante) {
        NotasIntegranteDTO dto = notaService.obtenerNotasConIntegrante(idIntegrante);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }
}