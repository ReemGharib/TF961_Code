package com.retail.discount;

import com.retail.discount.dtos.UserDTO;
import com.retail.discount.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMockitoTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    ObjectMapper objectMapper=new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllUserTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/getAllUsers/").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
    }

    @Test
    public void addUserTest() throws Exception {
        UserDTO user = new UserDTO();
        user.setId("");
        user.setEmail("myEmail@gmail.com");
        user.setFirstName("Sam");
        user.setLastName("Sam");
        String jsonRequest;
        try{
            jsonRequest = objectMapper.writeValueAsString(user);
        }catch (NoSuchElementException e){
            throw new Exception("error!!!");
        }
        mockMvc.perform(MockMvcRequestBuilders.post("/users/addUser/{userTypeId}","5dd51b48-f276-4df3-8a0a-94543939abd2").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}
