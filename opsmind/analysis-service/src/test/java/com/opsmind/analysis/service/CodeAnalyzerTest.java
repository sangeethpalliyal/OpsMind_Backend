package com.opsmind.analysis.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CodeAnalyzerTest {
    private final CodeAnalyzer analyzer = new CodeAnalyzer();

    @Test
    void detectsNestedLoops() {
        var result = analyzer.analyze("for(int i=0;i<n;i++){ for(int j=0;j<n;j++){} }");
        assertEquals("GREEN", result.status());
        assertTrue(result.findings().contains("Nested loop"));
    }

    @Test
    void detectsHardCodedPassword() {
        var result = analyzer.analyze("String password = \"secret\";");
        assertTrue(result.findings().contains("hard-coded password"));
        assertTrue(result.score() < 100);
    }
}
