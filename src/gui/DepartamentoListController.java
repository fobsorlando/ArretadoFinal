package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoListController implements Initializable{
	
	private DepartamentoService service;
	
			
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnID;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnName;

	@FXML
	private Button btNovo;
	
	private ObservableList<Departamento> obsList;
	
	@FXML
	public void onBtNovoAction() {
		System.out.println("Novo");
	}

	public void setDepartamentoService(DepartamentoService service) {
		this.service=service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new   PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("no_departamento"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		// Departamento acompanhar tamanho do menu
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		// testa se veio dados na dependencia service
		if  (service == null) {
			throw new IllegalStateException("Serice veio NULLO");
		}
		
		List<Departamento> list = service.findAll();
		obsList  = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
		
	}

}
