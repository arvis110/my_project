package studentmanagementsystem;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class clientController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private AnchorPane gradeCourse_form;

    @FXML
    private Button gradesOfCourse_btn;

    @FXML
    private TableColumn<?, ?> homeCourse_col_course;

    @FXML
    private TableColumn<?, ?> homeCourse_col_degree;

    @FXML
    private TableColumn<?, ?> homeCourse_col_description;

    @FXML
    private TableView<courseData> homeCourse_tableView;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private Label home_totalFemale;

    @FXML
    private Label home_totalMale;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_course;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_final;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_firstSem;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_secondSem;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_studentNum;

    @FXML
    private TableColumn<studentData, String> studentGrade_col_year;

    @FXML
    private TextField studentGrade_search;

    @FXML
    private TableView<studentData> studentGrade_tableView;
    @FXML
    private TableView<studentData> studentInformation_tableView;
    @FXML
    private Button studentInformation_btn;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_birthDate;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_course;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_firstName;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_gender;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_lastName;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_status;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_studentNum;

    @FXML
    private TableColumn<studentData, String> studentInformation_col_year;

    @FXML
    private AnchorPane studentInformation_form;

    @FXML
    private ImageView studentInformation_imageView;

    @FXML
    private TextField studentInformation_search;

    @FXML
    private AnchorPane student_form;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    public ObservableList<studentData> studentInformationListData() {

        ObservableList<studentData> listStudents = FXCollections.observableArrayList();

        String sql = "SELECT * FROM student";

        connect = database.connectDb();

        try {
            studentData studentD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentD = new studentData(result.getInt("studentNum"),
                        result.getString("year"),
                        result.getString("course"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getDate("birth"),
                        result.getString("status"),
                        result.getString("image"));

                listStudents.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }
    private ObservableList<studentData> studentInformationListD;
    public void studentInformationShowListData() {
        studentInformationListD = studentInformationListData();

        studentInformation_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        studentInformation_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentInformation_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentInformation_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentInformation_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentInformation_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentInformation_col_birthDate.setCellValueFactory(new PropertyValueFactory<>("birth"));
        studentInformation_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        studentInformation_tableView.setItems(studentInformationListD);

    }

    public void studentInformationSelect() {

        studentData studentD = studentInformation_tableView.getSelectionModel().getSelectedItem();
        int num = studentInformation_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        String uri = "file:" + studentD.getImage();

        image = new Image(uri, 137, 173, false, true);
        studentInformation_imageView.setImage(image);

        getData.path = studentD.getImage();

    }

    public ObservableList<studentData> studentGradesListData() {

        ObservableList<studentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM student_grade";

        connect = database.connectDb();

        try {
            studentData studentD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentD = new studentData(result.getInt("studentNum"),
                        result.getString("year"),
                        result.getString("course"),
                        result.getDouble("first_sem"),
                        result.getDouble("second_sem"),
                        result.getDouble("final"));

                listData.add(studentD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<studentData> studentGradesList;

    public void studentGradesShowListData() {
        studentGradesList = studentGradesListData();

        studentGrade_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        studentGrade_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentGrade_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentGrade_col_firstSem.setCellValueFactory(new PropertyValueFactory<>("firstSem"));
        studentGrade_col_secondSem.setCellValueFactory(new PropertyValueFactory<>("secondSem"));
        studentGrade_col_final.setCellValueFactory(new PropertyValueFactory<>("finals"));
        studentGrade_tableView.setItems(studentGradesList);

    }

    public ObservableList<courseData> availableCourseListData() {

        ObservableList<courseData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM course";

        connect = database.connectDb();

        try {
            courseData courseD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                courseD = new courseData(result.getString("course"),
                        result.getString("description"),
                        result.getString("degree"));

                listData.add(courseD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<courseData> homeCourseList;

    public void homeCourseShowListData() {
        homeCourseList = availableCourseListData();

        homeCourse_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        homeCourse_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        homeCourse_col_degree.setCellValueFactory(new PropertyValueFactory<>("degree"));

        homeCourse_tableView.setItems(homeCourseList);

    }

    public void studentGradesSearch() {

        FilteredList<studentData> filter = new FilteredList<>(studentGradesList, e -> true);

        studentGrade_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateStudentData -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateStudentData.getStudentNum().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getYear().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getCourse().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getFirstSem().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getSecondSem().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getFinals().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<studentData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(studentGrade_tableView.comparatorProperty());
        studentGrade_tableView.setItems(sortList);

    }

    public void studentInformationSearch() {

        FilteredList<studentData> filter = new FilteredList<>(studentInformationListD, e -> true);

        studentInformation_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateStudentData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateStudentData.getStudentNum().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getYear().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getCourse().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getBirth().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<studentData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(studentInformation_tableView.comparatorProperty());
        studentInformation_tableView.setItems(sortList);

    }

    private double x = 0;
    private double y = 0;
    public void switchForm(ActionEvent event){
        if(event.getSource() == home_btn){
            home_form.setVisible(true);
            studentInformation_form.setVisible(false);
            gradeCourse_form.setVisible(false);
            homeCourseShowListData();

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            studentInformation_btn.setStyle("-fx-background-color:transparent");
            gradesOfCourse_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == studentInformation_btn) {
            home_form.setVisible(false);
            studentInformation_form.setVisible(true);
            gradeCourse_form.setVisible(false);
            studentInformationShowListData();
            studentInformationSearch();

            studentInformation_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            gradesOfCourse_btn.setStyle("-fx-background-color:transparent");

        }else if (event.getSource() == gradesOfCourse_btn) {
            home_form.setVisible(false);
            studentInformation_form.setVisible(false);
            gradeCourse_form.setVisible(true );
            studentGradesShowListData();
            studentGradesSearch();

            gradesOfCourse_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            studentInformation_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
        }
    }

    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want  to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml "));
                Stage stage = new Stage();
                Scene scene = new Scene(root);


                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }else{
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)student_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
