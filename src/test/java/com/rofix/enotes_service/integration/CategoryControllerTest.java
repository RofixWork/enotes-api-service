package com.rofix.enotes_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.request.LoginUserRequestDTO;
import com.rofix.enotes_service.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@DisplayName("Category Controller Integration Test")
public class CategoryControllerTest {
    CategoryRequestDTO categoryRequestDTO;
    CategoryRequestDTO updatedCategoryRequestDTO;
    CategoryRequestDTO invalidCategoryRequestDTO;
    Category category;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private String generateToken() throws Exception {
        LoginUserRequestDTO loginUserRequestDTO = LoginUserRequestDTO.builder()
                .email("wourkout123@gmail.com")
                .password("ahmed123456")
                .build();

        String response = mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginUserRequestDTO))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var root = objectMapper.readTree(response);

        String token = root.path("data").path("token").asText();

        return "Bearer " + token;
    }

    @BeforeEach
    public void setup() {
        categoryRequestDTO = CategoryRequestDTO.builder()
                .name("category test")
                .description("description test")
                .isActive(true)
                .isDeleted(false)
                .build();

        category = Category.builder()
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .isActive(categoryRequestDTO.getIsActive())
                .isDeleted(categoryRequestDTO.getIsDeleted())
                .build();

        updatedCategoryRequestDTO = CategoryRequestDTO.builder()
                .name("Drama Live")
                .description("This is Drama Live Category")
                .build();

        invalidCategoryRequestDTO = CategoryRequestDTO.builder()
                .name("  ")
                .description("bla")
                .build();
    }

    @Nested
    @DisplayName("Get Categories And Actives")
    class GetCategoriesAndActives {
        @Test
        @DisplayName("Should Get All Categories")
        public void shouldGetAllCategories() throws Exception {
            mockMvc.perform(
                            get("/api/v1/categories")
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true));
        }

        @Test
        @DisplayName("Should Get Active Categories")
        public void shouldGetActiveCategories() throws Exception {
            mockMvc.perform(
                            get("/api/v1/categories/active")
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true));
        }
    }

    @Nested
    @DisplayName("Create Category Tests")
    class CreateCategoryTests {
        @Test
        @DisplayName("should Create category success")
        public void shouldCreateCategorySuccessfully() throws Exception {
            String token = generateToken();
            mockMvc.perform(
                            post("/api/v1/categories")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(categoryRequestDTO))
                                    .header(HttpHeaders.AUTHORIZATION, token)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value(true))
                    .andExpect(jsonPath("$.message").value("Category created successfully"));
        }

        @Test
        @DisplayName("should Create category Failed")
        public void shouldCreateCategoryFailed() throws Exception {
            String token = generateToken();

            mockMvc.perform(
                            post("/api/v1/categories")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(invalidCategoryRequestDTO))
                                    .header(HttpHeaders.AUTHORIZATION, token)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(false));
        }

        @Test
        @DisplayName("should category Already Exist")
        public void shouldCategoryAlreadyExist() throws Exception {
            CategoryRequestDTO categoryRequest = CategoryRequestDTO.builder()
                    .name("Category test")
                    .description("bla bla bla bla")
                    .build();

            String token = generateToken();

            mockMvc.perform(
                            post("/api/v1/categories")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(categoryRequest))
                                    .header(HttpHeaders.AUTHORIZATION, token)
                    )
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(false));
        }
    }

    @Nested
    @DisplayName("Get Category Tests")
    class GetCategoryTests {
        @Test
        @DisplayName("should get category success")
        public void shouldGetCategorySuccessfully() throws Exception {
            String token = generateToken();
            mockMvc.perform(
                            get("/api/v1/categories/1")
                                    .header(HttpHeaders.AUTHORIZATION, token)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true))
                    .andExpect(jsonPath("$.message").value("Get Category"));
        }

        @Test
        @DisplayName("should get category not exist")
        public void shouldGetCategoryNotExist() throws Exception {
            String token = generateToken();
            mockMvc.perform(
                            get("/api/v1/categories/1000")
                                    .header(HttpHeaders.AUTHORIZATION, token)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(false));
        }
    }

    @Nested
    @DisplayName("Edit Category Tests")
    class EditCategoryTests {
        @Test
        @DisplayName("Should Edit Category Success")
        public void shouldEditCategorySuccessfully() throws Exception {
            mockMvc.perform(
                            put("/api/v1/categories/1")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedCategoryRequestDTO))
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true))
                    .andExpect(jsonPath("$.message").value("Category has been Updated"));
        }

        @Test
        @DisplayName("Should Edit Category Failed")
        public void shouldEditCategoryFailed() throws Exception {
            mockMvc.perform(
                            put("/api/v1/categories/1")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(invalidCategoryRequestDTO))
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(false));
        }

        @Test
        @DisplayName("Should Edit Category Failed")
        public void shouldCategoryNameAlreadyExist() throws Exception {
            mockMvc.perform(
                            put("/api/v1/categories/1")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedCategoryRequestDTO))
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(false));
        }
    }

    @Nested
    @DisplayName("Delete Category Tests")
    class DeleteCategoryTests {
        @Test
        @DisplayName("Should delete Category Success")
        public void shouldDeleteCategorySuccess() throws Exception {
            mockMvc.perform(
                            delete("/api/v1/categories/27")
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true));
        }

        @Test
        @DisplayName("Should Category Not Exist")
        public void shouldCategoryNotExist() throws Exception {
            mockMvc.perform(
                            delete("/api/v1/categories/1000")
                                    .header(HttpHeaders.AUTHORIZATION, generateToken())
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(false));
        }
    }
}
