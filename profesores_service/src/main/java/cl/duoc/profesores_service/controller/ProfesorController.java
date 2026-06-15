package cl.duoc.profesores_service.controller;

import cl.duoc.profesores_service.dto.ProfesorSeccionDTO;
import cl.duoc.profesores_service.model.Profesor;
import cl.duoc.profesores_service.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Profesor> profesores = profesorService.obtenerProfesores();
        if (profesores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay profesores");
        }
        return ResponseEntity.ok(profesores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Integer id) {
        Optional<Profesor> profesorOptional = profesorService.buscarProfesorPorId(id);

        if (profesorOptional.isPresent()) {
            return ResponseEntity.ok(profesorOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe el profesor con el ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody @Valid Profesor profesor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profesorService.guardarProfesor(profesor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProfesor(@PathVariable int id, @RequestBody Profesor profesor) {
        try {
            return ResponseEntity.ok(profesorService.actualizarProfesor(id, profesor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe profesor con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProfesor(@PathVariable Integer id) {
        try {
            profesorService.eliminarProfesor(id);
            return ResponseEntity.ok("profesor eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un profesor con el id " + id);
        }
    }

    @GetMapping("/{idProfesor}/con-secciones")
    public ResponseEntity<ProfesorSeccionDTO> obtenerConSecciones(
            @PathVariable Integer idProfesor) {
        return ResponseEntity.ok(profesorService.obtenerProfesorConSecciones(idProfesor));
    }
}