/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projet_dev_web_2021.servlet;


import com.mycompany.projet_dev_web_2021.fctPojo.FctJoueur;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pauline
 */
public class Joueur extends HttpServlet {
    
    
    /*@Override
    public void init() throws ServletException{
        super.init();
        System.out.println("hello");
    }*/

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        /*try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Joueur</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Joueur at " + request.getContextPath() + "</h1>");
            out.println("<label for=\"nomClient\">Nom <span class=\"requis\">*</span></label>");
            out.println("</body>");
            out.println("</html>");
        }*/
        
        String operation = request.getParameter("operation");
        String username = request.getParameter("Pseudo");
            String mdp = request.getParameter("mot de passe");
            String ville = request.getParameter("ville");
            String sexe = request.getParameter("Sexe");
            int age = Integer.parseInt(request.getParameter("Age"));

            FctJoueur fctj = new FctJoueur();
        if (operation.equals("connexion")) {
             
            boolean existe = false;
            /*int i = 0;
            ArrayList<String> listps = new ArrayList<String>();
            listps = fctj.getListe_Pseudo();
            while(i < fctj.getNb_Tot_Joueur() && !existe){
                if(username.equals(listps.get(i))){
                    existe = true;
                }
            }*/
            if(!existe){
              fctj.InitJoueur(username, mdp, age, sexe, ville);
              System.out.println(username);
              getServletConfig().getServletContext().getRequestDispatcher("/jeu.jsp").forward(request,response);
            }
                 
        }
        fctj.InitJoueur(username, mdp, age, sexe, ville);
        
         
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
