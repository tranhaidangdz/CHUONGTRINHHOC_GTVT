import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Arrays;

/**
 * Write a description of class Inversions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Inversions
{
    private String[] dsTenSv;
    private int inversions;
    
    public Inversions() throws java.io.FileNotFoundException {
        inversions = 0;
        
        readFile();
    }
    
    private void readFile() throws java.io.FileNotFoundException {
        System.setIn(new FileInputStream(new File("ds_tensv.txt")));
        
        String size = StdIn.readLine();
        dsTenSv = new String[Integer.parseInt(size)];
        
        int i = 0;
        while(!StdIn.isEmpty()) {
            String item = StdIn.readLine();
            dsTenSv[i++] = item;
        }
    }
    
    private void merge(int l, int r, int mid) {
        String[] tempLeft = Arrays.copyOfRange(dsTenSv, l, mid + 1);
        String[] tempRight = Arrays.copyOfRange(dsTenSv, mid + 1, r + 1);
        int i = 0, j = 0;
        
        while(i < tempLeft.length && j < tempRight.length) {
            if (tempLeft[i].compareTo(tempRight[j]) > 0) {
                // 0 1 2 3 4 5
                inversions += (mid - l + 1);
                dsTenSv[l++] = tempRight[j++];           
            } else {
                dsTenSv[l++] = tempLeft[i++];
            }
        }
        
        while(i < tempLeft.length) {
            dsTenSv[l++] = tempLeft[i++];
        }
        
        while(i < tempRight.length) {
            dsTenSv[l++] = tempRight[j++];
        }
        
    }
    
    private void mergeSort(int l, int r) {
        if (l >= r) return;
        
        int mid = (l + r) / 2;
        
        mergeSort(l, mid);
        mergeSort(mid + 1, r);
        merge(l, r, mid);
    }
    
    public void sort() {
        mergeSort(0, dsTenSv.length - 1);
    }
    
    public void inDs() {
        for (String sv : dsTenSv) {StdOut.println(sv);}
    }
    
    public int getInversions(){return inversions;}
    
    public static void main(String[] args) throws IOException {
        Inversions inversions = new Inversions();
        StdOut.println("Ds truoc khi sap xep:");
        inversions.inDs();
        
        inversions.sort();
        StdOut.println("\nDs sau khi sap xep:");
        inversions.inDs();
        
        StdOut.println("Tong so cap nghich the: " + inversions.getInversions());
    }
}
