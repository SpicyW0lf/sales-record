package com.example.salesrecord.services;

import com.example.salesrecord.DTO.UserDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Role;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.RoleRepository;
import com.example.salesrecord.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private AdminService adminService;

    @Test
    void findUsersShouldReturnUsers() {
        Role role = new Role(UUID.randomUUID(), "TEST");
        List<User> users = List.of(
                new User(
                        UUID.randomUUID(),
                        "test_user",
                        "1111",
                        List.of(role)
                )
        );

        doReturn(users).when(userRepository).findAll();

        assertEquals(users, adminService.findUsers());
    }

    @Test
    void deleteUserShouldCallDeleteMethods() {
        String id = "user";
        doReturn(Optional.of(new User())).when(userRepository).findById(id);
        adminService.deleteUser(id);

        verify(userRepository).findById(id);
        verify(userRepository).deleteUserRoles(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void createUserWithoutRolesShouldThrowException() throws AlreadyExistsException {
        UserDto userDto = new UserDto("user", "1111", null);

        doReturn(new ArrayList<Role>()).when(roleRepository).findByNames(userDto.getRoles());

        assertThrows(NotFoundException.class, () -> adminService.createUser(userDto));
    }

    @Test
    void createUserIfUserAlreadyExists() {
        UserDto userDto = new UserDto("user", "1111", null);

        doReturn(List.of(new Role(UUID.randomUUID(), "TEST"))).when(roleRepository).findByNames(userDto.getRoles());
        doThrow(new RuntimeException()).when(userRepository).save(User.of(userDto));

        assertThrows(AlreadyExistsException.class, () -> adminService.createUser(userDto));
    }

    @Test
    void createUserShouldCreateUser() throws AlreadyExistsException {
        UserDto userDto = new UserDto("user", "1111", null);
        Role role = new Role(UUID.randomUUID(), "TEST");
        doReturn(List.of(role)).when(roleRepository).findByNames(userDto.getRoles());
        adminService.createUser(userDto);

        verify(userRepository).save(Mockito.any());
        verify(userRepository).saveRoleForUser(Mockito.any(), Mockito.any());
    }

    @Test
    void updateThrowsErrorIfNotExists() {
        String id = "string";
        UserDto userDto = new UserDto("user", "1234", List.of("TEST"));

        doReturn(Optional.of(User.of(userDto))).when(userRepository).findById(id);
        doReturn(new ArrayList<Role>()).when(roleRepository).findByNames(Mockito.any());

        assertThrows(NotFoundException.class, () -> adminService.updateUser(id, userDto));
    }
}