package com.uvg.gt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.uvg.gt.Model.Climates;
import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public class RelationshipTests {
    @Test
    public void canChangeWeight() {
        HashMap<Climates, Integer> weights = new HashMap<>() {
            {
                put(Climates.NORMAL, 56);
                put(Climates.BLIZZARD, 20);
                put(Climates.RAIN, 21);
                put(Climates.STORM, 30);
            }
        };

        var relation = new Relationship(new Node("Hello"), new Node("Goodbye"), weights);
        assertEquals(Climates.NORMAL, relation.getClimate());
        assertEquals(56, relation.getWeight());

        relation.changeClimate(Climates.BLIZZARD);
        assertEquals(Climates.BLIZZARD, relation.getClimate());
        assertEquals(20, relation.getWeight());
    }
}
