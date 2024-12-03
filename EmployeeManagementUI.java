import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Top Section
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Middle Section
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        //extra
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        TextField ssnField = new TextField();
        ssnField.setPromptText("Enter SSN");
        TextField jobField = new TextField();
        jobField.setPromptText("Enter Job Title");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Enter Salary");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("SSN:"), 0, 1);
        form.add(ssnField, 1, 1);
        form.add(new Label("Job Title:"), 0, 2);
        form.add(jobField, 1, 2);
        form.add(new Label("Salary:"), 0, 3);
        form.add(salaryField, 1, 3);

        // Bottom Section
        HBox buttonBar = new HBox(10);
        Button addButton = new Button("Add Employee");
        Button updateButton = new Button("Update Employee");
        Button deleteButton = new Button("Delete Employee");
        Button searchButton = new Button("Search Employee");

        // Mock Actions
        addButton.setOnAction(e -> {
            showAlert("Add Employee", "Employee added successfully!");
            clearFields(nameField, ssnField, jobField, salaryField);
        });
        updateButton.setOnAction(e -> {
            showAlert("Update Employee", "Employee updated successfully!");
            clearFields(nameField, ssnField, jobField, salaryField);
        });
        deleteButton.setOnAction(e -> {
            showAlert("Delete Employee", "Employee deleted successfully!");
            clearFields(nameField, ssnField, jobField, salaryField);
        });
        searchButton.setOnAction(e -> {
            showAlert("Search Employee", "Employee found!");
            clearFields(nameField, ssnField, jobField, salaryField);
        });

        buttonBar.getChildren().addAll(addButton, updateButton, deleteButton, searchButton);

        // Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(titleLabel, form, buttonBar);

        // Scene
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add("styles.css");

        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper Method to Show Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Helper method to Clear Fields
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear(); // Clear the text field
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
