package com.mkyong.rest.controller;

import com.mkyong.bd.entidades.Sala;
import com.mkyong.rest.service.RoomsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping("/free")
    public Object getRawMetrics() {
        return roomsService.salasLivres();
    }
    @GetMapping("/comMetricas")
    public List<Sala> salasOcupada(){
        return roomsService.salasQueTemMetricas();
    }


//    @PostMapping("/topico")
//    public boolean adicionarSub(@RequestBody Topico topico) {
//        System.out.println("Entramos no adicionar sub");
//        return roomsService.addSubscriber(topico);
//    }


}
