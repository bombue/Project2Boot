package ru.akiselev.Project2Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (peopleService.loadUserByUsername(((Person) target).getUsername()).isPresent())
            errors.rejectValue("username", "", "Человек с таким username уже существует");
    }
}
