package com.campusfix.campusfix.service;

import com.campusfix.campusfix.dto.UserRequestDto;
import com.campusfix.campusfix.dto.UserResponseDto;
import com.campusfix.campusfix.entity.User;
import com.campusfix.campusfix.exception.ResourceNotFoundException;
import com.campusfix.campusfix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponseDto> response = new ArrayList<>();

        for (User user : users) {

            UserResponseDto dto = new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );

            response.add(dto);
        }

        return response;
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + id
                        )
                );

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPassword(dto.getPassword());
        existingUser.setRole(dto.getRole());

        User updatedUser = userRepository.save(existingUser);

        return new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole()
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}