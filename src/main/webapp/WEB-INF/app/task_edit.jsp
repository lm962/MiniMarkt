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

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Angebot bearbeiten
            </c:when>
            <c:otherwise>
                Angebot anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/"/>">Übersicht</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="task_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="task_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${task_form.values["task_category"][0] == category.id ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="task_status">
                    Art des Angebots:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="task_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${task_form.values["task_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="task_short_text">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="task_short_text" value="${task_form.values["task_short_text"][0]}">
                </div>

                <label for="task_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="task_long_text"><c:out value="${task_form.values['task_long_text'][0]}"/></textarea>
                </div>
                
                
                
                
                
                
               <label for="price_status">
                    Preis:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="price_status">
                        <c:forEach items="${types}" var="typ">
                            <option value="${typ}" ${task_form.values["price_status"][0] == typ ? 'selected' : ''}>
                                <c:out value="${typ.label}"/>
                            </option>
                        </c:forEach>
                    </select>
     
                    <div class="side-by-side">
                        <input type="text" name="task_preis" value="${task_form.values["task_preis"][0]}">
                    </div>
                </div>
                
                
                
                
                
                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
                </div>
            
               
           <label for="task_due_date">
                    Angelegt:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="task_due_date" value="${task_form.values["task_due_date"][0]}" readonly="readonly">
                    <input type="text" name="task_due_time" value="${task_form.values["task_due_time"][0]}" readonly="readonly">
                </div>
                
                
            <label for="task_owner">Anbieter:</label>   <!-- todo: Überarbeiten der Adresse -->
                <div class="side-by-side">
                    <input type="text" name="task_name" value="${task_form.values["task_name"][0]}" readonly="readonly">
                </div>
                <div class="side-by-side">
                    <input type="text" name="task_strasse" value="${task_form.values["task_strasse"][0]}" readonly="readonly">
                    <input type="text" name="task_hausnummer" value="${task_form.values["task_hausnummer"][0]}" readonly="readonly">
                </div>
                <div class="side-by-side">
                    <input type="text" name="task_postleitzahl" value="${task_form.values["task_postleitzahl"][0]}" readonly="readonly">
                    <input type="text" name="task_ort" value="${task_form.values["task_ort"][0]}" readonly="readonly">
                </div>
            </div>   

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty task_form.errors}">
                <ul class="errors">
                    <c:forEach items="${task_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>