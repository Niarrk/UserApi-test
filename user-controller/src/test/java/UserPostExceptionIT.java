import com.user.controller.UserApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserPostExceptionIT {

    @Autowired
    private MockMvc mvc;

    /**
     * Test : Post a user without name
     *
     * @throws Exception
     */
    @Test
    public void A_PostUserMissingName() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_without_name.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_missing_name.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user without birthdate
     *
     * @throws Exception
     */
    @Test
    public void B_PostUserMissingBirthdate() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_without_birthdate.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400,response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_missing_birthdate.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user without country
     *
     * @throws Exception
     */
    @Test
    public void C_PostUserMissingCountry() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_without_country.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_missing_country.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user with an invalid country
     *
     * @throws Exception
     */
    @Test
    public void D_PostUserIncorrectCountry() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_incorrect_country.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals( 400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_incorrect_country.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user too young
     *
     * @throws Exception
     */
    @Test
    public void E_PostUserIncorrectBirthdate() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_incorrect_birthdate.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_incorrect_birthdate.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user with unknow gender
     *
     * @throws Exception
     */
    @Test
    public void F_PostUserIncorrectGender() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_incorrect_gender.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_incorrect_gender.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post a user with wrong number format
     *
     * @throws Exception
     */
    @Test
    public void G_PostUserBadFormatPhone() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/exception/post_user_bad_format_phone.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(getStringJsonFromFile("out/exception/post_user_bad_format_phone.json"), response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    /**
     * Get json from ressource file
     *
     * @param filePath filepath
     * @return jsonString
     * @throws IOException
     */
    private String getStringJsonFromFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("src","test","resources",filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
