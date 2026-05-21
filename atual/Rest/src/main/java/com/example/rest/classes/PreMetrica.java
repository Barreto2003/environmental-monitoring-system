package com.example.rest.classes;

public class PreMetrica {
    private double temperatura;
    private double humidade;
    private Long timestamp;
    private int idSala;
    private int idDispositivo;

    public PreMetrica(double temperatura, double humidade, Long timestamp, int idSala, int idDispositivo) {
        this.temperatura = temperatura;
        this.humidade = humidade;
        this.timestamp = timestamp;
        this.idSala = idSala;
        this.idDispositivo = idDispositivo;
    }
    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumidade() {
        return humidade;
    }

    public void setHumidade(double humidade) {
        this.humidade = humidade;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getBytes() {
        String payload = String.format("temperatura;"+temperatura+";humidade;"+humidade+";timestamp;"+getTimestamp());
        return payload.getBytes();
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    @Override
    public String toString(){
        return "temperatura;"+temperatura+";humidade;"+humidade+";timestamp;"+getTimestamp()+";idSala;" + idSala + ";idDispositivo;" + idDispositivo;
    }

}
