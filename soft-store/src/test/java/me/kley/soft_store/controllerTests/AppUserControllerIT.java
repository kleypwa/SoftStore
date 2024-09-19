package me.kley.soft_store.controllerTests;

import me.kley.soft_store.controllers.AppUserController;
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

/** Integration tests for {@link AppUserController}*/
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AppUserControllerIT {
    private final MockMvc mockMvc;

    @Autowired
    public AppUserControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testListAppUsersReturnStatusIsOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/appusers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}