import com.fasterxml.jackson.core.JsonParseException;
import com.user.business.exception.*;
import com.user.business.validator.JsonValidatorUser;
import com.user.data.dto.UserDto;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class JsonValidatorUserTest extends UserTestUtils{

    @InjectMocks
    JsonValidatorUser jsonValidatorUser;

    private void init(){
        ReflectionTestUtils.setField(jsonValidatorUser, "countryList", List.of("France"));
        ReflectionTestUtils.setField(jsonValidatorUser, "minimalAge",18);
        ReflectionTestUtils.setField(jsonValidatorUser, "genderList", List.of("Male","Female"));
    }

    @Test
    public void validateShouldBeOk() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("user_ok.json");
        Assertions.assertEquals("Moi", user.getName());
        Assertions.assertEquals("France", user.getCountry());
        Assertions.assertEquals(LocalDate.of(2000,01,01), user.getBirthdate());
        Assertions.assertEquals( "06-01-01-01-01", user.getPhone());
        Assertions.assertEquals("Male", user.getGender());
    }

    @Test
    public void valideWithoutNameShouldMissingparameterException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/missing_name.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithoutCountryShouldMissingparameterException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/missing_country.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithoutBirthdateShouldMissingparameterException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/missing_birthdate.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithInvalidCountryShouldInvalidCountryException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/invalid_country.json");
        Assert.assertThrows(InvalidCountryException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithInvalidBirthdateShouldInvalidBirthdateException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/invalid_birthdate.json");
        Assert.assertThrows(InvalidBirthdateException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithInvalidGenderShouldInvalidGenderException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/invalid_gender.json");
        Assert.assertThrows(InvalidGenderException.class , () -> jsonValidatorUser.validate(user));
    }

    @Test
    public void valideWithInvalidPhoneShouldInvalidPhoneException() throws IOException {
        init();
        UserDto user = getUserFromJsonFile("validator/invalid_phone_number.json");
        Assert.assertThrows(InvalidPhoneNumberException.class , () -> jsonValidatorUser.validate(user));
    }
}
