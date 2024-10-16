package com.example;

import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.ValueSource;

// import com.example.sql.SqlQuery;
// import com.example.sql.exceptions.InvalidSyntaxError;

import org.junit.Test;

public class AppTest 
{
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    // @ParameterizedTest
    // @ValueSource(strings = {
    //     "SELECT * ", "SELECT name, students", "FROM table1", "SELECT * WHERE a=b FROM table"
    // })
    // public void testInvalidQuery(String candidate){
    //     Exception exception = assertThrows(InvalidSyntaxError.class, () -> {
    //         new SqlQuery(candidate);
    //     });
    //     assertTrue(exception.getMessage().contains("Invalid syntax near"));
    // }

    // @ParameterizedTest
    // @ValueSource(strings = {
    //         "SELECT * FROM students"
    // })
    // public void testValidQuery(String candidate) {
    //     assertDoesNotThrow(() -> {
    //         new SqlQuery(candidate);
    //     });
    // }
}
