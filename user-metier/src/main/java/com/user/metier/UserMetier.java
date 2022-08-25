package com.user.metier;

import com.user.data.dao.UserDao;
import com.user.data.dto.UserDto;
import com.user.data.entity.User;
import com.user.metier.exception.DuplicateUserException;
import com.user.metier.exception.UserNotFoundException;
import com.user.metier.validator.JsonValidatorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * UserMetier
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@Component
@PropertySource(value = "classpath:application.properties")
public class UserMetier {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JsonValidatorUser jsonValidatorUser;

    public UserMetier() {}

    /**
     * Add new user
     * @param json jsonUser
     * @return Userdto
     * @throws IOException
     */
    public UserDto createUser(String json) throws IOException {
        User user = jsonValidatorUser.validate(json);

        if(userDao.checkIfExist(user)){
            throw new DuplicateUserException(user);
        }

        userDao.insertUser(user);

        return new UserDto(user);
    }

    /**
     * Researcher user by id
     * @param idString userid
     * @return userDto
     */
    public UserDto findUserById(String idString){
        Long id = Long.parseLong(idString);
        Optional<User> user = userDao.findUsersById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return new UserDto(user.get());
    }
}
