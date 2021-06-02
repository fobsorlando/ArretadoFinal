package gui;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Cliente;
import model.entities.Sexo;
import model.entities.UF;
import model.exceptions.ValidationException;
import model.services.ClienteService;
import model.services.SexoService;
import model.services.UFService;

public class ClienteFormController implements Initializable {

	
	private Cliente entidade;
	private ClienteService service;
	
	private UFService UFService;
	private UF uf;
	
	private SexoService sexoService;
	private Sexo sexo;

	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtDoc;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtApelido;
	
	@FXML
	private DatePicker  dpDtNascimento;
	
	@FXML
	private TextField txtSexo ;
	
	@FXML
	private TextField txtEmail1 ;
	
	@FXML
	private TextField txtEmail2 ;
	
	@FXML
	private TextField txtTelefone1 ;
	
	@FXML
	private TextField txtTelefone2;
	
	@FXML
	private TextField txtCep;
	
	
	
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtEndereco;
	
	@FXML
	private TextField txtComplemento;
	
	@FXML
	private TextField txtCidade;
	
	@FXML
	private TextField txtObservacao;
	
	@FXML
	private TextField txtBairro;

	@FXML
	private ComboBox<UF> comboBoxUF;
	@FXML
	private ComboBox<Sexo> comboBoxSexo;
	
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
	
	private ObservableList<UF> obsListUF;
	private ObservableList<Sexo> obsListSexo;

	
	public void setCliente(Cliente entidade) {
		this.entidade = entidade;
		
	}
	
	public void setServices (ClienteService service, UFService UFService,
							 SexoService sexoService)
						      {
	
		this.service=service;
		this.UFService=UFService;
		this.sexoService=sexoService;
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
			System.out.println(e.getMessage());
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

	private Cliente  getFormData() {
		Cliente obj = new Cliente();
		
		ValidationException exception = new ValidationException("Erro validação");
	
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null     ||
		    txtName.getText().trim().equals("") ||
		    txtName.getText().trim().length() <5 ) {
			exception.addErrors("name", "Digite um texto valido!");
		}
		
		obj.setNo_cliente(txtName.getText());
		obj.setNo_apelido(txtApelido.getText());
		
		
		obj.setNr_documento(txtDoc.getText().replace(".", "").replace("/", "").replace("-", ""));
	
		
		if (dpDtNascimento.getValue() == null) {
			exception.addErrors("name", "Data Inválida!");
		} 
		else {
			// metodo orlando de covnerter para sqldate
			LocalDate localDate = dpDtNascimento.getValue();
			Date date = Date.valueOf(localDate);
			obj.setDt_nascimento(date);
		}
//	    if (txtVlVenda.getText() == null     ||
//			txtVlVenda.getText().trim().equals("")) {
//			exception.addErrors("name", "Digite um valor valido!");
//	    }
//	    
	   // obj.setFl_sexo(txtSexo.getText());
	    
	    obj.setNo_email1(txtEmail1.getText());
	    obj.setNo_email2(txtEmail2.getText());
	   	    
	    if   (txtTelefone1.getText().trim().replace("(","").replace(")","").replace("-","").length() >2 ) {
		    if   (txtTelefone1.getText().replace("(","").replace(")","").replace("-","").matches("[0-9]+") == false) {
				 exception.addErrors("name", "Digite apenas numeros no TELEFONE 1!");
		    }
	    	if   ((txtTelefone1.getText().replace("(","").replace(")","").replace("-","").length() != 10 )  &&
	    			(txtTelefone1.getText().replace("(","").replace(")","").replace("-","").length() != 11 ))  {
	    		exception.addErrors("name", "Digite Telefone 1  Vádido! ");
	    	}
	    }
	    if   (txtTelefone2.getText().trim().replace("(","").replace(")","").replace("-","").length() >2  ) {
		    if   (txtTelefone2.getText().replace("(","").replace(")","").replace("-","").matches("[0-9]+") == false) {
				 exception.addErrors("name", "Digite apenas numeros no TELEFONE 2!");
		    }
	    	if   ((txtTelefone2.getText().replace("(","").replace(")","").replace("-","").length() != 10 )  &&
		    	  (txtTelefone2.getText().replace("(","").replace(")","").replace("-","").length() != 11 ))  {
				 exception.addErrors("name", "Digite Telefone 2  Vádido! ");
		    }
	    }
	    obj.setNr_cep(txtCep.getText().replace("-","").replace(".",""));

	    
	    obj.setNr_telefone1(txtTelefone1.getText().replace("(","").replace(")","").replace("-",""));
	    obj.setNr_telefone2(txtTelefone2.getText().replace("(","").replace(")","").replace("-",""));
    
	    if   (txtCep.getText().replace("-","").replace(".","").matches("[0-9]+") == false) {
			 exception.addErrors("name", "Digite apenas numeros no CEP!");
	    }
	    obj.setNr_cep(txtCep.getText().replace("-","").replace(".",""));
	    obj.setNo_endereco(txtEndereco.getText());
	    obj.setNr_numero(Utils.tryParseToInt(txtNumero.getText()));
	    obj.setNo_complemento(txtComplemento.getText());
	    obj.setNo_cidade(txtCidade.getText());
	    obj.setNo_bairro(txtBairro.getText());
	    
	    obj.setUF(comboBoxUF.getValue());
	    obj.setSexo(comboBoxSexo.getValue());
	    
	    System.out.println("Sexo " + comboBoxSexo.getValue());
	    
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
		Constraints.setTextFieldMaxLength(txtName, 100);
		Constraints.setTextFieldMaxLength(txtApelido, 100);
		Constraints.setTextFieldMaxLength(txtTelefone1, 14);
		Constraints.setTextFieldMaxLength(txtTelefone1, 14);
		Constraints.setTextFieldMaxLength(txtCep, 10);
//		Constraints.setTextFieldMaxLength(txtEan, 13);
//		Constraints.setTextFieldDouble(txtVlVenda);
//		Constraints.setTextFieldDouble(txtVlCusto);
		//Utils.formatDatePicker(dpDtCriacao, "dd/MM/yyyy");
		
		
		initializeComboBoxUF();
		initializeComboBoxSexo();


		
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getNo_cliente());
		txtApelido.setText(entidade.getNo_apelido());
		txtDoc.setText(Utils.textToCpfCnpj(entidade.getNr_documento()));
		
		Locale.setDefault(Locale.US);
		if (entidade.getDt_nascimento() != null) {
		    String data = entidade.getDt_nascimento().toLocaleString();

			// Método Orlando	
		    String ld = entidade.getDt_nascimento().toString();
		    LocalDate localDate = Date.valueOf(ld).toLocalDate();
		    dpDtNascimento.setValue(localDate);
		    
		    // Metodo do professor 
			//dpDtCriacao.setValue(LocalDate.ofInstant( entidade.getDth_criacao().toInstant(), ZoneId.systemDefault()));
		}
		//txtVlVenda.setText(String.format("%.2f",entidade.getVl_venda(),ZoneId.systemDefault()));
		//txtVlCusto.setText(String.format("%.2f",entidade.getVl_custo(),ZoneId.systemDefault()));

	    // Format máscara do CEP
		txtCep.setText(Utils.textToCEP(entidade.getNr_cep()));
		
		UF ufs = new UF("PI","PIAUI");
	
		if (entidade.getUF() == null) {
			comboBoxUF.setValue(ufs);
		}
		else { 
		    comboBoxUF.setValue(entidade.getUF());
		}
		
		if (entidade.getSexo() == null) {
			comboBoxSexo.getSelectionModel().selectFirst();
		}
		else { 
		    comboBoxSexo.setValue(entidade.getSexo());
		}
		
		//txtSexo.setText(entidade.getFl_sexo());
		txtEmail1.setText(entidade.getNo_email1());
		txtEmail2.setText(entidade.getNo_email2());
		
		txtTelefone1.setText(Utils.textToFone(entidade.getNr_telefone1()));
		txtTelefone2.setText(Utils.textToFone(entidade.getNr_telefone2()));
		
		
//		txtCep.setText(entidade.getNr_cep());
		txtEndereco.setText(entidade.getNo_endereco());
		txtNumero.setText(String.valueOf(entidade.getNr_numero()));
		txtComplemento.setText(entidade.getNo_complemento());
		txtCidade.setText(entidade.getNo_cidade());
		txtBairro.setText(entidade.getNo_bairro());
		
		txtObservacao.setText(entidade.getNo_observacao());
		
		
		
		
	}
	
	public void loadAssociatedObjects() {
		if (UFService ==  null) {
			throw new IllegalStateException("Departamento Nulo");
			
		}
		List<UF> listUF = UFService.findAll();
		obsListUF = FXCollections.observableArrayList(listUF);
		comboBoxUF.setItems(obsListUF);
		
		if (sexoService ==  null) {
			throw new IllegalStateException("Sexo Nulo");
			
		}
		List<Sexo> listSexo = sexoService.findAll();
		obsListSexo = FXCollections.observableArrayList(listSexo);
		comboBoxSexo.setItems(obsListSexo);
	
	}
	
	private void setErrorMessages(Map <String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		/*
		if  (fields.contains("name")) {
 			labelErrorName.setText(errors.get("name"));
		}
		if  (fields.contains("VlVenda")) {
 			labelErrorName.setText(errors.get("VlVenda"));
		}
		if  (fields.contains("DtCriacao")) {
 			labelErrorName.setText(errors.get("DtCriacao"));
		}
		*/
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
	private void initializeComboBoxSexo() {
		Callback<ListView<Sexo>, ListCell<Sexo>> factorySexo = lv -> new ListCell<Sexo>() {
			@Override
			protected void updateItem(Sexo item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNo_sexo());
			}
		};
		comboBoxSexo.setCellFactory(factorySexo);
		comboBoxSexo.setButtonCell(factorySexo.call(null));
		
}

}

