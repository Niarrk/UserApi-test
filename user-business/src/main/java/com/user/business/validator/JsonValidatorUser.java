package com.user.business.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.user.business.exception.*;
import com.user.data.dto.UserDto;
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
     * Check the if the jsonUser is valid
     *
     * @param json user in json format
     * @return UserDto converted from json
     * @throws JsonProcessingException if the json format is incorrect
     */
    public UserDto validate(String json) throws JsonProcessingException {
        // Convert json to user
        UserDto user = wrapper(json);

        // Check parameters
        checkName(user.getName());
        checkBirthDateValid(user.getBirthdate());
        checkCountry(user.getCountry());
        checkGender(user.getGender());
        checkPhoneNumber(user.getPhone());

        return user;
    }

    /**
     * Map json to UserDto
     *
     * @param json user in json format
     * @return UserDto converted from json
     * @throws JsonProcessingException if the json format is incorrect
     */
    private UserDto wrapper(String json) throws JsonProcessingException {
        // Map json
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(json, UserDto.class);
    }

    /**
     * Verify the presence of name
     *
     * @param name user's name
     */
    private void checkName(String name){
        // name is mandatory
        if(name == null){
            throw new MissingParameterException("name");
        }
    }

    /**
     * Verify user has valid birthdate
     *
     * @param birthdate user's birthdate
     * @throws MissingParameterException if birthdate is missing
     * @throws InvalidBirthdateException if birthdate is not valid
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
     * @param country user's country
     * @throws MissingParameterException if country is null
     * @throws InvalidCountryException if country is not valid
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
     * @param gender user's gender
     * @throws InvalidGenderException if gender is not valid
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
     * @param phoneNumber user's phone number
     * @throws InvalidPhoneNumberException if phone format not valid
     */
    private void checkPhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile(PHONE_PATTERN);
        if(phoneNumber != null && !p.matcher(phoneNumber).matches()){
            throw  new InvalidPhoneNumberException();
        }
    }
}
