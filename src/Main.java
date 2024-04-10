import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Button start;
    Button stopSolver;
    Button resumeSolver;
    Button directoryChooser;
    DirectoryChooser dch = new DirectoryChooser();
    static ListView<String> view;
    File directory;

    CheckerThread checkerThread;
    SolverThread solverThread;
    ComparatorThread comparatorThread;
    UiUpdateThread uiUpdateThread;
    TestOverview testOverview;

    static ObservableList<String> output;

    @Override
    public void start(Stage primaryStage) {
        start = new Button("Start");
        start.setDisable(true);
        start.setOnAction(e -> begin());
        stopSolver = new Button("Stop solver thread");
        stopSolver.setOnAction(e -> pause());
        stopSolver.setDisable(true);
        resumeSolver = new Button("Resume solver thread");
        resumeSolver.setDisable(true);
        resumeSolver.setOnAction(e -> res());
        directoryChooser = new Button("Open directory with tests");
        directoryChooser.setOnAction(e -> pick());
        view = new ListView<>();
        output = FXCollections.observableArrayList();
        view.setItems(output);

        HBox hpane = new HBox(10);
        hpane.getChildren().addAll(directoryChooser, start, stopSolver, resumeSolver);
        VBox vpane = new VBox(10, hpane, view);

        Scene scene = new Scene(vpane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void begin() {
        Test[] tests = readInputFile(directory.getAbsolutePath());
        if(tests.length == 0) {
            return;
        }
        testOverview = new TestOverview(tests);
        checkerThread = new CheckerThread(testOverview);
        solverThread = new SolverThread(testOverview);
        comparatorThread = new ComparatorThread(testOverview, directory);
        uiUpdateThread = new UiUpdateThread(testOverview, output);
        stopSolver.setDisable(false);

        checkerThread.start();
        solverThread.start();
        comparatorThread.start();
        uiUpdateThread.start();
    }

    private void pause() {
        testOverview.setStopSolving(true);
        resumeSolver.setDisable(false);
        stopSolver.setDisable(true);
    }

    private void res() {
        testOverview.setStopSolving(false);
        stopSolver.setDisable(false);
        resumeSolver.setDisable(true);
    }

    private void pick() {
        dch.setTitle("Choose directory");
        directory = dch.showDialog(null);
        start.setDisable(false);
    }

    public static Test[] readInputFile(String directory) {
        ArrayList<Test> result = new ArrayList<>();
        int n;
        int k;
        int[][] incidenceMatrix;
        int count = 1;
        while (true) {
            String filePath = directory + "\\" + "close" + count + ".in";
            File temp = new File(filePath);
            if (temp.exists()) {
                try (Scanner reader = new Scanner(temp)) {
                    n = reader.nextInt();
                    k = reader.nextInt();
                    incidenceMatrix = new int[n][n];
                    for (int i = 0; i < n; ++i) {
                        for (int j = 0; j < n; ++j) {
                            incidenceMatrix[i][j] = reader.nextInt();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return result.toArray(new Test[0]);
                }
                result.add(new Test(n, k, incidenceMatrix));
                ++count;
            } else {
                if (count == 1) {
                    output.clear();
                    output.add("No input file found in this folder");
                }
                break;
            }
        }
        return result.toArray(new Test[0]);
    }

}