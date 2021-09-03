package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GalleryController extends ViewController {

    @FXML
    private VBox chosenPhotoCard;

    @FXML
    private Label photoNameLabel;

    @FXML
    private ImageView photoImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Button backButton;

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


/*


    private List<Photo> photos = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<Photo> getData() {
        List<Photo> photos = new ArrayList<>();
        Photo photo;

        photo = new Photo();
        photo.setName("Just Merried");
        photo.setImgSrc("@../images/wedding.png");
        photos.add(photo);


        return photos;
    }
    private void setChosenPhoto(Photo photo){
        photoNameLabel.setText(photo.getName());
        image = new Image(getClass().getResourceAsStream(photo.getImgSrc()));
        photoImg.setImage(image);

    }


    public void initialize(URL location, ResourceBundle resources) {
        photos.addAll(getData());
        if(photos.size() > 0){
            setChosenPhoto(photos.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Photo photo) {
                    setChosenPhoto(photo);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for(int i=0; i<photos.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/image.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ImageController itemController = fxmlLoader.getController();
                itemController.setData(photos.get(i), myListener);

                if (column == 1){
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child, column, row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/



}
