package mvc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;

import org.apache.commons.mail.SimpleEmail;
import javafx.concurrent.*;

public class Controller implements Initializable{


    @FXML
    private TextField Puerto;

    @FXML
    private TextField asunto;

    @FXML
    private Button cerrarButton;

    @FXML
    private CheckBox check;

    @FXML
    private TextField destinatario;

    @FXML
    private Button enviarButton;

    @FXML
    private GridPane gridPaneEmail;

    @FXML
    private TextArea mensaje;

    @FXML
    private PasswordField password;

    @FXML
    private TextField remitente;

    @FXML
    private TextField smtp;

    @FXML
    private Button vaciarButton;

    @FXML
    private BorderPane view;


    public Controller() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view.fxml"));
		loader.setController(this);
		loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		enviarButton.setOnAction(e->onEnviarButtonAction());
		vaciarButton.setOnAction(e->onVaciarButtonAction());
		cerrarButton.setOnAction(e->onCerrarButtonAction());
		
	}
	
	 
	   private void onCerrarButtonAction() {
		   Stage escenario = (Stage)cerrarButton.getScene().getWindow();
	    	escenario.close();
	    }

	    
	   private void onEnviarButtonAction() {
		   
			   Email email = new SimpleEmail();
			   email.setHostName(smtp.getText());
			   email.setSmtpPort(Integer.parseInt(Puerto.getText()));
			   email.setAuthenticator(new DefaultAuthenticator(remitente.getText(), password.getText()));
			   if(check.isSelected()) {
				   email.setSSLOnConnect(true);
			   } else {
				   email.setSSLOnConnect(false);
			   }
			   
			   
			   Task<String> enviarEmailTask = new Task<String>() {

				@Override
				protected String call() throws Exception {
					 email.setFrom(remitente.getText());
					   email.setSubject(asunto.getText());
					   email.setMsg(mensaje.getText());
					   email.addTo(destinatario.getText());
					   return email.send();
				}
				   
				   
				   
			   };
			  
			   enviarEmailTask.setOnSucceeded(event -> {
				   Alert alert = new Alert(AlertType.INFORMATION);
				   alert.setTitle("Mensaje enviado");
				   alert.setContentText("Mensaje enviado con éxito a 'fran.profe.icm@gmail.com'");
				   alert.showAndWait();
			   });
			  
			   

			   enviarEmailTask.setOnFailed(event -> {
			   Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("No se pudo enviar el email.");
				alert.showAndWait();
			   });
	   
	   }

	   
	   private void onVaciarButtonAction() {

		   smtp.setText("");
		   Puerto.setText("");
		   remitente.setText("");
		   password.setText("");
		   asunto.setText("");
		   destinatario.setText("");
		   mensaje.setText("");
		   check.selectedProperty().set(false);
		   
	    }
	   
	   public BorderPane getView() {
		return view;
		   
	   }
}
