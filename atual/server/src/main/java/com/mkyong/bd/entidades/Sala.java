package com.mkyong.bd.entidades;


import javax.persistence.*;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numero;

    private String edificio;

    private String tipo;

    private int andar;

    private String departamento;

    public Sala(){

    }
    public Sala(int numero, String edificio, String tipo, int andar, String departamento) {
        this.numero = numero;
        this.edificio = edificio;
        this.tipo = tipo;
        this.andar = andar;
        this.departamento = departamento;
    }


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

