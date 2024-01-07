package xindijia.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import xindijia.entity.Data;
import xindijia.service.SudokuGenerator;

import java.util.Date;
import java.util.Optional;

/**
 * @author xia
 * @since 2024/1/6/22:32
 */
public class PlayView extends Application {

    private static final int SIZE = 9;

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox(10);
        GridPane gridPane = new GridPane();
        Label label = new Label("第一关");
        label.setFont(Font.font(20));


        // 创建格子并添加到GridPane
        SudokuGenerator sudokuGenerator = new SudokuGenerator();

        Data data = (Data) stage.getUserData();
        if (data == null) {//没有设置难度,按照默认的参数
            sudokuGenerator.generate(40);
        } else {
            sudokuGenerator.generate(data.getDegree());//设置难度
        }


        int[][] question = sudokuGenerator.getQuestion();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField cell = new TextField();
                cell.setAlignment(Pos.CENTER);
                cell.setFont(Font.font(16));
                cell.setMinSize(60, 60);
                cell.setMaxSize(60, 60);
                if (question[row][col] == 0) {
                    cell.setText("");
                } else {
                    cell.setEditable(false);
                    cell.setText(question[row][col] + "");
                }
                gridPane.add(cell, col, row);
            }
        }

        int[][] board = sudokuGenerator.getBoard();
        //监听内容变化
        ObservableList<Node> children = gridPane.getChildren();
        for (Node child : children) {
            TextField cell = (TextField) child;
            //每个cell添加监听器
            cell.textProperty().addListener((observable, oldValue, newValue) -> {
                //获取位置
                Integer row = GridPane.getRowIndex(cell);
                Integer col = GridPane.getColumnIndex(cell);
                if (!"".equals(newValue) && board[row][col] != Integer.parseInt(newValue)) {//与正确答案比较,并提示还有几次机会
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setContentText("你输入的答案有误!");
                    alert.showAndWait();
                }
            });
        }

        HBox hBox = new HBox(20);
        hBox.setPadding(new Insets(20, 60, 20, 60));
        //确定
        Button sure = new Button("确定");
        sure.setFont(Font.font(20));
        sure.setMaxSize(100, 50);
        sure.setMinSize(100, 50);
        sure.setOnAction(event -> {
            ObservableList<Node> nodes = gridPane.getChildren();
            boolean flag = true;
            for (Node node : nodes) {
                TextField cell = (TextField) node;
                if ("".equals(cell.getText())) {//没有作答完成就点击了确定按钮
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setContentText("请继续作答完成");
                    alert.showAndWait();
                    flag = false;
                    break;
                }
            }
            if (flag) {//全部完成
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("闯关成功");
                alert.setContentText("恭喜你通过本关游戏");
                alert.showAndWait();
            }
        });


        //下一关
        Button next = new Button("下一关");
        next.setFont(Font.font(20));
        next.setMaxSize(100, 50);
        next.setMinSize(100, 50);
        next.setOnAction(event -> {
            ObservableList<Node> nodes = gridPane.getChildren();
            boolean flag = true;
            for (Node node : nodes) {
                TextField cell = (TextField) node;
                if ("".equals(cell.getText())) {//没有作答完成就点击了下一关按钮
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setContentText("请完成本关任务!");
                    alert.showAndWait();
                    flag = false;
                    break;
                }
            }
            if (flag) {
                //跳转到下一关
                try {
                    new PlayView().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        //退出
        Button exit = new Button("退出");
        exit.setFont(Font.font(20));
        exit.setMaxSize(100, 50);
        exit.setMinSize(100, 50);
        exit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("退出游戏");
            alert.setContentText("你确定要退出本关游戏吗?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    new HomePageView().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        hBox.getChildren().addAll(sure, next, exit);


        vBox.getChildren().addAll(label, gridPane, hBox);
        Scene scene = new Scene(vBox, 700, 700);
        stage.setScene(scene);
        stage.show();
    }
}
