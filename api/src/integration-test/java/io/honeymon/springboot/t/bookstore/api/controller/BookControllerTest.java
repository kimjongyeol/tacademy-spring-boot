package io.honeymon.springboot.t.bookstore.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import io.honeymon.springboot.t.bookstore.api.service.book.BookService;
import io.honeymon.springboot.t.bookstore.core.domain.book.Book;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {
    private MockMvc mockMvc;
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @InjectMocks
    BookController mockBookController;
    @Mock
    BookService bookService;

    @Before
    public void setUp() {
        //mockBookController 내에 Mock 처리된 bookService를 주입하기 위해 반드시 선언해줘야 함
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mockBookController)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testOptions() throws Exception {
        this.mockMvc.perform(options("/books").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("books-options"));
    }

    @Test
    public void testHead() throws Exception {
        Long id = 1L;
        when(bookService.findById(id)).thenReturn(Optional.of(new Book("test-isbn", "test-book")));

        this.mockMvc.perform(head("/books/{id}", 1).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(document("books-head"));
    }
}