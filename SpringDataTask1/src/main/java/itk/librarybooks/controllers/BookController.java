package itk.librarybooks.controllers;

import itk.librarybooks.model.entity.Book;
import itk.librarybooks.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) { this.service = service; }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable UUID id) { return service.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) { return service.create(book); }

    @PutMapping("/{id}")
    public Book update(@PathVariable UUID id, @RequestBody Book book) { return service.update(id, book); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { service.delete(id); }
}
