
/**
 * Write a description of class PutQueen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
/*
 * bài toán N-Queens sử dụng thuật toán quay lui (backtracking). Bài toán
 * N-Queens yêu cầu đặt N quân hậu trên một bàn cờ N x N sao cho không có hai
 * quân hậu nào đe dọa nhau (không cùng hàng, cùng cột, hoặc cùng đường chéo).
 */
public class NQueens {
    int N;// Kích thước bàn cờ
    int cnt = 1; // Biến đếm số lượng giải pháp

    public NQueens(int N) {
        this.N = N;
    }

    /*
     * int board[][]: Đây là ma trận 2 chiều kích thước N x N đại diện cho bàn cờ.
     * Mỗi phần tử board[i][j] có giá trị 0 hoặc 1, với 1 biểu thị rằng có một quân
     * hậu được đặt ở ô đó, và 0 biểu thị rằng ô đó trống.
     */

    void printSolution(int board[][]) {
        // Vòng lặp này chạy từ i = 0 đến i < N, nghĩa là nó sẽ lặp qua từng hàng của
        // bàn cờ.
        for (int i = 0; i < N; i++) {

            // Vòng lặp này chạy từ j = 0 đến j < N, nghĩa là nó sẽ lặp qua từng cột của
            // hàng hiện tại.
            for (int j = 0; j < N; j++)
                System.out.print(" " + board[i][j]
                        + " ");// in giá trị của ô hiện tại (board[i][j]), thêm khoảng trắng trước và sau để dễ
                               // nhìn.
            System.out.println();
        }
        System.out.println();
    }

    boolean isSafe(int board[][], int row, int col) { // isSafe có chức năng kiểm tra xem việc đặt một quân hậu tại vị
                                                      // trí [row][col] trên bàn cờ board có an toàn hay không, tức là
                                                      // không bị bất kỳ quân hậu nào khác tấn công.Cụ thể, hàm này kiểm
                                                      // tra các hướng có thể có quân hậu tấn công: hàng ngang bên trái,
                                                      // đường chéo trên bên trái và đường chéo dưới bên trái.
        int i, j; // Khai báo các biến i và j để sử dụng trong các vòng lặp kiểm tra.

        /* Kiểm tra hàng này ở bên trái */
        for (i = 0; i < col; i++)
            // Kiểm tra hàng ngang của vị trí [row][col] từ cột 0 đến col-1 (bên trái vị trí
            // hiện tại). Nếu có bất kỳ ô nào trong hàng này có giá trị 1 (có quân hậu), trả
            // về false vì vị trí không an toàn.
            if (board[row][i] == 1)
                return false;

        /* Kiểm tra đường chéo phía trên bên trái */
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            // Kiểm tra đường chéo trên bên trái của vị trí [row][col]. Bắt đầu từ vị trí
            // hiện tại và di chuyển lên trên và sang trái cho đến khi chạm biên của bàn cờ.
            // Nếu có bất kỳ ô nào trong đường chéo này có giá trị 1, trả về false vì vị trí
            // không an toàn.
            if (board[i][j] == 1)
                return false;

        /* Kiểm tra đường chéo dưới bên trái */
        // Kiểm tra đường chéo trên bên trái của vị trí [row][col]. Bắt đầu từ vị trí
        // hiện tại và di chuyển lên trên và sang trái cho đến khi chạm biên của bàn cờ.
        // Nếu có bất kỳ ô nào trong đường chéo này có giá trị 1, trả về false vì vị trí
        // không an toàn.
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;// Nếu không có quân hậu nào trên hàng ngang bên trái, đường chéo trên bên trái
                    // và đường chéo dưới bên trái của vị trí [row][col], trả về true vì vị trí này
                    // an toàn để đặt quân hậu.
    }

    /*
     * solveNQUtil là một hàm đệ quy được sử dụng để giải bài toán N-Queen. Hàm này
     * cố gắng đặt các quân hậu lên bàn cờ sao cho không quân hậu nào tấn công quân
     * hậu khác
     * tham số đầu vào là ma trận 2 chiều board đại diện cho bàn cờ và số nguyên col
     * đại diện cho cột hiện tại mà ta đang cố gắng đặt quân hậu.
     * 
     * 
     */
    boolean solveNQUtil(int board[][], int col) {
        /*
         * Trường hợp cơ bản: Nếu tất cả các quân hậu đã được đặt thành công (tức là cột
         * hiện tại col lớn hơn hoặc bằng số N), in ra bàn cờ và trả về true để chỉ ra
         * rằng đã tìm thấy một lời giải.
         */
        if (col >= N) {
            System.out.println(cnt++);
            printSolution(board);
            return true;
        }

        /*
         * Khởi tạo biến res là false. Bắt đầu vòng lặp qua từng hàng i trong cột col để
         * thử đặt quân hậu.
         */
        boolean res = false;
        for (int i = 0; i < N; i++) {
            /*
             * Kiểm tra xem có an toàn để đặt quân hậu tại vị trí [i][col] bằng cách gọi hàm
             * isSafe.
             */
            if (isSafe(board, i, col)) {
                /* Nếu an toàn, đặt quân hậu tại vị trí [i][col] (gán giá trị 1 cho ô này). */
                board[i][col] = 1;

                // Gọi đệ quy hàm solveNQUtil với cột tiếp theo (col + 1). Nếu việc đặt quân hậu
                // ở cột tiếp theo thành công (hàm solveNQUtil trả về true), gán res là true.
                // Kết hợp với || để đảm bảo res vẫn giữ giá trị true nếu có ít nhất một lời
                // giải.
                res = solveNQUtil(board, col + 1) || res;

                /*
                 * Nếu việc đặt quân hậu tại vị trí [i][col] không dẫn đến lời giải, loại bỏ
                 * quân hậu khỏi vị trí này (gán giá trị 0 cho ô này) và tiếp tục thử các hàng
                 * khác trong cột hiện tại.
                 */
                board[i][col] = 0; // BACKTRACK
            }
        }

        /*
         * If the queen cannot be placed in any row in
         * this column col, then return false
         */
        return res;
        /*
         * Chức năng tổng thể của solveNQUtil
         * Hàm solveNQUtil cố gắng đặt quân hậu vào từng cột của bàn cờ. Nó kiểm tra
         * từng hàng của cột hiện tại và nếu tìm thấy vị trí an toàn, nó đặt quân hậu
         * vào đó và tiếp tục giải quyết các cột tiếp theo bằng cách gọi đệ quy hàm
         * solveNQUtil. Nếu không tìm thấy vị trí an toàn nào trong cột hiện tại, nó sẽ
         * quay lui (backtrack) bằng cách loại bỏ quân hậu khỏi vị trí vừa đặt và thử
         * các vị trí khác. Quá trình này tiếp tục cho đến khi tất cả các quân hậu được
         * đặt thành công hoặc không thể tìm ra lời giải.
         * 
         * Ví dụ minh họa
         * Giả sử bạn có một bàn cờ 4x4 và đang cố gắng đặt quân hậu đầu tiên vào cột 0.
         * Hàm solveNQUtil sẽ thử từng hàng trong cột 0:
         * 
         * Đặt quân hậu vào hàng 0, cột 0.
         * Gọi đệ quy solveNQUtil cho cột 1.
         * Nếu việc đặt quân hậu vào cột 1 thành công, tiếp tục với cột 2.
         * Nếu không, loại bỏ quân hậu khỏi hàng 0, cột 0 và thử hàng 1, cột 0, v.v.
         * VD demo chạy kết quả
         * 1
         * 1 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 1 0
         * 0 0 0 0 1 0 0 0
         * 0 0 0 0 0 0 0 1
         * 0 1 0 0 0 0 0 0
         * 0 0 0 1 0 0 0 0
         * 0 0 0 0 0 1 0 0
         * 0 0 1 0 0 0 0 0
         * 
         * 2
         * 1 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 1 0
         * 0 0 0 1 0 0 0 0
         * 0 0 0 0 0 1 0 0
         * 0 0 0 0 0 0 0 1
         * 0 1 0 0 0 0 0 0
         * 0 0 0 0 1 0 0 0
         * 0 0 1 0 0 0 0 0
         */ // =>Khi bạn chạy đoạn mã trên và nhận được kết quả như vậy, điều đó có nghĩa là
            // chương trình đã tìm ra các cách đặt N quân hậu trên bàn cờ N x N sao cho
            // không quân hậu nào tấn công quân hậu khác. Mỗi số trong ma trận đại diện cho
            // một ô trên bàn cờ:
            //
            // Số 0 biểu thị rằng không có quân hậu nào được đặt ở ô đó.
            // Số 1 biểu thị rằng có một quân hậu được đặt ở ô đó.
            // Các số 1, 2, ... biểu thị thứ tự của các giải pháp khác nhau mà chương trình
            // tìm thấy. Cụ thể:
            //
            // Số 1, 2, ...: Mỗi số này đại diện cho một giải pháp riêng biệt. Ví dụ, số 1
            // đi kèm với ma trận đầu tiên là giải pháp đầu tiên mà chương trình tìm ra. Số
            // 2 là giải pháp thứ hai, và tiếp tục như vậy.
            // Ma trận chứa các số 0 và 1: Đây là các ma trận đại diện cho bàn cờ với các
            // quân hậu được đặt theo cách mà chúng không tấn công lẫn nhau. Mỗi ma trận
            // tương ứng với một giải pháp duy nhất và thể hiện cách đặt N quân hậu sao cho
            // hợp lệ.
            // Đây là giải pháp đầu tiên. Trong ma trận này:
            // 1
            // 1 0 0 0 0 0 0 0
            // 0 0 0 0 0 0 1 0
            // 0 0 0 0 1 0 0 0
            // 0 0 0 0 0 0 0 1
            // 0 1 0 0 0 0 0 0
            // 0 0 0 1 0 0 0 0
            // 0 0 0 0 0 1 0 0
            // 0 0 1 0 0 0 0 0
        // Quân hậu được đặt tại các vị trí (0, 0), (1, 6), (2, 4), (3, 7), (4, 1), (5,
        // 3), (6, 5), (7, 2).

        // Cứ tiếp tục như vậy, mỗi ma trận bạn thấy là một giải pháp hợp lệ cho bài
        // toán N-Queens, với các vị trí quân hậu được thể hiện bằng các số 1 trong ma
        // trận.
        // Tóm lại, khi bạn nhận được kết quả này, chương trình đã liệt kê ra tất cả các
        // giải pháp hợp lệ để đặt N quân hậu trên bàn cờ N x N mà không có hai quân hậu
        // nào tấn công lẫn nhau. Mỗi giải pháp được đánh số thứ tự để dễ theo dõi.

        // =>Trong bài toán N-Queen, cấu trúc dữ liệu chính được sử dụng là một ma trận
        // hai chiều (hay mảng hai chiều), thường được gọi là bàn cờ. Mỗi ô trong ma
        // trận biểu diễn một ô trên bàn cờ, và giá trị của ô này thường là 0 hoặc 1,
        // biểu thị xem có quân hậu được đặt tại ô đó hay không.

        // Bước đầu tiên để cài đặt thuật toán quay lui cho bài toán N-Queen là xác định
        // cấu trúc dữ liệu để biểu diễn bàn cờ và viết hàm để kiểm tra tính hợp lệ của
        // việc đặt quân hậu tại một vị trí cụ thể trên bàn cờ. Dưới đây là các bước cụ
        // thể:

        // Xác định cấu trúc dữ liệu: Sử dụng một ma trận hai chiều (hoặc một cấu trúc
        // dữ liệu phù hợp khác) để biểu diễn bàn cờ. Mỗi ô trong ma trận sẽ lưu giá trị
        // 0 hoặc 1, 0 biểu thị rằng ô đó trống và 1 biểu thị rằng có quân hậu đặt tại ô
        // đó.

        // Viết hàm kiểm tra tính hợp lệ: Viết một hàm để kiểm tra xem việc đặt quân hậu
        // tại một vị trí cụ thể trên bàn cờ có hợp lệ hay không. Trong hàm này, kiểm
        // tra xem không có quân hậu nào trên cùng một hàng, cột hoặc đường chéo với vị
        // trí đó. Điều này đảm bảo rằng mỗi quân hậu được đặt không xung đột với các
        // quân hậu khác.

        // Viết hàm đệ quy: Viết một hàm đệ quy để thử tất cả các khả năng đặt quân hậu
        // trên bàn cờ. Hàm này sẽ thử từng hàng của cột hiện tại, và nếu vị trí đó hợp
        // lệ, nó sẽ gán giá trị 1 cho ô đó, sau đó gọi đệ quy để tiếp tục với cột tiếp
        // theo. Nếu không tìm thấy giải pháp, hàm sẽ quay lại và thử các khả năng khác.

        // Quay lui (backtracking): Trong quá trình đệ quy, nếu không thể đặt quân hậu
        // trong cột hiện tại mà không xảy ra xung đột, hàm sẽ quay lại cột trước đó và
        // thử các khả năng khác.

        // Xử lý kết quả: Sau khi tất cả các quân hậu được đặt, hàm sẽ in ra bàn cờ với
        // các quân hậu được đặt sao cho không có quân hậu nào xung đột.
    }

    boolean solveNQ() {// Định nghĩa hàm solveNQ, trả về giá trị boolean để chỉ ra liệu có tìm thấy lời
                       // giải hay không.
        int board[][] = new int[N][N];// Tạo một ma trận 2 chiều board kích thước N x N, đại diện cho bàn cờ. Tất cả
                                      // các phần tử của ma trận ban đầu đều là 0, biểu thị rằng không có quân hậu nào
                                      // được đặt trên bàn cờ.

        if (solveNQUtil(board, 0) == false) {// Gọi hàm đệ quy solveNQUtil bắt đầu từ cột 0. Nếu hàm solveNQUtil trả về
                                             // false, điều đó có nghĩa là không tìm thấy lời giải cho bài toán N-Queen.

            System.out.print("Solution does not exist");// Nếu không có lời giải, in ra thông báo "Solution does not
                                                        // exist".
            return false; // Trả về false để chỉ ra rằng không tìm thấy lời giải.
        }

        return true;
    }

    public static void main(String args[]) {
        NQueens nQueen = new NQueens(8);
        System.out.println("All solutions:");
        nQueen.solveNQ();
    }
}
// Thuật toán N-Queens là một bài toán kinh điển trong lý thuyết thuật toán và
// trí tuệ nhân tạo, được sử dụng để đặt N quân hậu lên một bàn cờ N x N sao cho
// không có hai quân hậu nào đe dọa lẫn nhau. Điều này có nghĩa là không có hai
// quân hậu nào nằm cùng hàng, cùng cột hoặc cùng đường chéo.

// Mô tả thuật toán N-Queens:
// 1.Mục đích:
// Đặt N quân hậu trên một bàn cờ N x N sao cho không có hai quân hậu nào đe dọa
// lẫn nhau.
// 2.Ý tưởng chính:

// Sử dụng phương pháp quay lui (backtracking) để thử từng cách đặt quân hậu vào
// từng cột của bàn cờ, kiểm tra xem mỗi cách đặt có hợp lệ không (tức là không
// có quân hậu nào đe dọa lẫn nhau).
// Nếu đặt được một quân hậu hợp lệ, tiếp tục đặt quân hậu tiếp theo vào cột
// tiếp theo.
// Nếu không thể đặt quân hậu nào hợp lệ ở cột hiện tại, quay lui để thử lại các
// khả năng khác ở cột trước đó.
// 3.Các bước cài đặt:

// Bước 1: Khởi tạo một bàn cờ N x N trống, biểu diễn bằng một ma trận hai
// chiều.
// Bước 2: Viết hàm kiểm tra tính hợp lệ isSafe(int[][] board, int row, int col)
// để kiểm tra xem có thể đặt quân hậu tại vị trí (row, col) mà không bị đe dọa
// hay không.
// Bước 3: Viết hàm đệ quy solveNQUtil(int[][] board, int col) để đặt quân hậu
// vào cột col và tiếp tục giải quyết cho các cột tiếp theo.
// Bước 4: Trong hàm đệ quy, nếu không tìm được vị trí hợp lệ nào cho quân hậu ở
// cột hiện tại, quay lui để thử lại các khả năng khác ở cột trước đó.
// Bước 5: In ra tất cả các cách đặt quân hậu hợp lệ (nếu có).
// 4.Yếu tố quay lui:

// Quay lui xảy ra khi không thể đặt quân hậu nào hợp lệ ở cột hiện tại, do đó
// cần phải quay lại cột trước đó để thử lại các vị trí khác.
// Cài đặt thuật toán N-Queens
// Dưới đây là đoạn mã hoàn chỉnh cài đặt thuật toán N-Queens:

// Giải thích yếu tố quay lui trong đoạn mã:
// Đặt quân hậu hợp lệ: Trong hàm solveNQUtil(int[][] board, int col), nếu vị
// trí (i, col) hợp lệ, đặt quân hậu tại vị trí này.

// Gọi đệ quy để tiếp tục với cột tiếp theo: Gọi solveNQUtil(board, col + 1) để
// tiếp tục đặt quân hậu vào cột tiếp theo.

// Quay lui nếu không thể đặt quân hậu hợp lệ: Nếu không thể đặt quân hậu vào vị
// trí (i, col), loại bỏ quân hậu khỏi vị trí đó (board[i][col] = 0) và thử vị
// trí khác trong cùng cột.

// Nếu tất cả các hàng trong cột đều không hợp lệ: Hàm trả về false và quay lui
// để thử lại các khả năng khác ở cột trước đó.