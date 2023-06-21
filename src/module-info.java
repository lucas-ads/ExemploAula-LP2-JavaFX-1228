module AulaJavaFX1228 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.fxml;
	opens entidades to javafx.base;
}
