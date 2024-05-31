package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUserIdByEmail(final String part) {
        return userRepository.findByPartEmail(part);
    }

    @Override
    public List<User> findAllUsersOlderThen(final LocalDate time) {
        return userRepository.findAllUsersOlderThen(time);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public void updateUser(Long id, UserSupport2Dto userSupport2Dto){
        User user = userRepository.findById(id).get();

        if (userSupport2Dto.firstName() != null) {user.setFirstName(userSupport2Dto.firstName());}
        if (userSupport2Dto.lastName() != null) {user.setLastName(userSupport2Dto.lastName());}
        if (userSupport2Dto.birthdate() != null) {user.setBirthdate(userSupport2Dto.birthdate());}
        if (userSupport2Dto.email() != null) {user.setEmail(userSupport2Dto.email());}
        userRepository.save(user);
    }

}