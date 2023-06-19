package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.dto.dtoCategory.PaginationResponse;
import peaksoft.entity.Category;
import peaksoft.entity.User;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.CategoryService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final UserRepository userRepository;

    private User getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.getUserByEmail(name).orElseThrow(() -> new NotFoundException("User with email: " + name + " us bit found!"));
        return  user;
    }
    @Override
    public PaginationResponse getAllCategories(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1, pageSize);
        Page<CategoryResponse> allCategories = repository.getAllCategories(pageable);
        return PaginationResponse.builder()
                .categories(allCategories.getContent())
                .currentPage(allCategories.getNumber()+1)
                .pageSize(allCategories.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        repository.save(category);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is not found"));
        category.setName(categoryRequest.name());
        repository.save(category);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is not found"));
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public SimpleResponse deleteCategoryById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }else throw new BadRequestException("User with id:"+id+" is not found");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public PaginationResponse searchCategoryByName(String word, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<CategoryResponse> search = repository.search(word, pageable);
        return PaginationResponse.builder()
                .categories(search.getContent())
                .currentPage(search.getNumber()+1)
                .pageSize(search.getTotalPages())
                .build();
    }


}
