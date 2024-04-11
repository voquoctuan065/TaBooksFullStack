package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.*;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.mapper.impl.CategoriesMapper;
import com.tacm.tabooksapi.mapper.impl.NXBsMapper;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.CategoriesService;
import com.tacm.tabooksapi.service.NXBsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private CategoriesService categoriesService;
    private CategoriesMapper categoriesMapper;
    private BookService bookService;
    private BookMapper bookMapper;
    private NXBsMapper nxbMapper;
    private NXBsService nxbService;
    @Autowired
    public AdminController(CategoriesService categoriesService, CategoriesMapper categoriesMapper,
                           BookService bookService, BookMapper bookMapper,
                           NXBsMapper nxbMapper, NXBsService nxbService) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.nxbMapper = nxbMapper;
        this.nxbService = nxbService;
    }


    //------------------------------------------------- Category ------------------------------------------------//
    @GetMapping("/category")
    public CategoriesPageDto getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Categories> categoriesPage = categoriesService.getAllCategorires(page, size);
        List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
        int totalPages = categoriesPage.getTotalPages();

        return new CategoriesPageDto(categoriesDtoList, totalPages);
    }

    @GetMapping("/category/search")
    public CategoriesPageDto searchCategoriesByName(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Categories> categoriesPage =  categoriesService.searchCategoriesByName(keyword, PageRequest.of(page, size));
        List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
        int totalPages = categoriesPage.getTotalPages();
        return new CategoriesPageDto(categoriesDtoList, totalPages);
    }

    @PostMapping(path = "/category/add-category")
    public CategoriesDto createCategories(@RequestBody CategoriesDto categoriesDto) {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories savedCategories = categoriesService.create(categories);
        return categoriesMapper.mapTo(savedCategories);
    }

    @GetMapping(path = "/category/list-category")
    public List<CategoriesDto> listCategories() {
        List<Categories> category = categoriesService.findAll();
        return category.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
    }

    @DeleteMapping(path = "/category/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategories(@PathVariable("categoryId") Long categoryId){
        categoriesService.deleteById(categoryId);
        ApiResponse res = new ApiResponse();
        res.setMessage("category deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/category/update/{categoryId}")
    public ResponseEntity<CategoriesDto> updateCategory(@RequestBody CategoriesDto categoriesDto,
                                                     @PathVariable("categoryId") Long categoryId) throws ApiException {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories updatedCategories = categoriesService.updateCategory(categories, categoryId);

        return new ResponseEntity<>(categoriesMapper.mapTo(updatedCategories), HttpStatus.CREATED);
    }
    //------------------------------------------------- End Category ------------------------------------------------//

    //------------------------------------------------- Book ------------------------------------------------//
    @GetMapping("/book")
    public BooksPageDto getPageBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Books> booksPage = bookService.getPageBook(page, size);
        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        int totalPages = booksPage.getTotalPages();

        return new BooksPageDto(booksDtoList, totalPages);
    }

    @GetMapping("/book/search")
    public BooksPageDto searchBookByName(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Books> booksPage =  bookService.searchBookByName(keyword, PageRequest.of(page, size));
        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        int totalPages = booksPage.getTotalPages();
        return new BooksPageDto(booksDtoList, totalPages);
    }

    public


    //------------------------------------------------- End Book ------------------------------------------------//

    //------------------------------------------------- NXBs ------------------------------------------------//
    @GetMapping(path = "/nxb/list-nxb")
    public List<NXBsDto> getListNXBs() {
        List<NXBs> nxbList = nxbService.findAll();
        return nxbList.stream().map(nxbMapper::mapTo).collect(Collectors.toList());
    }
    //------------------------------------------------- End NXBs ------------------------------------------------//
}
