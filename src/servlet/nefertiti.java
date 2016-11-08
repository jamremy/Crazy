package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import player.Joueur;
import player.ListeDeJoueurs;

/**
 * Servlet implementation class nefertiti
 */
@WebServlet("/nefertiti")
public class nefertiti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ListeDeJoueurs ldj = new ListeDeJoueurs();
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
		response.setContentType("text/html");
        response.setCharacterEncoding( "UTF-8" );
        //requete exemple pour creer un nouveau joueur 
        //http://localhost:8080/CrazyM2/nefertiti?requete=nouveau&pseudo=jjj
        String requete=request.getParameter("requete");
        if (requete.compareTo("nouveau")==0)
        {
        String pseudo=request.getParameter("pseudo");
        Joueur player = new Joueur(pseudo);
        ldj.addJoueur(player);
        PrintWriter out = response.getWriter();
        out.println(ldj.allPositions());
        }
        //requete exemple pour changer la position du joueur 
        //http://localhost:8080/CrazyM2/nefertiti?requete=jeu&id=1&sens=avancer&angle=droite
        else if (requete.compareTo("jeu")==0)
        {
        	String id=request.getParameter("id");
        	String sens=request.getParameter("sens");
        	String angle=request.getParameter("angle");
        	Joueur mon_joueur=ldj.searchJoueur(Integer.parseInt(id));
        	if (sens.compareTo("avancer")==0)
        	{	
        		mon_joueur.GetAvion().avancer();
        		System.out.println("kjoklckdd");
        	}
        	else if (sens.compareTo("reculer")==0)
        	{	
        		mon_joueur.GetAvion().reculer();
        		System.out.println("kjvcdsfvdsoklckdd");
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
            out.println(ldj.allPositions());	
        }
	}
}
