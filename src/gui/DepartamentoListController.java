package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoListController implements Initializable, DataChangeListener {

	private DepartamentoService service;

	@FXML
	private TableView<Departamento> tableViewDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> tableColumnID;

	@FXML
	private TableColumn<Departamento, String> tableColumnName;
	
	@FXML
	private TableColumn <Departamento, Departamento > tableColumnEDIT;

	@FXML
	private Button btNovo;

	private ObservableList<Departamento> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Departamento obj = new Departamento(); // criar vazio
		createDialogForm(obj, "/gui/DepartamentoForm.fxml", parentStage);
	}

	public void setDepartamentoService(DepartamentoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("no_departamento"));

		Stage stage = (Stage) Main.getMainScene().getWindow();

		// Departamento acompanhar tamanho do menu
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		// testa se veio dados na dependencia service
		if (service == null) {
			throw new IllegalStateException("Serice veio NULLO");
		}

		List<Departamento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
		initEditButtons(); // Acrescenta botão para alterar

	}

	private void createDialogForm(Departamento obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// Pegar controldor da tela carregada acima
			DepartamentoFormController controller = loader.getController();
			controller.setDepartamento(obj);
			controller.setDepartamentoService(new DepartamentoService()); // Inetando dependencia do servico
			controller.subscribeDataChangeListner(this);// Inscrever classe (ela mesma-this) para escutar evento
														// ONDATACHAGED
			controller.updateFormData(); // Carrega os dados do OBJ no formulario

			// novo Stage pq a janela vai ser modal.
			// tambem vai ser uma nova scena
			// uma janela em cima da outra

			Stage dialogStage = new Stage(); // novo palco
			dialogStage.setTitle("Incuir Departamento");
			dialogStage.setScene(new Scene(pane)); // Nova scena, que é o pane feito acima
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("ERRO IO", "ERRO CARREGA VIEW(E02)", e.getMessage(), AlertType.ERROR);

		}
	}

	@Override
	public void onDataChange() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("Alterar");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartamentoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

}
