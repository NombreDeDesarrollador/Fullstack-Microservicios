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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadservice;

    @GetMapping
    public ResponseEntity<List<Unidad>> listarUnidades(){
        List<Unidad> unidades = unidadservice.obtenerUnidades();

        if (unidades.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(unidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUnidadPorId (@PathVariable Integer id){
        Optional<Unidad> unidad = unidadservice.buscarUnidadPorId(id);
        if (unidad.isPresent()){
            return ResponseEntity.ok(unidad.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una unidad con el ID " + id);
    }

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
