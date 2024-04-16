package Agents;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BDD.Services;
import BDD.reservation;
import containers.avionContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class avionAgent extends GuiAgent{

	private avionContainer gui;
    private List<reservation> res= new ArrayList<>();



	private Services ser;
	   @Override  
	   protected void setup() {
System.out.print("ok");
		   // maintenant aget connait interface graphique , agent lié à interface graphique 
		   gui=(avionContainer) getArguments()[0];
		   // et interface graphique est lié à l'agent ; c'est une relation bidirectionnel
		   // si agent recoit un message il va demmander à l'interface de l'afficher et contrairement 
		   gui.setAvionAgent(this);
		   
		  ser =new Services();
		  
	         gui.List();
	      
	        addBehaviour(new CyclicBehaviour() {
			    @Override
			    public void action() {
			        ACLMessage message = receive();
			        if (message != null) {        
			        	gui.logMessage("Controleur : "+message.getContent());
			        	
			        } else {
			            block();
			        }
			    }
			});
		 

	   }
	
	
	public List<reservation> getRes() {
		res=ser.getAll();
		return res;
	}


	public void setRes(List<reservation> res) {
		this.res = res;
	}


	@Override
	public void onGuiEvent(GuiEvent guievent) {
		// TODO Auto-generated method stub
		
		         if(guievent.getType()==1) {
				
					ACLMessage aclMessage= new ACLMessage(ACLMessage.REQUEST);
					reservation res =(reservation) guievent.getParameter(0);
					try {
						aclMessage.setContentObject(res);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aclMessage.addReceiver(new AID("controleur",AID.ISLOCALNAME));  
					
					send(aclMessage);
	}
		         else if(guievent.getType()==2) {
						
						ACLMessage aclMessage= new ACLMessage(ACLMessage.INFORM);
						reservation res =(reservation) guievent.getParameter(0);
						try {
							aclMessage.setContentObject(res);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						aclMessage.addReceiver(new AID("controleur",AID.ISLOCALNAME));  
						
						send(aclMessage);
		}
		         }
	public Services getSer() {
		return ser;
	}


	public void setSer(Services ser) {
		this.ser = ser;
	}

}
