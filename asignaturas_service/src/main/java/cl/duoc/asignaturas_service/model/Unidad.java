package cl.duoc.asignaturas_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "unidades")
@Data
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidad")
    private Integer idUnidad; // Cambiado de int a Integer

    @Column(name = "idAsignatura")
    @NotNull(message = "el ID de la asignatura es obligatorio") // Cambiado de int a Integer para que valide nulidad
    private Integer idAsignatura;

    @Column(name = "numeroUnidad")
    @NotNull(message = "el número de la unidad es obligatorio") // Cambiado de int a Integer para que valide nulidad
    private Integer numeroUnidad;

    @Column(name = "nombreUnidad")
    @NotBlank(message = "el nombre de la unidad no puede estar vacio")
    private String nombreUnidad;
}
