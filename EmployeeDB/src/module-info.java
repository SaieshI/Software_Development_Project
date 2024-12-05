/**
 * 
 */
/**
 * 
 */
module EmployeeDB {
	requires java.sql;
	requires javafx.controls;
    requires javafx.fxml;

	exports com.employee.app.gui; 
    exports com.employee; 
}

//javac -d EmployeeDB/bin --module-path EmployeeDB\lib --add-modules javafx.controls,javafx.fxml EmployeeDB\src\com\employee\Main.java EmployeeDB/src/com/employee/model/*.java EmployeeDB/src/com/employee/app/gui/*.java
//javac -d EmployeeDB/bin --module-path EmployeeDB\lib\javafx-sdk-23.0.1\lib --add-modules javafx.controls,javafx.fxml EmployeeDB/src/com/employee/Main.java EmployeeDB/src/com/employee/model/*.java EmployeeDB/src/com/employee/app/gui/*.java