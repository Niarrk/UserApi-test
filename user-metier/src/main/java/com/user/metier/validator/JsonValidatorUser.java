package com.user.metier.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.user.data.entity.User;
import com.user.metier.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 *  Verify json parameters are valid and convert to user
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@Component
@PropertySource(value = "classpath:application.properties")
public class JsonValidatorUser {


    @Value("${user.valid.countries}")
    private List<String> countryList;
    @Value("${user.minimal.age:18}")
    private int minimalAge;
    @Value("${user.valid.genders}")
    private List<String> genderList = List.of("M","F","OTHER");
    private final static String PHONE_PATTERN = "\\d{2}-\\d{2}-\\d{2}-\\d{2}-\\d{2}";

    /**
     * Check the jsonUser is valid
     *
     * @param json jsonUser
     * @return User
     * @throws JsonProcessingException
     */
    public User validate(String json) throws JsonProcessingException {
        // Convert json to user
        User user = wrapper(json);

        // Check parameters
        checkName(user.getName());
        checkBirthDateValid(user.getBirthdate());
        checkCountry(user.getCountry());
        checkGender(user.getGender());
        checkPhoneNumber(user.getPhone());

        return user;
    }

    /**
     * Map json to User
     *
     * @param json json body
     * @return user
     * @throws JsonProcessingException json format exception
     */
    private User wrapper(String json) throws JsonProcessingException {
        // Map json
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(json, User.class);
    }

    private void checkName(String name){
        // name is mandatory
        if(name == null){
            throw new MissingParameterException("name");
        }
    }

    /**
     * Verify user has valid birthdate
     *
     * @param birthdate
     * @throws MissingParameterException birthdate is missing
     * @throws InvalidBirthdateException birthdate is not valid
     */
    private void checkBirthDateValid(LocalDate birthdate){
        // birthdate is mandatory
        if(birthdate == null){
            throw new MissingParameterException("birthdate");
        }
        LocalDate minimalBirthDate = LocalDate.now().minusYears(minimalAge);
        if(birthdate.isAfter(minimalBirthDate)){
            throw new InvalidBirthdateException();
        }
    }

    /**
     * Verify user has country and match valid country
     *
     * @param country country of user
     * @throws MissingParameterException country is null
     * @throws InvalidCountryException country is not valid
     */
    private void checkCountry(String country){
        // Country is mandatory
        if(country == null){
            throw new MissingParameterException("country");
        }
        // Don't check if no parameters
        if(countryList.isEmpty()){
            return;
        }

        for (String validCountry : countryList ) {
            if(country.equalsIgnoreCase(validCountry)){
                return;
            }
        }

        throw new InvalidCountryException(countryList);
    }

    /**
     * Check user's gender match valid gender
     *
     * @param gender gender of the user
     * @throws InvalidGenderException gender is not valid
     */
    private void checkGender(String gender){
        // Gender is optionnal and don't check if no parameters
        if(gender == null || genderList.isEmpty()){
            return;
        }

        for (String validGender : genderList) {
            if(gender.equalsIgnoreCase(validGender)){
                return;
            }
        }
        throw new InvalidGenderException(genderList);
    }

    /**
     * Check phone number format
     *
     * @param phoneNumber phone number of user
     * @throws InvalidPhoneNumberException phone format not valid
     */
    private void checkPhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile(PHONE_PATTERN);
        if(phoneNumber != null && !p.matcher(phoneNumber).matches()){
            throw  new InvalidPhoneNumberException();
        }
    }
}
