package com.api.devmed.controllers;


import com.api.devmed.model.dto.LoginDTO;
import com.api.devmed.services.TokenService;
import com.api.devmed.model.dto.TokenDTO;
import com.api.devmed.model.entities.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public TokenDTO fazerLogin(@RequestBody LoginDTO login){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        Authentication authenticate  = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var usuario = (Usuario) authenticate.getPrincipal();
        return new TokenDTO(tokenService.gerarToken(usuario));
    }


}
