/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.minimarkt.web;

import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Category;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Task;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MildL
 */

@WebServlet(urlPatterns = {"/app/user/"})
public class UserServlet extends HttpServlet {

    @EJB
    UserBean userBean;
    
    @EJB
    TaskBean taskBean;

    @EJB
    ValidationBean validationBean;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        User user = this.userBean.getCurrentUser();
        if(session.getAttribute("user_form")==null) {
        // Anfrage an dazugerhörige JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp").forward(request, response);
        session.removeAttribute("user_form");
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
          
        String username = request.getParameter("user_username");
        String password1 = request.getParameter("user_password1");
        String name = request.getParameter("user_name");
        String strasse = request.getParameter("user_strasse");
        String hausnummer = request.getParameter("user_hausnummer");
        String postleitzahl = request.getParameter("user_postleitzahl");
        String ort = request.getParameter("user_ort");
        String telefon = request.getParameter("user_telefon");                            
        String email = request.getParameter("user_email");
        
        // Eingaben prüfen
        User userNeu = new User(username, password1, name, strasse, hausnummer, postleitzahl, ort, telefon, email);
        User userAlt = this.userBean.getCurrentUser();
        List<String> errors = this.validationBean.validate(userNeu);
        this.validationBean.validate(userNeu.getPassword(), errors);
      
         if (name == null || name.isEmpty() || strasse == null || strasse.isEmpty() || hausnummer == null || hausnummer.isEmpty()  || postleitzahl == null || postleitzahl.isEmpty() || ort == null || ort.isEmpty() || telefon == null || telefon.isEmpty() || email == null || email.isEmpty()) {
            errors.add("Bitte geben Sie alle Felder korrekt ein.");
         }
         
          // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("user_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
        userAlt.setStrasse(username);
        userAlt.setHausnummer(hausnummer);
        userAlt.setPostleitzahl(postleitzahl);
        userAlt.setOrt(ort);
        userAlt.setTelefon(telefon);
        userAlt.setEmail(email);
        userAlt.setUsername(username);
        
        
        
        
    }
   
    
}
