package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;


public class Apunte
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idApunte;
    private String Contenido;
    private LocalDate FechaCreacion;

}
