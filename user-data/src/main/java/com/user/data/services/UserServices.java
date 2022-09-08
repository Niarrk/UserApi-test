package com.user.data.services;

import com.user.data.entity.User;
import com.user.data.repo.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserServices
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Service
public class UserServices {

    private UserRepository userRepository;

    /**
     * UserServices constructor
     *
     * @param repo userRepository
     */
    public UserServices(UserRepository repo) {
        this.userRepository = repo;
    }


    /**
     * Find an User by his id
     *
     * @param id Id of the user
     * @return user found
     */
    public Optional<User> findUsersById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Return if user already exist
     *
     * @param user the user to check
     * @return true when the user is already in bdd
     */
    public boolean checkIfExist(User user) {
        User example = User.builder().name(user.getName()).country(user.getCountry()).birthdate(user.getBirthdate()).build();
        return userRepository.exists(Example.of(example));
    }

    /**
     * Insert a new user
     *
     * @param user the user to insert
     * @return id of created user
     */
    public Long insertUser(User user) {
        user = this.userRepository.save(user);

        return user.getId();
    }
}
