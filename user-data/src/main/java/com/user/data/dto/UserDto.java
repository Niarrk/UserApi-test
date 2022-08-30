package com.user.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * UserDto
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    private Long id;
    private String name;
    private String country;
    private LocalDate birthdate;
    private String gender;
    private String phone;

    /**
     * Wrap user into userDto
     *
     * @param user
     * @return userDto
     */
    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.country = user.getCountry();
        this.birthdate = user.getBirthdate();
        this.gender = user.getGender();
        this.phone = user.getPhone();
    }

    /**
     * Return User from this
     *
     * @return user
     */
    public User mapUser(){
        User user = new User();
        user.setName(this.name);
        user.setCountry(this.country);
        user.setBirthdate(this.birthdate);
        user.setGender(this.gender);
        user.setPhone(this.phone);

        return user;
    }
}
