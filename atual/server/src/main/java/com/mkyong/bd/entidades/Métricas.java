package com.mkyong.bd.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Métricas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperatura;
    private double humidade;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    public Métricas() {

    }

    public Métricas(Double humidade, Double temperatura, Dispositivo dispositivo, Sala sala) {
        this.temperatura = temperatura;
        this.humidade = humidade;
        this.dispositivo = dispositivo;
        this.sala = sala;
        this.timestamp = LocalDateTime.now();
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
}

