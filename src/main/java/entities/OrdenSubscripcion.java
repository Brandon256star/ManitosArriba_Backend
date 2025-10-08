package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class OrdenSubscripcion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenSubscripcion;
    private String Estado;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;

}
