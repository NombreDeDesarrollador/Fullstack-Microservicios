package cl.duoc.integrantes_service.service;

import cl.duoc.integrantes_service.model.Integrante;
import cl.duoc.integrantes_service.repository.IntegranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IntegranteServiceTest {

    @Mock
    private IntegranteRepository integranteRepository;

    @InjectMocks
    private IntegranteService integranteService;

    private Integrante integranteMock;

    @BeforeEach
    void setUp() {
        integranteMock = new Integrante();
        integranteMock.setIdIntegrante(1);
        integranteMock.setNombre("Juan");
        integranteMock.setApellido("Pérez");
        integranteMock.setRutCuerpo(12345678);
        integranteMock.setRutDv("9");
        integranteMock.setCorreoElectronico("juan.perez@example.com");
        integranteMock.setIdRol(1);
        integranteMock.setIdGrupo(2);
        integranteMock.setDisponibilidad(Integrante.Disponibilidad.ALTA);
        integranteMock.setIdNota(3);
    }

    @Test
    void testObtenerTodosLosIntegrantes() {
        when(integranteRepository.findAll()).thenReturn(Arrays.asList(integranteMock));

        List<Integrante> resultado = integranteService.obtenerIntegrantes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(integranteRepository, times(1)).findAll();
    }

    @Test
    void testGuardarIntegrante() {
        when(integranteRepository.save(any(Integrante.class))).thenReturn(integranteMock);

        Integrante resultado = integranteService.guardarIntegrante(integranteMock);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(integranteRepository, times(1)).save(any(Integrante.class));
    }
}
