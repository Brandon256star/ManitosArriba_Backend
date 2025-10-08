package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Unidad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUnidad;
    private String Titulo;
    private String Descripcion;
    private int Nivel;
    private String Categoria;
    private int Duracion;
    private String Estado;


}
