import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParse {
    private FileReader fr;
    private BufferedReader br;
    private String[] lineArray;

    public FileParse(File file) {
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = null;
            ArrayList<String> lineArrayList = new ArrayList<String>();

            while ((line = br.readLine()) != null) {
                lineArrayList.add(line);
            }
            lineArray = new String[lineArrayList.size()];
            lineArray = lineArrayList.toArray(lineArray);

            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
        try {
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] getLineArray() {
        return lineArray;
    }

}
