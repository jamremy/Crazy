package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.Carte;
import player.Joueur;

/**
 * Servlet implementation class nefertiti
 */
//@WebServlet("/nefertiti")
public class nefertiti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Carte carteActuel = null; // La carte actuellement utilise dans le jeu.
    PrintWriter           out = null; // Porte de sortie des donnees. Utiliser cette variable pour renvoyer les donnees
    								  // vers le client.

    String id     = null;
    String sens   = null;
    String angle  = null;
    Joueur joueur = null;    
    String pseudo = null;
    
	public void init() {
		carteActuel = new Carte(getServletContext().getRealPath("/maps/1.map"));

		//carteActuel.GetListeDeEnnemis().add(new Ennemi("ennemi1", 200, 100, 100, 100));
		//carteActuel.GetListeDeEnnemis().add(new Ennemi("ennemi1", (int) (Math.random() * 800), (int) (Math.random() * 800), 100, 100));
	}
	
    /** 
     * @see HttpServlet#HttpServlet()
     */
    public nefertiti() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		// Explicite l'encodage a utiliser et le format/type de la sortie (ca peut sembler ambigue sachant que l'on
		// utilse JSON mais je n'ai pas meilleur explication sur le moment.)
		response.setContentType("text/html");
        response.setCharacterEncoding( "UTF-8" );
        
	    long time = System.currentTimeMillis();
	    
	    // Verification des evenements qui serait survenu.
	    this.carteActuel.DetectCollision(); // Verifie s'il n'y a eu aucune collusion.
	    this.carteActuel.Actualiser(time);  // 

	    // Prise en charge de la requete...
        String requete = request.getParameter("requete");
        if (requete != null) { // S'il n'y a rien a traiter alors on ne fait on ne fait rien.
        	this.out = response.getWriter();
        	
	        if (requete.compareTo("nouveau") == 0) {
	            /**
	             * requete exemple pour creer un nouveau joueur 
	             * http://localhost:8080/CrazyM2/nefertiti?requete=nouveau&pseudo=jjj 
	             **/
	        	this.pseudo = request.getParameter("pseudo");
	        	
	        	this.carteActuel.GetListeDeJoueurs().addJoueur(new Joueur(pseudo));	
	        } 
	        else if (requete.compareTo("jeu") == 0) {
	        	/**
	        	 * requete exemple pour changer la position du joueur 
	        	 * http://localhost:8080/CrazyM2/nefertiti?requete=jeu&id=1&sens=avancer&angle=droite 
	             **/
	        	this.id     = request.getParameter("id");
	        	this.sens   = request.getParameter("sens");
	        	this.angle  = request.getParameter("angle");
	        	this.joueur = this.carteActuel.GetListeDeJoueurs().searchJoueur(Integer.parseInt(id));
	        	
	        	if (sens.compareTo("avancer") == 0) {	
	        		this.joueur.GetAvion().avancer();
	        		
	        	} else if (sens.compareTo("reculer") == 0) {	
	        		this.joueur.GetAvion().reculer();
	        		
	        	} else if (sens.compareTo("libre") == 0) {	
	        	
	        	}
	        	
	        	if (angle.compareTo("gauche") == 0) {
	        		this.joueur.GetAvion().tournerGauche();
	        		
	        	} else if (angle.compareTo("droite") == 0) {	
	        		this.joueur.GetAvion().tournerDroite();
	        	}	
	        }
	        else if (requete.compareTo("tire") == 0) {
	        	/**
	        	 * requete exemple pour changer le tire du joueur  
	        	 * http://localhost:8080/CrazyM2/nefertiti?requete=tire&id=1
	        	 **/
	        	
	        	this.id     = request.getParameter("id");
	        	this.joueur = this.carteActuel.GetListeDeJoueurs().searchJoueur(Integer.parseInt(id));
	        	
	        	//Creation d'un objet de type bullet ajouter a la liste des graphiques de la carte
	        	this.joueur.GetAvion().tirer(this.carteActuel.GetListeDeGraphiques());	        	
	        }
	        
	        // Retourner la liste mise a jour des elements présents sur la carte.
	        this.out.println(this.carteActuel.GetAllGraphiquesPosition());
        }        
	}
}
