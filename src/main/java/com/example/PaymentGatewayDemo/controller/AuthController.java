package com.example.PaymentGatewayDemo.controller;

import com.example.PaymentGatewayDemo.dto.LoginDTO;
import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.entity.User;
import com.example.PaymentGatewayDemo.enums.StatusCodeEnum;
import com.example.PaymentGatewayDemo.repository.UserRepository;
import com.example.PaymentGatewayDemo.serviceImpl.CustomUserDetailService;
import com.example.PaymentGatewayDemo.util.Constants;
import com.example.PaymentGatewayDemo.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticate;

    @Resource(name = "customUserService")
    private CustomUserDetailService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseDTO createAuthenticationToken(@Valid @RequestBody LoginDTO authenticationRequest, Errors errors)
            throws Exception {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            try {
                authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getEmail());
                final String token = jwtTokenUtil.generateTokenFromUsername(userDetails.getUsername());
                Optional<User> userEntity = userRepository.findByEmailIgnoreCase(authenticationRequest.getEmail());

                if (userEntity.isPresent()) {
                    return new ResponseDTO("200", Constants.LOGIN_SUCCESSFULLY, token);
                }
                return new ResponseDTO("200", Constants.LOGIN_SUCCESSFULLY, token);
            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase(Constants.INVALID_CREDENTIALS)) {

                }
            }
            return new ResponseDTO("400", Constants.INVALID_CREDENTIALS, null);
        }
    }

    private void authenticate(String name, String password) throws Exception {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);

        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception(Constants.INVALID_CREDENTIALS, e);
        }
    }
}
