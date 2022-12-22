package studentmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author WINDOWS 10
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button close;
    
//    DATABASE TOOls
    private Connection connect;
    private PreparedStatement prepare;

    private PreparedStatement stdntPrepare;
    private ResultSet result;

    private ResultSet stdntResult;
    
//    CREATE DATABASE : )
    
    private double x= 0 ;
    private double y= 0;
    
    public void loginAdmin(){

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

//        String stdntSql = "SELECT * FROM user WHERE username = ? and password = ?";
        
        connect = database.connectDb();

        try{
            Alert alert;

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            
            result = prepare.executeQuery();

//            stdntPrepare = connect.prepareStatement(stdntSql);
//            stdntPrepare.setString(3, username.getText());
//            stdntPrepare.setString(4, password.getText());
//            stdntResult = stdntPrepare.executeQuery();


//            text field hooson uguig shalgah
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()) {
                    String userType = result.getString("type");
//                    THEN PROCEED TO DASHBOARD FORM
                    if (userType.equals("ADMIN")){

                        getData.username = username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    //hide login form
                    loginBtn.getScene().getWindow().hide();
                    //dashboard link
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                } else if (userType.equals("USER")) {
                        getData.username = username.getText();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Login!");
                        alert.showAndWait();

                        //hide login form
                        loginBtn.getScene().getWindow().hide();
                        //dashboard link
                        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);

                        stage.setScene(scene);
                        stage.show();
                    }
                }else{
                    // error massage
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void close(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
