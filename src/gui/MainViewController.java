package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClienteService;
import model.services.DepartamentoService;
import model.services.FornecedorService;
import model.services.GrupoService;
import model.services.ProdutoService;
import model.services.SecaoService;
import model.services.SubGrupoService;
import model.services.UnidadeService;
import model.services.VendaService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemGrupo;
	
	@FXML
	private MenuItem menuItemSubGrupo;
	
	@FXML
	private MenuItem menuItemSecao;
	
	@FXML
	private MenuItem menuItemFornecedor;
	
	@FXML
	private MenuItem menuItemUnidade;
	
	@FXML
	private MenuItem menuItemProduto;
	
	@FXML
	private MenuItem menuItemCadastro;
	
	@FXML
	private MenuItem menuItemVenda;
	
	@FXML
	private MenuItem menuItemAbout;
	
	
	@FXML
	public void onMenuItemAboutAction () {
		loadview("/gui/About.fxml", x -> {});
	}
	
	@FXML
	public void onMenuItemDepartamentoAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/DepartamentoList.fxml",(DepartamentoListController controller) -> {
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemGrupoAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/GrupoList.fxml",(GrupoListController controller) -> {
			controller.setGrupoService(new GrupoService());
			controller.updateTableView();
		});
		
	}
	
	@FXML
	public void onMenuItemSubGrupoAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/SubGrupoList.fxml",(SubGrupoListController controller) -> {
			controller.setSubGrupoService(new SubGrupoService());
			controller.updateTableView();
		});
		
	}
	
	@FXML
	public void onMenuItemSecaoAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/SecaoList.fxml",(SecaoListController controller) -> {
			controller.setSecaoService(new SecaoService());
			controller.updateTableView();
		});
		
	}
	
	@FXML
	public void onMenuItemFornecedorAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/FornecedorList.fxml",(FornecedorListController controller) -> {
			controller.setFornecedorService(new FornecedorService());
			controller.updateTableView();
		});
		
	}
	
	@FXML
	public void onMenuItemUnidadeAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/UnidadeList.fxml",(UnidadeListController controller) -> {
			controller.setUnidadeService(new UnidadeService());
			controller.updateTableView();
		});
		
	}
	
	@FXML
	public void onMenuItemProdutoAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/ProdutoList.fxml",(ProdutoListController controller) -> {
			controller.setProdutoService(new ProdutoService());
			controller.updateTableView();
		});
		
		
	}

	@FXML
	public void onMenuItemClienteAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/ClienteList.fxml",(ClienteListController controller) -> {
			controller.setClienteService(new ClienteService());
			controller.updateTableView();
		});
		
		
	}
	@FXML
	public void onMenuItemVendaAction () {
		// vai passar a FUNÇÃO DE INICIALIZAÇÃO como parametro
		// usando expressão lambda
		loadview("/gui/VendaList.fxml",(VendaListController controller) -> {
			controller.setVendaService(new VendaService());
			controller.updateTableView();
		});
		
		
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private  synchronized <T> void  loadview (String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox vbox = loader.load();
			
			
			// *** MANIPULADO A SCENA PRINCIPAL ***
			// Pega Scena do main
			Scene mainScene = Main.getMainScene();
			
			// salvando o content no mailvbox
			VBox mainVbox  = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			// Preserva o menubar 
			// Pega o primeiro vilho do maivbox , que é  o menu.
			Node mainMenu = mainVbox.getChildren().get(0);
			
			// Excluir os filhos orginais do vbox (clear)
			mainVbox.getChildren().clear();
			
			// Incluir novamente o menubar e os filhos do about
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(vbox.getChildren()); 
			
			// Get controler retorna o controlador do tipo que 
			// foi passado na função loader
			// Executa a função passada pelo loadview
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e ){
			System.out.println(e.getMessage());
			Alerts.showAlert("ERRO IO", "ERRO CARREGA VIEW", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	
	
}
