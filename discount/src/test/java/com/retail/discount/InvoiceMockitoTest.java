package com.retail.discount;

import com.retail.discount.dtos.InvoiceDTO;
import com.retail.discount.entity.Invoice;
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
public class InvoiceMockitoTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    ObjectMapper objectMapper=new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllBillTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/bill/getAllBills").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        System.out.println("**************");

    }

    @Test
    public void addBillTest() throws Exception {
        InvoiceDTO bill = new InvoiceDTO();
        bill.setId("");
        String billJsonRequest;
        try{
            billJsonRequest = objectMapper.writeValueAsString(bill);
        }catch (NoSuchElementException e){
            throw new Exception("error!!!");
        }
        mockMvc.perform(MockMvcRequestBuilders.post("/bill/calculateBill/{old_price}/{user_id}",79,"66f7be40-8740-49cf-a4f2-a73467623c24")
                .content(billJsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        System.out.println("*************");

    }
}
