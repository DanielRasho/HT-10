package com.uvg.gt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.uvg.gt.Model.Climates;
import com.uvg.gt.Model.DataParser;

public class DataParserTests {
    private DataParser parser = new DataParser();

    @Test
    public void parsesLine() {
        var line = "BuenosAires SaoPaulo 10 15 20 50";
        var parsed = parser.parse(line);

        assertEquals("BuenosAires", parsed.getOrigin().getLabel());
        assertEquals("SaoPaulo", parsed.getDestination().getLabel());
        assertEquals(10, parsed.getWeight());
        assertEquals(Climates.NORMAL, parsed.getClimate());
    }
}
