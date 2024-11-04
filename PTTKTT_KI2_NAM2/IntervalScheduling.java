import java.util.*;

// Erliest-Finish-Time-First 
public class IntervalScheduling {

    public static List<Interval> schedule(List<Interval> intervals) {
        // Sap xep cac khoang thoi gian theo thoi diem ket thuc
        Collections.sort(intervals);

        // Danh sach khoang thoi gian duoc chon
        List<Interval> selectedIntervals = new ArrayList<>();

        // Thoi diem ket thuc hien tai
        int currentTime = 0;

        for (Interval interval : intervals) {
            // Neu thoi diem bat dau lon hon hoac bang thoi diem ket thuc  hien tai
            if (interval.start >= currentTime) {
                // Chon khoang thoi gian nay
                selectedIntervals.add(interval);
                
                // Cap nhat thoi gian ket thuc
                currentTime = interval.end;
            }
        }

        return selectedIntervals;
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(8, 11, 0));
        intervals.add(new Interval(4, 7, 0));
        intervals.add(new Interval(3, 8, 0));
        intervals.add(new Interval(6, 10, 0));
        intervals.add(new Interval(5, 9, 0));
        intervals.add(new Interval(0, 6, 0));
        intervals.add(new Interval(3, 5, 0));
        intervals.add(new Interval(1, 4, 0));

        for (Interval it : intervals) StdOut.print(it);
        List<Interval> selectedIntervals = schedule(intervals);
        System.out.println("Cac khoang thoi gian duoc chon:");
        for (Interval interval : selectedIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}
