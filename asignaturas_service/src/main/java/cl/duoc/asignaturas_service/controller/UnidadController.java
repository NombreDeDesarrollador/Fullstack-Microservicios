package cl.duoc.asignaturas_service.controller;

import cl.duoc.asignaturas_service.dto.AsignaturaDTO;
import cl.duoc.asignaturas_service.dto.UnidadDTO;
import cl.duoc.asignaturas_service.model.Unidad;
import cl.duoc.asignaturas_service.service.UnidadService;
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

@Tag(name = "Unidades", description = "Operaciones relacionadas con la gestión de unidades")
@RestController
@RequestMapping("/api/v1/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadservice;

    @GetMapping
    public ResponseEntity<List<Unidad>> obtenerUnidades(){
        List<Unidad> unidades = unidadservice.obtenerUnidades();

        if (unidades.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(unidades);
    }

    @Operation(summary = "Obtiene una unidad por su id ", description = "Retorna la informacios de una Unidad específica por su ID")
    @GetMapping("/{id}")
    public EntityModel<Unidad> obtenerUnidad(@PathVariable Integer id){
        Unidad unidad = unidadservice.buscarUnidadPorId(id).orElseThrow();
        EntityModel<Unidad> model = EntityModel.of(unidad);

        model.add(
                linkTo(
                        methodOn(UnidadController.class).obtenerUnidad(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8084" +
                        "/api/v1/unidades" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(UnidadController.class)
                                .obtenerUnidades()
                ).withRel("todos-las unidades")
        );
        return model;
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> buscarUnidadPorId (@PathVariable Integer id){
        Optional<Unidad> unidad = unidadservice.buscarUnidadPorId(id);
        if (unidad.isPresent()){
            return ResponseEntity.ok(unidad.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una unidad con el ID " + id);
    }*/

    @PostMapping
    public ResponseEntity<Unidad> crearUnidad(@RequestBody @Valid Unidad unidad){
        return ResponseEntity.ok(unidadservice.guardarUnidad(unidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Unidad unidad) {
        try {
            return ResponseEntity.ok(unidadservice.actualizarUnidades(id, unidad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una unidad con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            unidadservice.eliminarUnidad(id);
            return ResponseEntity.ok("unidad eliminada con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe una unidad con el id " + id);
        }
    }



}
