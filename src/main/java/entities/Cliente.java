package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Cliente
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Celular;
    private String email;
    private int Edad;

}
