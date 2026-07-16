package com.adridevelop.journalytic_backend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.adridevelop.journalytic_backend.exceptions.UsuarioNoEncontradoException;
import com.adridevelop.journalytic_backend.models.dao.UsuarioDAO;
import com.adridevelop.journalytic_backend.models.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Autowired
    private UsuarioDAO usuarioDao;

    @Value("${jwt.token.validity}")
    private Long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public Claims getAllClaimsFromToken(String token){

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){

        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    private Date getTokenExpirationDate(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean tokenExpired(String token){
        return getTokenExpirationDate(token).before(new Date());
    }

    public String getUsernameClaim(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean checkTokenValidity(String token, UserDetails userDetails){

        String usernameFromClaims = getUsernameClaim(token);
        String usernameBBDD = userDetails.getUsername();

        boolean tokenExpired = tokenExpired(token);

        return usernameFromClaims.equals(usernameBBDD) && !tokenExpired;
    }

    public String generateToken(Map<String, Object> claims, String subject, Long validity){

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + validity * 60 * 1000))
        .signWith(key)
        .compact();
    }

    public String getToken(UserDetails userDetails){
        Usuario usuario = usuarioDao.findOneByUserName(userDetails.getUsername()).orElseThrow(() -> new UsuarioNoEncontradoException("No se ha encontrado ese usuario."));

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", userDetails.getAuthorities());
        claims.put("nombre", usuario.getName());

        return generateToken(claims, JWT_SECRET, JWT_TOKEN_VALIDITY);
    }



}
