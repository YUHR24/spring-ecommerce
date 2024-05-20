package com.yuhr.ecommerce.service;

import java.util.List;

import com.yuhr.ecommerce.model.Orden;

public interface IOrdenService {

    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();

}
