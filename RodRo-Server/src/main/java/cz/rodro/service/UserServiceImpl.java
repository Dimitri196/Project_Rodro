package cz.rodro.service;

import cz.rodro.dto.UserDTO;
import cz.rodro.entity.UserEntity;
import cz.rodro.entity.repository.UserRepository;
import cz.rodro.exception.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(UserDTO model) {
        try {
            UserEntity entity = new UserEntity();
            entity.setFullName(model.getFullName());
            entity.setEmail(model.getEmail());
            entity.setPassword(passwordEncoder.encode(model.getPassword()));
            entity.setAdmin(false); // prevent privilege escalation

            entity = userRepository.save(entity);

            UserDTO dto = new UserDTO();
            dto.setUserId(entity.getUserId());
            dto.setFullName(entity.getFullName());
            dto.setEmail(entity.getEmail());
            dto.setAdmin(entity.isAdmin());
            return dto;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("User with this email already exists");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }
}
