package com.user.data.dao.implement;

import com.user.data.dao.UserDao;
import com.user.data.entity.User;
import com.user.data.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

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
    public boolean checkIfExist(User user){
        User example = User.builder().name(user.getName()).country(user.getCountry()).birthdate(user.getBirthdate()).build();
        return userRepository.exists(Example.of(example));
    }

    /**
     * Insert a new user
     *
     * @param user the user to insert
     * @return id of created user
     */
    @Override
    public Long insertUser(User user) {
        user = this.userRepository.save(user);

        return user.getId();
    }
}
