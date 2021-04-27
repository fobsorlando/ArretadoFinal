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
import model.entities.Fornecedor;
import model.exceptions.ValidationException;
import model.services.FornecedorService;

public class FornecedorFormController implements Initializable {

	
	private Fornecedor entidade;
	private FornecedorService service;
	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtFantasia;
	
	@FXML
	private TextField txtContato;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtFone1;
	
	@FXML
	private TextField txtFone2;
	
	@FXML
	private TextField txtCep;
	
	@FXML
	private TextField txtEndereco;
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtComplemento;
	
	@FXML
	private TextField txtCidade;
	
	@FXML
	private TextField txtUF;
	
	@FXML
	private TextField txtObservacao;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	
	public void setFornecedor(Fornecedor entidade) {
		this.entidade = entidade;
	}
	
	public void setFornecedorService (FornecedorService service) {
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

	private Fornecedor getFormData() {
		Fornecedor obj = new Fornecedor();
		
		ValidationException exception = new ValidationException("Erro validação");
	
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null     ||
		    txtName.getText().trim().equals("") ||
		    txtName.getText().trim().length() <5 ) {
			exception.addErrors("name", "Digite um texto valido!");
		}
		
		obj.setNo_fornecedor(txtName.getText());
		obj.setNo_fantasia(txtFantasia.getText());
		obj.setNo_contato(txtContato.getText());
		obj.setNo_email(txtEmail.getText());
		obj.setNr_telefone1(txtFone1.getText());
		obj.setNr_telefone2(txtFone2.getText());
		obj.setNr_cep(txtCep.getText());
		obj.setNo_endereco(txtEndereco.getText());
		obj.setNr_numero(Integer.valueOf(txtNumero.getText()));
		obj.setNo_complemento(txtComplemento.getText());
		obj.setNo_cidade(txtCidade.getText());
		obj.setSg_uf(txtUF.getText());
		obj.setNo_observacao(txtObservacao.getText());
		
	
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
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getNo_fornecedor());
		txtFantasia.setText(entidade.getNo_fantasia());
		txtContato.setText(entidade.getNo_contato());
		txtEmail.setText(entidade.getNo_email());
		txtFone1.setText(entidade.getNr_telefone1());
		txtFone2.setText(entidade.getNr_telefone2());
		txtCep.setText(entidade.getNr_cep());
		txtEndereco.setText(entidade.getNo_endereco());
		txtNumero.setText(String.valueOf(entidade.getNr_numero()));
		txtComplemento.setText(entidade.getNo_complemento());
		txtCidade.setText(entidade.getNo_cidade());
		txtUF.setText(entidade.getSg_uf());
		txtObservacao.setText(entidade.getNo_observacao());
	}
	
	private void setErrorMessages(Map <String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if  (fields.contains("name")) {
 			labelErrorName.setText(errors.get("name"));
			
		}
	}
}