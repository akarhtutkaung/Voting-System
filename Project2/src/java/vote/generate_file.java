//Name of the file: generate_file.java
//What the code does: It is generate_file class;
//                    it will be used to generate the files
//Authors: Hao Wu
package vote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Generate file
 */
public class generate_file {

  /**
   * Create file
   *
   * @param string Name of the file to create
   * @return File
   * @throws IOException if the file exist
   */
  public static File create(String string) throws IOException {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    String strDate = string + formatter.format(date);
    String path = strDate + ".txt";
    File file = new File(path);
    file.createNewFile();
    return file;
  }

  /**
   * Write into the given file
   *
   * @param file   Open and writable file
   * @param string Data to write
   * @throws IOException if the file is writable
   */
  public static void writeFile(File file, String string) throws IOException {
    FileWriter fileWriter = new FileWriter(file, true);

    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(string + "\n");
    bufferedWriter.close();
  }
}
