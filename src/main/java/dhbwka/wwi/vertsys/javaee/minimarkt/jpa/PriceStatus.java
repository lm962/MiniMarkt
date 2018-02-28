/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.minimarkt.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum PriceStatus {
    VERHANDLUNGSBASIS, FESTPREIS;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case VERHANDLUNGSBASIS:
                return "Verhandlungsbasis";
            case FESTPREIS:
                return "Festpreis";
            default:
                return this.toString();
        }
    }
}
