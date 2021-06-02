package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.services.ClienteService;
import model.services.SexoService;
import model.services.UFService;

public class ClienteListController implements Initializable, DataChangeListener {

	private ClienteService service;
	private boolean isVenda;

	@FXML
	private TableView<Cliente> tableViewCliente;

	@FXML
	private TableColumn<Cliente, Integer> tableColumnID;

	@FXML
	private TableColumn<Cliente, String> tableColumnName;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnApelido;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnDoc;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnSexo;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnEmail1;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnEmail2;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnFone1;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnFone2;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnCep;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnEndereco;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnNumero;
	
	@FXML
	private TableColumn<Cliente, String> tableColumncomplemento;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnCidade;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnBairro;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnObservacao;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnDocumento;
	
	@FXML
	private TextField  txtFindByNome;
	
	@FXML
	private TableColumn <Cliente, Cliente> tableColumnREMOVE;
	
	@FXML
	private TableColumn <Cliente, Cliente> tableColumnEDIT;

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btPesquisa;

	private ObservableList<Cliente> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Cliente obj = new Cliente(); // criar vazio
		createDialogForm(obj, "/gui/ClienteForm.fxml", parentStage);
	}
	
	@FXML
	public void onBtPesquisaAction(ActionEvent event) {
		if (service == null) {
			throw new IllegalStateException("Serice veio NULLO");
		}

		List<Cliente> list = service.findByNome(txtFindByNome.getText());
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
		initEditButtons(); // Acrescenta botão para alterar
		initRemoveButtons(); // Botão para remover

	}


	public void setFlagVenda(boolean isVenda) {
		this.isVenda = isVenda;
	}
	
	public void setClienteService(ClienteService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("no_cliente"));
		tableColumnApelido.setCellValueFactory(new PropertyValueFactory<>("no_apelido"));
		tableColumnDoc.setCellValueFactory(new PropertyValueFactory<>("nr_documento"));
		tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("no_bairro"));

		Stage stage = (Stage) Main.getMainScene().getWindow();

		// Cliente acompanhar tamanho do menu
		tableViewCliente.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		// testa se veio dados na dependencia service
		if (service == null) {
			throw new IllegalStateException("Serice veio NULLO");
		}
		
		List<Cliente> list = service.findAll();



		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);

		System.out.println("Is Venda " + isVenda);
		if (isVenda == true) {
			initSelButtons();
		}
		else {
			initEditButtons(); // Acrescenta botão para alterar
			initRemoveButtons(); // Botão para remover
		}

	}

	private void createDialogForm(Cliente obj, String absoluteName, Stage parentStage) {
		try {
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// Pegar controldor da tela carregada acima
			ClienteFormController controller = loader.getController();
			controller.setCliente(obj);
			controller.setServices(new ClienteService(), new UFService(), new SexoService()); // Inetando dependencia do servico

			controller.loadAssociatedObjects(); // Carregar associados (ex. departamento)
											// ONDATACHAGED
			controller.subscribeDataChangeListner(this);// Inscrever classe (ela mesma-this) para escutar evento

			controller.updateFormData(); // Carrega os dados do OBJ no formulario

			// novo Stage pq a janela vai ser modal.
			// tambem vai ser uma nova scena
			// uma janela em cima da outra

			Stage dialogStage = new Stage(); // novo palco
			dialogStage.setTitle("Incuir Cliente");
			dialogStage.setScene(new Scene(pane)); // Nova scena, que é o pane feito acima
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			Alerts.showAlert("ERRO IO", "ERRO CARREGA VIEW(E02)", e.getMessage(), AlertType.ERROR);

		}
	}

	@Override
	public void onDataChange() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("Alterar");

			@Override
			protected void updateItem(Cliente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ClienteForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	private void initSelButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("Sel");

			@Override
			protected void updateItem(Venda obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						//event -> createDialogForm(obj, "/gui/VendaForm.fxml", Utils.currentStage(event)));
						event -> setCliente(obj));
			}
		});
	}
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("Exclui");

			@Override
			protected void updateItem(Cliente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}
	
	private void removeEntity(Cliente obj) {
		Optional <ButtonType> result =   Alerts.showConfirmation("Excluí", "Confirma Exclusão?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serice veio NULLO");
			}
			try { 
			 service.remove(obj);
			 updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Erro Excluíndo Cliente", null, e.getMessage(), AlertType.ERROR);
			}
		}
		return ;
	}
}
 
