package br.com.fiap.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.dto.UserCreateDTO;
import br.com.fiap.api.dto.UserUpdateDTO;
import br.com.fiap.api.log.LogSummaryService;
import br.com.fiap.api.model.User;
import br.com.fiap.api.repository.UserRepository;
import br.com.fiap.api.vo.EmailVO;
import br.com.fiap.api.vo.PasswordVO;
import br.com.fiap.api.vo.UsernameVO;
import jakarta.validation.Valid;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogSummaryService logSummaryService;

    // -------------------------- CRUD ---------------------------------
    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> searchForId(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(@Valid UserCreateDTO dto) {
        User user = new User();
        user.setClientName(dto.getClientName());
        user.setEmail(dto.getEmail() != null ? dto.getEmail().getValue() : null);
        user.setBetMaxValue(dto.getBetMaxValue());
        user.setUsername(dto.getUsername() != null ? dto.getUsername().getValue() : null);
        user.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword().getValue()) : null);
        user.setUserPixKey(dto.getUserPixKey());

        log.info("Creating user: {}", user.getUsername());
        logSummaryService.addLog("INFO", "Creating user: " + user.getUsername());

        return userRepository.save(user);
    }

    public User updateUser(Long id, @Valid UserUpdateDTO dto) {
        return userRepository.findById(id).map(user -> {
            if (dto.getClientName() != null) user.setClientName(dto.getClientName());
            if (dto.getEmail() != null) user.setEmail(dto.getEmail().getValue());
            if (dto.getBetMaxValue() != null) user.setBetMaxValue(dto.getBetMaxValue());
            if (dto.getUserPixKey() != null) user.setUserPixKey(dto.getUserPixKey());

            log.info("Updating user: {}", user.getUsername());
            logSummaryService.addLog("INFO", "Updating user: " + user.getUsername());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // ------------------------ SECURITY -----------------------------------------------
    public boolean autenticateUser(String typedUsername, String typedPassword) {
        User user = userRepository.findByUsername(typedUsername)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return passwordEncoder.matches(typedPassword, user.getPassword());
    }

    public boolean resetPassword(String username, ResetPasswordRequest request) {
        Optional<User> usuarioOpt = userRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) return false;

        User user = usuarioOpt.get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return false;
        }

        String newPasswordHash = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPasswordHash);
        userRepository.save(user);

        return true;
    }
}
