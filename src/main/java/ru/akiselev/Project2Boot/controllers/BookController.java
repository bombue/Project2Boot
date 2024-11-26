package ru.akiselev.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.Project2Boot.models.Book;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.services.BooksService;
import ru.akiselev.Project2Boot.services.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(required = false, name = "page") Optional<Integer> page,
                        @RequestParam(required = false, name = "books_per_page") Optional<Integer> pageSize,
                        @RequestParam(required = false, name = "sort_by_year") Optional<Boolean> sortByYear) {
        model.addAttribute("books", booksService.findAll(page, pageSize, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
//        if (book.getOwner() == null) {
//            model.addAttribute("bookOwner", null);
//        } else {
//            model.addAttribute("bookOwner", book.getOwner());
//        }
        model.addAttribute("bookOwner", book.getOwner());
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/search")
    public String searchByName(Model model,
                               @RequestParam(required = false, name = "name") Optional<String> name) {
        name.ifPresent(s -> model.addAttribute("books", booksService.findByNameStartingWith(s)));
        return "books/search";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        System.out.println("edit: "+book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        Book bookToBeUpdated = booksService.findOne(id);
        bookToBeUpdated.setName(book.getName());
        bookToBeUpdated.setAuthor(book.getAuthor());
        bookToBeUpdated.setYearOfPublish(book.getYearOfPublish());
        booksService.update(id, bookToBeUpdated);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/reserve")
    public String reserve(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        booksService.reserve(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books";
    }
}
