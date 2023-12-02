package com.example.salesrecord.services;

import com.example.salesrecord.DTO.UserDto;
import com.example.salesrecord.models.Role;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.RoleRepository;
import com.example.salesrecord.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void createUser(UserDto userDto) {
        User user = User.of(userDto.getUsername(), userDto.getPassword());
        userRepository.save(user);
        userDto.getRoles().stream()
                .map(roleRepository::findByName)
                .forEach(role -> userRepository.saveRolesForUser(user, role));
    }
}
