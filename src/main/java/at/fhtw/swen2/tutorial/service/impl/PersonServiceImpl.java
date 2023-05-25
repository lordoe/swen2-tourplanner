package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.PersonRepository;
import at.fhtw.swen2.tutorial.service.mapper.PersonMapper;
import at.fhtw.swen2.tutorial.service.PersonService;
import at.fhtw.swen2.tutorial.service.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<Person> getPersonList() {
        return personMapper.fromEntity(personRepository.findAll());
    }

    @Override
    public Person addNew(Person person) {
        if (person == null){
            return null;
        }
        PersonEntity entity = personRepository.save(personMapper.toEntity(person));
        return personMapper.fromEntity(entity);
    }

    @Override
    public Person findByName(String name) {
        return personMapper.fromEntity(personRepository.findByName(name));
    }
}
