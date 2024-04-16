package containers;

import java.io.File;
import java.sql.Date;

import Agents.controleurAgent;
import BDD.reservation;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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

public class controleurContainer extends Application{  
	private controleurAgent controleurAgent ;
	public ObservableList<String> observableList;
	public ObservableList<String[]> resList=FXCollections.observableArrayList();
	public ListView<String> listView;
	TableView<String[]> table;

	
public static void main(String[] args) {
		
		launch(controleurContainer.class);
			}

public void startContainer() {
	
	try	{
		Runtime runtime=Runtime.instance ();
		
		Profile profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profile);
	    // deployer agent 
		AgentController agentController= agentContainer.createNewAgent("controleur", "Agents.controleurAgent",new Object[]{this});
			agentController.start();
		} catch (ControllerException e) {
			
			// TODO Auto-generated catch block
		
			e.printStackTrace();
	}
		
}

public controleurAgent getControleurAgent() {
	return controleurAgent;
}

public void setControleurAgent(controleurAgent controleurAgent) {
	this.controleurAgent = controleurAgent;
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
        


        TableColumn<String[], String> entrepriseCol = new TableColumn<>("Avion");
        entrepriseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        
        TableColumn<String[], String> avionCol = new TableColumn<>("Entreprise");
        avionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> piloteCol = new TableColumn<>("Pilote");
        piloteCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        
        TableColumn<String[], String > jourCol = new TableColumn<>("Date d'atterrissage");
        jourCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        
  
        TableColumn<String[],String> pisteCol = new TableColumn<>("Piste");
        pisteCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
        
        TableColumn<String[],String> tarmacCol = new TableColumn<>("Tarmac");
        tarmacCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
       
        
        table.getColumns().addAll(entrepriseCol, avionCol, piloteCol, jourCol,pisteCol,tarmacCol); 

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
	    primaryStage.setTitle("Controleur du trafic");
	    primaryStage.setScene(scene);
        File file = new File("C:/Users/HP/Desktop/SMA/controleur.jpg");
        String localUrl = file.toURI().toURL().toString();
        primaryStage.getIcons().add(new Image(localUrl));
	    primaryStage.setMaxWidth(562);
	    primaryStage.show();
}
public void logMessage(String message) {
	Platform.runLater(()->{
		observableList.add(
				message);
	});

}
public void List() {
	
	Platform.runLater(()->{
		resList.clear();
		int i=1;
		for (reservation re : controleurAgent.getPiste1()){
			if(re!=null) resList.add(new String[]{re.getAvion(), re.getEntreprise(),re.getPilote(),re.getJour().toString()+" à "+re.getHeure()+":"+re.getMin(),"1",""+i}) ;
            i++;
		}
		i=1;
		for (reservation re : controleurAgent.getPiste2()){
			if(re!=null) resList.add(new String[]{re.getAvion(), re.getEntreprise(),re.getPilote(),re.getJour().toString()+" à "+re.getHeure()+":"+re.getMin(),"2",""+i}) ;
		    i++;
		}
		table.setItems(resList);
	});
}
}
