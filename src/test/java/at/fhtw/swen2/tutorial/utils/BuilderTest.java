package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.service.dto.Person;
import org.junit.jupiter.api.Test;

public class BuilderTest {

    @Test
    void testPersonBuilder() {
        Person maxi = Person.builder()
                .name("Maxi")
                .id(11L)
                .build();
    }


}
