//Name of the file: generate_file_test.java
//What the code does: Test the generate_file class
//Authors: Yao Ming
package test;

import org.junit.jupiter.api.Test;
import vote.generate_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing for generate_file class
 */
public class generate_file_test {

    /**
     * Create file test case
     *
     * @throws IOException if the file name already exists
     */
    @Test
    public void test_create() throws IOException {
        String filename = "test_file";
        File f = generate_file.create(filename);
        assertEquals(true, f.isFile());
    }

    /**
     * Writing data into file test
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_file_writer() throws IOException {
        File file = new File("test_file1.txt");
        generate_file.writeFile(file, "Hello world");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String out = br.readLine();
        assertEquals("Hello world", out);
    }
}
