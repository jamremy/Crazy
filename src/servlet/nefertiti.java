package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import carte.Carte;
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
    String avion  = null;
    
    JSONObject erreur     = null;
    
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
        response.setCharacterEncoding("UTF-8");
        
	    long time = System.currentTimeMillis();
	    
	    // Verification des evenements qui serait survenu.
	    this.carteActuel.detectCollision(); // Verifie s'il n'y a eu aucune collusion.
	    this.carteActuel.actualiser(time);  // 

	    // Prise en charge de la requete...
        String requete = request.getParameter("requete");
        if (requete != null) { // S'il n'y a rien a traiter alors on ne fait on ne fait rien.
        	this.out    = response.getWriter();
        	this.erreur = new JSONObject();
        	
		    if (requete.compareTo("nouveau") == 0) {
	        	// On limite le nombre de joueur
				if (this.carteActuel.nombreDeJoueurs() < Joueur.MAXIMUM) {
					/**
					 * requete exemple pour creer un nouveau joueur 
					 * http://localhost:8080/CrazyM2/nefertiti?requete=nouveau&pseudo=jjj 
					 **/
					this.pseudo = request.getParameter("pseudo");
					this.avion  = request.getParameter("avion");
					
					this.joueur = new Joueur(pseudo, this.avion);
					this.carteActuel.ajouterJoueur(this.joueur);
										
					System.out.println("Un nouveau joueur avec le pseudo \""+ this.pseudo + "\" a ete cree " + 
									   "il a demande l'avion \"" + request.getParameter("avion") + "\".\n");
					
					try {
						this.ajouterMsgErreur("-1", "retour,id,requete", "OK", "" + this.joueur.getId(), requete);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Erreur : le nombre de maximum joueur pouvant se connecter est atteint \n");
					try {
						this.ajouterMsgErreur("-1", "retour,id,requete", "NOK", "" + this.joueur.getId(), requete);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
	        } else if (requete.compareTo("jeu") == 0) {
	        	/**
	        	 * requete exemple pour changer la position du joueur 
	        	 * http://localhost:8080/CrazyM2/nefertiti?requete=jeu&id=1&sens=avancer&angle=droite 
	             **/
	        	this.id     = request.getParameter("id");
	        	this.sens   = request.getParameter("sens");
	        	this.angle  = request.getParameter("angle");
	        	this.joueur = this.carteActuel.getJoueur(Integer.parseInt(id));
	        	
	        	if (sens != null) {
		        	if (sens.compareTo("avancer") == 0) {	
		        		this.joueur.getAvion().avancer();
		        		System.out.print("Avancer : ");
		        		
		        	} else if (sens.compareTo("reculer") == 0) {	
		        		this.joueur.getAvion().reculer();
		        		System.out.print("Reculer : ");
		        		
		        	}
	        	}
	        	
	        	if (angle != null) {
		        	if (angle.compareTo("gauche") == 0) {
		        		this.joueur.getAvion().tournerGauche();
		        		System.out.print("Gauche : ");
		        		
		        	} else if (angle.compareTo("droite") == 0) {	
						this.joueur.getAvion().tournerDroite();
		        		System.out.print("Droite : ");
		        	}
	        	}
	        	
	        	System.out.println("x : " + this.joueur.getEtat()[0] + 
	        					   " y : " + this.joueur.getEtat()[1] + 
	        					   " angle : " + this.joueur.getEtat()[2] + 
	        					   " pseudo : " +  this.joueur.getEtat()[3]);
	        	
	        	try {
					this.ajouterMsgErreur("-1", "retour,id,requete", "OK", "" + this.joueur.getId(), requete);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        } else if (requete.compareTo("tire") == 0) {
	        	/**
	        	 * requete exemple pour changer le tire du joueur  
	        	 * http://localhost:8080/CrazyM2/nefertiti?requete=tire&id=1
	        	 **/
	        	
	        	this.id     = request.getParameter("id");
	        	this.joueur = this.carteActuel.getJoueur(Integer.parseInt(id));
	        	
	        	//Creation d'un objet de type bullet ajouter a la liste des graphiques de la carte
	        	this.joueur.getAvion().tirer(this.carteActuel.getListeDeGraphiques());
	        	
	        	try {
					this.ajouterMsgErreur("-1", "retour,id,requete", "OK", "" + this.joueur.getId(), requete);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        
	        // Retourner la liste mise a jour des elements présents sur la carte.
		    this.out.println(this.repondre());
        }        
	}
	
	public void ajouterMsgErreur(String id, String format, String... args) throws Exception {
		JSONObject contenu     = new JSONObject();
		JSONObject conteneur = new JSONObject();
		String[]   argsName  = format.split(",");
		int        i         = 0;	
		
		if (argsName.length != args.length)
			throw new Exception("Mismatch format length and args length.");
			
		for (String erreur : args) {
			try {
				contenu.put(argsName[i], erreur);				
				++i;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		try {
			conteneur.accumulate(id, contenu);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			this.erreur.accumulate("Erreur", conteneur);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Cette methode renvoie l'etat du serveur. 
	 **/
	public JSONObject repondre() {
		JSONObject etatServeur = new JSONObject();
		
		try {
			etatServeur.accumulate("Serveur", this.carteActuel.getEtat());
			etatServeur.accumulate("Serveur", this.erreur);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return etatServeur;
	}
}
