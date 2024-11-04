
public class Knapsack {
    private int n, w; // Số lượng đồ vật(n) và Trọng lượng tối đa của balo(w)
    private int[] profit; // Mảng lưu giá trị (profit) của từng đồ vật.
    private int[] weight; // Mảng lưu trọng lượng (weight) của từng đồ vật.

    private int[][] opt; // Bảng lưu trữ các giá trị tối ưu cho mỗi trường hợp (lập trình động).
    private boolean[][] sol; // Bảng lưu trữ thông tin về việc chọn đồ vật nào.

    private boolean[] take; // Mảng lưu trữ thông tin về việc đồ vật nào được chọn.

    public Knapsack(int n, int w) {
        this.n = n; // n: Số lượng đồ vật.
        this.w = w; // khởi tạo w: Trọng lượng tối đa của balo.
        profit = new int[n + 1];// khởi tạo mảng profit với kích thước n + 1 để lưu giá trị của các đồ vật. Chỉ
                                // số từ 1 đến n sẽ được sử dụng để lưu giá trị của các đồ vật, chỉ số 0 không
                                // được sử dụng.
        weight = new int[n + 1]; // Khởi tạo mảng weight với kích thước n + 1 để lưu trọng lượng của các đồ vật.
                                 // Tương tự, chỉ số từ 1 đến n sẽ được sử dụng để lưu trọng lượng của các đồ
                                 // vật.

        opt = new int[n + 1][w + 1]; // opt[i][j] sẽ chứa giá trị tối ưu khi xét đến i đồ vật với trọng lượng tối đa
                                     // là j.

        sol = new boolean[n + 1][w + 1]; // sol[i][j] sẽ là true nếu chọn đồ vật thứ i khi trọng lượng tối đa là j, và
                                         // false nếu không chọn.

        take = new boolean[n + 1];// take[i] sẽ là true nếu đồ vật thứ i được chọn vào balo, và false nếu không
                                  // được chọn.
        taoBaLo();
    }
    // 1. Phương thức taoBaLo thực hiện các nhiệm vụ sau:

    // In ra tổng số đồ vật và trọng lượng tối đa của balo.
    // Khởi tạo giá trị và trọng lượng ngẫu nhiên cho từng đồ vật.
    // In ra thông tin của từng đồ vật sau khi đã khởi tạo.
    private void taoBaLo() {
        StdOut.println("Tong so do vat:" + n + "\nTong trong luong cua tui: " + w);
        StdOut.println("Tat ca cac do vat:");
        for (int i = 1; i <= n; i++) { // lặp qua tất cả các đồ vật từ 1 đến n
            profit[i] = StdRandom.uniform(100); // Gia tri tu 0 -> 99 cho đồ vật thứ i
            weight[i] = 1 + StdRandom.uniform(w); // Trong luong tu 1 -> w (đảm bảo trọng lượng luôn >0 )
            StdOut.println("Do vat: " + i + "\tweight = " + weight[i] + "\tprofit = " + profit[i]);
        }
    }

    // 3.Phương thức tongGiaTri duyệt qua mảng take để kiểm tra xem đồ vật nào đã
    // được chọn. Nếu một đồ vật được chọn (take[i] == true), giá trị của đồ vật đó
    // sẽ được cộng vào tổng giá trị (res). Kết quả cuối cùng là tổng giá trị của
    // tất cả các đồ vật được chọn
    public int tongGiaTri() {
        int res = 0;// biến lưu trữ tổng giá trị của các đồ vật được chọn.
        for (int i = 0; i < take.length; i++)// i chạy từ 0 đến take.length - 1, tức là duyệt qua tất cả các chỉ số của
                                             // mảng take
            if (take[i])// Nếu take[i] là true, giá trị của đồ vật thứ i (profit[i]) sẽ được cộng vào
                        // biến res.
                res += profit[i];// tổng giá trị hiện tại được cộng thêm giá trị của đồ vật thứ i.

        return res; // res chứa tổng giá trị của tất cả các đồ vật được chọn để đưa vào balo
    }

    // 3. Phương thức này thực hiện việc tính toán và xác định những đồ vật nào sẽ
    // được chọn để tối đa hóa giá trị trong balo.Phương thức doVat gồm hai phần
    // chính:
    // Tính toán giá trị tối ưu: Sử dụng bảng opt để tính toán giá trị tối ưu cho
    // từng trường hợp với các trọng lượng từ 1 đến w và các đồ vật từ 1 đến n.

    // Truy vết các đồ vật được chọn: Sử dụng bảng sol để xác định các đồ vật nào
    // được chọn để đạt được giá trị tối ưu.
    public void doVat() {
        // Phần 1: Tính toán giá trị tối ưu
        // Duyet tung do vat
        for (int i = 1; i <= n; i++) {
            // Duyet tung trong luong 1 -> w
            for (int j = 1; j <= w; j++) {

                // Khong lay do vat i :Giá trị tối ưu nếu không chọn đồ vật i. Giá trị này là
                // giá trị tối ưu của i - 1 đồ vật với trọng lượng tối đa là j.
                int option1 = opt[i - 1][j];

                // Lay do vat i:
                int option2 = Integer.MIN_VALUE; // Khởi tạo option2 với giá trị rất nhỏ để đảm bảo nếu không thể chọn
                                                 // đồ vật i (do trọng lượng của nó lớn hơn j), option2 sẽ không được
                                                 // chọn.
                if (weight[i] <= j)// Nếu trọng lượng của đồ vật i nhỏ hơn hoặc bằng j, tính toán giá trị tối ưu
                                   // khi chọn đồ vật i. Giá trị này là tổng giá trị của đồ vật i (profit[i]) và
                                   // giá trị tối ưu của i - 1 đồ vật với trọng lượng tối đa là j - weight[i].
                    option2 = profit[i] + opt[i - 1][j - weight[i]];

                // Chon phuong an tot hon:
                opt[i][j] = Math.max(option1, option2);// Giá trị tối ưu của i đồ vật với trọng lượng tối đa là j là giá
                                                       // trị lớn hơn giữa option1 và option2.
                sol[i][j] = (option2 > option1); // Ghi nhận quyết định chọn hay không chọn đồ vật i. sol[i][j] là true
                                                 // nếu chọn đồ vật i (option2 lớn hơn option1).
            }
        }
        // Phần 2: Truy vết các đồ vật được chọn
        StdOut.println("Cac do vat duoc chon:");
        // i so do vat ban dau, j trong luong toi da
        for (int i = n, j = w; i > 0; i--) { // Duyệt ngược từ đồ vật thứ n về đồ vật thứ 1 và từ trọng lượng tối đa w
                                             // về 1.
            if (sol[i][j]) { // Nếu sol[i][j] là true, đồ vật i được chọn
                take[i] = true; // Ghi nhận rằng đồ vật i được chọn
                StdOut.println("Do vat: " + i + "\tweight = " + weight[i] + "\tprofit = " + profit[i]); // In ra thông
                                                                                                        // tin của đồ
                                                                                                        // vật i
                j = j - weight[i]; // Giảm trọng lượng j đi trọng lượng của đồ vật i để tiếp tục truy vết cho các
                                   // đồ vật trước đó.
            } else { // Nếu sol[i][j] là false, đồ vật i không được chọn
                take[i] = false; // Ghi nhận rằng đồ vật i không được chọn
            }
        }

    }

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(8, 9);
        knapsack.doVat();
        StdOut.println("Tong gia tri la: " + knapsack.tongGiaTri());
    }
}
// Trong bài toán xếp balo (Knapsack problem) trong đoạn code bạn đã cung cấp,
// thuật toán được sử dụng là quy hoạch động (dynamic programming). Đây là
// phương pháp rất hiệu quả để giải quyết các bài toán tối ưu có cấu trúc con
// tối ưu.

// Cụ thể, bài toán này sử dụng quy hoạch động để giải quyết bài toán 0/1
// Knapsack, trong đó mỗi đồ vật chỉ có thể được chọn hoặc không được chọn (0
// hoặc 1 lần). Quy hoạch động được sử dụng để tối ưu hóa tổng giá trị của các
// đồ vật được chọn mà không vượt quá trọng lượng cho phép.

// Quy hoạch động trong bài toán Knapsack
// Quy hoạch động trong bài toán này dựa trên việc xây dựng một bảng (mảng 2D)
// để lưu trữ các giá trị tối ưu của các bài toán con. Cụ thể, bảng opt[i][j]
// đại diện cho giá trị tối ưu khi xét đến đồ vật thứ i với trọng lượng tối đa
// là j.

// Các bước để cài đặt bài toán Knapsack bằng quy hoạch động
// 1.Khởi tạo bảng giá trị tối ưu (opt) và bảng quyết định (sol):

// opt[i][j]: Giá trị tối ưu với i đồ vật và trọng lượng tối đa j.
// sol[i][j]: Lưu trữ thông tin có chọn đồ vật thứ i khi trọng lượng tối đa là j
// hay không.
// 2. Điền các giá trị trong bảng opt:

// Đối với mỗi đồ vật i (từ 1 đến n) và mỗi trọng lượng j (từ 1 đến w):
// option1: Giá trị tối ưu khi không chọn đồ vật thứ i (bằng opt[i-1][j]).
// option2: Giá trị tối ưu khi chọn đồ vật thứ i (nếu có thể), tính bằng giá trị
// của đồ vật thứ i (profit[i]) cộng với giá trị tối ưu khi không chọn đồ vật
// thứ i với trọng lượng giảm đi weight[i] (bằng profit[i] +
// opt[i-1][j-weight[i]]).
// Chọn giá trị tối ưu giữa option1 và option2 và lưu vào opt[i][j].
// Cập nhật bảng sol để lưu trữ quyết định.

// 3.Truy vết lại để tìm các đồ vật được chọn:
// Bắt đầu từ opt[n][w] (đồ vật cuối cùng với trọng lượng tối đa).
// Dựa vào bảng sol để truy vết ngược lại các đồ vật được chọn.

// Thuật toán Knapsack (Bài toán xếp ba lô)
// 1. Mô tả thuật toán
// Bài toán xếp ba lô (Knapsack Problem) là một bài toán tối ưu hóa trong đó có
// n đồ vật, mỗi đồ vật có một trọng lượng và giá trị nhất định. Mục tiêu là
// chọn các đồ vật để đưa vào ba lô sao cho tổng trọng lượng không vượt quá một
// trọng lượng tối đa W và tổng giá trị của các đồ vật là lớn nhất.

// 2. Sử dụng thuật toán Knapsack để làm gì?
// Thuật toán Knapsack được sử dụng trong nhiều ứng dụng thực tế như:

// Quản lý tài sản: Chọn các khoản đầu tư để tối đa hóa lợi nhuận trong giới hạn
// ngân sách.
// Quản lý kho: Tối ưu hóa việc lưu trữ hàng hóa trong kho với diện tích giới
// hạn.
// Lập kế hoạch công tác: Lựa chọn các công việc hoặc dự án để thực hiện nhằm
// tối đa hóa lợi ích với nguồn lực có hạn.
// 3. Cài đặt thuật toán Knapsack
// Thuật toán Knapsack thường được cài đặt bằng quy hoạch động. Quy hoạch động
// là một kỹ thuật tính toán giải quyết các bài toán tối ưu hóa bằng cách chia
// bài toán lớn thành các bài toán con nhỏ hơn và lưu trữ kết quả của các bài
// toán con để tránh tính toán lại.

// Cấu trúc dữ liệu sử dụng
// profit: Mảng lưu trữ giá trị của từng đồ vật.
// weight: Mảng lưu trữ trọng lượng của từng đồ vật.
// opt: Mảng 2D lưu trữ giá trị tối ưu cho từng trường hợp.
// sol: Mảng 2D lưu trữ quyết định có chọn đồ vật hay không.
// take: Mảng lưu trữ các đồ vật được chọn trong kết quả cuối cùng.
// Các bước cài đặt
// Khởi tạo mảng:

// Mảng opt và sol có kích thước (n + 1) x (W + 1) để lưu trữ giá trị tối ưu và
// quyết định chọn đồ vật.
// Mảng profit và weight để lưu trữ giá trị và trọng lượng của các đồ vật.
// Tính toán giá trị tối ưu:

// Sử dụng vòng lặp để tính toán giá trị tối ưu cho từng đồ vật và từng trọng
// lượng từ 1 đến W.
// Lưu trữ quyết định chọn đồ vật hay không vào mảng sol.
// Truy vết các đồ vật được chọn:

// Duyệt ngược lại từ đồ vật cuối cùng để xác định các đồ vật được chọn dựa trên
// mảng sol.
// Yếu tố quy hoạch động trong đoạn mã
// Phần quy hoạch động trong bài toán xếp ba lô nằm trong phương thức doVat().
// Đây là nơi giá trị tối ưu cho từng trường hợp được tính toán và lưu trữ.
// public void doVat() {
// // Duyet tung do vat
// for (int i = 1; i <= n; i++) {
// // Duyet tung trong luong 1 -> w
// for (int j = 1; j <= w; j++) {

// // Khong lay do vat i
// int option1 = opt[i - 1][j];

// // Lay do vat i
// int option2 = Integer.MIN_VALUE;
// if (weight[i] <= j)
// option2 = profit[i] + opt[i - 1][j - weight[i]];

// // Chon phuong an tot hon
// opt[i][j] = Math.max(option1, option2);
// sol[i][j] = (option2 > option1);
// }
// }
// }

// Chi tiết các bước trong quy hoạch động
// Khởi tạo vòng lặp để duyệt từng đồ vật:

// for (int i = 1; i <= n; i++): Duyệt qua từng đồ vật từ 1 đến n.
// Duyệt từng trọng lượng từ 1 đến trọng lượng tối đa:

// for (int j = 1; j <= w; j++): Duyệt qua từng trọng lượng từ 1 đến w.
// Tính toán giá trị tối ưu khi không lấy đồ vật i:

// int option1 = opt[i - 1][j];: Giá trị tối ưu khi không chọn đồ vật i.
// Tính toán giá trị tối ưu khi lấy đồ vật i:

// int option2 = Integer.MIN_VALUE;: Giá trị mặc định nếu không thể chọn đồ vật
// i.
// if (weight[i] <= j): Kiểm tra nếu trọng lượng của đồ vật i nhỏ hơn hoặc bằng
// j.
// option2 = profit[i] + opt[i - 1][j - weight[i]];: Giá trị tối ưu khi chọn đồ
// vật i.
// Chọn giá trị tối ưu giữa việc chọn và không chọn đồ vật i:

// opt[i][j] = Math.max(option1, option2);: Lưu giá trị tối ưu vào mảng opt.
// sol[i][j] = (option2 > option1);: Lưu quyết định có chọn đồ vật i vào mảng
// sol.
// Kết luận
// Thuật toán Knapsack: Sử dụng quy hoạch động để giải quyết bài toán chọn các
// đồ vật sao cho tổng trọng lượng không vượt quá một giới hạn và tổng giá trị
// là lớn nhất.
// Ứng dụng: Quản lý tài sản, quản lý kho, lập kế hoạch công tác.
// Cài đặt: Quy hoạch động với mảng 2D để lưu trữ giá trị tối ưu và quyết định
// chọn đồ vật.
// Yếu tố quy hoạch động: Tính toán giá trị tối ưu cho từng trường hợp và lưu
// trữ kết quả để tránh tính toán lại.