public class SolverThread extends Thread {
    private final TestOverview testOverview;

    SolverThread(TestOverview testOverview) {
        this.testOverview = testOverview;
    }

    @Override
    public void run() {
        int numberOfTests = testOverview.getNumberOfTests();
        int firstNotSolved = 0;
        int lastChecked = testOverview.getLastChecked();
        while (firstNotSolved < numberOfTests) {
            while(testOverview.getStopSolving()) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if(testOverview.getChecked(firstNotSolved) == 1) {
                int n = testOverview.getN(firstNotSolved);
                int k = testOverview.getK(firstNotSolved);
                DijkstraAdjacencyMatrix solution = new DijkstraAdjacencyMatrix(n);
                int[][] tempMatrix = testOverview.getAdjacencyMatrix(firstNotSolved);
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < n; ++j) {
                        solution.addEdge(i, j, tempMatrix[i][j]);
                    }
                }
                for(int i = 0; i < n; ++i) {
                    int[] distances = solution.dijkstraGetMinDistances(i);
                    for (int j = 0; j < n; ++j) {
                        if (distances[j] <= k && distances[j] != 0) {
                            Solution sol = new Solution(i, j, distances[j]);
                            testOverview.addSolution(firstNotSolved, sol);
                        }
                    }
                }
                testOverview.setSolved(firstNotSolved, 1);
            } else {
                testOverview.setSolved(firstNotSolved, -1);
            }

            if (firstNotSolved < lastChecked) {
                testOverview.increaseLastSolved();
                ++firstNotSolved;
            } else {
                lastChecked = testOverview.getLastChecked(firstNotSolved);
                testOverview.increaseLastSolved();
                if (lastChecked > firstNotSolved) {
                    ++firstNotSolved;
                } else {
                    break;
                }
            }
        }
    }
}
