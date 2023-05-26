package com.semoss.agricola.GamePlay.domain.card.Occupation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class OccupationTest {

    @Value("${landAgent.name}")
    private String expectedName;

    @Value("${landAgent.description}")
    private String expectedDescription;

    @Autowired
    private LandAgent landAgent;

    @Test
    public void testYamlValueInjection() {
        // Given

        // When
        String actualName = landAgent.getName();
        String actualDescription = landAgent.getDescription();

        // Then
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedDescription, actualDescription);
    }

}