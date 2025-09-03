package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Category Controller Unit Test")
public class CategoryControllerTest {
    Category category;
    CategoryRequestDTO categoryRequestDTO;
    CategoryResponseDTO categoryResponseDTO;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setup() {
        categoryRequestDTO = CategoryRequestDTO.builder()
                .name("category test")
                .description("description test")
                .isActive(true)
                .isDeleted(false)
                .build();

        category = Category.builder()
                .id(1L)
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .isActive(true)
                .isDeleted(false)
                .build();

        categoryResponseDTO = CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .build();
    }

    @Nested
    @DisplayName("Create Category Tests")
    class CreateCategoryTests {
        @Test
        @DisplayName("should create category success")
        public void shouldCreateCategorySuccessfully() {
            when(categoryService.saveCategory(any(CategoryRequestDTO.class))).thenReturn(any(CategoryResponseDTO.class));

            ResponseEntity<?> categoryResponse = categoryController.createCategory(categoryRequestDTO);
            var body = categoryResponse.getBody();
            Map<String, Object> json = (Map<String, Object>) body;

            assertEquals(HttpStatus.CREATED, categoryResponse.getStatusCode());
            assertEquals(true, json.get("status"));
            assertEquals("Category created successfully", json.get("message"));
        }

        @Test
        @DisplayName("should create category failed")
        public void shouldCreateCategoryFailed() {
            doThrow(new BadRequestException("Category Created Failed")).when(categoryService).saveCategory(any(CategoryRequestDTO.class));

            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> categoryController.createCategory(categoryRequestDTO));

            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
            assertEquals("Category Created Failed", exception.getMessage());
        }

        @Test
        @DisplayName("should Category Already Exist")
        public void shouldCategoryAlreadyExist() {
            doThrow(new ConflictException("Category Name Already Exist")).when(categoryService).saveCategory(any(CategoryRequestDTO.class));

            ConflictException exception = assertThrows(
                    ConflictException.class,
                    () -> categoryController.createCategory(categoryRequestDTO));

            assertEquals(HttpStatus.CONFLICT, exception.getStatus());
            assertEquals("Category Name Already Exist", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Get Category Tests")
    class GetCategoryTests {
        @Test
        @DisplayName("should get category success")
        public void shouldGetCategorySuccessfully() {
            when(categoryService.getCategoryById(anyLong())).thenReturn(categoryResponseDTO);

            ResponseEntity<?> response = categoryController.getCategory(anyLong());
            var body = response.getBody();
            Map<String, Object> json = (Map<String, Object>) body;

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(true, json.get("status"));
            assertEquals("Get Category", json.get("message"));

        }

        @Test
        @DisplayName("should Category not Exist")
        public void shouldCategoryNotExist() {
            doThrow(new NotFoundException("Category Not Exist")).when(categoryService).getCategoryById(anyLong());

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> categoryController.getCategory(anyLong())
            );

            assertNotNull(exception);
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Category Not Exist", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Edit Category Tests")
    class EditCategoryTests {
        @Test
        @DisplayName("Should Edit Category Success")
        public void shouldEditCategorySuccessfully() {
            when(categoryService.editCategory(anyLong(), any(CategoryRequestDTO.class))).thenReturn(categoryResponseDTO);
            ResponseEntity<?> response = categoryController.updateCategory(1L, categoryRequestDTO);
            var body = response.getBody();
            Map<String, Object> json = (Map<String, Object>) body;

            assertNotNull(response);
            assertNotNull(json);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(true, json.get("status"));
            assertEquals("Category has been Updated", json.get("message"));
        }

        @Test
        @DisplayName("Should Edit Category Failed")
        public void shouldEditCategoryFailed() {
            doThrow(new BadRequestException("Category Edit Failed")).when(categoryService).editCategory(1L, categoryRequestDTO);

            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> categoryController.updateCategory(1L, categoryRequestDTO)
            );

            assertNotNull(exception);
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
            assertEquals("Category Edit Failed", exception.getMessage());
        }

        @Test
        @DisplayName("Should Category Name Already Exist")
        public void shouldCategoryNameAlreadyExist() {
            doThrow(new ConflictException("Category Name Already Exist")).when(categoryService).editCategory(1L, categoryRequestDTO);

            ConflictException exception = assertThrows(
                    ConflictException.class,
                    () -> categoryController.updateCategory(1L, categoryRequestDTO)
            );

            assertNotNull(exception);
            assertEquals(HttpStatus.CONFLICT, exception.getStatus());
            assertEquals("Category Name Already Exist", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Delete Category Tests")
    class DeleteCategoryTests {
        @Test
        @DisplayName("Should delete Category Success")
        public void shouldDeleteCategorySuccess() {
            when(categoryService.deleteCategoryById(anyLong())).thenReturn("Category has been deleted");
            ResponseEntity<?> response = categoryController.deleteCategory(1L);
            var body = response.getBody();
            Map<String, Object> json = (Map<String, Object>) body;

            assertNotNull(response);
            assertNotNull(json);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Category has been deleted", json.get("message"));
        }

        @Test
        @DisplayName("Should Category Not Exist")
        public void shouldCategoryNotExist() {
            doThrow(new NotFoundException("Category Not Exist")).when(categoryService).deleteCategoryById(anyLong());

            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> categoryController.deleteCategory(1L)
            );
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Category Not Exist", exception.getMessage());
        }
    }
}
