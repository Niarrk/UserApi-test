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
     * @param id User id
     * @return User
     */
    public Optional<User> findUsersById(Long id);

    /**
     * Return if an user already exist
     * @param user
     * @return boolean
     */
    public boolean checkIfExist(User user);

    /**
     * Create an user
     * @param user User
     */
    public void insertUser(User user);
}
