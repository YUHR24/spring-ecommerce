package com.yuhr.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhr.ecommerce.model.Orden;
import com.yuhr.ecommerce.model.Usuario;
import com.yuhr.ecommerce.service.IOrdenService;
import com.yuhr.ecommerce.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/registro")
    public String create() {

        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario) {
        log.info("Usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "/usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        log.info("Accesos : {}", usuario);

        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        log.info("Usuario obtenido de db: {}", user.get());

        if (user.isPresent()) {
            session.setAttribute("idusuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        } else {
            log.info("Usuario no existe");
        }

        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session) {

        model.addAttribute("session", session.getAttribute("idusuario"));

        Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString() ) ).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);

        return "/usuario/compras";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
        log.info("Id de la orden : {}", id);
        Optional<Orden> orden = ordenService.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());

        //Session
        model.addAttribute("session", session.getAttribute("idusuario"));

        return "usuario/detallecompra";
    }
    
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {

        session.removeAttribute("idusuario");
        // session.invalidate();

        return "redirect:/";
    }
    
    
    
    

}
