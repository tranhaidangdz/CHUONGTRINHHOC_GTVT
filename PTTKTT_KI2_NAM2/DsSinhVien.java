import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Write a description of class DSSinhVien here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DsSinhVien
{
    class SortByName implements Comparator<SinhVien> {
        public int compare(SinhVien a, SinhVien b) {
            if (a.ten().compareTo(b.ten()) != 0) return a.ten().compareTo(b.ten());
            if (a.hoDem().compareTo(b.hoDem()) != 0) return a.hoDem().compareTo(b.hoDem());
            return 0;
        }
    }
    // SinhVien, msv
    private HashMap<SinhVien, String> dsSinhVien;
    private ArrayList<SinhVien> dsTheoTen;
    
    public DsSinhVien() throws java.io.FileNotFoundException {
        dsSinhVien = new HashMap<SinhVien, String>();
        dsTheoTen = new ArrayList<SinhVien>();
        // Doc file hasmap_sinhvien.txt de tao danh sach
        initDsSinhVien();
    }
    
    private void initDsSinhVien() throws java.io.FileNotFoundException {
        System.setIn(new FileInputStream(new File("hashmap_sinhvien.txt")));
        
        // doc file 
        while (!StdIn.isEmpty()) {
            String item = StdIn.readLine();
            SinhVien sv = new SinhVien(item);       
            
            dsTheoTen.add(sv);           
            
        }
        
        Collections.sort(dsTheoTen, new SortByName());
        
        Integer msvBanDau = 221230000;
        for (SinhVien sv : dsTheoTen) {
            sv.setMsv(Integer.toString(msvBanDau));
            dsSinhVien.put(sv, sv.msv());
            msvBanDau++;
        }
        
        
    }
    
    
    
    public void inDsSinhVien() {
        for (SinhVien sv : dsSinhVien.keySet()) {
            StdOut.println(sv);
        }
    }
    
    public static void main(String[] args) throws IOException{
        DsSinhVien ds = new DsSinhVien();
        
        ds.inDsSinhVien();
    }
}
