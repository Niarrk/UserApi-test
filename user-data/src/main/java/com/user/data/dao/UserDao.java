package com.user.data.dao;

import com.user.data.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDao
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Service
public interface UserDao {
    /**
     * Find user by id
     *
     * @param id Id of the user
     * @return user found
     */
    public Optional<User> findUsersById(Long id);

    /**
     * Return if an user already exist
     *
     * @param user the user to check
     * @return true when the user is already in bdd
     */
    public boolean checkIfExist(User user);

    /**
     * Create an user
     *
     * @param user the user to inse
     * @return id of created user
     */
    public Long insertUser(User user);
}
