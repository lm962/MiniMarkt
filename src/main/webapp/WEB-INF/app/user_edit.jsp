<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        User bearbeiten
    </jsp:attribute>

   <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/"/>">Übersicht</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">
                    
                    <h1>Anschrift</h1>
                     <label for="signup_name">
                        Vor- und Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_name" value="${user_form.values["signup_name"][0]}">
                    </div>
                    
                    <label for="signup_strasse">
                        Straße:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_strasse" value="${user_form.values["signup_strasse"][0]}">
                    </div>

                    <label for="signup_hausnummer">
                        Hausnummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_hausnummer" value="${user_form.values["signup_hausnummer"][0]}">
                    </div>
                    
                    <label for="signup_postleitzahl">
                        Postleitzahl:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_postleitzahl" value="${user_form.values["signup_postleitzahl"][0]}">
                    </div>
                    
                    <label for="signup_ort">
                        Ort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_ort" value="${user_form.values["signup_ort"][0]}">
                    </div>
                    
                    <h1>Kontaktdaten</h1>
                    <label for="signup_telefon">
                        Telefon:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_telefon" value="${user_form.values["signup_telefon"][0]}">
                    </div>
                    
                    <label for="signup_email">
                        E-Mail:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_email" value="${user_form.values["signup_email"][0]}">
                    </div>
                    
                   
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Aktualisieren
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>