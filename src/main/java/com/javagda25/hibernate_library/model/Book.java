package com.javagda25.hibernate_library.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book implements IBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private int yearOfPublish;

    @Formula(value = "(year(now()) - yearOfPublish)")
    private int bookAge;

    private int numberOfPages;

    @Formula(value = "(select count(*) from booklent bl where bl.book_id = id and bl.dateReturn is not null)")
    private int copiesAvailable;

    @Formula(value = "(select count(*) from booklent bl where bl.book_id = id and bl.dateReturn is null)")
    private int copiesBorrowed;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookLent> currentLents;
}
