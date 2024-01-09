package xindijia.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import xindijia.entity.Data;

/**
 * @author xia
 * @since 2024/1/7/21:08
 */
public class SettingView extends Application {
    @Override
    public void start(Stage stage) throws Exception {//难度选择与时间选择
        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);
        Data data = new Data();


        Label label = new Label("请选择挑战难度与挑战时间");
        label.setFont(Font.font("宋体", FontWeight.BOLD, FontPosture.ITALIC, 36));
        //难度选择单选按钮
        ToggleGroup toggleGroup = new ToggleGroup();//处理单选按钮事件，只能选一个
        //创建两个单选按钮
        RadioButton button1 = new RadioButton("简单");
        button1.setToggleGroup(toggleGroup);
        button1.setSelected(true);//默认选择
        button1.setFont(Font.font("宋体", FontWeight.BOLD, FontPosture.ITALIC, 18));
        button1.setUserData("40");

        RadioButton button2 = new RadioButton("中等");
        button2.setToggleGroup(toggleGroup);
        button2.setFont(Font.font("宋体", FontWeight.BOLD, FontPosture.ITALIC, 18));
        button2.setUserData("51");

        RadioButton button3 = new RadioButton("困难");
        button3.setToggleGroup(toggleGroup);
        button3.setFont(Font.font("宋体", FontWeight.BOLD, FontPosture.ITALIC, 18));
        button3.setUserData("64");

        //时间选择按钮
        String[] s = new String[]{"2:00", "5:00", "10:00"};
        ChoiceBox<String> stringChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("2:00", "5:00", "10:00"));
        stringChoiceBox.setStyle("-fx-font-size: 24px;");
        //设置默认值
        stringChoiceBox.setValue("5:00");
        //获取对应的信息
        stringChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println(s[newValue.intValue()]);
                    data.setTime(s[newValue.intValue()]);
                });


        //确定按钮
        Button sure = new Button("确定");
        sure.setMinSize(100, 50);
        sure.setMaxSize(100, 50);
        sure.setFont(Font.font(24));
        sure.setOnAction(event -> {
            Integer degree = Integer.parseInt(toggleGroup.getSelectedToggle().getUserData().toString());
            data.setDegree(degree);
            stage.setUserData(data);
            try {
                new HomePageView().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        vBox.getChildren().addAll(label, button1, button2, button3, stringChoiceBox, sure);
        Scene scene = new Scene(vBox, 700, 700);
        stage.setScene(scene);
        stage.setTitle("设置");
        stage.show();
    }
}
