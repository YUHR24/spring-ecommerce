package com.yuhr.ecommerce.service;

import java.util.List;

import com.yuhr.ecommerce.model.Orden;
import com.yuhr.ecommerce.model.Usuario;

public interface IOrdenService {

    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);
}
