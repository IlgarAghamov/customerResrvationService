package com.customer.reservation.service.jwt.jwt;


import com.customer.reservation.model.Role;
import com.customer.reservation.model.User;
import com.customer.reservation.repository.UserRepository;
import com.customer.reservation.reques.auth.AuthenticationRequest;
import com.customer.reservation.reques.auth.RegisterRequest;
import com.customer.reservation.response.AuthenticationResponse;
import com.customer.reservation.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var role = request.getRole() == 1 ? Role.STUDENT : request.getRole() == 2 ? Role.FACULTY : Role.ADMIN;
        var user = User.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isEmpty()){
            userRepository.save(user);
        }
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}