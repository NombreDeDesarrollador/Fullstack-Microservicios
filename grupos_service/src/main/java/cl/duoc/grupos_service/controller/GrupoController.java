package cl.duoc.grupos_service.controller;

import cl.duoc.grupos_service.dto.GrupoIntegranteDTO;
import cl.duoc.grupos_service.dto.GrupoTrabajoDTO;
import cl.duoc.grupos_service.model.Grupo;
import cl.duoc.grupos_service.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Grupo> grupos = grupoService.obtenerGrupos();
        if (grupos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay grupo");
        }
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerGrupoPorId(@PathVariable Integer id) {
        Optional<Grupo> grupo = grupoService.obtenerGrupoPorId(id);
        if (grupo.isPresent()) {
            return ResponseEntity.ok(grupo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe grupo con el ID "+ id);
    }

    @PostMapping
    public Grupo crearGrupo(@RequestBody Grupo grupo) {
        return grupoService.guardarGrupo(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Grupo grupo) {
        try {
            return ResponseEntity.ok(grupoService.actualizarGrupo(id, grupo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe grupo con el id " +id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            grupoService.eliminarGrupo(id);
            return ResponseEntity.ok("grupo eliminado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe un grupo con el id " + id);
        }
    }

    @GetMapping("/{idGrupo}/con-integrantes")
    public ResponseEntity<GrupoIntegranteDTO> getGrupoConIntegrantes(
            @PathVariable Integer idGrupo) {
        GrupoIntegranteDTO dto = grupoService.obtenerGrupoConIntegrantes(idGrupo);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{idGrupo}/con-trabajo")
    public ResponseEntity<GrupoTrabajoDTO> obtenerConTrabajo(
            @PathVariable Integer idGrupo) {
        return ResponseEntity.ok(grupoService.obtenerGrupoConTrabajo(idGrupo));
    }
}