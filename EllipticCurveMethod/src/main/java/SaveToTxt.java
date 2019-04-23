import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Class that allows to save some of results to .txt format files
 */
public class SaveToTxt {
    private static final String SAVE_PATH = "results\\";
    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;
    private static File file;

    /**
     * @param dirName  directory name where .txt file should be created, or already exist
     * @param fileName name of created file
     */
    public SaveToTxt(String dirName, String fileName) {
        try {
            file = new File(SAVE_PATH + dirName);
            file.mkdirs();
            // Creating such file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file + "\\" + fileName + ".txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param data actual data that is saved to .txt. file
     */
    public void save(String data) {
        try {
            if (fileWriter != null && bufferedWriter != null) {
                bufferedWriter.write(data + "\r\n");
                bufferedWriter.flush();
                fileWriter.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * closing buffered writer and file writer, that are created on class instance construction
     */
    public void close() {
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
