package com.rebolucion.app.Servicio;




import com.rebolucion.app.Auth.AuthResponse;
import com.rebolucion.app.Auth.Request.LoginRequest;
import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import com.rebolucion.app.Entidades.Rol;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Jwt.JwtService;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServicio {
    private final UsuarioRepositorio usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    //@Autowired
    // private EmailService emailService;
    private final Validator validator;




    public AuthResponse register(@Valid UsuarioEntradaDto request) {
        //Validando el DTO
        //Comentado ya que la validacion manual esta causando interferencias al momento de agregar un segundo apellido. Con las anotaciones @Valid ya es suficiente
        /*Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()){
            //Manejar las violaciones segun necesidades
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequest> violation : violations){
                sb.append(violation.getMessage()).append(" ");
            }
            throw new IllegalArgumentException(sb.toString());
        }*/

        Rol rol = (request.getRol() != null) ? request.getRol() : Rol.USUARIO;


        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .rol(rol)
                .contra(passwordEncoder.encode(request.getContra()))  // Encriptar la contraseña
                .build();

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);


        //Mandar mail al usuario - PENDIENTE
        //  String subject = "Confirmación de registro";
        // String text = "Hola " + usuario.getNombre() + ",\n\nGracias por registrarte en sonidos prestados.";
        //   emailService.sendEmail(usuario.getEmail(), subject, text);



        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

   public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContra()));
        UserDetails user = usuarioRepository.findUsuarioByCorreo(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
