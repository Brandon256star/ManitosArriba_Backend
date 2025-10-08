package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Evaluacion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvaluacion;
    private String Titulo;
    private String Descripcion;
    private LocalDate Fecha;
    private int Duracion;

}
