package com.mkyong.classes;


public class Topico {
    private String topico;
    private int salaId;
    private String tipo;

    public Topico() {
    }

    public Topico(String topico, int salaId, String tipo) {
        this.topico = topico;
        this.salaId = salaId;
        this.tipo = tipo;
    }

    public String getTopico() {
        return topico;
    }

    public int getSalaId() {
        return salaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
