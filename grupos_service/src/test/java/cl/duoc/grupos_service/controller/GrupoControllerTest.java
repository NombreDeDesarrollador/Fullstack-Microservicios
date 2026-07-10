package cl.duoc.grupos_service.controller;

import cl.duoc.grupos_service.dto.GrupoIntegranteDTO;
import cl.duoc.grupos_service.dto.GrupoTrabajoDTO;
import cl.duoc.grupos_service.model.Grupo;
import cl.duoc.grupos_service.service.GrupoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GrupoController.class)
public class GrupoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GrupoService grupoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Grupo grupoMock;

    @BeforeEach
    void setUp() {
        grupoMock = new Grupo();
        grupoMock.setIdGrupo(1);
        grupoMock.setNombreGrupo("Grupo B");
        grupoMock.setCapacidadMaxima(5);
        grupoMock.setFechaCreacion(LocalDate.now());
        grupoMock.setGrupoLleno(false);
    }

    @Test
    void testObtenerGrupos() throws Exception {
        when(grupoService.obtenerGrupos()).thenReturn(Arrays.asList(grupoMock));

        mockMvc.perform(get("/api/v1/grupos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreGrupo").value("Grupo B"));
    }

    @Test
    void testObtenerGrupo() throws Exception {
        when(grupoService.buscarGrupoPorId(1)).thenReturn(Optional.of(grupoMock));

        mockMvc.perform(get("/api/v1/grupos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreGrupo").value("Grupo B"));
    }

    @Test
    void testCrearGrupo() throws Exception {
        when(grupoService.guardarGrupo(any(Grupo.class))).thenReturn(grupoMock);

        mockMvc.perform(post("/api/v1/grupos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(grupoMock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreGrupo").value("Grupo B"));
    }
}
