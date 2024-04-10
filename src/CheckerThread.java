
public class CheckerThread extends Thread {
    private final TestOverview testOverview;

    CheckerThread(TestOverview testOverview) {
        this.testOverview = testOverview;
    }

    @Override
    public void run() {
        int numberOfTests = testOverview.getNumberOfTests();
        int lastChecked = -1;
        while (lastChecked < numberOfTests - 1) {
            int n = testOverview.getN(lastChecked + 1);
            int k = testOverview.getK(lastChecked + 1);
            if (n < 2 || n > 10000) {
                testOverview.setChecked(lastChecked + 1, -1);
                testOverview.setResult(lastChecked + 1, "Wrong input(wrong number of vertices)");
                testOverview.increaseLastChecked();
                ++lastChecked;
            } else if (k < 1 || k > 10000000) {
                testOverview.setChecked(lastChecked + 1, -1);
                testOverview.setResult(lastChecked + 1, "Wrong input(wrong distance)");
                testOverview.increaseLastChecked();
                ++lastChecked;
            } else {
                int[][] tempMatrix = testOverview.getAdjacencyMatrix(lastChecked + 1);
                int count = 0;
                boolean br = false;
                for (int i = 0; i < n; ++i) {
                    if (br) {
                        break;
                    }
                    for (int j = 0; j < n; ++j) {
                        if (tempMatrix[i][j] != 0) {
                            ++count;
                            if (tempMatrix[j][i] != 0 || tempMatrix[i][j] < 1 || tempMatrix[i][j] > 1000) {
                                testOverview.setChecked(lastChecked + 1, -1);
                                testOverview.setResult(lastChecked + 1,
                                        "Wrong input(error in adjacency matrix)");
                                br = true;
                                break;
                            }
                        }
                    }
                }
                if (br) {
                    testOverview.increaseLastChecked();
                    continue;
                }
                if (count > n * n - n) {
                    testOverview.setChecked(lastChecked + 1, -1);
                    testOverview.setResult(lastChecked + 1, "Wrong input(wrong number of edges)");
                    testOverview.increaseLastChecked();
                    continue;
                }
                testOverview.setChecked(lastChecked + 1, 1);
                testOverview.increaseLastChecked();
                ++lastChecked;
            }
        }
    }
}
