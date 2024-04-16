package containers;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import Agents.entrepriseAgent;
import BDD.reservation;
import jade.core.Profile;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import javafx.stage.Stage;

public class entrepriseContainer extends Application {
	private entrepriseAgent entrepriseAgent ;
	public ObservableList<String> observableList;
	public ListView<String> listView;

	
public static void main(String[] args) {
	
launch(entrepriseContainer.class);
		}

public void startContainer() {

	try	{
		Runtime runtime=Runtime.instance ();
		
		Profile profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profile);
	    // deployer agent 
	
		AgentController agentController= agentContainer.createNewAgent("entreprise", "Agents.entrepriseAgent",new Object[]{this  });
		// Récupération de la liste des agents dans la plateforme
		if(agentController != null) {
		    System.out.println("Agent entreprise-2 créé avec succès");
		    try {
		        // Démarrage de l'agent
		        agentController.start();
		        System.out.println("Agent entreprise-2 démarré avec succès");
		    } catch (ControllerException e) {
		        System.out.println("Erreur lors du démarrage de l'agent entreprise-2 : " + e.getMessage());
		    }
		} else {
		    System.out.println("Erreur lors de la création de l'agent entreprise-2");
		}

			//agentController.start();
			

		} catch (ControllerException e) {
			
			// TODO Auto-generated catch block
		
			e.printStackTrace();
	}
	
}

@Override

public void start(Stage primaryStage) throws Exception {
	startContainer();
	  BorderPane root = new BorderPane();
      root.setPrefSize(530, 400);


      // Top AnchorPane
      AnchorPane topAnchorPane = new AnchorPane();
      topAnchorPane.setPrefSize(600, 235);
      topAnchorPane.setStyle("-fx-background-color: #C7F8FA;");
      File file = new File("C:/Users/HP/Desktop/SMA/vv.jpg");

      // --> file:/C:/MyImages/myphoto.jpg
       String localUrl = file.toURI().toURL().toString();
      ImageView imageView = new ImageView(new Image(localUrl));
      imageView.setFitHeight(240);
      imageView.setFitWidth(300);
      imageView.setPickOnBounds(true);
      imageView.setPreserveRatio(true);
      AnchorPane.setLeftAnchor(imageView, 0.0);
      AnchorPane.setTopAnchor(imageView, 0.0);

      Label entrepriseLabel = new Label("Entreprise :");
      entrepriseLabel.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(entrepriseLabel, 264.0);
      AnchorPane.setTopAnchor(entrepriseLabel, 14.0);
      
      Label piloteLabel = new Label("Pilote :");
      piloteLabel.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(piloteLabel, 264.0);
      AnchorPane.setTopAnchor(piloteLabel, 48.0);
      
      Label avionLabel = new Label("Avion :");
      avionLabel.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(avionLabel, 264.0);
      AnchorPane.setTopAnchor(avionLabel, 82.0);
      
      Label jourLabel = new Label("Jour :");
      jourLabel.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(jourLabel, 264.0);
      AnchorPane.setTopAnchor(jourLabel, 116.0);
      
      Label heureLabel = new Label("Heure :");
      heureLabel.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(heureLabel, 264.0);
      AnchorPane.setTopAnchor(heureLabel, 150.0);

      DatePicker date =new DatePicker();
      date.setPrefWidth(182.0);
      AnchorPane.setLeftAnchor(date, 369.0);
      AnchorPane.setTopAnchor(date, 111.0);
      
      Spinner heure = new Spinner();
      heure.setPrefWidth(87.5);
      AnchorPane.setLeftAnchor(heure, 369.0);
      AnchorPane.setTopAnchor(heure, 150.0);
      heure.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
      
      Spinner min = new Spinner();
      min.setPrefWidth(87.5);
      AnchorPane.setLeftAnchor(min, 460.0);
      AnchorPane.setTopAnchor(min, 150.0);
      min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

      TextField entrepriseTextField = new TextField();
      entrepriseTextField.setPrefWidth(182.0);
      AnchorPane.setLeftAnchor(entrepriseTextField, 369.0);
      AnchorPane.setTopAnchor(entrepriseTextField, 9.0);
      
      TextField piloteTextField = new TextField();
      piloteTextField.setPrefWidth(182.0);
      AnchorPane.setLeftAnchor(piloteTextField, 369.0);
      AnchorPane.setTopAnchor(piloteTextField, 43.0);
      
      TextField AvionTextField = new TextField();
      AvionTextField.setPrefWidth(182.0);
      AnchorPane.setLeftAnchor(AvionTextField, 369.0);
      AnchorPane.setTopAnchor(AvionTextField, 77.0);
      
      Button reserver = new Button("Reserver");
      reserver.setPrefWidth(82.0);
      reserver.setStyle("-fx-background-color: #45B39D ;");
      reserver.setFont(new Font("Arial Bold", 13));
      AnchorPane.setLeftAnchor(reserver, 395.0);
      AnchorPane.setTopAnchor(reserver, 200.0);

      topAnchorPane.getChildren().addAll(imageView, entrepriseLabel, entrepriseTextField , piloteLabel, piloteTextField,avionLabel,AvionTextField,jourLabel,heureLabel,date,heure,min,reserver );

      // Bottom AnchorPane
      AnchorPane bottomPane = new AnchorPane();
      bottomPane.setPrefSize(600, 155);
      bottomPane.setStyle("-fx-background-color: #FFFFFF;");
      
      Label communicationZoneLabel = new Label("Zone de communication");
      communicationZoneLabel.setLayoutX(237);
      communicationZoneLabel.setLayoutY(10);
      Font font = new Font("Arial Bold", 13);
      communicationZoneLabel.setFont(font);
      
      observableList=FXCollections.observableArrayList();
      listView = new ListView<String>(observableList);
      
      listView.setPrefSize(607, 162);
      listView.setLayoutX(-3);
      listView.setLayoutY(24);
     // listView.setVisible(false);
      
      Separator separator1 = new Separator();
      separator1.setPrefHeight(3);
      separator1.setPrefWidth(237);
      separator1.setLayoutY(10);
      
      Separator separator2 = new Separator();
      separator2.setPrefHeight(4);
      separator2.setPrefWidth(219);
      separator2.setLayoutX(387);
      separator2.setLayoutY(10);
      
      bottomPane.getChildren().addAll(communicationZoneLabel, listView, separator1, separator2);
      
      // evenements :
      reserver.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			String message ="vous : je veux  un slot le " +date.getValue()+" à "+heure.getValue()+":"+min.getValue()+ " pour l'avion "+AvionTextField.getText()+" et qui sera piloté par "+piloteTextField.getText();
			observableList.add(message);		
			GuiEvent guiEvent = new GuiEvent(this, 1);
			reservation res= new reservation(entrepriseTextField.getText(),AvionTextField.getText(),piloteTextField.getText(),Date.valueOf(date.getValue()),Integer.parseInt(heure.getValue().toString()) ,Integer.parseInt(min.getValue().toString()),"Non");
			guiEvent.addParameter(res);
			entrepriseAgent.onGuiEvent(guiEvent);
		}
    	  
    	  
    	  
      });
      
      root.setTop(topAnchorPane);
      root.setBottom(bottomPane);
     
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      
      File file2 = new File("C:/Users/HP/Desktop/SMA/entreprise.jpg");
      String localUrl2 = file2.toURI().toURL().toString();
      primaryStage.getIcons().add(new Image(localUrl2));
      primaryStage.setTitle("Entreprise");
     
      primaryStage.show(); 

      
}
public entrepriseAgent getEntrepriseAgent() {
	return entrepriseAgent;
} 

public void setEntrepriseAgent(entrepriseAgent entrepriseAgent) {
	this.entrepriseAgent = entrepriseAgent;
}

public void logMessage(ACLMessage aclMessage) {
	Platform.runLater(()->{
		observableList.add(
				" Aéroport : " + 
				aclMessage.getContent());
	});
}




}
