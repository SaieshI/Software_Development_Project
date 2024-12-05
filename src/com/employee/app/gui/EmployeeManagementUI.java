package com.employee.app.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the EmployeeDAO instance
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Top Section
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Middle Section
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

        // Add Employee functionality
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String ssn = ssnField.getText();
                String jobTitle = jobField.getText();
                double salary = Double.parseDouble(salaryField.getText());

                if (name.isEmpty() || ssn.isEmpty() || jobTitle.isEmpty()) {
                    showAlert("Error", "All fields except Salary are required.");
                    return;
                }

                Employee newEmployee = new Employee(name, ssn, jobTitle, salary);
                employeeDAO.addEmployee(newEmployee);
                showAlert("Success", "Employee added to the database!");

                // Clear input fields
                nameField.clear();
                ssnField.clear();
                jobField.clear();
                salaryField.clear();
            } catch (Exception ex) {
                showAlert("Error", "Failed to add employee: " + ex.getMessage());
            }
        });

        // Search Employee functionality
        searchButton.setOnAction(e -> {
            try {
                String ssn = ssnField.getText();
                if (ssn.isEmpty()) {
                    showAlert("Error", "SSN is required to search.");
                    return;
                }

                Employee employee = employeeDAO.searchEmployee(ssn);
                if (employee != null) {
                    showAlert("Employee Found", "Name: " + employee.getName() + "\nJob Title: " + employee.getJobTitle());
                } else {
                    showAlert("Not Found", "No employee found with SSN: " + ssn);
                }
            } catch (Exception ex) {
                showAlert("Error", "Failed to search employee: " + ex.getMessage());
            }
        });

        // Update Employee functionality
        updateButton.setOnAction(e -> {
            try {
                String ssn = ssnField.getText();
                String jobTitle = jobField.getText();
                double salary = Double.parseDouble(salaryField.getText());

                if (ssn.isEmpty() || jobTitle.isEmpty()) {
                    showAlert("Error", "SSN and Job Title are required to update.");
                    return;
                }

                employeeDAO.updateEmployee(ssn, jobTitle, salary);
                showAlert("Success", "Employee updated successfully!");
            } catch (Exception ex) {
                showAlert("Error", "Failed to update employee: " + ex.getMessage());
            }
        });

        // Delete Employee functionality
        deleteButton.setOnAction(e -> {
            try {
                String ssn = ssnField.getText();
                if (ssn.isEmpty()) {
                    showAlert("Error", "SSN is required to delete.");
                    return;
                }

                employeeDAO.deleteEmployee(ssn);
                showAlert("Success", "Employee deleted successfully!");
                ssnField.clear();
            } catch (Exception ex) {
                showAlert("Error", "Failed to delete employee: " + ex.getMessage());
            }
        });

        buttonBar.getChildren().addAll(addButton, updateButton, deleteButton, searchButton);

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, form, buttonBar);

        // Scene
        Scene scene = new Scene(layout, 400, 300);
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

    public static void main(String[] args) {
        launch(args);
    }
}
