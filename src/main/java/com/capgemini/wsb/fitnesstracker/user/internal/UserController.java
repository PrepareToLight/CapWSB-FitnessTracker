package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserSupport1Dto;
import com.capgemini.wsb.fitnesstracker.user.api.UserSupport2Dto;
import com.capgemini.wsb.fitnesstracker.user.api.UserSupportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserDataById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUser(id).get());
    }

    @GetMapping("/simple")
    public List<UserSupportDto> getAllUsersSimple() {
        return userService.findAllUsers().stream().map(userMapper::toUserSupportDto).toList();
    }

    @GetMapping("/email")
    public List<UserSupport1Dto> getUserIdByEmail(@RequestParam String email) {
        return userService.getUserIdByEmail(email).stream().map(userMapper::toUserSupport1Dto).toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> findAllUsersOlderThen(@PathVariable LocalDate time) {
        return userService.findAllUsersOlderThen(time).stream().map(userMapper::toDto).toList();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        // TODO: saveUser with Service and return User
        User user = userService.createUser(userMapper.toEntity(userDto));
        return user;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        System.out.println("User with id" + id.toString() +" has been deleted");
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserSupport2Dto userSupport2Dtos) {
        userService.updateUser(id, userSupport2Dtos);
    }


}