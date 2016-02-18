import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class FileWrite {
    private PrintWriter pw;

    public FileWrite(File f) {
        try {
            this.pw = new PrintWriter(new FileOutputStream(f, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        pw.append(s + '\n');
        pw.flush();
    }

    public void close(){
        pw.close();
    }
}
