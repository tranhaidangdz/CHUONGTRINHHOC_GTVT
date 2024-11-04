
/**
 * Write a description of class Mon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mon implements Comparable<Mon>
{
    private String ten;
    private int soTin, kyThu;

    /**
     * Constructor for objects of class Mon
     */
    public Mon(String ten, int soTin, int kyThu)
    {
        this.ten = ten;
        this.soTin = soTin;
        this.kyThu = kyThu;
    }

    public void setTen(String ten) {this.ten = ten;}
    public void setSoTin(int soTin) {this.soTin = soTin;}
    public void setKyThu(int kyThu) {this.kyThu = kyThu;}
    
    public String getTen() {return this.ten;}
    public int getSoTin() {return this.soTin;}
    public int getKyThu() {return this.kyThu;}
    
    @Override
    public String toString() {
        return String.format(ten + "\t" + soTin +"\t"+ kyThu);
    }
    
    @Override
    public int compareTo(Mon that) {
        int result = this.ten.compareTo(that.ten);
        return result;
    }

    
}
