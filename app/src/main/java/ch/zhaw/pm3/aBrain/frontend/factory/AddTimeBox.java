package ch.zhaw.pm3.aBrain.frontend.factory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddTimeBox {
    public static HBox getDetailItem(int id, String title, String description, EventHandler<ActionEvent> buttonClickEvent, EventHandler<? super KeyEvent> textChangeEvent){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("framed-box");
        hBox.setSpacing(10);

        Button button = AddTimeBox.createButton();
        button.setOnAction(buttonClickEvent);
        button.setId(""+id);

        hBox.getChildren().add(createTextContainer(id, title, description, textChangeEvent));
        hBox.getChildren().add(1, button);

        return hBox;
    }

    private static VBox createTextContainer(int id, String title, String time, EventHandler<? super KeyEvent> textChangeEvent){
        Label titleLabel = new Label();
        titleLabel.setText(title);
        titleLabel.getStyleClass().add("framed-box--text");

        HBox timebox = new HBox();

        TextField timeField = new TextField();
        timeField.setText(time);
        timeField.getStyleClass().add("medData__inputBox");
        timeField.setOnKeyTyped(textChangeEvent);
        timeField.setId("" + id);

        Label unitLabel = new Label();
        unitLabel.setText("  1 Einheit(en)");
        unitLabel.getStyleClass().add("medData__TimeBoxLabel");

        timebox.getChildren().add(timeField);
        timebox.getChildren().add(unitLabel);
        timebox.setAlignment(Pos.CENTER_LEFT);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        vBox.getStyleClass().add("medData__detail_vBox");
        vBox.getChildren().add(0, titleLabel);
        vBox.getChildren().add(1, timebox);

        return vBox;
    }
    private static Button createButton(){
        Button button = new Button();
        button.getStyleClass().add("edit-button");

        Image buttonIcon;

        try{
            String imagePath = "src/main/resources/ch/zhaw/pm3/aBrain/design/img_elements/button/Trash.png";
            buttonIcon = ImageLoader.getImageFromPath(imagePath);
        }catch (Exception e){
            //TODO: add Logger
            buttonIcon = null;
        }

        ImageView imageView = new ImageView(buttonIcon);
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.CENTER);

        return button;
    }
}
