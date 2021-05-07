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
import model.entities.Departamento;
import model.entities.Fornecedor;
import model.entities.Grupo;
import model.entities.Produto;
import model.entities.Secao;
import model.entities.SubGrupo;
import model.exceptions.ValidationException;
import model.services.DepartamentoService;
import model.services.FornecedorService;
import model.services.GrupoService;
import model.services.ProdutoService;
import model.services.SecaoService;
import model.services.SubGrupoService;

public class ProdutoFormController implements Initializable {

	
	private Produto entidade;
	private ProdutoService service;
	private DepartamentoService departamentoService;
	private SecaoService secaoService;
	private GrupoService grupoService;
	private SubGrupoService subGrupoService;
	private FornecedorService fornecedorService;

	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEan;
	
	@FXML
	private DatePicker  dpDtCriacao;
	
	@FXML
	private TextField txtVlVenda ;
	
	@FXML
	private TextField txtVlCusto ;

	@FXML
	private ComboBox<Departamento> comboBoxDepartamento;
	
	@FXML
	private ComboBox<Secao> comboBoxSecao;
	
	@FXML
	private ComboBox<Grupo> comboBoxGrupo;
	
	@FXML
	private ComboBox<SubGrupo> comboBoxSubGrupo;
	
	@FXML
	private ComboBox<Fornecedor> comboBoxFornecedor;
	
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
	
	private ObservableList<Departamento> obsListDep;
	private ObservableList<Secao> obsListSec;
	private ObservableList<Grupo> obsListGrp;
	private ObservableList<SubGrupo> obsListSubG;
	private ObservableList<Fornecedor> obsListForn;

	
	
	public void setProduto(Produto entidade) {
		this.entidade = entidade;
		
	}
	
	public void setServices (ProdutoService service, DepartamentoService departamentoService,
						     SecaoService secaoService, GrupoService grupoService,  
						     SubGrupoService subGrupoService, FornecedorService fornecedorService) {
	
		this.service=service;
		this.departamentoService=departamentoService;
		this.secaoService=secaoService;
		this.grupoService=grupoService;
		this.subGrupoService=subGrupoService;
		this.fornecedorService=fornecedorService;
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

	private Produto  getFormData() {
		Produto obj = new Produto();
		
		ValidationException exception = new ValidationException("Erro validação");
	
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null     ||
		    txtName.getText().trim().equals("") ||
		    txtName.getText().trim().length() <5 ) {
			exception.addErrors("name", "Digite um texto valido!");
		}
		
		obj.setNo_produto(txtName.getText());
		obj.setCd_ean13(Utils.tryParseToLong(txtEan.getText()));
	
		
//		if (dpDtCriacao.getValue() == null) {
//			exception.addErrors("name", "Data Inválida!");
//		} 
//		else {
//			// metodo orlando de covnerter para sqldate
//			LocalDate localDate = dpDtCriacao.getValue();
//			Date date = Date.valueOf(localDate);
//			obj.setDth_criacao(date);
//		}
	    if (txtVlVenda.getText() == null     ||
			txtVlVenda.getText().trim().equals("")) {
			exception.addErrors("name", "Digite um valor valido!");
	    }
	    
	    obj.setVl_venda(Utils.tryParseToDouble(txtVlVenda.getText()));
	    obj.setVl_custo(Utils.tryParseToDouble(txtVlCusto.getText()));

	    obj.setDepartamento(comboBoxDepartamento.getValue());
	    obj.setSecao(comboBoxSecao.getValue());
	    obj.setGrupo(comboBoxGrupo.getValue());
	    obj.setSubGrupo(comboBoxSubGrupo.getValue());
	    obj.setFornecedor(comboBoxFornecedor.getValue());

	    	
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
		Constraints.setTextFieldMaxLength(txtEan, 13);
		Constraints.setTextFieldDouble(txtVlVenda);
		Constraints.setTextFieldDouble(txtVlCusto);
		//Utils.formatDatePicker(dpDtCriacao, "dd/MM/yyyy");
		
		
		initializeComboBoxDepartment();
		initializeComboBoxSecao();
		initializeComboBoxGrupo();
		initializeComboBoxSubGrupo();
		initializeComboBoxFornecedor();
		
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getNo_produto());
		txtEan.setText(String.valueOf(entidade.getCd_ean13()));
		Locale.setDefault(Locale.US);
		if (entidade.getDth_criacao() != null) {
		    //String data = entidade.getDth_criacao().toLocaleString();


			// Método Orlando	
//		    String ld = entidade.getDth_criacao().toString();
//		    LocalDate localDate = Date.valueOf(ld).toLocalDate();
//		    dpDtCriacao.setValue(localDate);
		    
		    // Metodo do professor 
			//dpDtCriacao.setValue(LocalDate.ofInstant( entidade.getDth_criacao().toInstant(), ZoneId.systemDefault()));
		}
		txtVlVenda.setText(String.format("%.2f",entidade.getVl_venda(),ZoneId.systemDefault()));
		txtVlCusto.setText(String.format("%.2f",entidade.getVl_custo(),ZoneId.systemDefault()));

		if (entidade.getDepartamento() == null) {
			comboBoxDepartamento.getSelectionModel().selectFirst();
		}
		else { 
		    comboBoxDepartamento.setValue(entidade.getDepartamento());
		}
		
		
		if (entidade.getSecao() == null) {
			comboBoxSecao.getSelectionModel().selectFirst();
		}
		else { 
		    comboBoxSecao.setValue(entidade.getSecao());
		}
		
		if (entidade.getGrupo() == null) {
			comboBoxGrupo.getSelectionModel().selectFirst();
		}
		else { 
		    comboBoxGrupo.setValue(entidade.getGrupo());
		}
		if (entidade.getSubGrupo() == null) {
			comboBoxSubGrupo.getSelectionModel().selectFirst();
		}
		else { 
		    comboBoxSubGrupo.setValue(entidade.getSubGrupo());
		}
		if (entidade.getFornecedor() == null) {
			comboBoxFornecedor.getSelectionModel().selectFirst();
		}
		else { 
			System.out.println(entidade.getFornecedor());
		    comboBoxFornecedor.setValue(entidade.getFornecedor());
		}
	}
	
	public void loadAssociatedObjects() {
		if (departamentoService ==  null) {
			throw new IllegalStateException("Departamento Nulo");
			
		}
		List<Departamento> listDep = departamentoService.findAll();
		obsListDep = FXCollections.observableArrayList(listDep);
		comboBoxDepartamento.setItems(obsListDep);
		
		if (secaoService ==  null) {
			throw new IllegalStateException("Secao Nulo");
			
		}
		
		List<Secao> listSec = secaoService.findAll();
		obsListSec = FXCollections.observableArrayList(listSec);
		comboBoxSecao.setItems(obsListSec);
		
		
		List<Grupo> listGrp = grupoService.findAll();
		obsListGrp = FXCollections.observableArrayList(listGrp);
		comboBoxGrupo.setItems(obsListGrp);
		
		List<SubGrupo> listSubG = subGrupoService.findAll();
		obsListSubG = FXCollections.observableArrayList(listSubG);
		comboBoxSubGrupo.setItems(obsListSubG);
		
		List<Fornecedor> listForn = fornecedorService.findAll();
		obsListForn = FXCollections.observableArrayList(listForn);
		comboBoxFornecedor.setItems(obsListForn);
		
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
	
	private void initializeComboBoxDepartment() {
			Callback<ListView<Departamento>, ListCell<Departamento>> factoryDep = lv -> new ListCell<Departamento>() {
				@Override
				protected void updateItem(Departamento item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getNo_departamento());
				}
			};
			comboBoxDepartamento.setCellFactory(factoryDep);
			comboBoxDepartamento.setButtonCell(factoryDep.call(null));
			
	}
	
	private void initializeComboBoxSecao() {
			
			Callback<ListView<Secao>, ListCell<Secao>> factorySec = lv -> new ListCell<Secao>() {
				@Override
				protected void updateItem(Secao item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getNo_secao());
				}
			};
			
			comboBoxSecao.setCellFactory(factorySec);
			comboBoxSecao.setButtonCell(factorySec.call(null));
	}
	private void initializeComboBoxGrupo() {

			Callback<ListView<Grupo>, ListCell<Grupo>> factoryGrp = lv -> new ListCell<Grupo>() {
				@Override
				protected void updateItem(Grupo item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getNo_grupo());
				}
			};
			comboBoxGrupo.setCellFactory(factoryGrp);
			comboBoxGrupo.setButtonCell(factoryGrp.call(null));
	}	

	private void initializeComboBoxSubGrupo() {
			Callback<ListView<SubGrupo>, ListCell<SubGrupo>> factorySubG = lv -> new ListCell<SubGrupo>() {
				@Override
				protected void updateItem(SubGrupo item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getNo_subGrupo());
				}
			};
			comboBoxSubGrupo.setCellFactory(factorySubG);
			comboBoxSubGrupo.setButtonCell(factorySubG.call(null));
	}
	private void initializeComboBoxFornecedor() {
			Callback<ListView<Fornecedor>, ListCell<Fornecedor>> factoryForn = lv -> new ListCell<Fornecedor>() {
				@Override
				protected void updateItem(Fornecedor item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getNo_fornecedor());
				}
			};
			comboBoxFornecedor.setCellFactory(factoryForn);
			comboBoxFornecedor.setButtonCell(factoryForn.call(null));
			
	}

	
}
