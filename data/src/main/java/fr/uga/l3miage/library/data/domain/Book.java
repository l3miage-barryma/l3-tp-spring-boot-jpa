package fr.uga.l3miage.library.data.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Book")
@NamedQueries({
    @NamedQuery(name="all-books", 
                query="select a from Book a order by a.title ASC"),

    @NamedQuery(name="find-books-by-title", 
                query="SELECT b FROM Book b WHERE LOWER(b.title) LIKE :titlePart"),
   @NamedQuery(name="find-books-by-author-and-title",
                query="SELECT b FROM Book b join b.authors a WHERE a.id =: id and  LOWER(b.title) LIKE lower(:titlePart)"),

    @NamedQuery(name="find-books-by-authors-name",
             query="SELECT b FROM Book b join b.authors a where a.fullName ILIKE concat('%' , :namePart, '%')"),


 @NamedQuery(name="find-books-by-several-authors",
                query="SELECT b FROM Book b join b.authors a group by b having count(a) > ?1" )
 
})


public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name= "title")
    private String title;
    private long isbn;
    private String publisher;
    @Column(name= "annee")
    private short year;
    private Language language;

    //@Transient
    @ManyToMany(mappedBy="books")
    private Set<Author> authors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new HashSet<>();
        }
        this.authors.add(author);
    }

    public enum Language {
        FRENCH,
        ENGLISH
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn && year == book.year && Objects.equals(title, book.title) && Objects.equals(publisher, book.publisher) && language == book.language && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, publisher, year, language, authors);
    }
}
