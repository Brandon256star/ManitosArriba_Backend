package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Clase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClase;
    private String ClasePersonalizada;

}
