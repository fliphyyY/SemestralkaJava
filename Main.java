package ondrusek;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {

    public static void main(String[] args) {
        Okno okno = new Okno();
        okno.setVisible(true);
        doStredu(okno);
    }

    /**
     * Okno sa nám otvorí v strede monitora.
     * @param okno
     */
    public static void doStredu(Okno okno) {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int w = okno.getSize().width;
        int h = okno.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        okno.setLocation(x, y);
    }
}
