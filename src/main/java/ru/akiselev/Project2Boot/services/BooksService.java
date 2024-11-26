package ru.akiselev.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.Project2Boot.models.Book;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Optional<Integer> page, Optional<Integer> pageSize, Optional<Boolean> sortByYear) {
        if (page.isPresent() && pageSize.isPresent() && sortByYear.isPresent() && sortByYear.get())
            return booksRepository.findAll(PageRequest.of(page.get(), pageSize.get(), Sort.by("yearOfPublish"))).getContent();
        else if (page.isPresent() && pageSize.isPresent()) {
            return booksRepository.findAll(PageRequest.of(page.get(), pageSize.get())).getContent();
        } else if (sortByYear.isPresent() && sortByYear.get())
            return booksRepository.findAll(Sort.by("yearOfPublish"));
        else
            return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void reserve(int id, Person person) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.setOwner(person);
            book.setBookReservedDate(new Date());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.setOwner(null);
            book.setBookReservedDate(null);
            booksRepository.save(book);
        }
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findByNameStartingWith(String name) {
        return booksRepository.findByNameStartingWith(name);
    }
}
