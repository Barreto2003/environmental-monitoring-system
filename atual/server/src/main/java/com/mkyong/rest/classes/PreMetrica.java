package com.mkyong.rest.classes;

public class PreMetrica {
    private double temperatura;
    private double humidade;
    private long timestamp;
    private int idSala;
    private int idDispositivo;

    public PreMetrica(double temperatura, double humidade, long timestamp, int idSala, int idDispositivo) {
        this.temperatura = temperatura;
        this.humidade = humidade;
        this.timestamp = timestamp;
        this.idSala = idSala;
        this.idDispositivo = idDispositivo;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getBytes() {
        String payload = String.format("temperatura;"+temperatura+";humidade;"+humidade+";timestamp;"+getTimestamp());
        return payload.getBytes();
    }
    @Override
    public String toString(){
        return "temperatura;"+temperatura+";humidade;"+humidade+";timestamp;"+getTimestamp()+";idSala;" + idSala + ";idDispositivo;" + idDispositivo;
    }

}
