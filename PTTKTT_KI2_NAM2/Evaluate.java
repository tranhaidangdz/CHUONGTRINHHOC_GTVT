
import java.io.*;
public class Evaluate {
 public static void main(String[] args) throws IOException {
     System.setIn(new FileInputStream(new File("Postfix_expression.txt")));
     String[] expression = StdIn.readLine().split("\\s+");
     
     Stack<Double> vals = new Stack<Double>();
     
     for (String exp : expression) {
         Double res = 0.0d;
         
         if (exp.equals("sqrt")) {
             res = Math.sqrt(vals.pop());
             vals.push(res);
         }
         
         else if (exp.equals("+")) {
             res = vals.pop() + vals.pop();
             //StdOut.println(res);
             vals.push(res);
         }
         
         else if (exp.equals("-")) {
             Double temp1 = vals.pop();
             Double temp2 = vals.pop();
             res = temp2 - temp1;
             vals.push(res);
         }
         
         else if (exp.equals("*")) {
             res = vals.pop() * vals.pop();
             vals.push(res);
         }
         
         else if (exp.equals("/")) {
             Double temp1 = vals.pop();
             Double temp2 = vals.pop();
             res = temp2 / temp1;
             vals.push(res);
         }
        
         else {
             vals.push(Double.parseDouble(exp)); // Khong phai cac toan tu
         }
         
         
     }
     
         
     StdOut.println(vals.pop());
    }
}
