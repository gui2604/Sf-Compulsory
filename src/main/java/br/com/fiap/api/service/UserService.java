package br.com.fiap.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.log.LogSummaryService;
import br.com.fiap.api.model.User;
import br.com.fiap.api.repository.UserRepository;
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

    public User save(User user){
    	log.info("Saving user: {}", user.getUsername());
    	logSummaryService.addLog("INFO", "Saving user: " + user.getUsername());
    	String encryptedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }
    
    public User update(Long id, @Valid User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setClientName(updatedUser.getClientName());
            user.setEmail(updatedUser.getEmail());
            user.setBetMaxValue(updatedUser.getBetMaxValue());
            user.setUserPixKey(updatedUser.getUserPixKey());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found."));
    }
    
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
   
    // ------------------------ SECURITY -----------------------------------------------
    //autenticação de segurança 
    public boolean autenticateUser(String typedUsername, String typedPassword) {
        User user = userRepository.findByUsername(typedUsername)
            .orElseThrow(() -> new RuntimeException("User not found."));

        return passwordEncoder.matches(typedPassword, user.getPassword());
    }
    
    //redefinição de senha
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
