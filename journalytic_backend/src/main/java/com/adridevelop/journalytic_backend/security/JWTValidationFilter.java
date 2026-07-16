package com.adridevelop.journalytic_backend.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.adridevelop.journalytic_backend.exceptions.UsuarioNoEncontradoException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JWTUserDetailsService jwtUserDetailService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Value("${authorization.header}")
    public String AUTHORIZATION_HEADER;

    @Value("${authorization.header.bearer}")
    public String AUTHORIZATION_HEADER_BEARER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
                String username = null;
                String jwt = null;

                try{
                    if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)){
                        jwt = requestTokenHeader.substring(AUTHORIZATION_HEADER_BEARER.length());
                        try{
                            username = jwtService.getUsernameClaim(jwt);
                        }catch(Exception excep){
                            resolver.resolveException(request, response, null, new UsuarioNoEncontradoException(excep.getMessage()));
                        }

                        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
                            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);

                            if (jwtService.checkTokenValidity(jwt, userDetails)){
                                UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(upaToken);
                            }
                        }

                        filterChain.doFilter(request, response);
                    }
                }catch(Exception excep){

                }
    }

}
