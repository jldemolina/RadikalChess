import radikalchess.controller.SwingApplicationController;

/**
 * This is the main class if we talk about Swing. Responsible for calling the controller,
 * which in turn is responsible for connecting the view and model.
 *
 * @author Jose Luis Molina
 * @see radikalchess.controller.SwingApplicationController
 */
public class SwingApplication {

    public static void main(String[] args) {
        new SwingApplicationController().execute();
    }
}
