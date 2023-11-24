package logikschicht;

import praesentationsschicht_GUI.ViewFactory;

/**
 * Die Klasse Modell implementiert das Singleton-Designmuster und stellt sicher,
 * dass nur eine Instanz dieser Klasse existiert. Sie hält eine Instanz der
 * ViewFactory, die für die Erstellung und Verwaltung der Benutzeroberfläche
 * zuständig ist. Durch das Singleton-Muster wird sichergestellt, dass alle
 * Teile der Anwendung dieselbe Instanz der ViewFactory verwenden.
 *
 * @author Diyar, Singleton Design Pattern
 */
public class Modell {

    private static Modell modell;
    private final ViewFactory viewFacotry;

    /**
     * Privater Konstruktor, um die direkte Instanziierung von außen zu
     * verhindern.
     */
    private Modell() {
        this.viewFacotry = new ViewFactory();
    }

    /**
     * Stellt die Singleton-Instanz der Klasse Modell bereit. Wenn die Instanz
     * noch nicht existiert, wird sie erstellt.
     *
     * @return Die Singleton-Instanz von Modell.
     */
    public static synchronized Modell getInstance() {
        if (modell == null)
        {
            modell = new Modell();
        }
        return modell;
    }

    /**
     * Gibt die ViewFactory-Instanz zurück, die für die Erstellung der
     * GUI-Komponenten verwendet wird.
     *
     * @return Die Instanz von ViewFactory.
     */
    public ViewFactory getViewFacotry() {
        return viewFacotry;
    }
}
