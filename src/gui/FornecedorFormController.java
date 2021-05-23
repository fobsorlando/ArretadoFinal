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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.entities.Fornecedor;
import model.entities.Sexo;
import model.entities.UF;
import model.exceptions.ValidationException;
import model.services.FornecedorService;
import model.services.UFService;

public class FornecedorFormController implements Initializable {

	
	private Fornecedor entidade;
	private FornecedorService service;
	
	private UFService UFService;
	private UF uf;
	
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
	private TextField txtEmail1;
	
	@FXML
	private TextField txtEmail2;
	
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
	private TextField txtDocumento;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private ComboBox<UF> comboBoxUF;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	private ObservableList<UF> obsListUF;

	
	
	public void setFornecedor(Fornecedor entidade) {
		this.entidade = entidade;
	}
	
	public void setFornecedorService (FornecedorService service, UFService UFService) {
		this.service=service;
		this.UFService=UFService;

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
		
	    if   (txtFone1.getText().trim().replace("(","").replace(")","").replace("-","").length() >2 ) {
		    if   (txtFone1.getText().replace("(","").replace(")","").replace("-","").matches("[0-9]+") == false) {
				 exception.addErrors("name", "Digite apenas numeros no TELEFONE 1!");
		    }
	    	if   ((txtFone1.getText().replace("(","").replace(")","").replace("-","").length() != 10 )  &&
	    			(txtFone1.getText().replace("(","").replace(")","").replace("-","").length() != 11 ))  {
	    		exception.addErrors("name", "Digite Telefone 1  Vádido! ");
	    	}
	    }
	    if   (txtFone2.getText().trim().replace("(","").replace(")","").replace("-","").length() >2  ) {
		    if   (txtFone2.getText().replace("(","").replace(")","").replace("-","").matches("[0-9]+") == false) {
				 exception.addErrors("name", "Digite apenas numeros no TELEFONE 2!");
		    }
	    	if   ((txtFone2.getText().replace("(","").replace(")","").replace("-","").length() != 10 )  &&
		    	  (txtFone2.getText().replace("(","").replace(")","").replace("-","").length() != 11 ))  {
				 exception.addErrors("name", "Digite Telefone 2  Vádido! ");
		    }
	    }
	    obj.setNr_cep(txtCep.getText().replace("-","").replace(".",""));

	    
	    obj.setNr_telefone1(txtFone1.getText().replace("(","").replace(")","").replace("-",""));
	    obj.setNr_telefone2(txtFone2.getText().replace("(","").replace(")","").replace("-",""));
    
		
		obj.setNo_fornecedor(txtName.getText());
		obj.setNo_fantasia(txtFantasia.getText());
		obj.setNo_contato(txtContato.getText());
		obj.setNo_email1(txtEmail1.getText());
		obj.setNo_email2(txtEmail2.getText());
		obj.setNo_endereco(txtEndereco.getText());
		obj.setNr_numero(Integer.valueOf(txtNumero.getText()));
		obj.setNo_complemento(txtComplemento.getText());
		obj.setNo_cidade(txtCidade.getText());
		obj.setNo_observacao(txtObservacao.getText());
		obj.setNr_documento(txtDocumento.getText().replace(".", "").replace("/", "").replace("-", ""));
		
	    obj.setUf(comboBoxUF.getValue());

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
		initializeComboBoxUF();

	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		System.out.println("--------------------");
		System.out.println(entidade.getId());
		System.out.println(entidade.getNo_contato());
		System.out.println("--------------------");
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getNo_fornecedor());
		txtFantasia.setText(entidade.getNo_fantasia());
		txtContato.setText(entidade.getNo_contato());
		txtEmail1.setText(entidade.getNo_email1());
		txtEmail2.setText(entidade.getNo_email2());
		txtFone1.setText(Utils.textToFone(entidade.getNr_telefone1()));
		txtFone2.setText(Utils.textToFone(entidade.getNr_telefone2()));
		txtCep.setText(Utils.textToCEP(entidade.getNr_cep()));
		txtEndereco.setText(entidade.getNo_endereco());
		txtNumero.setText(String.valueOf(entidade.getNr_numero()));
		txtComplemento.setText(entidade.getNo_complemento());
		txtCidade.setText(entidade.getNo_cidade());
		txtObservacao.setText(entidade.getNo_observacao());
		txtDocumento.setText(Utils.textToCpfCnpj(entidade.getNr_documento()));
		
		UF ufs = new UF("PI","PIAUI");
		
		if (entidade.getUf() == null) {
			comboBoxUF.setValue(ufs);
		}
		else { 
		    comboBoxUF.setValue(entidade.getUf());
		}
		

	}
	
	public void loadAssociatedObjects() {
		if (UFService ==  null) {
			throw new IllegalStateException("Departamento Nulo");
			
		}
		List<UF> listUF = UFService.findAll();
		obsListUF = FXCollections.observableArrayList(listUF);
		comboBoxUF.setItems(obsListUF);
		
	}
	
	private void setErrorMessages(Map <String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if  (fields.contains("name")) {
 			labelErrorName.setText(errors.get("name"));
			
		}
	}
	private void initializeComboBoxUF() {
		Callback<ListView<UF>, ListCell<UF>> factoryUF = lv -> new ListCell<UF>() {
			@Override
			protected void updateItem(UF item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNo_unidade());
			}
		};
		comboBoxUF.setCellFactory(factoryUF);
		comboBoxUF.setButtonCell(factoryUF.call(null));
		
}
}