package xindijia.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import xindijia.service.SudokuSolve;


/**
 * @author xia
 * @since 2024/1/6/13:48
 */
public class SolutionView extends Application {

    private static final int SIZE = 9;


    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox(10);

        // 创建格子并添加到GridPane
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField cell = new TextField();
                cell.setAlignment(Pos.CENTER);
                cell.setFont(Font.font(16));
                cell.setMinSize(60, 60);
                cell.setMaxSize(60, 60);
                gridPane.add(cell, col, row);
            }
        }


        Label label = new Label("请输入你要求解的数独");
        label.setFont(Font.font(20));


        HBox hBox = new HBox(10);
        Button solution = new Button("解题");
        solution.setMaxSize(100, 50);
        solution.setMinSize(100, 50);
        solution.setFont(Font.font(20));
        solution.setOnAction(event -> {
            //获取数据
            int[][] map = new int[9][9];
            ObservableList<Node> children = gridPane.getChildren();
            int i = 0;
            int j = 0;
            int index = 0;
            for (Node node : children) {
                TextField textField = (TextField) node;
                i = index / 9;
                j = index % 9;
                if (textField.getText() == null || "".equals(textField.getText())) {
                    map[i][j] = 0;
                } else {
                    map[i][j] = Integer.parseInt(textField.getText());
                }
                index++;
            }
//            int[][] map = {{1, 0, 3, 0, 0, 0, 5, 0, 9},
//                    {0, 0, 2, 1, 0, 9, 4, 0, 0},
//                    {0, 0, 0, 7, 0, 4, 0, 0, 0},
//                    {3, 0, 0, 5, 0, 2, 0, 0, 6},
//                    {0, 6, 0, 0, 0, 0, 0, 5, 0},
//                    {7, 0, 0, 8, 0, 3, 0, 0, 4},
//                    {0, 0, 0, 4, 0, 1, 0, 0, 0},
//                    {0, 0, 9, 2, 0, 5, 8, 0, 0},
//                    {8, 0, 4, 0, 0, 0, 1, 0, 7}};
            //判断是否 全 为空
            boolean flag = true;
            for (int x = 0; x < SIZE; x++) {
                for (int y = 0; y < SIZE; y++) {
                    if (map[x][y] != 0) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误提示");
                alert.setContentText("输入的数据不能为空");
                alert.showAndWait();
            } else {
                //调用函数解决
                SudokuSolve sudokuSolve = new SudokuSolve();
                sudokuSolve.backtrack(0, map);
                int[][] board = sudokuSolve.getBoard();
                //无解 提示错误信息
                //有解 将解展示
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        TextField cell = new TextField();
                        cell.setAlignment(Pos.CENTER);
                        cell.setFont(Font.font(16));
                        cell.setMinSize(60, 60);
                        cell.setMaxSize(60, 60);
                        cell.setText(board[row][col] + "");
                        gridPane.add(cell, col, row);
                    }
                }
            }
        });


        Button reset = new Button("重置");
        reset.setFont(Font.font(20));
        reset.setMaxSize(100, 50);
        reset.setMinSize(100, 50);
        reset.setOnAction(event -> {
            ObservableList<Node> children = gridPane.getChildren();
            for (Node child : children) {
                TextField cell = (TextField) child;
                cell.setText("");
            }
        });


        Button back = new Button("返回");
        back.setFont(Font.font(20));
        back.setMaxSize(100, 50);
        back.setMinSize(100, 50);
        back.setOnAction(event -> {
            try {
                new HomePageView().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        hBox.getChildren().addAll(solution, reset, back);
        hBox.setPadding(new Insets(10, 60, 20, 10));

        vBox.getChildren().addAll(label, gridPane, hBox);
        vBox.setPadding(new Insets(10, 60, 20, 10));
        Scene scene = new Scene(vBox, 700, 700);
        stage.setScene(scene);
        stage.setTitle("数独世界");
        stage.show();
    }
}
