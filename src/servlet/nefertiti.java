package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import VaisseauEnnemi.Ennemi;
import AirCraft.Bullet;
import Base.Carte;
import Base.ListeDeGraphiques;
import player.Joueur;
import player.ListeDeJoueurs;

/**
 * Servlet implementation class nefertiti
 */
@WebServlet("/nefertiti")
public class nefertiti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Carte ma_carte=new Carte();
	
	public void init()
	{
		ma_carte.GetListeDeEnnemis().add(new Ennemi("ennemi1", 200, 100, 100, 100));
		ma_carte.GetListeDeEnnemis().add( new Ennemi("ennemi1", (int) (Math.random() * ( 800 )), (int) (Math.random() * ( 800 )), 100, 100));
		
	}
    /** 
     * @see HttpServlet#HttpServlet()
     */
    public nefertiti() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	     long time=System.currentTimeMillis();
	     ma_carte.DetectCollision();
	       ma_carte.Actualiser(time);
		response.setContentType("text/html");
        response.setCharacterEncoding( "UTF-8" );
        //requete exemple pour creer un nouveau joueur 
        //http://localhost:8080/CrazyM2/nefertiti?requete=nouveau&pseudo=jjj
        String requete=request.getParameter("requete");
        if (requete.compareTo("nouveau")==0)
        {
        String pseudo=request.getParameter("pseudo");
        Joueur player = new Joueur(pseudo);
        ma_carte.GetListeDeJoueurs().addJoueur(player);
        PrintWriter out = response.getWriter();
        out.println(ma_carte.GetAllGraphiquesPosition());
        }
        //requete exemple pour changer la position du joueur 
        //http://localhost:8080/CrazyM2/nefertiti?requete=jeu&id=1&sens=avancer&angle=droite
        else if (requete.compareTo("jeu")==0)
        {
        	String id=request.getParameter("id");
        	String sens=request.getParameter("sens");
        	String angle=request.getParameter("angle");
        	Joueur mon_joueur=ma_carte.GetListeDeJoueurs().searchJoueur(Integer.parseInt(id));
        	if (sens.compareTo("avancer")==0)
        	{	
        		mon_joueur.GetAvion().avancer();
        	}
        	else if (sens.compareTo("reculer")==0)
        	{	
        		mon_joueur.GetAvion().reculer();
        	}
        	else if (sens.compareTo("libre")==0)
        	{	
        	
        	}
        	if (angle.compareTo("gauche")==0)
        	{
        		mon_joueur.GetAvion().tournerGauche();
        	}
        	else if (angle.compareTo("droite")==0)
        	{	
        		mon_joueur.GetAvion().tournerDroite();
        	}	
            PrintWriter out = response.getWriter();
            out.println(ma_carte.GetAllGraphiquesPosition());	
        }
      //requete exemple pour changer le tire du joueur 
        //http://localhost:8080/CrazyM2/nefertiti?requete=tire&id=1
        else if (requete.compareTo("tire")==0)
        {
        	PrintWriter out = response.getWriter();
        	String id=request.getParameter("id");
        	Joueur mon_joueur=ma_carte.GetListeDeJoueurs().searchJoueur(Integer.parseInt(id));
        	//Creation d'un objet de type bullet ajouter a la liste des graphiques de la carte
        	ma_carte.GetListeDeGraphiques().add(new Bullet("Bullet",mon_joueur.GetAvion().GetX(),mon_joueur.GetAvion().GetY(),21,21,mon_joueur.GetAvion().getAngle()));
        	out.println(ma_carte.GetAllGraphiquesPosition());
        }
        
	}
}

