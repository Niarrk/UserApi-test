package com.user.controller.controller;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.user.business.UserBusiness;
import com.user.controller.response.ResponseHandler;
import com.user.data.dto.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * UserController
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@RestController
@RequestMapping("users")
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    /**
     * GET user by id
     *
     * @param id long id of user
     * @return ResponseEntity format by ResponseHandler
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable final Long id){

        UserDto result = userBusiness.findUserById(id);
        return ResponseHandler.responseOk(HttpStatus.OK, result);
    }

    /**
     * POST user
     *
     * @param json User in json format
     * @return ResponseEntity format by ResponseHandler
     */
    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody String json) throws IOException, ProcessingException, IllegalAccessException {
        UserDto result = userBusiness.createUser(json);
        return ResponseHandler.responseOk(HttpStatus.CREATED, result);
    }
}
