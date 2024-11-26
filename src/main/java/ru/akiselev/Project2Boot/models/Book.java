package ru.akiselev.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Book name must be entered")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Book author must be entered")
    @Column(name = "author")
    private String author;
    @Min(value = 1, message = "Year of publish must be > 0")
    @Column(name = "yearofpublish")
    private int yearOfPublish;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "book_reserved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookReservedDate;

    public Book() {}

    public Book(String name, String author, int yearOfPublish) {
        this.name = name;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublish=" + yearOfPublish +
                ", owner=" + owner +
                '}';
    }
}
