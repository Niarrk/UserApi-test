import com.user.services.UserApplication;
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
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    /**
     * Test : Post a valid user
     *
     * @throws Exception
     */
    @Test
    public void A_PostUserValid() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/post_user_ok.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(response.getStatus(), 201);
        JSONAssert.assertEquals(response.getContentAsString(), getStringJsonFromFile("out/post_user_ok.json"),  JSONCompareMode.LENIENT);
    }

    /**
     * Test : Post existing user
     *
     * @throws Exception
     */
    @Test
    public void B_PostSameUserDuplicateException() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user-test/users/")
                .accept(MediaType.APPLICATION_JSON).content(getStringJsonFromFile("in/post_user_ok.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(response.getStatus(), 409);
        JSONAssert.assertEquals(response.getContentAsString(), getStringJsonFromFile("out/post_user_duplicate_error.json"),  JSONCompareMode.LENIENT);
    }

    /**
     * Test : Get existing user
     *
     * @throws Exception
     */
    @Test
    public void C_GetExistingUser() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user-test/users/1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(response.getStatus(), 200);
        JSONAssert.assertEquals(response.getContentAsString(), getStringJsonFromFile("out/get_user_ok.json"),  JSONCompareMode.LENIENT);
    }

    /**
     * Test : Get unknow user
     *
     * @throws Exception
     */
    @Test
    public void D_GetUnkwonUser() throws Exception {
        // Send user to /user-test/users/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user-test/users/2")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assert.assertEquals(response.getStatus(), 404);
        JSONAssert.assertEquals(response.getContentAsString(), getStringJsonFromFile("out/get_user_unknow.json"),  JSONCompareMode.LENIENT);
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
