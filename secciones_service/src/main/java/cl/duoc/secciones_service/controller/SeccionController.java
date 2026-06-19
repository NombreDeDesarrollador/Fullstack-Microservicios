package cl.duoc.secciones_service.controller;

import cl.duoc.secciones_service.dto.SeccionIntegranteDTO;
import cl.duoc.secciones_service.model.Seccion;
import cl.duoc.secciones_service.service.SeccionService;
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

@Tag(name = "Secciones", description = "Operaciones relacionadas con la gestión de secciones")
@RestController
@RequestMapping("/api/v1/secciones")
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @GetMapping
    public ResponseEntity<List<Seccion>> obtenerSecciones(){
        List<Seccion> secciones = seccionService.obtenerSecciones();

        if (secciones.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(secciones);
    }

    @Operation(summary = "Obtiene una seccion por su id ", description = "Retorna la informacios de una seccion específico por su ID")
    @GetMapping("/{id}")
    public EntityModel<Seccion> obtenerSeccion(@PathVariable Integer id){
        Seccion seccion = seccionService.buscarSeccionPorId(id).orElseThrow();
        EntityModel<Seccion> model = EntityModel.of(seccion);

        model.add(
                linkTo(
                        methodOn(SeccionController.class).obtenerSeccion(id)
                ).withSelfRel()
        );

        model.add(
                Link.of("http://localhost:8089" +
                        "/api/v1/secciones" + id, "eliminar")
        );

        model.add(
                linkTo(
                        methodOn(SeccionController.class)
                                .obtenerSecciones()
                ).withRel("todos-los secciones")
        );
        return model;
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> obtenerSeccionPorId(@PathVariable Integer id) {
        Optional<Seccion> seccion = seccionService.buscarSeccionPorId(id);

        if (seccion.isPresent()) {
            return ResponseEntity.ok(seccion.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una seccion con el ID " + id);
    }*/

    @PostMapping
    public ResponseEntity<Seccion> crearSeccion(@RequestBody @Valid Seccion seccion) {
        return ResponseEntity.ok(seccionService.guardarSeccion(seccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Seccion seccion) {
        try {
            return ResponseEntity.ok(seccionService.actualizarSeccion(id, seccion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe seccion con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSeccion(@PathVariable Integer id) {
        try {
            seccionService.eliminarSeccion(id);
            return ResponseEntity.ok("Seccion eliminada con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe seccion con el id "+id);
        }
    }

    @GetMapping("/{idSeccion}/con-integrante/{idIntegrante}")
    public ResponseEntity<SeccionIntegranteDTO> getSeccionConIntegrante(
            @PathVariable Integer idSeccion, @PathVariable Integer idIntegrante){

        SeccionIntegranteDTO dto = seccionService.obtenerSeccionConIntegrante(idSeccion, idIntegrante);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/asignatura/{idAsignatura}")
    public ResponseEntity<List<Seccion>> obtenerPorAsignatura(
            @PathVariable Integer idAsignatura) {
        return ResponseEntity.ok(seccionService.obtenerPorAsignatura(idAsignatura));
    }

    @GetMapping("/profesor/{idProfesor}")
    public ResponseEntity<List<Seccion>> obtenerPorProfesor(@PathVariable Integer idProfesor) {
        return ResponseEntity.ok(seccionService.obtenerPorProfesor(idProfesor));
    }

}
