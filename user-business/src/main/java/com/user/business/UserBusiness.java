package com.user.business;

import com.user.data.dao.UserDao;
import com.user.data.dto.UserDto;
import com.user.data.entity.User;
import com.user.business.exception.DuplicateUserException;
import com.user.business.exception.UserNotFoundException;
import com.user.business.validator.JsonValidatorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 *  UserBusiness
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@Component
@PropertySource(value = "classpath:application.properties")
public class UserBusiness {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JsonValidatorUser jsonValidatorUser;

    public UserBusiness() {}

    /**
     * Add a new user if the passed json is valid
     * @param json user in json format
     * @return UserDto of created user
     * @throws IOException
     */
    public UserDto createUser(String json) throws IOException {
        UserDto user = jsonValidatorUser.validate(json);
        if(user != null){
            if(userDao.checkIfExist(user.mapUser())){
                throw new DuplicateUserException(user);
            }

            user.setId(userDao.insertUser(user.mapUser()));
        }

        return user;
    }

    /**
     * Researcher user by id
     *
     * @param id research user id
     * @return userDto found
     */
    public UserDto findUserById(Long id){
        Optional<User> user = userDao.findUsersById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return new UserDto(user.get());
    }
}
