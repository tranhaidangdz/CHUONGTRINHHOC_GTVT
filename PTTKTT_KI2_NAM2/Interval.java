import java.util.Comparator;

public class Interval implements Comparable<Interval> {
    public int start;
    public int end;
    public int weight;

    public Interval(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    
    public int compareTo (Interval y){
        return Double.compare(this.end, y.end);
    }
    
    @Override
    public String toString() {
        return start + "\t" + end + "\t" + weight + "\n";
    }
}
