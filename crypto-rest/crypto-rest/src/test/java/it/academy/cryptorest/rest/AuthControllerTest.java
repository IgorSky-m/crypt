package it.academy.cryptorest.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.academy.cryptorest.pojo.AuthRequest;
import it.academy.cryptorest.pojo.AuthResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends TestDatabaseDBUnitManagerImpl {

    @Autowired
    private MockMvc mockMvc;

    @Value("${origin.test.url}")
    private String testUrl;

    @Before
    public void setUp() throws Exception {
       cleanInsertData();
    }

    @After
    public void tearDown(){
        deleteData();
    }

    @Test
    public void authWithValidData() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, AuthRequest.builder()
                .name("testUser92")
                .password("12345678")
                .build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
            .content(stringWriter.toString())
            .header("Origin", testUrl)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        int responseStatus = result
                .getResponse()
                .getStatus();



        AuthResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                AuthResponse.class
        );

        String[] tokenParts = response.getJwtToken().split("\\.");

        Pattern pattern = Pattern.compile(".{20}\\..{72}\\..{86}");

        Pattern failPattern = Pattern.compile(".{21}\\..{71}\\..{90}");

        assertEquals(200,responseStatus);

        Matcher matcher =pattern.matcher(response.getJwtToken());
        assertTrue(matcher.matches());

        matcher = failPattern.matcher(response.getJwtToken());
        assertFalse(matcher.matches());

        assertEquals(3,tokenParts.length);
        assertEquals(20,tokenParts[0].length());
        assertEquals(72,tokenParts[1].length());
        assertEquals(86,tokenParts[2].length());
    }

    @Test
    public void authWithInvalidData() throws Exception {


            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            objectMapper.writeValue(stringWriter, AuthRequest.builder()
                    .name("invalidData")
                    .password("invalidData")
                    .build());

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                    .content(stringWriter.toString())
                    .header("Origin", testUrl)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            int responseStatus = result
                    .getResponse()
                    .getStatus();



            assertEquals(401,responseStatus);



    }

    @Test
    public void authWithEmptyFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, AuthRequest.builder()
                .name("")
                .password("")
                .build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(stringWriter.toString())
                .header("Origin", testUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int responseStatus = result
                .getResponse()
                .getStatus();



        assertEquals(401,responseStatus);

    }

    @Test
    public void authWithNullFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, AuthRequest.builder()
                .name(null)
                .password(null)
                .build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(stringWriter.toString())
                .header("Origin", testUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int responseStatus = result
                .getResponse()
                .getStatus();



        assertEquals(401,responseStatus);
    }

    @Test
    public void authWithEmptyRequest() throws Exception {
        String content = "";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(content)
                .header("Origin", testUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int responseStatus = result
                .getResponse()
                .getStatus();



        assertEquals(400,responseStatus);
    }

    @Test
    public void authWithEmptyData() throws Exception {
        String content = "{}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(content)
                .header("Origin", testUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int responseStatus = result
                .getResponse()
                .getStatus();



        assertEquals(401,responseStatus);
    }

}
