package xindijia.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 登录进来的主页
 * 显示功能按钮：玩一玩，求解数独
 *
 * @author xia
 * @since 2024/1/6/22:07
 */
public class HomePageView extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        //玩一玩 按钮
        Button play = new Button("玩一玩");
        play.setMaxSize(300, 100);
        play.setMinSize(300, 100);
        play.setFont(Font.font(40));
        play.setOnAction(event -> {//点击事件
            try {
                new PlayView().start(stage);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //求解数独 按钮
        Button solution = new Button("求解数独");
        solution.setMaxSize(300, 100);
        solution.setMinSize(300, 100);
        solution.setFont(Font.font(40));
        solution.setOnAction(event -> {
            try {
                new SolutionView().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //设置 按钮
        Button setting = new Button("设置");
        setting.setMaxSize(300, 100);
        setting.setMinSize(300, 100);
        setting.setFont(Font.font(40));
        setting.setOnAction(event -> {
            try {
                new SettingView().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        vBox.getChildren().addAll(play, solution, setting);
        Scene scene = new Scene(vBox, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}
