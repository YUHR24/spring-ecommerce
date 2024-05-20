package com.yuhr.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhr.ecommerce.model.Orden;
import com.yuhr.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        
        return ordenRepository.findAll();
    }

    // public String generarNumeroOrden() {
    //     int numero = 0;
    //     String numeroConcatenado = "";

    //     List<Orden> ordenes = findAll();

    //     List<Integer> numeros = new ArrayList<Integer>();

    //     ordenes.stream().forEach(o -> numeros.add( Integer.parseInt( o.getNumero())));

    //     if (ordenes.isEmpty()) {
    //         numero = 1;
    //     } else {
    //         numero = numeros.stream().max(Integer::compare).get();
    //         numero++;
    //     }

    //     if (numero < 10) { //0000001000
	// 		numeroConcatenado = "000000000" + String.valueOf(numero);
	// 	}else if(numero < 100) {
	// 		numeroConcatenado = "00000000"+String.valueOf(numero);
	// 	}else if(numero < 1000) {
	// 		numeroConcatenado = "0000000"+String.valueOf(numero);
	// 	}else if(numero < 10000) {
	// 		numeroConcatenado = "0000000"+String.valueOf(numero);
	// 	}

    //     return numeroConcatenado;
    // }


    public String generarNumeroOrden() {
        int numero = 0;
        String numeroConcatenado = "";
    
        List<Orden> ordenes = findAll();
    
        List<Integer> numeros = new ArrayList<Integer>();
    
        ordenes.stream()
               .map(Orden::getNumero) // Obtener el campo numero de la orden
               .filter(num -> num != null && !num.trim().isEmpty()) // Filtrar nulos y vacíos
               .forEach(num -> numeros.add(Integer.parseInt(num))); // Convertir y agregar a la lista
    
        if (numeros.isEmpty()) {
            numero = 1;
        } else {
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }
    
        // Usar String.format para asegurar que el número tenga 10 dígitos
        numeroConcatenado = String.format("%010d", numero);
    
        return numeroConcatenado;
    }
    

}