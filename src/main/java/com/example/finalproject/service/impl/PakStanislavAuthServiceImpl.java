package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavAuthResponseDto;
import com.example.finalproject.dto.PakStanislavLoginRequestDto;
import com.example.finalproject.dto.PakStanislavRegisterRequestDto;
import com.example.finalproject.dto.PakStanislavUserDto;
import com.example.finalproject.entity.PakStanislavRole;
import com.example.finalproject.entity.PakStanislavRoleType;
import com.example.finalproject.entity.PakStanislavUser;
import com.example.finalproject.exception.PakStanislavBadRequestException;
import com.example.finalproject.mapper.PakStanislavUserMapper;
import com.example.finalproject.repository.PakStanislavRoleRepository;
import com.example.finalproject.repository.PakStanislavUserRepository;
import com.example.finalproject.security.PakStanislavJwtUtil;
import com.example.finalproject.service.PakStanislavAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PakStanislavAuthServiceImpl implements PakStanislavAuthService {

    private final PakStanislavUserRepository userRepository;
    private final PakStanislavRoleRepository roleRepository;
    private final PakStanislavUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PakStanislavJwtUtil jwtUtil;

    @Override
    @Transactional
    public PakStanislavUserDto register(PakStanislavRegisterRequestDto requestDto) {
        log.info("Registration attempt for email={}", requestDto.getEmail());
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            log.warn("Registration rejected: email already exists email={}", requestDto.getEmail());
            throw new PakStanislavBadRequestException("User with this email already exists");
        }

        PakStanislavRole role = findOrCreateRole(requestDto.getRole());

        PakStanislavUser user = PakStanislavUser.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .enabled(true)
                .roles(Set.of(role))
                .build();

        PakStanislavUser savedUser = userRepository.save(user);
        log.info("User registered successfully id={} email={} role={}",
                savedUser.getId(), savedUser.getEmail(), requestDto.getRole());
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public PakStanislavAuthResponseDto login(PakStanislavLoginRequestDto requestDto) {
        log.info("Login attempt for email={}", requestDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PakStanislavUser user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new PakStanislavBadRequestException("User is not found"));

        String token = jwtUtil.generateToken(userDetails);
        log.info("Login successful for userId={} email={}", user.getId(), user.getEmail());
        return PakStanislavAuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    private PakStanislavRole findOrCreateRole(PakStanislavRoleType roleType) {
        return roleRepository.findByName(roleType)
                .orElseGet(() -> roleRepository.save(PakStanislavRole.builder().name(roleType).build()));
    }
}
