package util.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FileReport {
    private BufferedWriter out;
    private File file;
    public FileReport(File selectedDirectory){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        file = new File(selectedDirectory+"\\" + dateFormat.format(date) + ".txt");

        try {
            out = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeString(String str) throws IOException {
        out.write(str+"\r\n");
        out.flush();
    }
    public void writeStep(int step,int [] arr, int n) throws IOException {
        out.write("Step " + step + ": "+Arrays.toString(arr)+ " = "+ n + "\r\n");
        out.flush();
    }

    public File getFile() {
        return file;
    }
}
