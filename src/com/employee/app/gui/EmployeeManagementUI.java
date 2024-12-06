package com.employee.app.gui;

import java.util.Map;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementUI extends Application {

    private TableView<Employee> tableView;

    @Override
    public void start(Stage primaryStage) {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Top Section
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Table Section
        tableView = new TableView<>();
        setupTableView();

        // Form Section
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        TextField ssnField = new TextField();
        ssnField.setPromptText("Enter SSN");
        TextField jobField = new TextField();
        jobField.setPromptText("Enter Job Title");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Enter Salary");
        TextField departmentField = new TextField();
        departmentField.setPromptText("Enter Department");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("SSN:"), 0, 1);
        form.add(ssnField, 1, 1);
        form.add(new Label("Job Title:"), 0, 2);
        form.add(jobField, 1, 2);
        form.add(new Label("Salary:"), 0, 3);
        form.add(salaryField, 1, 3);
        form.add(new Label("Department:"), 0, 4);
        form.add(departmentField, 1, 4);

        // Buttons Section
        HBox buttonBar = new HBox(10);
        Button addButton = new Button("Add Employee");
        Button updateButton = new Button("Update Employee");
        Button deleteButton = new Button("Delete Employee");
        Button searchButton = new Button("Search Employee");
        Button reportByJobTitleButton = new Button("Report by Job Title");
        Button reportByDepartmentButton = new Button("Report by Department");

        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(
                addButton, updateButton, deleteButton, searchButton,
                reportByJobTitleButton, reportByDepartmentButton
        );


        // Report by Job Title
reportByJobTitleButton.setOnAction(e -> {
    try {
        Map<String, Double> report = employeeDAO.getTotalPayByJobTitle();
        if (report.isEmpty()) {
            showAlert("Report", "No data available for the job title report.");
        } else {
            StringBuilder reportContent = new StringBuilder("Total Pay by Job Title:\n");
            report.forEach((jobTitle, totalPay) -> reportContent.append(String.format("%s: $%.2f\n", jobTitle, totalPay)));
            showAlert("Job Title Report", reportContent.toString());
        }
    } catch (Exception ex) {
        showAlert("Error", "Failed to generate job title report: " + ex.getMessage());
    }
});


// Report by Department
reportByDepartmentButton.setOnAction(e -> {
    try {
        Map<String, Double> report = employeeDAO.getTotalPayByDepartment();
        if (report.isEmpty()) {
            showAlert("Report", "No data available for the department report.");
        } else {
            StringBuilder reportContent = new StringBuilder("Total Pay by Department:\n");
            report.forEach((department, totalPay) -> reportContent.append(String.format("%s: $%.2f\n", department, totalPay)));
            showAlert("Department Report", reportContent.toString());
        }
    } catch (Exception ex) {
        showAlert("Error", "Failed to generate department report: " + ex.getMessage());
    }
});


        // Add Employee functionality
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String ssn = ssnField.getText();
                String jobTitle = jobField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String department = departmentField.getText();

                if (name.isEmpty() || ssn.isEmpty() || jobTitle.isEmpty() || department.isEmpty()) {
                    showAlert("Error", "All fields are required.");
                    return;
                }

                Employee newEmployee = new Employee(name, ssn, jobTitle, salary, department);
                employeeDAO.addEmployee(newEmployee);
                refreshTable(employeeDAO);
                showAlert("Success", "Employee added to the database!");

                // Clear input fields
                nameField.clear();
                ssnField.clear();
                jobField.clear();
                salaryField.clear();
                departmentField.clear();
            } catch (Exception ex) {
                showAlert("Error", "Failed to add employee: " + ex.getMessage());
            }
        });

        // Update Employee functionality
        updateButton.setOnAction(e -> {
            try {
                String ssn = ssnField.getText();
                String jobTitle = jobField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String department = departmentField.getText();
        
                if (ssn.isEmpty() || jobTitle.isEmpty() || department.isEmpty()) {
                    showAlert("Error", "SSN, Job Title, and Department are required to update.");
                    return;
                }
        
                // Perform update
                boolean isUpdated = employeeDAO.updateEmployee(ssn, jobTitle, salary, department);
                if (isUpdated) {
                    refreshTable(employeeDAO); // Refresh the table after update
                    showAlert("Success", "Employee updated successfully!");
                } else {
                    showAlert("Error", "No employee found with the given SSN.");
                }
            } catch (Exception ex) {
                showAlert("Error", "Failed to update employee: " + ex.getMessage());
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                String ssn = ssnField.getText();
                if (ssn.isEmpty()) {
                    showAlert("Error", "SSN is required to delete.");
                    return;
                }
        
                employeeDAO.deleteEmployee(ssn);
                refreshTable(employeeDAO);
                showAlert("Success", "Employee deleted successfully!");
                ssnField.clear();
            } catch (Exception ex) {
                showAlert("Error", "Failed to delete employee: " + ex.getMessage());
            }
        });
        
        

        // Search Employee functionality
        searchButton.setOnAction(e -> {
            try {
                String identifier = ssnField.getText();
                if (identifier.isEmpty()) {
                    showAlert("Error", "SSN or Name is required to search.");
                    return;
                }

                Employee employee = employeeDAO.searchEmployee(identifier);
                if (employee != null) {
                    ObservableList<Employee> searchResult = FXCollections.observableArrayList(employee);
                    tableView.setItems(searchResult);
                    showAlert("Success", "Employee found.");
                } else {
                    showAlert("Error", "No employee found with the given identifier.");
                }
            } catch (Exception ex) {
                showAlert("Error", "Failed to search employee: " + ex.getMessage());
            }
        });

        // Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(titleLabel, tableView, form, buttonBar);

        // Scene
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial Table Load
        refreshTable(employeeDAO);
    }
        //Helper method to Clear Fields
        private void clearFields(TextField... fields) {
            for (TextField field : fields) {
                field.clear(); // Clear the text field
            }
        }

    private void setupTableView() {
        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> ssnColumn = new TableColumn<>("SSN");
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));

        TableColumn<Employee, String> jobTitleColumn = new TableColumn<>("Job Title");
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(ssnColumn);
        tableView.getColumns().add(jobTitleColumn);
        tableView.getColumns().add(salaryColumn);
        tableView.getColumns().add(departmentColumn);
    }

    private void refreshTable(EmployeeDAO employeeDAO) {
        try {
            ObservableList<Employee> employees = FXCollections.observableArrayList(employeeDAO.getAllEmployees());
            tableView.setItems(employees);
        } catch (Exception ex) {
            showAlert("Error", "Failed to refresh table: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
