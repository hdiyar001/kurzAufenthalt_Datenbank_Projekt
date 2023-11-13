package datenhaltungsschicht;

import praesentationsschicht.ViewFactory;

/**
 *
 * @author Diyar
 */
public class Modell {

    private static Modell modell;

    private final ViewFactory viewFacotry;

    public ViewFactory getViewFacotry() {
        return viewFacotry;
    }

    public Modell() {
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
