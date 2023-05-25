package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.PersonService;
import at.fhtw.swen2.tutorial.service.dto.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceDbImplTest {

    @Autowired
    PersonService personService;

    @Test
    public void findByNameTest(){
        // Arrange
        Person person = Person.builder()
                .name("Hans")
                .isEmployed(true)
                .build();
        personService.addNew(person);
        String name = "Hans";

        // Act
        Person person1 = personService.findByName(name);

        // Assert
        assertNotNull(person1);
        assertEquals(name, person1.getName());
    }

}