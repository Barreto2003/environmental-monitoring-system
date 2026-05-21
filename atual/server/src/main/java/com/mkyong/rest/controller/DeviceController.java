package com.mkyong.rest.controller;

import com.mkyong.bd.entidades.Dispositivo;
import com.mkyong.bd.entidades.Sala;
import com.mkyong.rest.service.DevicesService;
import com.mkyong.rest.service.MetricsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DevicesService devicesService;

    public DeviceController(DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @PostMapping
    public String activateDevice(@RequestBody Integer id){
        devicesService.activate(id);
        return "Ativado com sucesso";
    }
    @GetMapping
    public Object getAllDisp(){
        return devicesService.getAllDisp();
    }
    @GetMapping("/{id}")
    public Object getDevice(@PathVariable int id) {
        return devicesService.getById(id);
    }
    @PutMapping("/{id}")
    public Boolean update( @PathVariable int id, @RequestBody Dispositivo d) {
        return devicesService.update(id, d);
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id){
        return devicesService.delete(id);
    }

    @GetMapping("/comMetricas")
    public List<Integer> comMetricas(){
        return devicesService.comMetricas();
    }

    @GetMapping("/ativos")
    public Object ativos(){
        return devicesService.ativos();
    }

}
