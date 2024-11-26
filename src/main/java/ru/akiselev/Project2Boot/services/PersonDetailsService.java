package ru.akiselev.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.repositories.PeopleRepository;
import ru.akiselev.Project2Boot.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = this.peopleRepository.findByUsername(username);
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(person.get());
    }
}
