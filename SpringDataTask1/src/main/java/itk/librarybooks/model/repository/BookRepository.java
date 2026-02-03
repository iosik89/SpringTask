package itk.librarybooks.model.repository;

import itk.librarybooks.model.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (resSet, rowNum) -> new Book(
        resSet.getObject("id", UUID.class),
        resSet.getString("title"),
        resSet.getString("author"),
        resSet.getInt("publicationYear")
    );

    public int save(Book book) {
        return jdbcTemplate.update(
                "INSERT INTO BOOKS (id, title, author, publication_year) VALUES (?, ?, ?, ?)",
                book.id(), book.title(), book.author(), book.publicationYear()
        );
    }

    public int update(Book book) {
        return jdbcTemplate.update(
                "UPDATE BOOKS SET title = ?, author = ?, publication_year = ? WHERE id = ?",
                book.title(), book.author(), book.publicationYear(), book.id()
        );
    }

    public Optional<Book> findById(UUID id) {
        List<Book> results = jdbcTemplate.query("SELECT * FROM BOOKS WHERE id = ?", bookRowMapper, id);
        return results.stream().findFirst();
    }

    public int deleteById(UUID id){
        return jdbcTemplate.update("DELETE FROM BOOKS WHERE id = ?",id);
    }


}
