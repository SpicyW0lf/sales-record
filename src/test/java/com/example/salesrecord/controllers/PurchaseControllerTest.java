package com.example.salesrecord.controllers;

import com.example.salesrecord.models.Purchase;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.ProductRepository;
import com.example.salesrecord.repositories.PurchaseItemRepository;
import com.example.salesrecord.repositories.PurchaseRepository;
import com.example.salesrecord.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PurchaseControllerTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseController purchaseController;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    static {
        postgres.start();
    }
    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterAll
    void stopContainer() {
        postgres.stop();
    }

    @BeforeEach
    void clear() {
        purchaseRepository.clear();
        purchaseItemRepository.clear();
        productRepository.clear();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @WithUserDetails("admin")
    void startPurchaseShouldStartPurchase() throws Exception {
        mockMvc.perform(post("/purchase/start"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                        {
                                            "message": "Purchase successfully started"
                                        }
                                        """)
                );
    }

    @Test
    @WithUserDetails("admin")
    void doubleStartPurchaseShouldShowMessage() throws Exception {
        mockMvc.perform(post("/purchase/start"));
        mockMvc.perform(post("/purchase/start"))
                .andExpectAll(
                        status().is(200),
                        content().json("""
                                        {
                                            "message": "You already have started purchase"
                                        }
                                        """)
                );
    }

    @Test
    @WithUserDetails("admin")
    void stopPurchaseWithoutStartShouldShowMessage() throws Exception {
        mockMvc.perform(post("/purchase/stop"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                       {
                                            "message": "Purchase is not started"
                                       }
                                       """)
                );
    }

    @Test
    @WithUserDetails("admin")
    void stopPurchaseWithStartShouldStopPurchase() throws Exception {
        mockMvc.perform(post("/purchase/start"));
        mockMvc.perform(post("/purchase/stop"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                       {
                                            "message": "Current purchase is stopped"
                                       }
                                       """)
                );
    }

    @Test
    @WithUserDetails("admin")
    void addItemWithoutPurchaseShouldShowMessage() throws Exception {
        mockMvc.perform(post("/purchase/add").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "productCode": "asbies",
                            "qty": 2
                        }
                        """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                       {
                                            "message": "Purchase is not started"
                                       }
                                       """)
                );
    }
}