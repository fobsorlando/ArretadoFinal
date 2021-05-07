package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.services.DepartamentoService;
import model.services.FornecedorService;
import model.services.GrupoService;
import model.services.ProdutoService;
import model.services.SecaoService;
import model.services.SubGrupoService;

public class ProdutoListController implements Initializable, DataChangeListener {

	private ProdutoService service;

	@FXML
	private TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, Integer> tableColumnID;

	@FXML
	private TableColumn<Produto, String> tableColumnName;
	
	@FXML
	private TableColumn<Produto, String> tableColumnEan;
	
	@FXML
	private TableColumn<Produto, Double> tableColumnVlVenda;
	
	@FXML
	private TableColumn<Produto, Double> tableColumnVlCusto;
	
	@FXML
	private TableColumn<Produto, Date> tableColumnDtCriacao;
	
	@FXML
	private TableColumn <Produto, Produto> tableColumnREMOVE;
	
	@FXML 
	private TableColumn <Produto, Produto> tableColumnEDIT;
	


	@FXML
	private Button btNovo;

	private ObservableList<Produto> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Produto obj = new Produto(); // criar vazio
		createDialogForm(obj, "/gui/ProdutoForm.fxml", parentStage);
	}

	public void setProdutoService(ProdutoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("no_produto"));
		tableColumnVlVenda.setCellValueFactory(new PropertyValueFactory<>("vl_venda"));
		Utils.formatTableColumnDouble(tableColumnVlVenda, 2);
		tableColumnVlCusto.setCellValueFactory(new PropertyValueFactory<>("vl_custo"));
		Utils.formatTableColumnDouble(tableColumnVlCusto, 2);
		
		tableColumnDtCriacao.setCellValueFactory(new PropertyValueFactory<>("dth_criacao"));
		Utils.formatTableColumnDate(tableColumnDtCriacao, "dd/MM/YYYY");
		tableColumnEan.setCellValueFactory(new PropertyValueFactory<>("cd_ean13"));

		
		Stage stage = (Stage) Main.getMainScene().getWindow();

		// Produto acompanhar tamanho do menu
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		// testa se veio dados na dependencia service
		if (service == null) {
			throw new IllegalStateException("Serice veio NULLO");
		}

		List<Produto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProduto.setItems(obsList);
 	    initEditButtons(); // Acrescenta botão para alterar
 		initRemoveButtons(); // Botão para remover

	}

	private void createDialogForm(Produto obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// Pegar controldor da tela carregada acima
			ProdutoFormController controller = loader.getController();
			controller.setProduto(obj);
			controller.setServices(new ProdutoService(), new DepartamentoService(),
								   new SecaoService(), new GrupoService(),
								   new SubGrupoService() , new FornecedorService()); // Inetando dependencia do servico
			
			controller.loadAssociatedObjects(); // Carregar associados (ex. departamento)
						
			controller.subscribeDataChangeListner(this);// Inscrever classe (ela mesma-this) para escutar evento
												// ONDATACHAGED
			controller.updateFormData(); // Carrega os dados do OBJ no formulario

			// novo Stage pq a janela vai ser modal.
			// tambem vai ser uma nova scena
			// uma janela em cima da outra

			Stage dialogStage = new Stage(); // novo palco
			dialogStage.setTitle("Incuir Produto");
			dialogStage.setScene(new Scene(pane)); // Nova scena, que é o pane feito acima
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			// colocar nos outros 
			e.printStackTrace();
			Alerts.showAlert("ERRO IO", "ERRO CARREGA VIEW(E02)", e.getMessage(), AlertType.ERROR);
			

		}
	}

	@Override
	public void onDataChange() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Alterar");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ProdutoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Exclui");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
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
	
	private void removeEntity(Produto obj) {
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
				Alerts.showAlert("Erro Excluíndo Produto", null, e.getMessage(), AlertType.ERROR);
			}
		}
		return ;
	}
}
 