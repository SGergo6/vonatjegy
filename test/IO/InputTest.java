package IO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputTest {
    @BeforeEach
    public void setUp(){
        //Forrás: https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        String[] input = new String[]{"1", "2", "3,3", "3.3", "asd", "5"};
        ByteArrayInputStream in = new ByteArrayInputStream(String.join("\n", input).getBytes());
        System.setIn(in);
        Input.initialize(System.in);
    }

    @Test
    public void testIntStandardInput(){
        //Forrás: https://howtodoinjava.com/junit5/expected-exception-example/
        assertEquals(1, Input.nextInt(), "input: 1");
        assertEquals(2, Input.nextInt(), "input: 2");
        assertThrows(InputMismatchException.class, Input::nextInt, "input: 3,3");
        assertThrows(InputMismatchException.class, Input::nextInt, "input: 3.3");
        assertThrows(InputMismatchException.class, Input::nextInt, "input: asd");
    }


    @Test
    public void testDoubleStandardInput(){
        assertEquals(1.0, Input.nextDouble(), "input: 1");
        assertEquals(2.0, Input.nextDouble(), "input: 2");
        assertEquals(3.3, Input.nextDouble(), "input: 3,3");
        assertThrows(InputMismatchException.class, Input::nextDouble, "input: 3.3");
        assertThrows(InputMismatchException.class, Input::nextDouble, "input: asd");
    }

    @Test
    public void testStringStandardInput(){
        assertEquals("1", Input.nextLine(), "input: 1");
        assertEquals("2", Input.nextLine(), "input: 2");
        assertEquals("3,3", Input.nextLine(), "input: 3,3");
        assertEquals("3.3", Input.nextLine(), "input: 3.3");
        assertEquals("asd", Input.nextLine(), "input: asd");
    }

    @Test
    public void testIntAutoTryInput(){
        assertEquals(1, Input.getInt(""), "input: 1");
        assertEquals(2, Input.getInt(""), "input: 2");
        assertEquals(5, Input.getInt(""), "input: 3,3, 3.3, asd, 5");
    }

    @Test
    public void testDoubleAutoTryInput(){
        assertEquals(1.0, Input.getDouble(""), "input: 1");
        assertEquals(2.0, Input.getDouble(""), "input: 2");
        assertEquals(3.3, Input.getDouble(""), "input: 3,3, 3.3, asd, 5");
        assertEquals(5.0, Input.getDouble(""), "input: 3.3, asd, 5");
    }

    @AfterEach
    public void tearDown(){
        Input.close();
    }
}
