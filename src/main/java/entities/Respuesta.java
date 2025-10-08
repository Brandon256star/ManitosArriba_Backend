package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Respuesta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;
    private String Texto;
    private String PRespuesta;

}
