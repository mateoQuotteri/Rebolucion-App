package com.rebolucion.app.Controladores;


import com.rebolucion.app.Auth.AuthResponse;
import com.rebolucion.app.Auth.Request.LoginRequest;
import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  com.rebolucion.app.Servicio.AuthServicio;
@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:5173")

public class AuthController {
    @Autowired
    private final AuthServicio authService;

    public AuthController(AuthServicio authService) {
       this.authService = authService;
    }


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UsuarioEntradaDto request){
        System.out.printf("Informacion recibida: " + request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
