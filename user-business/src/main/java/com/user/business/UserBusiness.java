package com.user.business;

import com.user.business.dto.UserDto;
import com.user.business.exception.DuplicateUserException;
import com.user.business.exception.UserNotFoundException;
import com.user.business.validator.JsonValidatorUser;
import com.user.data.entity.User;
import com.user.data.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    private UserServices userServices;

    private JsonValidatorUser jsonValidatorUser;

    public UserBusiness(UserServices userServices, JsonValidatorUser jsonValidatorUser) {
        this.userServices = userServices;
        this.jsonValidatorUser = jsonValidatorUser;
    }

    /**
     * Add a new user if the passed json is valid
     * @param json user in json format
     * @return UserDto of created user
     * @throws IOException
     */
    public UserDto createUser(UserDto json) throws IOException {
        UserDto user = jsonValidatorUser.validate(json);
        if(user != null){
            if(userServices.checkIfExist(user.mapUser())){
                Map<String, Object> params = new HashMap<>();
                params.put("name", user.getName());
                params.put("country", user.getCountry());
                params.put("birthdate", user.getBirthdate());
                throw new DuplicateUserException(params);
            }

            user.setId(userServices.insertUser(user.mapUser()));
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
        Optional<User> user = userServices.findUsersById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return new UserDto(user.get());
    }
}
