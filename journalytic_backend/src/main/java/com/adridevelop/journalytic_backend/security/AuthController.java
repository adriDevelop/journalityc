package com.adridevelop.journalytic_backend.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.adridevelop.journalytic_backend.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class AuthController {

    private JWTUserDetailsService jwtUserDetailsService;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> autentication(@RequestBody JWTRequest jwtRequest) {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }catch(Exception exception){
            throw new UsuarioNoEncontradoException("No se ha podido hacer el logueo correctamente");
        }
        
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = jwtService.getToken(userDetails);
        Map<String, Object> claims = new HashMap<>();

        claims.put("message", "Login realizado con éxito.");
        claims.put("satus", HttpStatus.ACCEPTED);

        return ResponseEntity
        .ok()
        .header("Authorization", "Bearer " + token)
        .body(new JWTResponse(token, claims));
    }
    

}
