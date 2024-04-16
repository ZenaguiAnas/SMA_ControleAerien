package containers;


import java.io.File;
import java.sql.Date;


import Agents.GestionnaireAgent;
import BDD.reservation;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestionnaireContainer extends Application{
	private GestionnaireAgent gestionnaireAgent ;
	public ObservableList<String> observableList;
	public ObservableList<reservation> resList=FXCollections.observableArrayList();
	public ListView<String> listView;
	TableView<reservation> table;
	


	public static void main(String[] args) {
		
		launch(GestionnaireContainer.class);
			}

	
	public void startContainer() {
		
		try	{
			Runtime runtime=Runtime.instance ();
			
			Profile profile=new ProfileImpl();
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer agentContainer=runtime.createAgentContainer(profile);
		    // deployer agent 
			AgentController agentController= agentContainer.createNewAgent("gestionnaire", "Agents.GestionnaireAgent",new Object[]{this});
				agentController.start();
			} catch (ControllerException e) {
				
				// TODO Auto-generated catch block
			
				e.printStackTrace();
		}
			
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		startContainer();
		//----------------------------------------
		BorderPane root = new BorderPane();
	      root.setPrefSize(560, 350);


	      // Top AnchorPane
	      AnchorPane topAnchorPane = new AnchorPane();
	      topAnchorPane.setPrefSize(560, 200);
	      
	       table = new TableView(resList);
	        table.setPrefSize(560, 220);
	       // table.setLayoutX(20);
	        

	    /*    TableColumn    entrepriseCol = new TableColumn("Entreprise");
	        entrepriseCol.setMinWidth(20);
	        TableColumn avionCol = new TableColumn("Avion");
	        
	        TableColumn piloteCol = new TableColumn("Pilote");
	        TableColumn jourCol = new TableColumn("Jour");
	        TableColumn heureCol = new TableColumn("Heure");
	        TableColumn minCol = new TableColumn("min");
	        //TableColumn arriveCol = new TableColumn("arrivé");*/
	        TableColumn<reservation, String> entrepriseCol = new TableColumn<>("Entreprise");
	        entrepriseCol.setCellValueFactory(new PropertyValueFactory<>("entreprise"));
	        
	        TableColumn<reservation, String> avionCol = new TableColumn<>("Avion");
	        avionCol.setCellValueFactory(new PropertyValueFactory<>("avion"));

	        TableColumn<reservation, String> piloteCol = new TableColumn<>("Pilote");
	        piloteCol.setCellValueFactory(new PropertyValueFactory<>("pilote"));
	        
	        TableColumn<reservation, Date> jourCol = new TableColumn<>("Jour");
	        jourCol.setCellValueFactory(new PropertyValueFactory<>("jour"));
	        
	        TableColumn<reservation, Integer> heureCol = new TableColumn<>("Heure");
	        heureCol.setCellValueFactory(new PropertyValueFactory<>("heure"));
	        
	        TableColumn<reservation, Integer> minCol = new TableColumn<>("Min");
	        minCol.setCellValueFactory(new PropertyValueFactory<>("min"));
	        
	        TableColumn<reservation, String> arriveCol = new TableColumn<>("Arrivé");
	        arriveCol.setCellValueFactory(new PropertyValueFactory<>("arrive"));
	        
	        table.getColumns().addAll(entrepriseCol, avionCol, piloteCol, jourCol, heureCol, minCol,arriveCol); 

	      topAnchorPane.getChildren().addAll(table);

	      // Bottom AnchorPane
	      AnchorPane bottomPane = new AnchorPane();
	      bottomPane.setPrefSize(600, 155);
	      bottomPane.setStyle("-fx-background-color: #FFFFFF;");
	      
	      Label communicationZoneLabel = new Label("Zone de communication");
	      communicationZoneLabel.setLayoutX(220);
	      communicationZoneLabel.setLayoutY(10);
	      Font font = new Font("Arial Bold", 13);
	      communicationZoneLabel.setFont(font);
	      
	      observableList=FXCollections.observableArrayList();
	      listView = new ListView<String>(observableList);
	      
	      listView.setPrefSize(600, 162);
	      listView.setLayoutX(-3);
	      listView.setLayoutY(24);
	     // listView.setVisible(false);
	      
	      Separator separator1 = new Separator();
	      separator1.setPrefHeight(3);
	      separator1.setPrefWidth(220);
	      separator1.setLayoutY(10);
	      
	      Separator separator2 = new Separator();
	      separator2.setPrefHeight(3);
	      separator2.setPrefWidth(219);
	      separator2.setLayoutX(370);
	      separator2.setLayoutY(10);
	      
	      bottomPane.getChildren().addAll(communicationZoneLabel, listView, separator1, separator2);
	      root.setTop(topAnchorPane);
	      root.setBottom(bottomPane);


		    Scene scene = new Scene(root);
		    primaryStage.setTitle("Gestionnaire du trafic");
		    primaryStage.setScene(scene);
	        File file = new File("C:/Users/HP/Desktop/SMA/gestionnaire.jpg");
	        String localUrl = file.toURI().toURL().toString();
	        primaryStage.getIcons().add(new Image(localUrl));
		    primaryStage.setMaxWidth(562);
		    primaryStage.show();
	}
	
	public GestionnaireAgent getGestionnaireAgent() {
		return gestionnaireAgent;
	}


	public void setGestionnaireAgent(GestionnaireAgent gestionnaireAgent) {
		this.gestionnaireAgent = gestionnaireAgent;
	}
	public void logMessage(String message) {
		Platform.runLater(()->{
			observableList.add(
					
					message);
		});

	}
	public void List(java.util.List<reservation> res) {
		resList.clear();
		Platform.runLater(()->{
			for (reservation re : res){
				System.out.println(re.getArrive());
				resList.add(re);
				table.setItems(resList);
				
			}
			
		});
	}
}
