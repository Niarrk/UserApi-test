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
        user.setName("Moi");
        user.setBirthdate(LocalDate.of(2000,01,01));
        user.setCountry("France");
        return user;
    }

    protected String getStringJsonFromFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("src","test","resources",filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

}
