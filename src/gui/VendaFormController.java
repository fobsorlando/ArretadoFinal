package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Venda;
import model.services.ClienteService;
import model.services.VendaService;

public class VendaFormController  implements Initializable{

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	private Venda entidade;
	private VendaService service;
	private List <DataChangeListener> dataChangeListners = new ArrayList<>();
	
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtEndereco;
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtBairroCid;
	
	@FXML
	private TextField txtCep;
	
	@FXML
	private TextField txtNumeroVenda;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtData;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Button btEndereco;
	
	@FXML
	private Button btCliente;

	@FXML
	public void onBtSalvaAction(ActionEvent event) {
		System.out.println("Salvei");
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		System.out.println("Cancelei");
	}
	@FXML
	public void onBtEndAction(ActionEvent event) {
		System.out.println("Endereco");
	}
	@FXML
	
	public void onBtCliAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/ClienteList.fxml"));
		Parent root = loader.load();
		ClienteListController controller = loader.getController();
		//controller.setAtributo(parametro);
		controller.setFlagVenda(true);
		controller.setClienteService(new ClienteService());
		controller.updateTableView();
		


		
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Escolha um Cliente");
		stage.setAlwaysOnTop(true);
		stage.show();
		System.out.println("de volta");
	}
	
	public void setVenda(Venda entidade) {
		this.entidade = entidade;
	}
	
	public void setVendaService (VendaService service) {
		this.service=service;
	}
	
	public void subscribeDataChangeListner(DataChangeListener listener) {
		dataChangeListners.add(listener);
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalMonitorStateException("Entidade está null");
		}
		
		Cliente cli = new Cliente();
		//cli.setNo_cliente("JOSÉ DA SILVA SAURO");
		
		entidade.setId_venda(123321);
		entidade.setCliente(cli);
		
		txtNumeroVenda.setText(String.valueOf(entidade.getId_venda()));
		txtNomeCliente.setText(entidade.getCliente().getNo_cliente());
		
	}

}
