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

import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Task;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
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

 
     @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        User user = this.userBean.getCurrentUser();
        
        if(session.getAttribute("user_form")==null) {
        // Anfrage an dazugerhörige JSP weiterleiten
        request.setAttribute("user_form", this.createUserForm(user));
        
        }
        request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp").forward(request, response);
       
        session.removeAttribute("user_form");
        
    
    }

   @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        String action = request.getParameter("action");
        if(action==null) {
            action = "";
        }
        switch (action) {
            case "save":
                this.saveUser(request, response);
                break;
        }   
    }
    
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<String> errors = new ArrayList<>();
        
        String name = request.getParameter("user_name");
        String strasse = request.getParameter("user_strasse");
        String hausnummer = request.getParameter("user_hausnummer");
        String postleitzahl = request.getParameter("user_postleitzahl");
        String ort = request.getParameter("user_ort");
        String telefon = request.getParameter("user_telefon");                            
        String email = request.getParameter("user_email");
        
        // Eingaben prüfen
        User user = this.userBean.getCurrentUser();
      
            
            user.setName(name);
            user.setStrasse(strasse);
            user.setHausnummer(hausnummer);
            user.setPostleitzahl(postleitzahl);
            user.setOrt(ort);
            user.setTelefon(telefon);
            user.setEmail(email);
            
            this.validationBean.validate(user, errors);
            

        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
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
    }
     private FormValues createUserForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("user_Name", new String[]{
            user.getName()
        });
        
        values.put("user_Strasse", new String[]{
            user.getStrasse()
        });
        
        values.put("user_Hausnummer", new String[]{
            user.getHausnummer()
        });
        
        values.put("user_Postleitzahl", new String[]{
            user.getPostleitzahl()
        });
        
        values.put("user_Ort", new String[]{
            user.getOrt()
        });
        
        values.put("user_Telefon", new String[]{
            user.getTelefon()
        });
        
        values.put("user_Email", new String[]{
            user.getEmail()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
   
    
}
