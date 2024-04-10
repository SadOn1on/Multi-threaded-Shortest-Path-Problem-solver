import java.util.ArrayList;
import java.util.List;

public class Test {
    private final int n;
    private final int k;
    private final int[][] adjacency;
    private int checked;
    private int solved;
    private int compared;
    private StringBuffer result;

    private List<Solution> solutions;

    public Test() {
        this.n = -1;
        this.k = -1;
        this.adjacency = new int[1][1];
        this.adjacency[0][0] = -1;
        this.checked = 0;
        this.compared = 0;
        this.result = new StringBuffer("No");
    }

    public Test(int n, int k, int[][] incidenceMatrix) {
        this.n = n;
        this.k = k;
        this.adjacency = incidenceMatrix;
        this.checked = 0;
        this.solved = 0;
        this.compared = 0;
        this.result = new StringBuffer("No");
        solutions = new ArrayList<>();
    }


    public List<Solution> getSolutions() {
        return solutions;
    }

    public void addSolution(Solution solution) {
        solutions.add(solution);
    }

    public int[][] getAdjacencyMatrix() {
        return adjacency;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public int getChecked() {
        return checked;
    }

    public StringBuffer getResult() {
        return result;
    }

    public int getCompared() {
        return compared;
    }

    public int getSolved() {
        return solved;
    }

    public boolean setChecked(int checked) {
        if (checked == 1 || checked == -1) {
            this.checked = checked;
            return true;
        } else {
            return false;
        }
    }

    public boolean setSolved(int solved) {
        if (solved == 1 || solved == -1) {
            this.solved = solved;
            return true;
        } else {
            return false;
        }
    }

    public boolean setCompared(int compared) {
        if (compared == 1 || compared == -1) {
            this.compared = compared;
            return true;
        } else {
            return false;
        }
    }

    public void setResult(String result) {
        this.result.replace(0, this.result.length(), result);
    }
}

