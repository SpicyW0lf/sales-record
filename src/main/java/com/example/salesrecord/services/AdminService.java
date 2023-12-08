package com.example.salesrecord.services;

import com.example.salesrecord.DTO.UserDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.RoleRepository;
import com.example.salesrecord.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void createUser(UserDto userDto) throws AlreadyExistsException {
        User user = User.of(userDto);
        user.setRoles(roleRepository.findByNames(userDto.getRoles()));
        if (user.getRoles().isEmpty()) {
            throw new NotFoundException("Roles with such name not found");
        }
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new AlreadyExistsException("User with such name is already exists");
        }
        roleRepository.findByNames(userDto.getRoles()).forEach(role -> userRepository.saveRoleForUser(user, role));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteUserRoles(id);
        userRepository.deleteById(id);
    }

    public void updateUser(String id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User newUser = user.get();
            userRepository.deleteUserRoles(id);
            newUser.setUsername(userDto.getUsername());
            newUser.setPassword(userDto.getPassword());
            newUser.setRoles(roleRepository.findByNames(userDto.getRoles()));
            if (newUser.getRoles().isEmpty()) {
                throw new NotFoundException("Roles with such names not found");
            }
            userRepository.update(newUser);
            newUser.getRoles().forEach(role -> userRepository.saveRoleForUser(newUser, role));
            return;
        }
        User newUser = User.of(userDto);
        newUser.setRoles(roleRepository.findByNames(userDto.getRoles()));
        if (newUser.getRoles().isEmpty()) {
            throw new NotFoundException("Roles with such names not found");
        }
        userRepository.save(newUser);
        newUser.getRoles().forEach(role -> userRepository.saveRoleForUser(newUser, role));
    }
}
