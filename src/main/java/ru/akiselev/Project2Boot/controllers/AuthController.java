package ru.akiselev.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.services.PeopleService;
import ru.akiselev.Project2Boot.util.PersonValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {
//    private final PersonValidator personValidator;
//    private final PeopleService peopleService;
//
//    @Autowired
//    public AuthController(PersonValidator personValidator, PeopleService peopleService) {
//        this.personValidator = personValidator;
//        this.peopleService = peopleService;
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") Person person) {
//        return "auth/registration";
//    }
//
//    @PostMapping("/registration")
//    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
//        personValidator.validate(person, bindingResult);
//        if (bindingResult.hasErrors())
//            return "/auth/registration";
//        peopleService.create(person);
//        return "redirect:auth/login";
//    }
}
