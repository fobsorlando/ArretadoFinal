package gui;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.exceptions.ValidationException;
import model.services.ProdutoService;

public class ProdutoFormController implements Initializable {

	
	private Produto entidade;
	private ProdutoService service;
	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private DatePicker  dpDtCriacao;
	
	@FXML
	private TextField txtVlVenda ;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrordpDtCriacao;
	@FXML
	private Label labelErrortxtVlVenda;

	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	
	public void setProduto(Produto entidade) {
		this.entidade = entidade;
	}
	
	public void setProdutoService (ProdutoService service) {
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

	private Produto getFormData() {
		Produto obj = new Produto();
		
		ValidationException exception = new ValidationException("Erro validação");
	
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null     ||
		    txtName.getText().trim().equals("") ||
		    txtName.getText().trim().length() <5 ) {
			exception.addErrors("name", "Digite um texto valido!");
		}
		
		obj.setNo_produto(txtName.getText());

	
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
		Constraints.setTextFieldDouble(txtVlVenda);
		Utils.formatDatePicker(dpDtCriacao, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getNo_produto());
		Locale.setDefault(Locale.US);
		if (entidade.getDth_criacao() != null) {
		    //String data = entidade.getDth_criacao().toLocaleString();


			// Método Orlando
		    String ld = entidade.getDth_criacao().toString();
		    LocalDate localDate = Date.valueOf(ld).toLocalDate();
		    dpDtCriacao.setValue(localDate);
		    
		    // Metodo do professor 
			//dpDtCriacao.setValue(LocalDate.ofInstant( entidade.getDth_criacao().toInstant(), ZoneId.systemDefault()));
		}
		txtVlVenda.setText(String.format("%.2f",entidade.getVl_venda(),ZoneId.systemDefault()));
		
	}
	
	private void setErrorMessages(Map <String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if  (fields.contains("name")) {
 			labelErrorName.setText(errors.get("name"));
			
		}
	}
}
