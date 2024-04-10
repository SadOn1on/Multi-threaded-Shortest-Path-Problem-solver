import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ComparatorThread extends Thread{
    private final TestOverview testOverview;
    private final File solutionsDirectory;

    private int count = 1;

    ComparatorThread(TestOverview testOverview, File solutionsDirectory) {
        this.testOverview = testOverview;
        this.solutionsDirectory = solutionsDirectory;
    }

    @Override
    public void run() {
        int numberOfTests = testOverview.getNumberOfTests();
        int firstNotCompared = 0;
        int lastSolved = testOverview.getLastSolved();
        ArrayList<Solution> solFile;
        while (firstNotCompared < numberOfTests) {
            if(testOverview.getSolved(firstNotCompared) == 1) {
                solFile = readNextSolutions();
                if(testOverview.solutionsEquality(firstNotCompared, solFile)) {
                    testOverview.setCompared(firstNotCompared, 1);
                    testOverview.setResult(firstNotCompared, "Ok");
                } else {
                    testOverview.setCompared(firstNotCompared, -1);
                    testOverview.setResult(firstNotCompared, "Wrong answers in file");
                }
            } else {
                ++count;
            }
            testOverview.increaseLastCompared();
            if (firstNotCompared < lastSolved) {
                ++firstNotCompared;
            } else {
                lastSolved = testOverview.getLastSolved(firstNotCompared);
                if (lastSolved > firstNotCompared) {
                    ++firstNotCompared;
                } else {
                    break;
                }
            }
        }
    }

    private ArrayList<Solution> readNextSolutions() {
        ArrayList<Solution> result = new ArrayList<>();
        String filePath = solutionsDirectory.getAbsolutePath() + "\\" + "close" + count + ".out";
        ++count;
        int number;
        int source;
        int destination;
        int distance;
        try(Scanner reader = new Scanner(new File(filePath))) {
            number = reader.nextInt();
            for (int i = 0; i < number; ++i) {
                source = reader.nextInt();
                destination = reader.nextInt();
                distance = reader.nextInt();
                result.add(new Solution(source, destination, distance));
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return result;
        }
    }
}
