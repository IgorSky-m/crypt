package it.academy.cryptorest.rest;


import com.fasterxml.jackson.databind.DeserializationFeature;
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
public class CustomUserControllerTest extends TestDatabaseDBUnitManagerImpl {

    @Autowired
    private MockMvc mockMvc;

    @Value("${origin.test.url}")
    private String testUrl;

    @Before
    public void setUp() {
        cleanInsertData();
    }

    @After
    public void tearDown(){
        deleteData();
    }

    @Test
    public void allWithoutAuth() throws Exception{


        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();

        String content = result.getResponse().getContentAsString();

        assertEquals(403,status);

        assertTrue(content.isEmpty());

    }

    @Test
    public void allWithAuth() throws Exception{
        String token = "Bearer ".concat(auth());

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .header("Authorization",token)
                        .header("Origin", testUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();

        String response = result.getResponse().getContentAsString();

        assertFalse(response.isEmpty());
        assertEquals(200,status);


    }





    private String auth() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, AuthRequest.builder()
                .name("testUser92")
                .password("12345678")
                .build());

        MvcResult authResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(stringWriter.toString())
                .header("Origin", testUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        AuthResponse response = objectMapper.readValue(
                authResult.getResponse().getContentAsString(),
                AuthResponse.class
        );
        Pattern pattern = Pattern.compile(".{20}\\..{72}\\..{86}");

        assertNotNull(response);
        assertNotNull(response.getJwtToken());

        Matcher matcher =pattern.matcher(response.getJwtToken());
        assertTrue(matcher.matches());

        return response.getJwtToken();

    }





}
