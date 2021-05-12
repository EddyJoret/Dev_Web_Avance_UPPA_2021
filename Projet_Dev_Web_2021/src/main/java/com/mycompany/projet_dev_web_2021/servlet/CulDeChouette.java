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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pauline
 */

public class CulDeChouette extends HttpServlet {

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
        FctJoueur fctj = new FctJoueur();
        response.setContentType("text/html;charset=UTF-8");
        String operation = request.getParameter("operation");
        
         if (operation.equals("inscription")) {
            String username = request.getParameter("Pseudo");
            String mdp = request.getParameter("mot de passe");
            String ville = request.getParameter("ville");
            String sexe = request.getParameter("Sexe");
            int age = Integer.parseInt(request.getParameter("Age"));
           
            ArrayList<String> ps = new ArrayList<String>();
            ps = fctj.getListe_Pseudo();
            boolean existe = false;
            int i = 0;
            
            while(i < fctj.getNb_Tot_Joueur() && !existe){
                if(username.equals(ps.get(i))){
                    existe = true;
                }
                i++;
            }
            
            if(!existe){
                fctj.InitJoueur(username, mdp, age, sexe, ville);
                getServletConfig().getServletContext().getRequestDispatcher("/jeu.jsp").forward(request,response);
                System.out.println(request.getContextPath());
            }else{
                System.out.println("pseudo déjà existant");
                getServletConfig().getServletContext().getRequestDispatcher("/connexionJeu.jsp").forward(request,response);
            } 
         }
         
        if(operation.equals("connexion")){
            String usernameco = request.getParameter("usernameco");
            String mdpco = request.getParameter("mdpco");
            ArrayList<String> ps = new ArrayList<String>();
            ps = fctj.getListe_Pseudo();
            boolean existe = false;
            int i = 0;
            
            while(i < fctj.getNb_Tot_Joueur() && !existe){
                if(usernameco.equals(ps.get(i)) && mdpco.equals(fctj.getMdp(usernameco))){
                    existe = true;
                }
                i++;
            }
            
            if(existe){
                request.setAttribute("pseudo", usernameco);
                //response.sendRedirect(request.getContextPath() + "/jeu.jsp");
                getServletConfig().getServletContext().getRequestDispatcher("/jeu.jsp").forward(request,response);
            }else{
                System.out.println("joueur pas existant");
                getServletConfig().getServletContext().getRequestDispatcher("/connexionJeu.jsp").forward(request,response);
            } 
        }
        
        if(operation.equals("essai")){
            essai();
        }
        
        
    }
    
    public static void essai(){
        
        System.out.println("ok reussi");
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
            Logger.getLogger(CulDeChouette.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CulDeChouette.class.getName()).log(Level.SEVERE, null, ex);
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
