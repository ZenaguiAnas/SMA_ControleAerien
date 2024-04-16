package Agents;


import java.io.IOException;

import BDD.reservation;
import containers.entrepriseContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;






public class entrepriseAgent extends GuiAgent {
	
	
	private entrepriseContainer gui;

	
	
   @Override  
   protected void setup() {
	   System.out.println("initialisation de l'agent name " + this.getAID().getName());

	   // maintenant aget connait interface graphique , agent lié à interface graphique 
	   gui=(entrepriseContainer) getArguments()[0];
	   // et interface graphique est lié à l'agent ; c'est une relation bidirectionnel
	   // si agent recoit un message il va demmander à l'interface de l'afficher et contrairement 
	   gui.setEntrepriseAgent(this);
	  
	   addBehaviour(new CyclicBehaviour() {
		    @Override
		    public void action() {
		        ACLMessage message = receive();
		        if (message != null) {        
		        	gui.logMessage(message);
		        	
		        } else {
		            block();
		        }
		    }
		});

	

   }
	@Override
	public void onGuiEvent(GuiEvent guievent) {
		// TODO Auto-generated method stub
	        if(guievent.getType()==1) {
		//ystem.out.println("koko");

			ACLMessage aclMessage= new ACLMessage(ACLMessage.REQUEST);
			reservation res =(reservation) guievent.getParameter(0);
			try {
				aclMessage.setContentObject(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			aclMessage.addReceiver(new AID("gestionnaire",AID.ISLOCALNAME));  
			// Ajout du sender
	//System.out.println(aclMessage.getSender());
			send(aclMessage);
//	System.out.println("Envoi de message depuis l'agent " + getLocalName());

	//stem.out.println(agent.getName());

			
			
		 //jjj.out.println(aclMessage);

		}
		else {System.out.println("TOTO");}
		
	}






	//---------------------------------------------------------
	

	
	
}
// au demarrage quand interface graphique demarre il va deployer agent