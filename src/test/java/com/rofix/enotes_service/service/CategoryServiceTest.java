package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Category Service Unit Test")
public class CategoryServiceTest {
    CategoryRequestDTO categoryDTO;
    CategoryResponseDTO categoryResponseDTO;
    Category category;
    List<Category> categories;
    List<CategoryResponseDTO> categoriesResponseList;
    Category updateCategory;
    CategoryResponseDTO updateCategoryResponseDTO;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryHelper categoryHelper;

    @BeforeEach
    public void setup() {
        categoryDTO = CategoryRequestDTO.builder()
                .name("category test")
                .description("description test")
                .isActive(true)
                .isDeleted(false)
                .build();

        updateCategory = Category.builder()
                .id(1L)
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .isActive(true)
                .isDeleted(false)
                .build();

        category = Category.builder()
                .id(1L)
                .name("category test")
                .description("description test")
                .isActive(true)
                .isDeleted(false)
                .build();

        categoryResponseDTO = CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .build();

        updateCategoryResponseDTO = CategoryResponseDTO.builder()
                .id(updateCategory.getId())
                .name(updateCategory.getName())
                .description(updateCategory.getDescription())
                .isActive(updateCategory.getIsActive())
                .build();

        categories = new ArrayList<>(List.of(
                Category.builder()
                        .id(1L)
                        .name("Category one")
                        .description("bla bla bla")
                        .isActive(true)
                        .build(),
                Category.builder()
                        .id(2L)
                        .name("Category 2")
                        .description("bla bla bla")
                        .isActive(true)
                        .build(),
                Category.builder()
                        .id(3L)
                        .name("Category 3")
                        .description("bla bla bla")
                        .isActive(true)
                        .build()
        ));

        categoriesResponseList = new ArrayList<>(List.of(
                CategoryResponseDTO.builder()
                        .id(1L)
                        .name("Category one")
                        .description("bla bla bla")
                        .isActive(true)
                        .build(),
                CategoryResponseDTO.builder()
                        .id(2L)
                        .name("Category 2")
                        .description("bla bla bla")
                        .isActive(true)
                        .build(),
                CategoryResponseDTO.builder()
                        .id(3L)
                        .name("Category 3")
                        .description("bla bla bla")
                        .isActive(true)
                        .build()
        ));

    }

    @Nested
    @DisplayName("Save Category Tests")
    class CreateCategoryTests {
        @Test
        @DisplayName("Save Category method test")
        public void shouldSaveCategorySuccessfully() {
            when(modelMapper.map(categoryDTO, Category.class)).thenReturn(category);
            when(categoryRepository.save(category)).thenReturn(category);
            when(modelMapper.map(category, CategoryResponseDTO.class)).thenReturn(categoryResponseDTO);

            CategoryResponseDTO categoryResponseDTO = categoryService.saveCategory(categoryDTO);

            Assertions.assertEquals(category.getId(), categoryResponseDTO.getId());
            Assertions.assertEquals(category.getName(), categoryResponseDTO.getName());
            Assertions.assertEquals(category.getDescription(), categoryResponseDTO.getDescription());

            verify(categoryRepository).save(category);
        }

        @Test
        @DisplayName("Category Not Found Test")
        public void shouldCategoryNotFound() {
            doThrow(new ConflictException("Category with name 'Existing Category' already exists!"))
                    .when(categoryHelper).checkedCategory(categoryDTO.getName(), null);

            Assertions.assertThrows(ConflictException.class, () -> categoryService.saveCategory(categoryDTO));
        }
    }

    @Nested
    @DisplayName("Get Category Tests")
    class GetCategoryTests {
        @Test
        public void shouldGetCategorySuccessfully() {
            when(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(anyLong())).thenReturn(category);
            when(modelMapper.map(category, CategoryResponseDTO.class)).thenReturn(categoryResponseDTO);

            CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(anyLong());

            Assertions.assertEquals(category.getId(), categoryResponseDTO.getId());
            Assertions.assertEquals(category.getName(), categoryResponseDTO.getName());
            Assertions.assertEquals(category.getDescription(), categoryResponseDTO.getDescription());

            verify(categoryHelper).getByIdAndActiveAndNotDeletedOrThrow(anyLong());
            verify(modelMapper).map(category, CategoryResponseDTO.class);
        }

        @Test
        public void shouldCategoryByIdNotFound() {
            Long categoryId = 99L;

            when(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(categoryId)).thenThrow(NotFoundException.class);

            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> categoryService.getCategoryById(categoryId),
                    "Not Exist any Category with Id: " + categoryId
            );

            verify(categoryHelper).getByIdAndActiveAndNotDeletedOrThrow(categoryId);
        }
    }

    @Nested
    @DisplayName("Get Categories Tests")
    class GetCategoriesTests {
        @Test
        public void shouldGetCategoriesSuccessfully() {
            when(categoryRepository.findAll()).thenReturn(categories);
            when(categoryHelper.getCategoryResponseDTO(categories)).thenReturn(categoriesResponseList);

            List<CategoryResponseDTO> categoryResponseDTOList = categoryService.getAllCategories();

            Assertions.assertEquals(categoriesResponseList, categoryResponseDTOList);

            verify(categoryRepository).findAll();
            verify(categoryHelper).getCategoryResponseDTO(categories);

        }

        @Test
        public void shouldGetEmptyList() {
            when(categoryRepository.findAll()).thenReturn(List.of());

            List<CategoryResponseDTO> categoryResponseDTOList = categoryService.getAllCategories();

            Assertions.assertEquals(List.of(), categoryResponseDTOList);
            Assertions.assertEquals(0, categoryResponseDTOList.size());

            verify(categoryRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Delete Category Tests")
    class DeleteCategoryTests {
        @Test
        public void shouldDeleteCategorySuccessfully() {
            when(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(category.getId())).thenReturn(category);

            String message = categoryService.deleteCategoryById(category.getId());

            String expected = "Category with name '" + category.getName() + "' has been deleted successfully...";

            Assertions.assertEquals(expected, message);

            verify(categoryHelper).getByIdAndActiveAndNotDeletedOrThrow(category.getId());
            verify(categoryRepository).save(category);
        }

        @Test
        public void shouldCategoryNotFound() {
            Long categoryId = 99L;
            when(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(categoryId)).thenThrow(NotFoundException.class);

            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> categoryService.deleteCategoryById(categoryId),
                    "category with Id " + categoryId + " not found"
            );

            verify(categoryHelper).getByIdAndActiveAndNotDeletedOrThrow(categoryId);
        }
    }

    @Nested
    @DisplayName("Edit Category Tests")
    class EditCategoryTests {
        @Test
        @DisplayName("Category editing Successfully")
        public void editCategorySuccessfully() {
            Long categoryId = 1L;

            when(categoryHelper.getCategoryByIdThrow(categoryId)).thenReturn(category);
            doNothing().when(categoryHelper).checkedCategory(categoryDTO.getName(), categoryId);
            when(categoryHelper.getUpdatedCategory(category, categoryDTO)).thenReturn(updateCategory);
            when(categoryRepository.save(updateCategory)).thenReturn(updateCategory);
            when(modelMapper.map(updateCategory, CategoryResponseDTO.class)).thenReturn(updateCategoryResponseDTO);

            CategoryResponseDTO categoryResponse = categoryService.editCategory(categoryId, categoryDTO);

            Assertions.assertNotNull(categoryResponse);
            Assertions.assertEquals(categoryId, categoryResponse.getId());
            Assertions.assertEquals(updateCategory.getName(), categoryResponse.getName());
            Assertions.assertEquals(updateCategory.getDescription(), categoryResponse.getDescription());

            verify(categoryHelper).getCategoryByIdThrow(categoryId);
            verify(categoryHelper).checkedCategory(categoryDTO.getName(), categoryId);
            verify(categoryHelper).getUpdatedCategory(category, categoryDTO);
            verify(categoryRepository).save(updateCategory);
            verify(modelMapper).map(updateCategory, CategoryResponseDTO.class);
        }

        @Test
        @DisplayName("Category Name already Exist")
        public void editCategoryNameAlreadyExist() {
            Long categoryId = 99L;
            String message = "Category with name '" + categoryDTO.getName() + "' already exists!";

            when(categoryHelper.getCategoryByIdThrow(categoryId)).thenReturn(category);
            doThrow(new ConflictException(message)).when(categoryHelper).checkedCategory(categoryDTO.getName(), categoryId);

            ConflictException exception = Assertions.assertThrows(
                    ConflictException.class,
                    () -> categoryService.editCategory(categoryId, categoryDTO)
            );

            Assertions.assertEquals(message, exception.getMessage());

            verify(categoryHelper).getCategoryByIdThrow(categoryId);
            verify(categoryHelper).checkedCategory(categoryDTO.getName(), categoryId);
        }
    }
}
