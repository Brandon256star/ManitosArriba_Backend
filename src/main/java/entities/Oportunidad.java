package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Oportunidad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOportunidad;
    private int Intento;
    private LocalDate FechaInicio;
}
