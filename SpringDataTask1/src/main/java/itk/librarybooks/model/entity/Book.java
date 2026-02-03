package itk.librarybooks.model.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("BOOKS")
public record Book(

        UUID id,
        String title,
        String author,
        Integer publicationYear
) {

}
