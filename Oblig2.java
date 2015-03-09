import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig2 {

    public static void main(String[] args) {
        // TODO code application logic here
        Operations start = new Operations(); //contains the tasklist - calling it 0
        Scanner input = new Scanner(System.in);
        String tekstfil = args[0];
        start.readFile(tekstfil);

    }

}
