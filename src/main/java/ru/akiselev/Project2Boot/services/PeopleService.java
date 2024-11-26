package ru.akiselev.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.Project2Boot.models.Book;
import ru.akiselev.Project2Boot.models.Person;
import ru.akiselev.Project2Boot.repositories.PeopleRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Person person = peopleRepository.findById(id).orElse(null);
        Hibernate.initialize(person.getBookList());
        return person;
    }

    @Transactional
    public void create(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Map<Book, Boolean> checkIfBookExpired(int personId) {
        Map<Book, Boolean> booksMap = new HashMap<>();
        peopleRepository.findById(personId).get().getBookList().forEach(book -> {
            System.out.println("akiselev: "+(Math.abs(ChronoUnit.DAYS.between(LocalDateTime.now(), book.getBookReservedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) > 10));
            booksMap.put(book, (Math.abs(ChronoUnit.DAYS.between(LocalDateTime.now(), book.getBookReservedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) > 10));
        });

        return booksMap;
    }

}
