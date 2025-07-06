package cz.rodro.service;

import cz.rodro.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO create(UserDTO model);

}