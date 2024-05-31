package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserSupport1Dto;
import com.capgemini.wsb.fitnesstracker.user.api.UserSupportDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    UserSupportDto toUserSupportDto(User user) {
        return new UserSupportDto(user.getId(),
                                  user.getFirstName(),
                                  user.getLastName());
    }

    UserSupport1Dto toUserSupport1Dto(User user) {
        return new UserSupport1Dto(user.getId(),
                                   user.getEmail());
    }

}
