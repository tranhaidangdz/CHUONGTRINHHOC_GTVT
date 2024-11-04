import java.util.*;


/**
 * Write a description of class DSSinhVien here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DsLop
{
    // ds Lop
    private ST<String, SinhVien> dsLop;

    /**
     * Constructor for objects of class DsLop
     */
    public DsLop()
    {
        // Nhap danh sach lop gom ho dem, ten, ngay sinh, msv, que
        initDsLop();
        
        // Nhap diem
        nhapDiemMon();
        
        // Tinh trung binh cong
        tinhTBC();
    }
    
    private void initDsLop() {
        dsLop = new ST<String, SinhVien>();
        
        In in = new In("student.csv");
        
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            String msv = tokens[0]; // cot 0: msv
            SinhVien SinhVien = new SinhVien(tokens[1]); // cot 1: sinh vien
            dsLop.put(msv, SinhVien);
        }
    }
    
    public void nhapDiemMon() {
        String[] tatCaMon = {"THDC 2 1", "GT 2 1", "CTDLGT 3 3", "KTLT 3 2", "CSDL 3 4"};
        for (String tenMon : tatCaMon) {

            nhapDiem(tenMon);
        }
    }
    
    private void nhapDiem(String tenMon) {
        // KTLT 3 1 
        int tenIndex = 0, soTinIndex = 1, kyThuIndex = 2;
        String fileDiem = tenMon + ".csv";
        String[] monInfor = tenMon.split("\\s+");
        
        In in = new In(fileDiem);
        
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            String msv = tokens[0]; // cot 0: msv
            
            // Neu co trong ds lop
            if (dsLop.contains(msv)) {
                ST<Mon, Double> bangDiem = dsLop.get(msv).getBangDiem();
                bangDiem.put(new Mon(monInfor[0], Integer.parseInt(monInfor[1]), Integer.parseInt(monInfor[2])), Double.parseDouble(tokens[1])); // cot 1: diem
            }
            
        }
                
    }
    
    private void tinhTBC() {
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            sv.TinhdiemTBC();
        }
    }
    
    public void tongKetHocKy(int kyThu) {
        int m = 3; // 3sv diem cao nhat
        
        MinPQ<Double> pq = new MinPQ<Double>(m+1);
        
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            Double diem = sv.TinhdiemTBC(kyThu);
            
            pq.insert(diem);

            if (pq.size() > m)
                pq.delMin();
        }
        
        // dao nguoc thu tu
        Stack<Double> stack = new Stack<Double>();
        for (Double diem : pq) {
            stack.push(diem);
        }
            
        for (Double diem : stack) {
            for(String msv : dsLop.keys()) {
                SinhVien sv = dsLop.get(msv);
                
                if (diem.compareTo(sv.TinhdiemTBC(kyThu)) == 0) StdOut.println(diem + "\t" + sv);
            }
        }
            
    }
    
    public void inDanhSachTheoTen() {
        ST<String, String> dsTheoTen = new ST<String, String>(); // ten, msv
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            dsTheoTen.put(sv.ten(), sv.msv());
        }
        
        for (String ten : dsTheoTen.keys()) {
            String msv = dsTheoTen.get(ten);
            StdOut.println(dsLop.get(msv));
        }
    }
    
    public void inDanhSachTheoTuoi() {
        ST<VietnameseDate, String> dsTheoTuoi = new ST<VietnameseDate, String>(); // ngaysinh tang dan -> tuoi giam dan, msv
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            dsTheoTuoi.put(sv.NS(), sv.msv());
        }
        
        for (VietnameseDate ns : dsTheoTuoi.keys()) {
            String msv = dsTheoTuoi.get(ns);
            StdOut.println(dsLop.get(msv));
        }
    }
    
    public void inDanhSachTheoQue(String que) {
        ST<String, String> dsTheoQue = new ST<String, String>(); // ten, que
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            if (sv.que().compareTo(que) == 0) {
                StdOut.println(sv);
            }
        }
        
    }
    
    public void indanhsachSinhvien(Double diem) {
        ST<Double, SinhVien> dsTheoDiem = new ST<Double, SinhVien>();
        
        for (String msv : dsLop.keys()) {
            SinhVien sv = dsLop.get(msv);
            if (sv.dtbc() >= diem) {
                dsTheoDiem.put(sv.dtbc(), sv);
            }
        }
        
        for (Double dtbc : dsTheoDiem.keys()) {
            SinhVien sv = dsTheoDiem.get(dtbc);
            StdOut.printf("%.2f \t %100s\n", dtbc, sv);
        }
    }
    
    public ST<String, SinhVien> getDsLop() {return dsLop;}

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static void main(String[] args) {
        DsLop ds = new DsLop();
        
        StdOut.println("Danh sach thi dua hoc ki 1:");
        ds.tongKetHocKy(1); // hoc ki 1
        
        StdOut.println("Danh sach lop theo ten:");
        ds.inDanhSachTheoTen();
        
        StdOut.println("Danh sach lop theo tuoi(gia -> tre):");
        ds.inDanhSachTheoTuoi();
        
        StdOut.println("Danh sach theo que(Bac Giang):");
        ds.inDanhSachTheoQue("Bac Giang");
        
        StdOut.println("Danh sach co diem tbc >= 8.0:");
        ds.indanhsachSinhvien(8.0d);
    }
}
