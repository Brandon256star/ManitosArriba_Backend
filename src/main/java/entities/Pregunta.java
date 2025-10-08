package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Pregunta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;
    private String Enunciado;
    private String Tipo;
    private Float Puntaje;

}
