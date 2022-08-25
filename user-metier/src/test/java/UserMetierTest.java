import com.user.data.dao.UserDao;
import com.user.data.dto.UserDto;
import com.user.data.entity.User;
import com.user.metier.UserMetier;
import com.user.metier.exception.DuplicateUserException;
import com.user.metier.exception.MissingParameterException;
import com.user.metier.exception.UserNotFoundException;
import com.user.metier.validator.JsonValidatorUser;
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
public class UserMetierTest extends UserTestUtils {

    @InjectMocks
    UserMetier userMetier;

    @Mock
    JsonValidatorUser jsonValidatorUser;

    @Mock
    UserDao userDao;

    @Test
    public void createUserReturnUserWhenValid() throws IOException {
        // init
        String json = getStringJsonFromFile("user_ok.json");
        User attendee = getValidUser();
        Mockito.when(userDao.checkIfExist(any())).thenReturn(false);
        Mockito.when(jsonValidatorUser.validate(json)).thenReturn(attendee);

        // do
        UserDto result = userMetier.createUser(json);

        // Then
        Assert.assertEquals(result.getCountry(), attendee.getCountry());
        Assert.assertEquals(result.getName(), attendee.getName());
        Assert.assertEquals(result.getBirthdate(), attendee.getBirthdate());
        Assert.assertEquals(result.getPhone(), attendee.getPhone());
        Assert.assertEquals(result.getGender(), attendee.getGender());
    }

    @Test
    public void createUserThrowDuplicateExceptionWhenExist() throws IOException {
        // init
        String json = getStringJsonFromFile("user_ok.json");
        User attendee = getValidUser();
        Mockito.when(userDao.checkIfExist(any())).thenReturn(true);
        Mockito.when(jsonValidatorUser.validate(json)).thenReturn(attendee);

        // Then
        Assert.assertThrows(DuplicateUserException.class , () -> userMetier.createUser(json));
    }

    @Test
    public void findUserReturnUserFound() throws IOException {
        // init
        User attendee = getValidUser();
        Mockito.when(userDao.findUsersById(anyLong())).thenReturn(java.util.Optional.ofNullable(attendee));

        // do
        UserDto result = userMetier.findUserById("1");

        // Then
        Assert.assertEquals(result.getCountry(), attendee.getCountry());
        Assert.assertEquals(result.getName(), attendee.getName());
        Assert.assertEquals(result.getBirthdate(), attendee.getBirthdate());
        Assert.assertEquals(result.getPhone(), attendee.getPhone());
        Assert.assertEquals(result.getGender(), attendee.getGender());
    }

    @Test
    public void findUserThrowNotFoundExceptionWhenDontExist() throws IOException {
        // init
        Mockito.when(userDao.findUsersById(anyLong())).thenReturn(Optional.ofNullable(null));

        // Then
        Assert.assertThrows(UserNotFoundException.class , () -> userMetier.findUserById("1"));
    }
}
