package com.user.services.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.user.data.dto.UserDto;
import com.user.metier.UserMetier;
import com.user.metier.exception.DuplicateUserException;
import com.user.metier.exception.InvalidJsonException;
import com.user.metier.exception.MissingParameterException;
import com.user.metier.exception.UserNotFoundException;
import com.user.services.response.ResponseHandler;
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
@RequestMapping("user-test/users")
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserMetier userMetier;

    /**
     * GET user by id
     *
     * @param id userId
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable final String id){

        try{
            UserDto result = userMetier.findUserById(id);
            return ResponseHandler.responseOk(HttpStatus.OK, result);
        } catch (UserNotFoundException e){
            return ResponseHandler.responseKO(HttpStatus.NOT_FOUND,e);
        } catch (NumberFormatException e){
            return ResponseHandler.responseKO(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * POST user
     *
     * @param json jsonUser
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody String json) throws IOException, ProcessingException, IllegalAccessException {
        try{
            UserDto result = userMetier.createUser(json);
            return ResponseHandler.responseOk(HttpStatus.CREATED, result);
        } catch (InvalidJsonException | MissingParameterException | InvalidFormatException | JsonParseException e ){
            return ResponseHandler.responseKO(HttpStatus.BAD_REQUEST, e);
        } catch (DuplicateUserException e){
            return ResponseHandler.responseKOWithParams(HttpStatus.CONFLICT, e, e.getParamMap());
        }
    }
}
