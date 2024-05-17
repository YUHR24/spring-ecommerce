package com.yuhr.ecommerce.service;

import java.util.Optional;

import com.yuhr.ecommerce.model.Usuario;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);

}
