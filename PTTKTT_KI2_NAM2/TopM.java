
/**
 * Write a description of class TopM here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TopM
{
    private MinPQ<SinhVien> pq;
    private DsLop ds;
    private ST<String, SinhVien> dsSinhVien;
    private int m;
    public TopM(int m) {
        this.m = m;
        pq = new MinPQ<SinhVien>(m+1);
        ds = new DsLop();
        dsSinhVien = ds.getDsLop();
    }
    
    public void solve() {
        for (String msv : dsSinhVien.keys()) {
            SinhVien sv = dsSinhVien.get(msv);
            pq.insert(sv);
            
            if (pq.size() > m)
                pq.delMin();
        }

        
        
        StdOut.println("3 sinh vien sap xep theo tieu chi: tre, dtbc, ten, ho dem");
        Stack<SinhVien> stack = new Stack<SinhVien>();
        for (SinhVien sv : pq)
            stack.push(sv);
        for (SinhVien sv : stack)
            StdOut.printf("%.2f \t %20s\n", sv.dtbc(), sv);
    }

    public static void main(String[] args) {
        int m = 3;
        
        TopM topM = new TopM(m);
        
        topM.solve();
    }
}
