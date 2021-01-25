package com.enzo.api;

import com.enzo.entities.UserEntity;
import com.enzo.jwt.JwtUserDetailsService;
import com.enzo.jwt.util.JwtRequest;
import com.enzo.jwt.util.JwtTokenUtil;
import com.enzo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {

    @Autowired
    private UserRepository userRep;


    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private JwtUserDetailsService userDetailsService;
    @Autowired private PasswordEncoder bcryptEncoder;


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        try{

            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            UserEntity user = userRep.getUserByEmail(authenticationRequest.getUsername());

            params.put("authorities",  userDetails.getAuthorities());
            params.put("name",user.getName());
            params.put("username",  authenticationRequest.getUsername());
            params.put("token", token);

            return ResponseEntity.ok(params);

        }catch (Exception e) {
            params.put("error", e.getMessage());
            return ResponseEntity.accepted().body(params);
        }

    }


    @RequestMapping(value = "/register/user", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserEntity dto) throws Exception {

        try {
            UserEntity userEnt = dto;
            if(userRep.getUserByEmail(dto.getEmail()) != null) throw new RuntimeException("e-mail existe deja !!");
            userEnt.setCfp(dto.getPassword());
            userEnt.setPassword(bcryptEncoder.encode(dto.getPassword()));
            userEnt = userRep.save(userEnt);
            return ResponseEntity.ok(userEnt);
        } catch (Exception e) {
            return ResponseEntity.accepted().body(e.getMessage());
        }

    }



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
