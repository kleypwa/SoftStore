package me.kley.soft_store.controllerTests;

import me.kley.soft_store.controllers.ContentController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** Integration tests for {@link ContentController}*/
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ContentControllerIT {
    private final MockMvc mockMvc;

    @Autowired
    public ContentControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testLoginReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/login"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSignupReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/signup"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testHomeReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCatalogReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/catalog"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCabinetReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cabinet"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testMarketsReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/markets"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBucketReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/bucket"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}