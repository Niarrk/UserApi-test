import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.user.data.dto.UserDto;
import com.user.data.entity.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class UserTestUtils {
    protected UserDto getValidUser(){
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("Moi");
        user.setBirthdate(LocalDate.of(2000,01,01));
        user.setCountry("France");
        user.setGender("Male");
        user.setPhone("06-01-01-01-01");
        return user;
    }

    protected UserDto getUserFromJsonFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("src","test","resources",filePath));
        return wrapper(new String(encoded, StandardCharsets.UTF_8));
    }

    private UserDto wrapper(String json) throws JsonProcessingException {
        // Map json
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(json, UserDto.class);
    }
}
