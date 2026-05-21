package com.example.restCli.classes;

public class Sala {
    private int id;

    private int numero;

    private String edificio;

    private String tipo;

    private int andar;

    private String departamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    @Override
    public String toString() {
        return  "id- "+this.id+" SALA Nº " + this.numero + " EDIFICIO: " + this.edificio + " ANDAR: " + this.andar + " TIPO: " + this.tipo +" DEPARTAMENTO : " + this.departamento;
    }
}
