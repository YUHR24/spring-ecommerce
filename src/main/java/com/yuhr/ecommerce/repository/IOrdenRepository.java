package com.yuhr.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuhr.ecommerce.model.Orden;
import com.yuhr.ecommerce.model.Usuario;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

    List<Orden> findByUsuario(Usuario usuario);

}
