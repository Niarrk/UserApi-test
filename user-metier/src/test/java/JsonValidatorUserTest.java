import com.fasterxml.jackson.core.JsonParseException;
import com.user.data.entity.User;
import com.user.metier.exception.*;
import com.user.metier.validator.JsonValidatorUser;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        String json = getStringJsonFromFile("user_ok.json");
        User user = jsonValidatorUser.validate(json);
        Assertions.assertEquals(user.getName(), "Moi");
        Assertions.assertEquals(user.getCountry(), "France");
        Assertions.assertEquals(user.getBirthdate(), LocalDate.of(2000,01,01));
        Assertions.assertEquals(user.getPhone(), "06-01-01-01-01");
        Assertions.assertEquals(user.getGender(), "Male");
    }

    @Test
    public void valideWithoutNameShouldMissingparameterException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/missing_name.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithoutCountryShouldMissingparameterException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/missing_country.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithoutBirthdateShouldMissingparameterException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/missing_birthdate.json");
        Assert.assertThrows(MissingParameterException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithInvalidCountryShouldInvalidCountryException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/invalid_country.json");
        Assert.assertThrows(InvalidCountryException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithInvalidBirthdateShouldInvalidBirthdateException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/invalid_birthdate.json");
        Assert.assertThrows(InvalidBirthdateException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithInvalidGenderShouldInvalidGenderException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/invalid_gender.json");
        Assert.assertThrows(InvalidGenderException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithInvalidPhoneShouldInvalidPhoneException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/invalid_phone_number.json");
        Assert.assertThrows(InvalidPhoneNumberException.class , () -> jsonValidatorUser.validate(json));
    }

    @Test
    public void valideWithBadFormatJsonShouldJsonParserException() throws IOException {
        init();
        String json = getStringJsonFromFile("validator/bad_format.json");
        Assert.assertThrows(JsonParseException.class , () -> jsonValidatorUser.validate(json));
    }
}