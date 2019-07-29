package ondrusek;

import javax.swing.JFrame;

public class Okno extends JFrame {

    public Okno() {

        this.setTitle("Brick Breaker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Hra hra = new Hra();
        this.add(hra);
        this.pack();

    }

}
