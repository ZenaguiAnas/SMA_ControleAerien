package Agents;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BDD.Services;
import BDD.reservation;
import containers.GestionnaireContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;


public class GestionnaireAgent  extends GuiAgent{
	private GestionnaireContainer gui;
    private List<reservation> res= new ArrayList<>();
	private Services ser;
	   @Override  
	   protected void setup() {

		   // maintenant aget connait interface graphique , agent lié à interface graphique 
		   gui=(GestionnaireContainer) getArguments()[0];
		   // et interface graphique est lié à l'agent ; c'est une relation bidirectionnel
		   // si agent recoit un message il va demmander à l'interface de l'afficher et contrairement 
		   gui.setGestionnaireAgent(this);
		   
		  ser =new Services();
		  res=ser.getAll();
	      gui.List(res);   	  
		   addBehaviour(new CyclicBehaviour() {
			    @Override
			    public void action() {
			        ACLMessage message = receive();
			        if (message != null) {   
			        	reservation re;
						try {
							re = (reservation) message.getContentObject();
						   if(message.getPerformative()==ACLMessage.INFORM && message.getOntology().equals("arrivé")) {
								String mess="Controleur : l'avion "+re.getAvion()+" d'entreprise "+re.getEntreprise()+" est arrivé";
								gui.logMessage(mess);
								ser.update(re.getEntreprise(), re.getAvion());
								res=ser.getAll();
								gui.List(res);
								String mes ="Ok parfait ! ";
								
								ACLMessage aclMessage= new ACLMessage(ACLMessage.CONFIRM);
								gui.logMessage("Vous : "+mes);
								aclMessage.setContent(mes);
								aclMessage.addReceiver(new AID("controleur",AID.ISLOCALNAME));  
								send(aclMessage);						
							}
							
							else if(message.getPerformative()==ACLMessage.INFORM && message.getOntology().equals("décollé")) {
							String mess="Controleur : l'avion "+re.getAvion()+" d'entreprise "+re.getEntreprise()+" vient de décoller";
							gui.logMessage(mess);
							ser.supprimer(re.getEntreprise(), re.getAvion());
							res=ser.getAll();
							gui.List(res);
							String mes ="Ok parfait ! ";
							ACLMessage aclMessage= new ACLMessage(ACLMessage.CONFIRM);
							gui.logMessage("Vous : "+mes);
							aclMessage.setContent(mes);
							aclMessage.addReceiver(new AID("controleur",AID.ISLOCALNAME));  
							send(aclMessage);						
						}
						else {
							String mess=re.getEntreprise()+" : je veux un slot le "+re.getJour()+" à "+re.getHeure()+":"+re.getMin()+" pour l'avion "+re.getAvion()+" et qui sera piloté par "+re.getPilote();
							gui.logMessage(mess);
							
							String mes=Reponse(re.getJour(),re.getHeure(),re.getMin());
							gui.logMessage("Vous : "+mes);
							if(mes.equals("Parfait vous avez reservé ce creneau ")) {
								
							ser.ajouter(re); 
							res=ser.getAll();
							gui.List(res);}
							ACLMessage aclMessage= new ACLMessage(ACLMessage.INFORM);
							aclMessage.setContent(mes);
							aclMessage.addReceiver(new AID("entreprise",AID.ISLOCALNAME));  
							send(aclMessage);
						}
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
			        	
			        } else {
			            block();
			        }
			    }
			});
		 
	   }
	
	
	public String Reponse(Date jour, int heure ,int min) {
		String message ="";
		if(min!=15 && min != 30 && min!=45 && min !=0) {
		if(min < 15 ) {
			 
			int [] heureApres=plusProcheApres(jour, heure, 15);
			int [] heureAvant=plusProcheAvant(jour, heure, 15);
			if(heureApres[0]==100 && heureAvant[0]!=100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureAvant[0] +":"+heureAvant[1];	}
			else if(heureApres[0]!=100 && heureAvant[0]==100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureApres[0] +":"+heureApres[1];	}
			else if(heureApres[0]==100 && heureAvant[0]==100) {message= "il y a pas de  creneau vide ce jour svp chosir un autre jour ";	}
			else {message= "vous pouvez choisir l'un de ces creneaux "+ heureApres[0] +":"+heureApres[1]+"  ou bien "+  heureAvant[0] +":"+heureAvant[1];	}

		}
		else if(min < 30) {
			int [] heureApres=plusProcheApres(jour, heure, 30);
			int [] heureAvant=plusProcheAvant(jour, heure, 30);
			if(heureApres[0]==100 && heureAvant[0]!=100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureAvant[0] +":"+heureAvant[1];	}
			else if(heureApres[0]!=100 && heureAvant[0]==100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureApres[0] +":"+heureApres[1];	}
			else if(heureApres[0]==100 && heureAvant[0]==100) {message= "il y a pas de  creneau vide ce jour svp chosir un autre jour";	}
			else { message= "vous pouvez choisir l'un de ces creneaux "+ heureApres[0] +":"+heureApres[1]+"  ou bien "+  heureAvant[0] +":"+heureAvant[1];	
		}}
		else if(min < 45) {
			int [] heureApres=plusProcheApres(jour, heure, 45);
			int [] heureAvant=plusProcheAvant(jour, heure, 45);
			if(heureApres[0]==100 && heureAvant[0]!=100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureAvant[0] +":"+heureAvant[1];	}
			else if(heureApres[0]!=100 && heureAvant[0]==100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureApres[0] +":"+heureApres[1];	}
			else if(heureApres[0]==100 && heureAvant[0]==100) {message= "il y a pas de  creneau vide ce jour svp chosir un autre jour ";	}
			else {message= "vous pouvez choisir l'un de ces creneaux "+ heureApres[0] +":"+heureApres[1]+"  ou bien "+  heureAvant[0] +":"+heureAvant[1];	}
		}
		else  {
			int [] heureApres=plusProcheApres(jour, heure, 45);
			int [] heureAvant=plusProcheAvant(jour, heure, 45);
			if(heureApres[0]==100 && heureAvant[0]!=100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureAvant[0] +":"+heureAvant[1];	}
			else if(heureApres[0]!=100 && heureAvant[0]==100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureApres[0] +":"+heureApres[1];	}
			else if(heureApres[0]==100 && heureAvant[0]==100) {message= "il y a pas de  creneau vide ce jour svp chosir un autre jour";	}
			else {message= "vous pouvez choisir l'un de ces creneaux "+ heureApres[0] +":"+heureApres[1]+"  ou bien "+  heureAvant[0] +":"+heureAvant[1];	}
		}
		}
		else {
			if(verifier(jour,heure,min)) { message= "Parfait vous avez reservé ce creneau ";
			
			}	
			else {
				int [] heureApres=plusProcheApres(jour, heure, min);
				int [] heureAvant=plusProcheAvant(jour, heure, min);
				if(heureApres[0]==100 && heureAvant[0]!=100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureAvant[0] +":"+heureAvant[1];	}
				else if(heureApres[0]!=100 && heureAvant[0]==100) {message= "vous pouvez choisir ce creneau le plus proche "+  heureApres[0] +":"+heureApres[1];	}
				
				else if(heureApres[0]==100 && heureAvant[0]==100) {message= "il y a pas de  creneau vide ce jour svp chosir un autre jour";	System.out.print( heureAvant[0]);}
				else {message= "vous pouvez choisir l'un de ces creneaux "+ heureApres[0] +":"+heureApres[1]+"  ou bien "+  heureAvant[0] +":"+heureAvant[1];	}
			}
			
		}
		return message;
	}
	
	
	public int [] plusProcheApres(Date jour,int heure,int min){		
	    int [] value= new int[2];
		int h=heure;
		int m =min;
		while(true) {
	    if( verifier(jour,h,m)) { 
	    	value[0]=h;
	        value[1]=m;
	        return value;
	    } 
	    if(m==45 && h==23) {
			value[0]=value[1]=100;
			return value;
		}	
		else if(m==45) { h++; m=0;}
		
		else  m=m+15 ;	}
		//return plusProcheApres(jour, h, m);
		
		}
	
	public int [] plusProcheAvant(Date jour,int heure,int min){		
	    int [] value= new int[2];
		int h=heure;
		int m =min;
		while(true) {
	    
		if(m==0 && h==0) {
				value[0]=value[1]=100;
				return value;
			}	
		
		else  if(m==0) { h--; m=45;}
		
		else  m=m-15 ;	
		if( verifier(jour,h,m)) { 
	    	value[0]=h;
	        value[1]=m;
	        return value;
	    } 
}
		
		//return plusProcheApres(jour, h, m);
		
		}
	
	
public boolean verifier(Date jour,int h,int m) {
	int i=0;
	for (reservation re : res){
		if(re.getJour().equals(jour) && re.getHeure()==h && re.getMin() ==m) i++;
	}
	if (i<2) return true;
	else  return false ;
}
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
