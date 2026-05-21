package com.mkyong.bd.entidades;

import javax.persistence.*;

@Entity
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;
    private String tipo;

    private String estado;

    public void setId(int id) {
        this.id = id;

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Dispositivo(){

    }
    public Dispositivo(String tipo, Sala s, String estado){
        this.tipo = tipo;
        this.sala = s;
        this.estado = estado;
        this.nome = getSala() + getTipo();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return "ID: " +getId()+ "NOME: "+ getNome() +  " TIPO: " + getTipo()+" EDIFICIO: " + getSala().getEdificio() +" SALA: " + getSala().getNumero() +" DEPARTAMENTO: " + getSala().getDepartamento() + " ANDAR: " + getSala().getAndar() + " TIPO DE SALA: " + getSala().getTipo();
    }
}
