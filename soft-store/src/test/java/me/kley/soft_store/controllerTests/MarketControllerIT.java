package me.kley.soft_store.controllerTests;

import me.kley.soft_store.controllers.MarketController;
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

/** Integration tests for {@link MarketController}*/
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MarketControllerIT {
    private final MockMvc mockMvc;

    @Autowired
    public MarketControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testListMarketsReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/toys"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}