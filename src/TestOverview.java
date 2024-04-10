import java.util.ArrayList;
import java.util.List;

public class TestOverview {
    private final Test[] tests;
    private final int numberOfTests;
    private final ArrayList<Integer> last = new ArrayList<>();

    private boolean stopSolving = false;

    public TestOverview(Test[] tests) {
        this.tests = tests;
        this.numberOfTests = tests.length;
        last.add(-1);
        last.add(-1);
        last.add(-1);
    }

    public synchronized void addSolution(int index, Solution solution) {
        tests[index].addSolution(solution);
    }

    public void setStopSolving(boolean s) {
        stopSolving = s;
    }

    public boolean getStopSolving() {
        return stopSolving;
    }

    public int getLastChecked() {
        synchronized (last) {
            try {
                if (last.get(0).equals(-1)) last.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return last.get(0);
        }
    }

    public int getLastChecked(int compareTo) {
        synchronized (last) {
            try {
                while (last.get(0).equals(-1) || (last.get(0).equals(compareTo) && !last.get(0).equals(numberOfTests - 1)))
                    last.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return last.get(0);
        }
    }

    public void increaseLastChecked() {
        synchronized (last) {
            last.set(0, last.get(0) + 1);
            last.notify();
        }
    }

    public int getLastSolved() {
        synchronized (last) {
            try {
                while (last.get(1) == -1) last.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return last.get(1);
        }
    }

    public int getLastSolved(int compareTo) {
        synchronized (last) {
            try {
                while (last.get(1) == -1 || (last.get(1) == compareTo && last.get(1) != numberOfTests - 1))
                    last.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return last.get(1);
        }
    }

    public void increaseLastSolved() {
        synchronized (last) {
            last.set(1, last.get(1) + 1);
            last.notify();
        }
    }

    public synchronized int getLastCompared() {
        return last.get(2);
    }

    public synchronized int getLastCompared(int compareTo) {
        try {
            while (last.get(2) == -1 || last.get(2) == compareTo) last.wait();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return last.get(2);
    }

    public synchronized void increaseLastCompared() {
        synchronized (last) {
            last.set(2, last.get(2) + 1);
            last.notify();
        }
    }

    public synchronized int getK(int index) {
        return tests[index].getK();
    }

    public synchronized int getN(int index) {
        return tests[index].getN();
    }

    public synchronized int getChecked(int index) {
        return tests[index].getChecked();
    }

    public synchronized boolean setChecked(int index, int checked) {
        return tests[index].setChecked(checked);
    }

    public synchronized int getSolved(int index) {
        return tests[index].getSolved();
    }

    public synchronized boolean setSolved(int index, int solved) {
        return tests[index].setSolved(solved);
    }

    public synchronized int getCompared(int index) {
        return tests[index].getCompared();
    }

    public synchronized boolean setCompared(int index, int compared) {
        return tests[index].setSolved(compared);
    }

    public synchronized StringBuffer getResult(int index) {
        return tests[index].getResult();
    }

    public synchronized void setResult(int index, String result) {
        tests[index].setResult(result);
    }

    public boolean solutionsEquality(int index, List<Solution> solutions) {
        List<Solution> compare = tests[index].getSolutions();
        if (compare.size() != solutions.size()) {
            return false;
        }
        int j = 0;
        for (Solution i : compare) {
            if (i.equals(solutions.get(j))) {
                ++j;
            } else {
                return false;
            }
        }
        return true;
    }

    public synchronized int[][] getAdjacencyMatrix(int index) {
        return tests[index].getAdjacencyMatrix();
    }

    public synchronized int getNumberOfTests() {
        return numberOfTests;
    }
}
