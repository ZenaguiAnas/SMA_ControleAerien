package Agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BDD.Services;
import BDD.reservation;
import containers.controleurContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class controleurAgent extends GuiAgent{

	
	
	private controleurContainer gui;
    private List<reservation> res= new ArrayList<>();
	private Services ser;
	private reservation[] piste1= new reservation[4];
	private reservation[] piste2= new reservation[4];
	
	   @Override  
	   protected void setup() {

		   // maintenant aget connait interface graphique , agent lié à interface graphique 
		   gui=(controleurContainer) getArguments()[0];
		   // et interface graphique est lié à l'agent ; c'est une relation bidirectionnel
		   // si agent recoit un message il va demmander à l'interface de l'afficher et contrairement 
		   gui.setControleurAgent(this);
		   
		  ser =new Services();
		  res=ser.getAll();
	  
		   addBehaviour(new CyclicBehaviour() {
			    @Override
			    public void action() {
			        ACLMessage message = receive();
			        if (message != null ) {   
			        	reservation re;
			        	if(message.getPerformative()==ACLMessage.CONFIRM){ 
							String mes=message.getContent();	
							gui.logMessage("Gestionnaire : "+mes);			
						
			        }
			        	if(message.getPerformative()==ACLMessage.INFORM){ 
							try {		
								
				      			re = (reservation) message.getContentObject();	
								String m=re.getAvion()+" d'entreprise "+re.getEntreprise()+" :  je vais décoller";
							    gui.logMessage(m);			
							    
							    String messag=decollage(re);
							    gui.logMessage("Vous : "+ messag);							    
							    gui.List();
							    //message à l'avion
							    ACLMessage aclMessage= new ACLMessage(ACLMessage.INFORM);
								aclMessage.setContent(messag);
								aclMessage.addReceiver(new AID("avion",AID.ISLOCALNAME));  
								send(aclMessage);
								//message au gestionnaire
							    ACLMessage aclMessage2= new ACLMessage(ACLMessage.INFORM);
								aclMessage2.setContentObject(re);
								aclMessage2.setOntology("décollé");
								aclMessage2.addReceiver(new AID("gestionnaire",AID.ISLOCALNAME));  
								send(aclMessage2);
							    
							} catch (UnreadableException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	}
			        	if(message.getPerformative()==ACLMessage.REQUEST) { 
						try {		
							
			      			re = (reservation) message.getContentObject();	
							String m=re.getAvion()+" d'entreprise "+re.getEntreprise()+" :  je vais atterrir !! veuillez m'affecter une piste  ";
						    gui.logMessage(m);				    
						    String messag=atterissage(re);
						    gui.logMessage("Vous : "+ messag);	
						    gui.List();
						    ACLMessage aclMessage= new ACLMessage(ACLMessage.INFORM);
							aclMessage.setContent(messag);
							aclMessage.addReceiver(new AID("avion",AID.ISLOCALNAME));  
							send(aclMessage);
							
							String mess="Vous : l'avion "+re.getAvion()+" d'entreprise "+re.getEntreprise()+" est arrivé";
						    gui.logMessage( mess);	
						    gui.List();
							ACLMessage aclMessage2= new ACLMessage(ACLMessage.INFORM);
						    aclMessage2.setContentObject(re);
						    aclMessage2.setOntology("arrivé");
							aclMessage2.addReceiver(new AID("gestionnaire",AID.ISLOCALNAME));  
							send(aclMessage2);			    
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
			        	}
			        	
			        	else {
			            block();
			        }
			    }
			    }});
		 

	   }
	
	
	
	public String atterissage(reservation res) {
		if(res.getMin()==0) {
			if (piste1[0]==null) {
				piste1[0]=res;
				return " ok vous pouvez atterir sur la piste 1 avec le tarmac 1";
			}
			else if (piste1[0]!=null && piste2[0]==null) {
				piste2[0]=res;
				return " ok vous pouvez atterir sur la piste 2 avec le tarmac 1";
				
				}
		    else     return " atterissage impossible";
			}
		
		else if(res.getMin()==15) {
			if (piste1[1]==null) {
				piste1[1]=res;
				return " ok vous pouvez atterir sur la piste 1 avec le tarmac 2";
			}
			else if (piste1[1]!=null && piste2[1]==null) {
				piste2[1]=res;
				return " ok vous pouvez atterir sur la piste 2 avec le tarmac 2";
				
				}
		    else     return " atterissage impossible";
			}
		else if(res.getMin()==30) {
			if (piste1[2]==null) {
				piste1[2]=res;
				return " ok vous pouvez atterir sur la piste 1 avec le tarmac 3";
			}
			else if (piste1[2]!=null && piste2[2]==null) {
				piste2[2]=res;
				return " ok vous pouvez atterir sur la piste 2 avec le tarmac 3";
				
				}
		    else     return " atterissage impossible";
			}
		else if(res.getMin()==45) {
			if (piste1[3]==null) {
				piste1[3]=res;
				return " ok vous pouvez atterir sur la piste 1 avec le tarmac 4";
			}
			else if (piste1[3]!=null && piste2[3]==null) {
				piste2[3]=res;
				return " ok vous pouvez atterir sur la piste 2 avec le tarmac 4";
				
				}
		    else     return " atterissage impossible";
			}
		return "erreur";
	}
	
	
	
	public String decollage(reservation res) {
		if(res.getMin()==0) {
			if (piste1[0]!=null || piste2[0]!=null ) {
				if(piste1[0]!=null) {
				 if( res.getAvion().equals(piste1[0].getAvion() ) && res.getAvion().equals(piste1[0].getAvion())) {
				piste1[0]=null;
				return " ok vous pouvez decoller et  le tarmac 1 de la piste 1 sera vide";
				 }
				}	
				else if( piste2[0]!=null) {
					if( res.getAvion().equals(piste2[0].getAvion() ) && res.getAvion().equals(piste2[0].getAvion())) {
					piste2[0]=null;
					return " ok vous pouvez decoller et  le tarmac 1 de la piste 2 sera vide";
					}
					}
			    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}	
		    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}
		
		else if(res.getMin()==15) {
			if (piste1[1]!=null || piste2[0]!=null ) {
				if(piste1[1]!=null) {
				 if( res.getAvion().equals(piste1[1].getAvion() ) && res.getAvion().equals(piste1[1].getAvion())) {
				piste1[1]=null;
				return " ok vous pouvez decoller et  le tarmac 2 de la piste 1 sera vide";
				 }
				}	
				else if( piste2[1]!=null) {
					if( res.getAvion().equals(piste2[1].getAvion() ) && res.getAvion().equals(piste2[1].getAvion())) {
					piste2[1]=null;
					return " ok vous pouvez decoller et  le tarmac 2 de la piste 2 sera vide";
					}
					}
			    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}	
		    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}
		else if(res.getMin()==30) {
			if (piste1[2]!=null || piste2[2]!=null ) {
				if(piste1[2]!=null) {
				 if( res.getAvion().equals(piste1[2].getAvion() ) && res.getAvion().equals(piste1[2].getAvion())) {
				piste1[2]=null;
				return " ok vous pouvez decoller et  le tarmac 1 de la piste 1 sera vide";
				 }
				}	
				else if( piste2[2]!=null) {
					if( res.getAvion().equals(piste2[2].getAvion() ) && res.getAvion().equals(piste2[2].getAvion())) {
					piste2[2]=null;
					return " ok vous pouvez decoller et  le tarmac 3 de la piste 2 sera vide";
					}
					}
			    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}	
		    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}
		
		else if(res.getMin()==45) {
			if (piste1[3]!=null || piste2[3]!=null ) {
				if(piste1[3]!=null) {
				 if( res.getAvion().equals(piste1[3].getAvion() ) && res.getAvion().equals(piste1[3].getAvion())) {
				piste1[3]=null;
				return " ok vous pouvez decoller et  le tarmac 4 de la piste 1 sera vide";
				 }
				}	
				else if( piste2[3]!=null) {
					if( res.getAvion().equals(piste2[3].getAvion() ) && res.getAvion().equals(piste2[3].getAvion())) {
					piste2[3]=null;
					return " ok vous pouvez decoller et  le tarmac 4 de la piste 2 sera vide";
					}
					}
			    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}		
		    else     return " vous pouvez pas decoller car vous n'etes pas dans l'aeroport";
			}
		
		return "erreur";
	}
	
	
	
	public reservation[] getPiste1() {
		return piste1;
	}



	public void setPiste1(reservation[] piste1) {
		this.piste1 = piste1;
	}



	public reservation[] getPiste2() {
		return piste2;
	}



	public void setPiste2(reservation[] piste2) {
		this.piste2 = piste2;
	}



	public List<reservation> getRes() {
		return res;
	}










	public void setRes(List<reservation> res) {
		this.res = res;
	}










	public Services getSer() {
		return ser;
	}










	public void setSer(Services ser) {
		this.ser = ser;
	}










	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}
     
	
	
	
}
