import Assistance.Assistance;
import View.ConsoleView;
import View.View;


public class Main {

    public static void main(String[] args) {
        Assistance<View> prog = new Assistance<View>(new ConsoleView());
        prog.start();
    }
}