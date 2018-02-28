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

import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.User;
import java.io.IOException;
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
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        String username = request.getParameter("signup_username");
        String password1 = request.getParameter("signup_password1");
        String password2 = request.getParameter("signup_password2");
        String name = request.getParameter("signup_name");
        String strasse = request.getParameter("signup_strasse");
        String hausnummer = request.getParameter("signup_hausnummer");
        String postleitzahl = request.getParameter("signup_postleitzahl");
        String ort = request.getParameter("signup_ort");
        String telefon = request.getParameter("signup_telefon");                            
        String email = request.getParameter("signup_email");
                 
        
        // Eingaben prüfen
        User user = new User(username, password1, name, strasse, hausnummer, postleitzahl, ort, telefon, email);
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (name == null || name.isEmpty() || strasse == null || strasse.isEmpty() || hausnummer == null || hausnummer.isEmpty()  || postleitzahl == null || postleitzahl.isEmpty() || ort == null || ort.isEmpty() || telefon == null || telefon.isEmpty() || email == null || email.isEmpty()) {
            errors.add("Bitte geben Sie alle Felder korrekt ein.");
            }
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
            }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, password1, name, strasse, hausnummer, postleitzahl, ort, telefon, email);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
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
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}
