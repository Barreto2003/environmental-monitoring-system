package com.mkyong.bd.repositorios;

import com.mkyong.bd.entidades.Sala;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalaRepository extends CrudRepository<Sala, Integer> {
    List<Sala> findAll();
    Sala findSalasById(int id);
    Sala findByNumero(int numero);
    List<Sala> findByEdificio(String edificio);
    List<Sala> findByAndar(int andar);
    List<Sala> findByTipo(String tipo);
    // Combinações também funcionam:
    Sala findByNumeroAndEdificioAndTipoAndAndar(int numero, String edificio, String tipo, int andar);



}
