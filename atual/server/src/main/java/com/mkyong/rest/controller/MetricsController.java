package com.mkyong.rest.controller;

import com.mkyong.bd.entidades.Métricas;
import com.mkyong.rest.service.MetricsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.mkyong.rest.classes.PreMetrica;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping("/ingest")
    public void addMetric(@RequestBody PreMetrica a) {
        this.metricsService.addMetric(a);
    }

    @GetMapping("/average")
    public String getAverage(@RequestParam String level, @RequestParam String id, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        return metricsService.getAverage(level, id, from, to);
    }
    @GetMapping("/raw")
    public List<Métricas> getMetricaBruta(@RequestParam String id, @RequestParam(required = false) String from, @RequestParam(required = false) String to){
        return metricsService.getMetricaBruta(id, from, to);
    }

    @GetMapping("/dep")
    public String allDep(){
        return metricsService.allDep();
    }
    @GetMapping("/piso")
    public String allPiso(){
        return metricsService.allPiso();
    }

    @GetMapping("/edificio")
    public String allEdificio(){
        return metricsService.allEdificio();
    }


}
