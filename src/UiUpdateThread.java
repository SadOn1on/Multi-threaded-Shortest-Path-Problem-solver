import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UiUpdateThread extends Thread {
    ObservableList<String> output;

    TestOverview testOverview;

    UiUpdateThread(TestOverview testOverview, ObservableList<String> output) {
        this.testOverview = testOverview;
        this.output = output;
    }

    @Override
    public void run() {
        int n = testOverview.getNumberOfTests();
        while(n - 1 > testOverview.getLastCompared())  {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < testOverview.getNumberOfTests(); ++i) {
            results.add("Test " + (i + 1) + ": " + testOverview.getResult(i));
        }
        Platform.runLater(() -> {
            output.clear();
            output.addAll(results);
        });
    }
}
