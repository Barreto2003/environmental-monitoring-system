package mqtt.classes;

import java.time.LocalDateTime;

public class Métrica {
    private double temperatura;
    private double humidade;
    private Long timestamp;

    public Métrica(double temperatura, double humidade, Long timestamp) {
        this.temperatura = temperatura;
        this.humidade = humidade;
        this.timestamp = timestamp;
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

}
