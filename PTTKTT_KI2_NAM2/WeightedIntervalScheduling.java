import java.util.*;

/**
 * Write a description of class WeightedIntervalScheduling here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WeightedIntervalScheduling
{
    private List<Interval> intervals;
    
    public WeightedIntervalScheduling() {
        intervals = Arrays.asList(new Interval( 3, 10, 20 ),
                                  new Interval( 1, 2, 50 ),
                                  new Interval( 6, 19, 100 ),
                                  new Interval( 2, 100, 200 ));
                                  
        for (Interval it : intervals) StdOut.print(it);
    }
    
    private int findLastNonConflictingJob(int n)
    {
        // search space
        int low = 0;
        int high = n;

        // iterate till search space is not exhausted
        while (low <= high)
        {
            int mid = (low + high) / 2;
            if (intervals.get(mid).end <= intervals.get(n).start) {
                if (intervals.get(mid + 1).end <= intervals.get(n).start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            }
            else {
                high = mid - 1;
            }
        }

        // return negative index if no non-conflicting job is found
        return -1;
    }
    
    public int maxWeight()
    {
        // sort intervals in increasing order of their finish times
        //Collections.sort(intervals, (x, y) -> x.finish - y.finish);
        Collections.sort(intervals);
        // get number of intervals
        int n = intervals.size();

        // construct an lookup table where the i'th index stores the maximum profit
        // for first i intervals
        int[] maxWeight = new int[n];

        // maximum profit gained by including the first job
        maxWeight[0] = intervals.get(0).weight;

        // fill maxWeight[] table in bottom-up manner from the second index
        for (int i = 1; i < n; i++)
        {
            // find the index of last non-conflicting job with current job

            // Tim index la cong viec sau cung khong xung dot voi cong viec hien thoi i .....  
            int index = findLastNonConflictingJob(i);

            // include the current job with its non-conflicting intervals
            int incl = intervals.get(i).weight;
            if (index != -1) {
                incl += maxWeight[index];
            }

            // store the maximum profit by including or excluding current job
            maxWeight[i] = Math.max(incl, maxWeight[i - 1]);
        }

        // return maximum profit
        return maxWeight[n - 1];
    }
    public static void main(String[] args) {
        WeightedIntervalScheduling test = new WeightedIntervalScheduling();
        
        StdOut.print("Max weight: " + test.maxWeight());
    }
}

