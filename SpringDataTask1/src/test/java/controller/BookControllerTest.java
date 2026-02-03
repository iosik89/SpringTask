package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itk.librarybooks.Application;
import itk.librarybooks.controllers.BookController;
import itk.librarybooks.model.entity.Book;
import itk.librarybooks.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = Application.class)
public class BookControllerTest {

   private Book book;

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   ObjectMapper objectMapper;

   @MockitoBean
   BookService bookService;


   @BeforeEach
   void create(){
       book = new Book(UUID.randomUUID(),
               "BookTest",
               "AuthorTest",
               2026);
   }

   @Test
   public void getBookTest() throws Exception {

       when(bookService.getById(book.id())).thenReturn(book);

       mockMvc.perform(get("/api/books/{id}",book.id()))
               .andExpect(status().isOk())
               .andExpect(content().json("{'id':'" + book.id() + "','title':'BookTest'}"));

   }

   @Test
   public void createBookTest() throws Exception {

       when(bookService.create(book)).thenReturn(book);

       String json = objectMapper.writeValueAsString(book);

       mockMvc.perform(post("/api/books")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isCreated())
               .andExpect(content().json(json));
   }

   @Test
   public void updateBook() throws Exception {

   Book update = new Book(
           UUID.randomUUID(),
           "BookUpdate",
           "AuthorUpdate",
           2020);

   when(bookService.update(book.id(),update)).thenReturn(update);

   String json = objectMapper.writeValueAsString(update);

   mockMvc.perform(put("/api/books/{id}",book.id())
           .contentType(MediaType.APPLICATION_JSON)
           .content(json))
           .andExpect(status().isOk())
           .andExpect(content().json(json));
   }

   @Test
   public void deleteBook() throws Exception {

       mockMvc.perform(delete("/api/books/{id}", book.id()))
               .andExpect(status().isNoContent());

       Mockito.verify(bookService, Mockito.times(1)).delete(book.id());
   }




}
