package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoFormController implements Initializable {

	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorNaame;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	
	@FXML
	public void onBtSalvaAction() {
		System.out.println("Salvei");
	}

	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancelei ");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
		
	}

	private void initializableNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}
}
