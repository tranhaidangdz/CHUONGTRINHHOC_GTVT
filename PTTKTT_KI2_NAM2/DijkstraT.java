/******************************************************************************
 *  Compilation:  javac DijkstraT.java
 *  Execution:    java DijkstraT input.txt s
 *  Dependencies: EdgeWeightedDigraph.java IndexMinPQ.java Stack.java DirectedEdge.java
 *  Data files:   https://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 *                https://algs4.cs.princeton.edu/44sp/mediumEWD.txt
 *                https://algs4.cs.princeton.edu/44sp/largeEWD.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraT tinyEWD.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (1.05)  0->4  0.38   4->5  0.35   5->1  0.32   
 *  0 to 2 (0.26)  0->2  0.26   
 *  0 to 3 (0.99)  0->2  0.26   2->7  0.34   7->3  0.39   
 *  0 to 4 (0.38)  0->4  0.38   
 *  0 to 5 (0.73)  0->4  0.38   4->5  0.35   
 *  0 to 6 (1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   
 *  0 to 7 (0.60)  0->2  0.26   2->7  0.34   
 *
 *  % java DijkstraT mediumEWD.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (0.71)  0->44  0.06   44->93  0.07   ...  107->1  0.07   
 *  0 to 2 (0.65)  0->44  0.06   44->231  0.10  ...  42->2  0.11   
 *  0 to 3 (0.46)  0->97  0.08   97->248  0.09  ...  45->3  0.12   
 *  0 to 4 (0.42)  0->44  0.06   44->93  0.07   ...  77->4  0.11   
 *  ...
 *
 ******************************************************************************/

/**
 * The {@code DijkstraT} class represents a data type for solving the
 * single-source shortest paths problem in edge-weighted digraphs
 * where the edge weights are nonnegative.
 * <p>
 * This implementation uses Dijkstra's algorithm with a binary heap.
 * The constructor takes time proportional to <em>E</em> log <em>V</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of
 * edges.
 * Afterwards, the {@code distTo()} and {@code hasPathTo()} methods take
 * constant time and the {@code pathTo()} method takes time proportional to the
 * number of edges in the shortest path returned.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DijkstraT {
    private double[] distTo; // Mảng lưu trữ khoảng cách ngắn nhất từ CÁC ĐỈNH đến ĐỈNH ĐÍCH.
    private DirectedEdge[] edgeTo; // Mảng lưu trữ cạnh cuối cùng trên đường đi ngắn nhất đến mỗi đỉnh.
    IndexMinPQ<Double> pq; // Hàng đợi ưu tiên để quản lý các đỉnh theo khoảng cách ngắn nhất tạm thời.

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every
     * other
     * vertex in the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */

    /*
     * Các lớp phụ thuộc
     * EdgeWeightedDigraph: Đại diện cho đồ thị có hướng và có trọng số.
     * DirectedEdge: Đại diện cho một cạnh có hướng và có trọng số.
     * IndexMinPQ: Hàng đợi ưu tiên với khả năng cập nhật khóa (key) một cách hiệu
     * quả.
     * Stack: Cấu trúc dữ liệu ngăn xếp để lưu trữ đường đi.
     * => Đoạn mã trên là một triển khai của thuật toán Dijkstra để tìm đường đi
     * ngắn
     * nhất trong đồ thị có trọng số không âm.
     */
    public DijkstraT(EdgeWeightedDigraph G, int s) {
        // 1. Hàm khởi tạo DijkstraT(EdgeWeightedDigraph G, int s):

        // Khởi tạo mảng distTo và edgeTo.
        // Kiểm tra các cạnh có trọng số âm, nếu có thì ném ra ngoại lệ.
        // Gọi phương thức solve để tính đường đi ngắn nhất từ đỉnh nguồn s đến tất cả
        // các đỉnh khác.
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            solve(G, v, s);
    }

    // 2. Phương thức reset(EdgeWeightedDigraph G):
    // Đặt lại mảng distTo và edgeTo trước khi bắt đầu tính toán cho một đỉnh nguồn
    // mới.
    private void reset(EdgeWeightedDigraph G) {
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        edgeTo = new DirectedEdge[G.V()]; // Reset edgeTo
    }

    // 3.Phương thức printPath(int v, int s):
    // In đường đi ngắn nhất từ đỉnh v đến đỉnh s.
    private void printPath(int v, int s) {
        if (hasPathTo(s)) {
            StdOut.printf("%d to %d (%.2f)  ", v, s, distTo(s));
            if (v != s) {
                for (DirectedEdge e : pathTo(s)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        } else {
            StdOut.printf("%d to %d         no path\n", v, s);
        }
    }

    // 4.Phương thức solve(EdgeWeightedDigraph G, int v, int s):
    // Thiết lập lại các mảng distTo và edgeTo.
    // Khởi tạo hàng đợi ưu tiên pq và thêm đỉnh nguồn v vào hàng đợi.
    // Thực hiện thuật toán Dijkstra để tính toán đường đi ngắn nhất.
    // Gọi phương thức printPath để in kết quả.
    public void solve(EdgeWeightedDigraph G, int v, int s) {
        reset(G);
        distTo[v] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(v, distTo[v]);

        // Bo sung vong while chon phan tu min trong PQ .......
        while (!pq.isEmpty()) {
            int vt = pq.delMin();
            for (DirectedEdge e : G.adj(vt))
                relax(e);
        }

        // print path
        printPath(v, s);
    }

    // 5.Phương thức relax(DirectedEdge e): GIẢI PHÓNG cạnh e và cập nhật hàng đợi
    // ưu tiên nếu cần thiết.
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to
     * vertex {@code v}.
     * 
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to
     *         vertex {@code v};
     *         {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    // 7.Phương thức distTo(int v):
    // Trả về khoảng cách ngắn nhất từ đỉnh nguồn đến đỉnh v
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex {@code s} to vertex
     * {@code v}.
     *
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     *         {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    // 8.Phương thức hasPathTo(int v):
    // Kiểm tra xem có đường đi từ đỉnh nguồn đến đỉnh v hay không.
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     *         as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */

    // 8.Phương thức pathTo(int v):
    // Trả về đường đi ngắn nhất từ đỉnh nguồn đến đỉnh v dưới dạng một iterable của
    // các cạnh.
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }

        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}:Kiểm tra tính hợp
    // lệ của đỉnh v.
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * Unit tests the {@code DijkstraT} data type.
     *
     * @param args the command-line arguments
     */
    // Phương thức main thực hiện các bước sau:

    // Đọc đồ thị từ file tinyEWD.txt.
    // Khởi tạo một đối tượng DijkstraT với đồ thị và đỉnh nguồn s (ở đây là đỉnh
    // 3).
    // Tính toán và in ra đường đi ngắn nhất từ đỉnh 3 đến tất cả các đỉnh khác
    // trong đồ thị.
    public static void main(String[] args) {
        In in = new In("tinyEWD.txt");
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt("3");

        // compute shortest paths
        DijkstraT sp = new DijkstraT(G, s);

    }
}
// Trong bài toán trên, thuật toán được sử dụng là thuật toán Dijkstra. Đây là
// một thuật toán nổi tiếng để tìm đường đi ngắn nhất từ một đỉnh nguồn đến tất
// cả các đỉnh khác trong đồ thị có trọng số không âm. Thuật toán Dijkstra sử
// dụng hàng đợi ưu tiên để quản lý các đỉnh và cập nhật khoảng cách ngắn nhất
// tạm thời từ đỉnh khác đến các đỉnh nguồn.

// Các bước chính của thuật toán Dijkstra
// 1.Khởi tạo:

// Đặt khoảng cách từ đỉnh nguồn đến chính nó bằng 0 (distTo[s] = 0).
// Đặt khoảng cách từ đỉnh nguồn đến tất cả các đỉnh khác là vô cùng (distTo[v]
// = ∞).
// Sử dụng hàng đợi ưu tiên (ở đây là IndexMinPQ) để quản lý các đỉnh theo
// khoảng cách ngắn nhất tạm thời.
// 2.Thư giãn (Relaxation):

// Lặp lại quá trình sau cho đến khi hàng đợi ưu tiên rỗng:
// Lấy đỉnh v có khoảng cách ngắn nhất từ hàng đợi ưu tiên.
// Với mỗi cạnh v->w từ đỉnh v:
// Nếu khoảng cách mới từ nguồn đến w qua v nhỏ hơn khoảng cách hiện tại
// (distTo[w]), cập nhật distTo[w] và edgeTo[w].
// Nếu w đã có trong hàng đợi ưu tiên, cập nhật khóa của w trong hàng đợi. Nếu
// không, thêm w vào hàng đợi ưu tiên.
// 3.In kết quả:

// Sau khi tính toán xong, khoảng cách ngắn nhất từ đỉnh nguồn đến mỗi đỉnh sẽ
// được lưu trong mảng distTo, và đường đi ngắn nhất có thể được truy vết lại
// thông qua mảng edgeTo.
// Mã nguồn chính
// Trong đoạn mã trên, các phương thức chính thực hiện các bước này bao gồm:

// Hàm khởi tạo DijkstraT(EdgeWeightedDigraph G, int s):

// Khởi tạo mảng distTo và edgeTo.
// Kiểm tra các cạnh có trọng số âm.
// Gọi phương thức solve để tính đường đi ngắn nhất từ đỉnh nguồn s đến tất cả
// các đỉnh khác.
// Phương thức solve(EdgeWeightedDigraph G, int v, int s):

// Thiết lập lại các mảng distTo và edgeTo.
// Khởi tạo hàng đợi ưu tiên pq và thêm đỉnh nguồn v vào hàng đợi.
// Thực hiện thuật toán Dijkstra để tính toán đường đi ngắn nhất.
// Gọi phương thức printPath để in kết quả.
// Phương thức relax(DirectedEdge e):

// Thư giãn cạnh e và cập nhật hàng đợi ưu tiên nếu cần thiết.

// Chiến lược tham lam trong thuật toán Dijkstra
// Trong thuật toán Dijkstra, chiến lược tham lam được sử dụng ở những chỗ sau:

// 1.Chọn đỉnh có khoảng cách ngắn nhất hiện tại:

// Mỗi lần, thuật toán chọn đỉnh v từ hàng đợi ưu tiên (pq) có khoảng cách ngắn
// nhất từ đỉnh nguồn (distTo[v]).
// Đây là bước tham lam, vì tại mỗi bước, thuật toán chọn đỉnh có khoảng cách
// ngắn nhất hiện tại mà chưa được xử lý, với hy vọng rằng đây sẽ là lựa chọn
// tốt nhất để tiếp tục mở rộng đường đi ngắn nhất.

// 2. Cập nhật khoảng cách đến các đỉnh kề:
// Sau khi chọn đỉnh v có khoảng cách ngắn nhất hiện tại, thuật toán sẽ thư giãn
// (relax) tất cả các cạnh đi từ v đến các đỉnh kề w.
// Thư giãn một cạnh nghĩa là kiểm tra xem liệu có thể cải thiện khoảng cách
// ngắn nhất đến đỉnh w thông qua đỉnh v. Nếu có, cập nhật khoảng cách ngắn nhất
// và cập nhật hàng đợi ưu tiên.

// ************** SỰ KHÁC NHAU GIỮA PRIM VÀ DISJKTRA */

// Thuật toán Dijkstra và thuật toán Prim đều là các thuật toán đồ thị nổi tiếng
// và đều sử dụng chiến lược tham lam (greedy strategy). Tuy nhiên, chúng được
// sử dụng để giải các bài toán khác nhau và có những điểm khác biệt chính sau
// đây:

// Mục đích
// Thuật toán Dijkstra:

// Mục đích: Tìm đường đi ngắn nhất từ một đỉnh nguồn đến tất cả các đỉnh khác
// trong đồ thị có trọng số không âm.
// Bài toán: Single-source shortest path (Đường đi ngắn nhất từ một nguồn).
// Thuật toán Prim:

// Mục đích: Tìm cây khung nhỏ nhất (Minimum Spanning Tree - MST) của một đồ thị
// liên thông, không có hướng và có trọng số.
// Bài toán: Minimum spanning tree (Cây khung nhỏ nhất).
// Hoạt động
// Thuật toán Dijkstra:

// Cách hoạt động:
// Khởi tạo khoảng cách từ đỉnh nguồn đến chính nó là 0 và đến các đỉnh khác là
// vô cùng.
// Sử dụng hàng đợi ưu tiên để liên tục chọn đỉnh có khoảng cách ngắn nhất chưa
// được xử lý.
// Thư giãn các cạnh của đỉnh vừa chọn và cập nhật khoảng cách ngắn nhất nếu tìm
// thấy đường đi ngắn hơn.
// Đặc điểm: Khoảng cách từ đỉnh nguồn đến các đỉnh khác được tối ưu hóa từng
// bước một, theo thứ tự tăng dần của khoảng cách.
// Thuật toán Prim:

// Cách hoạt động:
// Bắt đầu từ một đỉnh tùy ý trong đồ thị.
// Sử dụng hàng đợi ưu tiên để liên tục chọn cạnh có trọng số nhỏ nhất mà nối
// đỉnh đã thuộc cây khung với đỉnh chưa thuộc cây khung.
// Thêm đỉnh mới vào cây khung và lặp lại cho đến khi tất cả các đỉnh đều thuộc
// cây khung.
// Đặc điểm: Chọn cạnh có trọng số nhỏ nhất để thêm vào cây khung, đảm bảo rằng
// cây khung luôn được mở rộng một cách tối ưu.
// Đầu vào và yêu cầu
// Thuật toán Dijkstra:

// Đầu vào: Đồ thị có trọng số không âm.
// Yêu cầu: Trọng số của các cạnh phải không âm.
// Thuật toán Prim:

// Đầu vào: Đồ thị liên thông, có thể có trọng số âm.
// Yêu cầu: Đồ thị phải liên thông để đảm bảo rằng có một cây khung.

// Tóm tắt
// Dijkstra: Tìm đường đi ngắn nhất từ các đỉnh đến đỉnh đích , yêu cầu trọng số
// không âm, sử dụng hàng đợi ưu tiên để chọn đỉnh có khoảng cách ngắn nhất hiện
// tại so vs đỉnh đich.
// Prim: Tìm cây khung nhỏ nhất, áp dụng cho đồ thị liên thông, sử dụng hàng đợi
// ưu tiên để chọn cạnh có trọng số nhỏ nhất mở rộng cây khung.

// thuật toán disktra chọn đỉnh như thế nào

// Trong thuật toán Dijkstra, việc chọn đỉnh được thực hiện theo chiến lược tham
// lam, nghĩa là luôn chọn đỉnh có khoảng cách ngắn nhất từ đỉnh nguồn trong số
// các đỉnh chưa được xử lý. Điều này được thực hiện bằng cách sử dụng một hàng
// đợi ưu tiên (priority queue), cụ thể là một hàng đợi ưu tiên tối thiểu
// (min-priority queue).

// Cách chọn đỉnh trong thuật toán Dijkstra
// Khởi tạo:

// Đặt khoảng cách từ đỉnh nguồn s đến chính nó là 0 (distTo[s] = 0).
// Đặt khoảng cách từ đỉnh nguồn đến tất cả các đỉnh khác là vô cùng (distTo[v]
// = ∞).
// Thêm đỉnh nguồn s vào hàng đợi ưu tiên với giá trị khóa là khoảng cách của nó
// (pq.insert(s, distTo[s])).
// Vòng lặp chính:

// Trong mỗi bước của vòng lặp chính, đỉnh v có khoảng cách ngắn nhất hiện tại
// (tức là đỉnh có giá trị khóa nhỏ nhất trong hàng đợi ưu tiên) được chọn và
// xóa khỏi hàng đợi (pq.delMin()).
// Đây là bước chọn đỉnh theo chiến lược tham lam, đảm bảo rằng đỉnh được chọn
// có khoảng cách ngắn nhất tạm thời từ đỉnh nguồn.
// Thư giãn các cạnh của đỉnh vừa chọn:

// Sau khi chọn đỉnh v, thuật toán duyệt qua tất cả các cạnh kề của v.
// Thực hiện thư giãn các cạnh này, tức là kiểm tra và cập nhật khoảng cách ngắn
// nhất đến các đỉnh kề nếu tìm thấy đường đi ngắn hơn thông qua v.

// public class DijkstraT {
// private double[] distTo; // distTo[v] = distance of shortest s->v path
// private DirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
// private IndexMinPQ<Double> pq; // priority queue of vertices

// public DijkstraT(EdgeWeightedDigraph G, int s) {
// // Kiểm tra cạnh có trọng số âm
// for (DirectedEdge e : G.edges()) {
// if (e.weight() < 0)
// throw new IllegalArgumentException("edge " + e + " has negative weight");
// }

// distTo = new double[G.V()];
// edgeTo = new DirectedEdge[G.V()];

// // Khởi tạo khoảng cách
// for (int v = 0; v < G.V(); v++)
// distTo[v] = Double.POSITIVE_INFINITY;
// distTo[s] = 0.0;

// // Khởi tạo hàng đợi ưu tiên
// pq = new IndexMinPQ<Double>(G.V());
// pq.insert(s, distTo[s]);

// // Vòng lặp chính của thuật toán Dijkstra
// while (!pq.isEmpty()) {
// int v = pq.delMin(); // Chọn đỉnh có khoảng cách ngắn nhất hiện tại
// for (DirectedEdge e : G.adj(v))
// relax(e);
// }
// }

// // Thư giãn cạnh e và cập nhật pq nếu cần
// private void relax(DirectedEdge e) {
// int v = e.from(), w = e.to();
// if (distTo[w] > distTo[v] + e.weight()) {
// distTo[w] = distTo[v] + e.weight();
// edgeTo[w] = e;
// if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
// else pq.insert(w, distTo[w]);
// }
// }

// // Các phương thức khác như distTo(), hasPathTo(), pathTo(),
// validateVertex()...
// }

/*
 * Giải thích chi tiết
 * Hàng đợi ưu tiên (pq):
 * 
 * Được sử dụng để quản lý các đỉnh và chọn đỉnh có khoảng cách ngắn nhất hiện
 * tại. Hàng đợi ưu tiên giúp tìm và loại bỏ đỉnh có giá trị khóa nhỏ nhất một
 * cách hiệu quả.
 * Chọn đỉnh (pq.delMin()):
 * 
 * Trong vòng lặp chính, pq.delMin() được gọi để chọn và loại bỏ đỉnh v có
 * khoảng cách ngắn nhất từ hàng đợi ưu tiên. Đây là bước tham lam, đảm bảo rằng
 * đỉnh được chọn có khoảng cách ngắn nhất hiện tại từ đỉnh nguồn.
 * Thư giãn các cạnh:
 * 
 * Sau khi chọn đỉnh v, các cạnh kề của v được duyệt qua và thực hiện thư giãn
 * (relax(e)). Thư giãn có nghĩa là kiểm tra xem liệu có thể cải thiện khoảng
 * cách ngắn nhất đến đỉnh kề w thông qua v. Nếu có, cập nhật khoảng cách và cập
 * nhật hàng đợi ưu tiên tương ứng.
 */

// Kết quả của thuật toán Dijkstra được liệt kê ở trên cho biết khoảng cách ngắn
// nhất từ mỗi đỉnh trong đồ thị đến đỉnh đích là đỉnh 3, cùng với đường đi
// tương ứng.

// Giải thích chi tiết kết quả:
// 0 to 3 (0.99): Khoảng cách ngắn nhất từ đỉnh 0 đến đỉnh 3 là 0.99. Đường đi
// ngắn nhất là:

// 0 -> 2 (0.26), 2 -> 7 (0.34), 7 -> 3 (0.39)
// 1 to 3 (0.29): Khoảng cách ngắn nhất từ đỉnh 1 đến đỉnh 3 là 0.29. Đường đi
// ngắn nhất là:

// 1 -> 3 (0.29)
// 2 to 3 (0.73): Khoảng cách ngắn nhất từ đỉnh 2 đến đỉnh 3 là 0.73. Đường đi
// ngắn nhất là:

// 2 -> 7 (0.34), 7 -> 3 (0.39)
// 3 to 3 (0.00): Khoảng cách ngắn nhất từ đỉnh 3 đến chính nó là 0.00. Không có
// đường đi cần thiết.

// 4 to 3 (0.76): Khoảng cách ngắn nhất từ đỉnh 4 đến đỉnh 3 là 0.76. Đường đi
// ngắn nhất là:

// 4 -> 7 (0.37), 7 -> 3 (0.39)
// 5 to 3 (0.61): Khoảng cách ngắn nhất từ đỉnh 5 đến đỉnh 3 là 0.61. Đường đi
// ngắn nhất là:

// 5 -> 1 (0.32), 1 -> 3 (0.29)
// 6 to 3 (1.13): Khoảng cách ngắn nhất từ đỉnh 6 đến đỉnh 3 là 1.13. Đường đi
// ngắn nhất là:

// 6 -> 2 (0.40), 2 -> 7 (0.34), 7 -> 3 (0.39)
// 7 to 3 (0.39): Khoảng cách ngắn nhất từ đỉnh 7 đến đỉnh 3 là 0.39. Đường đi
// ngắn nhất là:

// 7 -> 3 (0.39)
// Ý nghĩa của kết quả:
// Kết quả trên cho thấy:

// Khoảng cách ngắn nhất: Mỗi dòng kết quả hiển thị khoảng cách ngắn nhất từ
// đỉnh ban đầu đến đỉnh đích (trong trường hợp này là đỉnh 3).

// Đường đi ngắn nhất: Các cạnh trong đồ thị tạo thành đường đi ngắn nhất từ
// đỉnh ban đầu đến đỉnh đích. Mỗi cạnh hiển thị đỉnh bắt đầu, đỉnh kết thúc và
// trọng số của cạnh đó.