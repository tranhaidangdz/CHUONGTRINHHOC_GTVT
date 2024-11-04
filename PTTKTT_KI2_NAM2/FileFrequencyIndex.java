
import java.io.*;
import edu.princeton.cs.algs4.*;

/**
 * Write a description of class FileFrequencyIndex here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FileFrequencyIndex
{
    private ST<String, ST<File, Integer>> st;
    
    public FileFrequencyIndex() {
        st = new ST<String, ST<File, Integer>>();
    }
    
    public void docFile(String[] allFiles) {
        for (String filename : allFiles) {
            //StdOut.println("  " + filename);
            File file = new File(filename);
            In in = new In(file);
            
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) st.put(word, new ST<File, Integer>());
                ST<File, Integer> stFile = st.get(word); // Symbol table file
                if (!stFile.contains(file)) stFile.put(file, 1); // If not contains file before
                else stFile.put(file, stFile.get(file) + 1); // Get value then plus by 1
            }
        }
    }
    
    public void query(String word) {

        if (st.contains(word)) {
            StdOut.println("Frequency of word: " + word);
            ST<File, Integer> stFiles = st.get(word);
            ST<Integer, File> reverseStFiles = new ST<Integer, File>(); // Reverse of stFiles
            MaxPQ<Integer> pq = new MaxPQ<Integer>();
            for (File file : stFiles.keys()) {
                 //StdOut.println(file + "\t" + stFiles.get(file));
                 reverseStFiles.put(stFiles.get(file), file);
                 pq.insert(stFiles.get(file));
            }
            
            while(!pq.isEmpty()) {
                int freq = pq.delMax();
                StdOut.println(freq + "\t" + reverseStFiles.get(freq));
            }
            
        } else {
            StdOut.println("Not found word: " + word);
        }
    }
    
    
    
    public static void main(String[] args)throws IOException {
        String[] allFiles = {"ex1.txt",  "ex2.txt",  "ex3.txt",  "ex4.txt", "tale.txt"};
        FileFrequencyIndex fileFrequencyIndex = new FileFrequencyIndex();
        fileFrequencyIndex.docFile(allFiles);
        
        String[] words = {"it", "was", "dog", "red", "kind"};
        
        for (String word : words) 
            fileFrequencyIndex.query(word);
    }
}
