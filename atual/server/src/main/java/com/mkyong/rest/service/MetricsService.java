package com.mkyong.rest.service;

import com.mkyong.bd.entidades.Métricas;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import com.mkyong.bd.repositorios.DispositivoRepository;

import com.mkyong.rest.classes.PreMetrica;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MetricsService {
    public static final List<Long> tempos = java.util.Collections.synchronizedList(new java.util.ArrayList<>());
    private final MétricasRepository metricRepo;
    private final SalaRepository salaRepo;
    private final DispositivoRepository dispRepo;
    public static Long startTime = null;

    public MetricsService(MétricasRepository metricRepo, SalaRepository salaRepo, DispositivoRepository dispRepo) {
        this.metricRepo = metricRepo;
        this.salaRepo = salaRepo;
        this.dispRepo = dispRepo;
    }

    public List<Métricas> getRaw(String deviceId, LocalDateTime from, LocalDateTime to) {
        return metricRepo.findByDispositivoIdAndTimestampBetween(Integer.parseInt(deviceId), from, to);
    }

    public List<Métricas> getAllMetrics(String deviceId) {
        return metricRepo.findByDispositivoId(Integer.parseInt(deviceId));
    }

    public void addMetric(PreMetrica metrica) {

        double temperatura = metrica.getTemperatura();
        double humidade = metrica.getHumidade();

        int idSala = metrica.getIdSala();
        int idDispositivo = metrica.getIdDispositivo();

        long recebido = metrica.getTimestamp();
        long now = Instant.now().toEpochMilli();
        if(startTime == null){
            startTime = now;
        }

        long diff = now - recebido;
        tempos.add(diff);
        

        if (dispRepo.existsByTipoAndSalaIdAndEstado("REST", idSala, "Ativo")) {
            metricRepo.save(new Métricas(humidade, temperatura, dispRepo.findById(idDispositivo), salaRepo.findSalasById(idSala)));
        }
    }

    public String getAverage(@RequestParam String level, @RequestParam String id, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        LocalDateTime fromDate;
        LocalDateTime toDate;
        if (from == null || to == null) {
            toDate = LocalDateTime.now();
            fromDate = toDate.minusHours(24);
        } else {
            fromDate = LocalDateTime.parse(from.replace(" ", "T"));
            toDate = LocalDateTime.parse(to.replace(" ", "T"));
        }
        List<Métricas> metricas = new ArrayList<>();
         switch (level.toLowerCase()) {
            case "sala":
                metricas = metricRepo.findBySala_IdAndTimestampBetween(Integer.parseInt(id), fromDate, toDate);
                break;
            case "departamento":
                metricas = metricRepo.findBySala_DepartamentoAndTimestampBetween(id, fromDate, toDate);
                break;
            case "piso":
                metricas = metricRepo.findBySala_AndarAndTimestampBetween(Integer.parseInt(id), fromDate, toDate);
                break;
            case "edificio":
                metricas = metricRepo.findBySala_EdificioAndTimestampBetween(String.valueOf(id), fromDate, toDate);
                break;
        };
        double humidadeMedia = 0;
        double temperaturaMedia = 0;
        int counter = 0;
        for(Métricas a : metricas){
            humidadeMedia += a.getHumidade();
            temperaturaMedia +=a.getTemperatura();
            counter ++;
        }
        if(counter == 0){
            return "Não existem metricas no intervalo";
        }else{
            humidadeMedia  = humidadeMedia/counter;
            temperaturaMedia = temperaturaMedia/counter;

            return "Temperatura média : " + temperaturaMedia + " Humidade Média : " + humidadeMedia;
        }
    }
    public List<Métricas> getMetricaBruta(@RequestParam String id, @RequestParam(required = false) String from, @RequestParam(required = false) String to){
        LocalDateTime fromDate;
        LocalDateTime toDate;
        if (from == null || to == null) {
            toDate = LocalDateTime.now();
            fromDate = toDate.minusHours(24);
        } else {
            fromDate = LocalDateTime.parse(from.replace(" ", "T"));
            toDate = LocalDateTime.parse(to.replace(" ", "T"));
        }
        return metricRepo.findByDispositivoIdAndTimestampBetween(Integer.parseInt(id),fromDate,toDate);
    }
    public String allDep(){
        return  metricRepo.findDistinctDepartamentos().toString();
    }
    public String allPiso(){
        return  metricRepo.findDistinctAndar().toString();
    }
    public String allEdificio(){
        return metricRepo.findDistinctEdificio().toString();
    }
}
