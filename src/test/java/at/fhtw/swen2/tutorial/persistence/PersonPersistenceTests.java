package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonPersistenceTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	void testPersonRepository() {
		PersonEntity maxi = PersonEntity.builder()
				.name("Maxi")
				.email("maxi@email.com")
				.build();
		personRepository.save(maxi);
		personRepository.findAll().forEach(System.out::println);
	}

}
