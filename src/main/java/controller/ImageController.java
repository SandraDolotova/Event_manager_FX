package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ImageController {
    @FXML
    private Label nameLabel;

    @FXML
    private ImageView img;
/*
    @FXML
    private void click(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(photo);
    }

    private Photo photo;
    private MyListener myListener;

    public void setData(Photo photo, MyListener myListener){
        this.photo = photo;
        this.myListener = myListener;
        nameLabel.setText(photo.getName());
        Image image = new Image(getClass().getResourceAsStream(photo.getImgSrc()));
        img.setImage(image);
    }

 */
}

