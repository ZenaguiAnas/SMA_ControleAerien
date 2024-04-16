package BDD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Services {

	private Connection con=null ;
	public Services() {
		this.con=  SingletonBDD.getConnexion();	
	}
	
	public void ajouter(reservation res) {
		
		
		String SQL = "INSERT INTO reservation(entreprise,avion,pilote,jour,heure,min,arrive) "+ "VALUES(?,?,?,?,?,?,?)";
		try {
			System.out.println(res.getJour());
			//java.util.Date javaDate = new java.util.Date();
	    //     java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
			PreparedStatement pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, res.getEntreprise());
			pstmt.setString(2, res.getAvion());
			pstmt.setString(3, res.getPilote());
		    pstmt.setDate(4, res.getJour());
			//pstmt.setDate(4, mySQLDate);

			pstmt.setInt(5, res.getHeure());
			pstmt.setInt(6, res.getMin());
			pstmt.setString(7, res.getArrive());
			pstmt.executeUpdate();
			//pstmt.addBatch();
			//pstmt.executeBatch();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<reservation> getAll(){
		List<reservation> reservations =new ArrayList<reservation>();
		String sql = "SELECT entreprise, avion, pilote, jour,heure,min,arrive FROM reservation";
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
	            //Display values
				reservations.add(new reservation(rs.getString("entreprise"),rs.getString("avion"),rs.getString("pilote"),rs.getDate("jour"),rs.getInt("heure"),rs.getInt("min"), rs.getString("arrive")));

	         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
		return reservations;
		
	}
	 
	public reservation getOne(String entreprise,String avion ) throws SQLException {
		reservation res = null;
		//Requête SQL avec deux paramètres
		String sql = "SELECT  entreprise, avion, pilote, jour,heure,min,arrive FROM reservation WHERE entreprise = ? AND avion = ?";

		//Préparation de la requête
		PreparedStatement pstmt = con.prepareStatement(sql);

		//Affectation des paramètres
		pstmt.setString(1, entreprise);
		pstmt.setString(2, avion);

		//Exécution de la requête
		ResultSet rs = pstmt.executeQuery();

		//Traitement des résultats
		while (rs.next()) {
			res=new reservation(rs.getString("entreprise"),rs.getString("avion"),rs.getString("pilote"),rs.getDate("jour"),rs.getInt("heure"),rs.getInt("min"), rs.getString("arrive"));
		    //...
		}

		return  res;
	}
	public void supprimer(String entreprise,String avion) throws SQLException {
		
		
		//Requête SQL avec deux paramètres
		String sql = "DELETE  FROM  reservation WHERE entreprise =? AND avion =?";

		//Préparation de la requête
		PreparedStatement pstmt = con.prepareStatement(sql);

		//Affectation des paramètres
		pstmt.setString(1, entreprise);
		pstmt.setString(2, avion);
		pstmt.executeUpdate();
		
	}
	
public void update(String entreprise,String avion) throws SQLException {
		
		
		//Requête SQL avec deux paramètres
		String sql = "UPDATE  reservation SET arrive=?  WHERE entreprise =? AND avion =?";

		//Préparation de la requête
		PreparedStatement pstmt = con.prepareStatement(sql);

		//Affectation des paramètres
		pstmt.setString(1, "Oui");
		pstmt.setString(2, entreprise);
		pstmt.setString(3, avion);
		pstmt.executeUpdate();
		
	}
}
