import java.util.Arrays;
import java.util.Comparator;
import java.awt.Font;
import java.util.HashMap;
import edu.princeton.cs.algs4.StdOut;

/**
 * Write a description of class SinhVien here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SinhVien implements Comparable<SinhVien> {
    private String hoDem;
    private String ten;
    private VietnameseDate NS; // Ngay sinh
    private String msv; // Ma sinh vien
    private String que; 
    private ST<Mon, Double> bangDiem;
    private Double dtbc; // diem trung binh cong
    
    public SinhVien (String hoDem, VietnameseDate NS, String ten, String msv, String que) {
        this.hoDem = hoDem;
        this.ten = ten;
        this.NS = NS;
        this.msv = msv;
        this.que = que;
    }
    
    public SinhVien (String SinhVien) {
        String[] a = SinhVien.split("\\s+");        
        hoDem = a[0] + " " + a[1] + " ";
        
        ten = a[2] + " ";
        
        NS = new VietnameseDate(a[3]);
        
        msv = a[4];
        
        que = a[5] + " " + a[6];
        
        bangDiem = new ST<Mon, Double>();
    }
    
    public String hoDem() {return hoDem;}
    
    public String ten() {return ten;}
    
    public VietnameseDate NS() {return NS;}
    
    public void setMsv(String msv) {this.msv = msv;}
    
    public String msv() {return msv;}
    
    public Double dtbc() {return dtbc;}
    
    public String que() {return que;}
        
    public ST<Mon, Double> getBangDiem() {return bangDiem;}
    
    public void NhapdiemmonSV(Mon mon, Double diem) {
        bangDiem.put(mon, diem);
        TinhdiemTBC();
    }
    
    public Double TinhdiemTBC(int kyThu) {
        int tongTin = 0;
        Double tongDiem = 0.0d;
        for (Mon mon : bangDiem.keys()) {
            if (mon.getKyThu() == kyThu) {
            tongDiem += bangDiem.get(mon) * mon.getSoTin(); // get diem cua mon
            tongTin += mon.getSoTin();
            }
        }
        
        //StdOut.println(tongDiem + " "+ tongTin );
        return tongTin == 0 ? 0.0d : tongDiem / tongTin;
    }
    
    
    public void TinhdiemTBC() {
        int tongTin = 0;
        Double tongDiem = 0.0d;
        for (Mon mon : bangDiem.keys()) {
            tongDiem += bangDiem.get(mon) * mon.getSoTin(); // cong diem cua tung mon
            tongTin += mon.getSoTin(); // cong tong so tin
        }
        this.dtbc = tongTin == 0 ? 0.0d: tongDiem / tongTin;
    }
    
    public void InHosoSV() {
        StdOut.println(this);
        
        // Bang diem
        StdOut.println("Bang diem sv:");
        for (Mon mon : bangDiem.keys()) {
            StdOut.println(mon + "\t" + bangDiem.get(mon));
        }
    }
    /**
     * Returns a string representation of this SinhVien.
     *
     * @return a string representation of this SinhVien
     */
    @Override
    public String toString() {
      return String.format("%-15s\t%-10s\t%-20s\t%-10s\t%-20s", msv, ten, hoDem, NS, que);
    }

    
    /**
     * Compares two SinhViens by amount.
     *
     * @param  that the other SinhVien
     * @return { a negative integer, zero, a positive integer}, depending
     *         on whether the amount of this SinhVien is { less than,
     *         equal to, or greater than } the amount of that SinhVien
     */
    public int compareTo(SinhVien that) {
        if (this.NS.compareTo(that.NS) == 1) return 1; // tre hon
        if (this.dtbc < that.dtbc) return -1; // diem thap hon
        if (this.ten.compareTo(that.ten) != 0) return this.ten.compareTo(that.ten); // so sanh ten
        if (this.hoDem.compareTo(that.hoDem) != 0) return this.hoDem.compareTo(that.hoDem); // so sanh ho dem
        return 0;
    }  
    
    /**
     * Compares this SinhVien to the specified object.
     *
     * @param  other the other SinhVien
     * @return true if this SinhVien is equal to {@code other}; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        SinhVien that = (SinhVien) other;
        return (this.dtbc == that.dtbc) && (this.ten.equals(that.ten)) && (this.hoDem.equals(that.hoDem)) 
        && (this.msv == that.msv) && (this.NS.equals(that.NS));
    }
    
    /**
     * Returns a hash code for this SinhVien.
     *
     * @return a hash code for this SinhVien
     */
    public int hashCode() {
        int hash = 1;
        hash = 31*hash + ten.hashCode();
        hash = 31*hash + hoDem.hashCode();
        hash = 31*hash + NS.hashCode();
        return hash;
    }

    
    
    public static void main(String[] args) {
        // test
        SinhVien[] a = new SinhVien[1];
        a[0] = new SinhVien("Truong Quang Vinh   01/01/2004      221231053    Bac Giang");
        
        
        for (int i = 0; i < a.length; i++) {
            a[i].InHosoSV();
        }
    }
}

