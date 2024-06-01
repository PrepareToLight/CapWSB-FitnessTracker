package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }





    default List<User> searchUserByEmail(String email){
        return findAll().stream()
                //.map(user -> new IDEmailUserDto(user.getId(), user.getEmail()))
                //.filter(user -> user.getEmail().toLowercase().contains(email.toLowerCase()))
                .filter(user -> user.getEmail().matches("(?i).*" + email + ".*"))
                .collect(Collectors.toList());
    }
    /*
    default List<User> findByPartEmail(String email){
        System.out.println("Looking for emials");
        return findAll()
                .stream()
                .filter(user -> user.getEmail().matches("(?i)." + email + "."))
                .collect(Collectors.toList());
    }
     */

    default List<User> findAllUsersOlderThen(LocalDate time){
        return findAll().stream()
                .filter(user -> (time.isAfter(user.getBirthdate())))
                .collect(Collectors.toList());
    }

}
