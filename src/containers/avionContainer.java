package containers;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;

import Agents.avionAgent;
import BDD.reservation;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class avionContainer extends Application{
	private avionAgent avionAgent ;
	public ObservableList<String> observableList=FXCollections.observableArrayList();;
	public ListView<String> listView;
	public ObservableList<String> entrepriseList=FXCollections.observableArrayList();;
	public ObservableList<String> avionList=FXCollections.observableArrayList();;
	ComboBox<String> entrepriseComboBox;
	ComboBox<String> avionComboBox;
public static void main(String[] args) {		
		launch(avionContainer.class);
			}
public void startContainer() {
	
	try	{
		Runtime runtime=Runtime.instance ();
		
		Profile profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profile);
	    // deployer agent 
		AgentController agentController= agentContainer.createNewAgent("avion", "Agents.avionAgent",new Object[]{this});
			agentController.start();
		} catch (ControllerException e) {
			
			// TODO Auto-generated catch block
		
			e.printStackTrace();
	}
		
}
	@Override
	public void start(Stage  primaryStage) throws Exception {
		startContainer();
        BorderPane root = new BorderPane();

        AnchorPane topPane = new AnchorPane();
        topPane.setPrefHeight(120.0);
        topPane.setPrefWidth(600.0);
        topPane.setStyle("-fx-background-color: #C7F8FA;");
        root.setTop(topPane);

        entrepriseComboBox = new ComboBox<>();
        entrepriseComboBox.setLayoutX(131.0);
        entrepriseComboBox.setLayoutY(30.0);
        entrepriseComboBox.setPrefWidth(150.0);
        topPane.getChildren().add(entrepriseComboBox);

         avionComboBox = new ComboBox<>();
        avionComboBox.setLayoutX(401.0);
        avionComboBox.setLayoutY(30.0);
        avionComboBox.setPrefWidth(150.0);
        topPane.getChildren().add(avionComboBox);

        Label entrepriseLabel = new Label("Entreprise");
        entrepriseLabel.setLayoutX(46.0);
        entrepriseLabel.setLayoutY(34.0);
        entrepriseLabel.setFont(Font.font("Arial Bold", 14.0));
        topPane.getChildren().add(entrepriseLabel);

        Label avionLabel = new Label("Avion");
        avionLabel.setLayoutX(350.0);
        avionLabel.setLayoutY(34.0);
        avionLabel.setFont(Font.font("Arial Bold", 14.0));
        topPane.getChildren().add(avionLabel);

        Button atterissageButton = new Button("Atterrissage");
        atterissageButton.setLayoutX(320.0);
        atterissageButton.setLayoutY(85.0);
        atterissageButton.setPrefHeight(25.0);
        atterissageButton.setPrefWidth(100.0);
        atterissageButton.setStyle("-fx-background-color: #45B39D;");
        atterissageButton.setFont(Font.font("Arial Bold", 14.0));
        topPane.getChildren().add(atterissageButton);
        Button decollageButton = new Button("Décollage");
        decollageButton.setLayoutX(180.0);
        decollageButton.setLayoutY(85.0);
        decollageButton.setPrefHeight(25.0);
        decollageButton.setPrefWidth(100.0);
        decollageButton.setStyle("-fx-background-color: #DCEB29;");
        decollageButton.setFont(Font.font("Arial Bold", 14.0));
        topPane.getChildren().add(decollageButton);

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
        // evenements 
        entrepriseComboBox.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
            	//List();
            	avionList.clear();
            	Platform.runLater(()->{
        			for (reservation re : avionAgent.getRes()){
        				//System.out.println(re.getArrive());
        				if(!avionList.contains(re.getEntreprise()) && entrepriseComboBox.getValue().equals(re.getEntreprise())) {
        				avionList.add(re.getAvion());}
        				//avionList.add(re.getAvion());
        				avionComboBox.setItems(avionList);
        			//	avionComboBox.setItems(avionList);

        				
        			}
        			
        		});
            }
         });
        
        atterissageButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {
    			// TODO Auto-generated method stub
    				String message ="vous : je vais atterir !! veuillez m'affecter une piste  " ;
    			observableList.add(message);		
    			GuiEvent guiEvent = new GuiEvent(this, 1);
    			reservation res=null;
				try {				
					res = (avionAgent.getSer()).getOne(entrepriseComboBox.getValue(), avionComboBox.getValue());				
					guiEvent.addParameter(res);
	    			avionAgent.onGuiEvent(guiEvent);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    		}

          });

        decollageButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {
    			// TODO Auto-generated method stub
   	
    				String message ="vous : je vais décoller " ;
        			observableList.add(message);		
        			GuiEvent guiEvent = new GuiEvent(this, 2);
        			reservation res=null;
    				try {				
    					res = (avionAgent.getSer()).getOne(entrepriseComboBox.getValue(), avionComboBox.getValue());				
    					guiEvent.addParameter(res);
    	    			avionAgent.onGuiEvent(guiEvent);
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

				}
		  
          });


       

        root.setBottom(bottomPane);
        Scene scene = new Scene(root, 600, 300);
        primaryStage.setScene(scene);
        File file = new File("C:/Users/HP/Desktop/SMA/avion.jpg");
        String localUrl = file.toURI().toURL().toString();
        primaryStage.getIcons().add(new Image(localUrl));
        primaryStage.setTitle("Avion");
        primaryStage.show();
       

		
	}
	public avionAgent getAvionAgent() {
		return avionAgent;
	}
	public void setAvionAgent(avionAgent avionAgent) {
		this.avionAgent = avionAgent;
	}
	
	public void List() {
		entrepriseList.clear();
		Platform.runLater(()->{
			for (reservation re : avionAgent.getRes()){
				
				if(!entrepriseList.contains(re.getEntreprise())) {
				entrepriseList.add(re.getEntreprise());}
				//avionList.add(re.getAvion());
				entrepriseComboBox.setItems(entrepriseList);
			//	avionComboBox.setItems(avionList);

				
			}
			
		});
	}
	public void logMessage(String message) {
		Platform.runLater(()->{
			observableList.add(
					message);
		});

	}
}
