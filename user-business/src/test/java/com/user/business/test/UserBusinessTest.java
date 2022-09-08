package com.user.business.test;

import com.user.business.UserBusiness;
import com.user.business.dto.UserDto;
import com.user.business.exception.DuplicateUserException;
import com.user.business.exception.UserNotFoundException;
import com.user.business.test.utils.UserTestUtils;
import com.user.business.validator.JsonValidatorUser;
import com.user.data.services.UserServices;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserBusinessTest extends UserTestUtils {

    @InjectMocks
    UserBusiness userBusiness;

    @Mock
    JsonValidatorUser jsonValidatorUser;

    @Mock
    UserServices userServices;

    @Test
    public void createUserReturnUserWhenValid() throws IOException {
        // init
        UserDto user = getUserFromJsonFile("user_ok.json");
        UserDto attendee = getValidUser();
        Mockito.when(userServices.checkIfExist(any())).thenReturn(false);
        Mockito.when(jsonValidatorUser.validate(user)).thenReturn(attendee);
        Mockito.when(userServices.insertUser(any())).thenReturn(1L);

        // do
        UserDto result = userBusiness.createUser(user);

        // Then
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals(attendee.getCountry(), result.getCountry());
        Assert.assertEquals(attendee.getName(), result.getName());
        Assert.assertEquals(attendee.getBirthdate(), result.getBirthdate());
        Assert.assertEquals(attendee.getPhone(), result.getPhone());
        Assert.assertEquals(attendee.getGender(), result.getGender());
    }

    @Test
    public void createUserThrowDuplicateExceptionWhenExist() throws IOException {
        // init
        UserDto user = getUserFromJsonFile("user_ok.json");
        Mockito.when(userServices.checkIfExist(any())).thenReturn(true);
        Mockito.when(jsonValidatorUser.validate(user)).thenReturn(user);

        // Then
        Assert.assertThrows(DuplicateUserException.class, () -> userBusiness.createUser(user));
    }

    @Test
    public void findUserReturnUserFound() throws IOException {
        // init
        UserDto attendee = getValidUser();
        Mockito.when(userServices.findUsersById(anyLong())).thenReturn(java.util.Optional.ofNullable(attendee.mapUser()));

        // do
        UserDto result = userBusiness.findUserById(1L);

        // Then
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals(attendee.getCountry(), result.getCountry());
        Assert.assertEquals(attendee.getName(), result.getName());
        Assert.assertEquals(attendee.getBirthdate(), result.getBirthdate());
        Assert.assertEquals(attendee.getPhone(), result.getPhone());
        Assert.assertEquals(attendee.getGender(), result.getGender());
    }

    @Test
    public void findUserThrowNotFoundExceptionWhenDontExist() throws IOException {
        // init
        Mockito.when(userServices.findUsersById(anyLong())).thenReturn(Optional.ofNullable(null));

        // Then
        Assert.assertThrows(UserNotFoundException.class, () -> userBusiness.findUserById(1L));
    }
}
