package com.example.restCli.classes;

import com.example.restCli.classes.*;

import java.time.LocalDateTime;


public class Métrica {


    private Long id;

    private double temperatura;
    private double humidade;
    private LocalDateTime timestamp;

    private Dispositivo dispositivo;


    private Sala sala;


    public Métrica(Double humidade, Double temperatura, Dispositivo dispositivo, Sala sala) {
        this.temperatura = temperatura;
        this.humidade = humidade;
        this.dispositivo = dispositivo;
        this.sala = sala;
        this.timestamp = LocalDateTime.now();
    }

    public Métrica() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "\n--- Métrica ---" + "\nID: " + id + "\nTemperatura: " + temperatura + "\nHumidade: " + humidade + "\nTimestamp: " + timestamp + "\nDispositivo ID: " + dispositivo.getId() +"\nSala ID: " + sala.getId() + "\n----------------\n";
    }
}

