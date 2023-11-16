package logikschicht;

import praesentationsschicht_GUI.ViewFactory;

/**
 *
 * @author Diyar, Singleton Design Pattern
 *
 * Das Singleton-Muster ist ein Entwurfsmuster, das sicherstellt, dass eine
 * Klasse nur eine einzige Instanz hat
 */
public class Modell {

    private static Modell modell;

    private final ViewFactory viewFacotry;

    public ViewFactory getViewFacotry() {
        return viewFacotry;
    }

    private Modell() {
        this.viewFacotry = new ViewFactory();
    }

    public static synchronized Modell getInstance() {
        if (modell == null)
        {
            modell = new Modell();
        }
        return modell;
    }
}
