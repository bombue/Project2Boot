package ru.akiselev.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("books", peopleService.checkIfBookExpired(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";
        peopleService.create(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
