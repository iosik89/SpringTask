package itk.librarybooks.service;

import itk.librarybooks.model.entity.Book;
import itk.librarybooks.model.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Книга не найдена"));
    }

    @Transactional
    public Book create(Book book) {
        Book newBook = new Book(UUID.randomUUID(), book.title(), book.author(), book.publicationYear());
        repository.save(newBook);
        return newBook;
    }

    @Transactional
    public Book update(UUID id, Book book) {
        Book toUpdate = new Book(id, book.title(), book.author(), book.publicationYear());
        if (repository.update(toUpdate) == 0) {
            throw new NoSuchElementException("Не удалось обновить: книга не найдена");
        }
        return toUpdate;
    }

    @Transactional
    public void delete(UUID id) {
        if (repository.deleteById(id) == 0) {
            throw new NoSuchElementException("Не удалось удалить: книга не найдена");
        }
    }
}
