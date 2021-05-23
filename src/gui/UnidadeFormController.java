package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Unidade;
import model.exceptions.ValidationException;
import model.services.UnidadeService;

public class UnidadeFormController implements Initializable {

	
	private Unidade entidade;
	private UnidadeService service;
	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtUnidade;
	
	@FXML
	private TextField txtName;
	

	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	
	public void setUnidade(Unidade entidade) {
		this.entidade = entidade;
	}
	
	public void setUnidadeService (UnidadeService service) {
		this.service=service;
	}
	
	public void subscribeDataChangeListner(DataChangeListener listener) {
		dataChangeListners.add(listener);
	}
	
	@FXML
	public void onBtSalvaAction(ActionEvent event) {
		if (entidade == null) {
			throw new IllegalStateException("Entidade está null");
		}
		if (service == null) {
			throw new IllegalStateException("Servico está null");
		}
		try {
			entidade = getFormData();
			
			service.saveOrUpdate(entidade);
			notifyDatachangeListeners();
			Utils.currentStage(event).close();
			
		}
		catch (DbException e) {
			Alerts.showAlert("Erro Salvando Ojbeto", null, e.getMessage(), AlertType.ERROR);
		}
		catch (ValidationException e) {
		   setErrorMessages(e.getErrors());
		}
		
	}

	private void notifyDatachangeListeners() {
		
		for (DataChangeListener listener: dataChangeListners) {
			 listener.onDataChange();
		}
		
	}

	private Unidade getFormData() {
		Unidade obj = new Unidade();
		
		ValidationException exception = new ValidationException("Erro validação");
	
		obj.setSg_unidade(txtUnidade.getText());
		
		if (txtName.getText() == null     ||
		    txtName.getText().trim().equals("") ||
		    txtName.getText().trim().length() <5 ) {
			exception.addErrors("name", "Digite um texto valido!");
		}
		
		obj.setNo_unidade(txtName.getText());
		

	
		if  (exception.getErrors().size() > 0 ) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
		
	}

	private void initializableNodes() {
		Constraints.setTextFieldMaxLength(txtUnidade,10);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		txtUnidade.setText(entidade.getSg_unidade());
		txtName.setText(entidade.getNo_unidade());
		
	}
	
	private void setErrorMessages(Map <String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if  (fields.contains("name")) {
 			labelErrorName.setText(errors.get("name"));
			
		}
	}
}