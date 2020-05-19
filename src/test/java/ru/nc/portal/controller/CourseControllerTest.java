package ru.nc.portal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.nc.portal.model.Course;
import ru.nc.portal.payload.LoginRequest;
import ru.nc.portal.payload.SignUpRequest;
import ru.nc.portal.service.CourseService;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
@ComponentScan({"ru.nc.portal"})
public class CourseControllerTest {

    @Autowired(required = true)
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @MockBean
    @Qualifier("courseServiceImpl")
    private CourseService courseService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(filterChainProxy))
                .build();
    }


    @Test
    public void givenCourses_whenGetCoursesByNameWithToken_thenReturnJsonArray() throws Exception {
//        ObjectWriter jsonMapper = createObjectToJsonMapper();
//        JacksonJsonParser jsonParser = new JacksonJsonParser();
//
//
//        String username = "test";
//        String email = "test@gmail.com";
//        String password = "test";
//
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail(email);
//        signUpRequest.setUsername(username);
//        signUpRequest.setPassword(password);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail(email);
//        loginRequest.setPassword(password);
//
//        String requestSignUpJson=jsonMapper.writeValueAsString(signUpRequest);
//        String requestLoginJson=jsonMapper.writeValueAsString(loginRequest);
//
//        mockMvc.perform(post("/auth/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestSignUpJson)
//        );
//
//        ResultActions result = mockMvc.perform(post("/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestLoginJson)
//        );
//
//        String resultString = result.andReturn().getResponse().getContentAsString();
//        String accessToken = jsonParser.parseMap(resultString).get("accessToken").toString();
//
//        String name ="java";
//        Course course = new Course("java", "description", null);
//        List<Course> courses= Collections.singletonList(course);
//        given(courseService.getCoursesByName(name)).willReturn(courses);
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/by-name/java")
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is("java")))
//                .andExpect((jsonPath("$[0].description", is("description"))));

    }

    private static ObjectWriter createObjectToJsonMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter();
    }
}
