package ch.zhaw.pm3.aBrain.frontend.factory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MedDetailItem {

    public static HBox getDetailItem(int id, String title, String description, EventHandler<ActionEvent> buttonClickEvent){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("framed-box");
        hBox.setSpacing(10);

        hBox.getChildren().add(0, createTextContainer(title, description));
        hBox.getChildren().add(1, createButton(id, buttonClickEvent));

        return hBox;
    }

    private static VBox createTextContainer(String title, String description){
        Label titleLabel = new Label();
        titleLabel.setText(title);
        titleLabel.getStyleClass().add("framed-box--text");

        Label descriptionLabel = new Label();
        descriptionLabel.setText(description);
        descriptionLabel.getStyleClass().add("framed-box--title");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        vBox.getStyleClass().add("medData__detail_vBox");
        vBox.getChildren().add(0, titleLabel);
        vBox.getChildren().add(1, descriptionLabel);

        return vBox;
    }

    private static Button createButton(int id, EventHandler<ActionEvent> buttonClickEvent){
        Button button = new Button();
        button.setOnAction(buttonClickEvent);
        button.setId(""+id);
        button.getStyleClass().add("edit-button");

        Image buttonIcon;

        try{
            String imagePath = "src/main/resources/ch/zhaw/pm3/aBrain/design/img_elements/button/Edit.png";
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
