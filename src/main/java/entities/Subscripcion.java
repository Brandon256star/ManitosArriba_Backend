package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Subscripcion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSubscripcion;
    private String Nombre;
    private String Descripcion;
    private Float Precio;

}
