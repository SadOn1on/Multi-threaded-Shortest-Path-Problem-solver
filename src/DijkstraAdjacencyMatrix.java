import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraAdjacencyMatrix {

    private static class Pair implements Comparable<Pair> {
        int vertex;
        int dist; //weight so far

        Pair(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj == this) {
                return true;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            final Pair pair = (Pair) obj;

            return this.vertex == pair.vertex && this.dist == pair.dist;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + vertex;
            result = 31 * result + dist;
            return result;
        }

        // using CompareTo function so that priority queue arranges the element in terms of weight so far
        public int compareTo(Pair o) {
            return this.dist - o.dist;
        }
    }

    int vertices;
    int[][] matrix;

    public DijkstraAdjacencyMatrix(int vertex) {
        this.vertices = vertex;
        matrix = new int[vertex][vertex];
    }

    public void addEdge(int source, int destination, int weight) {
        matrix[source][destination] = weight;
    }

    public int[] dijkstraGetMinDistances(int sourceVertex) {
        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        boolean[] visited = new boolean[vertices];
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        queue.add(new Pair(sourceVertex, 0));

        while (!queue.isEmpty()) {

            // removing the topmost element and storing it in topEle
            Pair topEle = queue.remove();

            //checking if vertex already visited
            if (visited[topEle.vertex]) {
                continue;
            }
            // marking true if  vertex not visited
            visited[topEle.vertex] = true;

            //adding the dist to reach that vertex
            distances[topEle.vertex] = topEle.dist;

            //adding all the unvisited neighbor of vertex in queue
            for (int i = 0; i < vertices; ++i) {
                if (matrix[topEle.vertex][i] > 0) {
                    queue.add(new Pair(i, topEle.dist + matrix[topEle.vertex][i]));
                }
            }
        }
        return distances;
    }
}