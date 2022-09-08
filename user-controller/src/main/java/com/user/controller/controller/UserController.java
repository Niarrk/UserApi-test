package com.user.controller.controller;

import com.user.business.UserBusiness;
import com.user.business.dto.UserDto;
import com.user.controller.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * UserController
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@RestController
@RequestMapping("users")
public class UserController {

    private UserBusiness userBusiness;

    /**
     * UserController constructor
     *
     * @param userBusiness userBusiness bean
     */
    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    /**
     * GET user by id
     *
     * @param id long id of user
     * @return ResponseEntity format by ResponseHandler
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable final Long id) {

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
    public ResponseEntity<Object> postUser(@RequestBody UserDto json) throws IOException {
        UserDto result = userBusiness.createUser(json);

        return ResponseHandler.responseOk(HttpStatus.CREATED, result);
    }
}
