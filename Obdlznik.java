package ondrusek;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Trieda Lopta je potomok triedy Utvar2D.
 */
public class Obdlznik extends Utvar2D {

    public Obdlznik(int x, int y, int sirka, int vyska) {

        super(x, y, sirka, vyska);

    }

    @Override
    public void vykresliSa(Graphics2D g) {

        //Kreslíme obdĺžnik.
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, sirka, vyska);
        g.drawRect(x, y, sirka, vyska);

    }

    /**
     * Pomocou tejto metódy ovladáme obddĺžnik pomocou klávesníce, doprava a
     * doľava.
     */
    public void doprava() {

        if (x >= 860) {  // Táto podmienka overuje, že či x-ová súradnica obddĺžnika je väčšia alebo rovná ako 860, ak áno tak už sme dosiahli maximálnu možnú polohu.
            x = 860;
        } else {
            x = x + 20; //Ak nie je splnená podmienka tak, potom môžeme meniť x-ovú súradnícu a budeme posúvať obdľžnik doprava.
        }
    }

    public void doľava() {

        if (x <= 0) { // To isté ako pre pohyb doprava, ale tu testujeme ľavý okraj hracej plochy.
            x = 0;
        } else {
            x = x - 20; //Ak nie je splnená podmienka, tak potom môžeme meniť  x-ovú súradnícu a budeme posúvať obdľžnik doľava.

        }
    }

    public Rectangle getOkraje() { // Táto metóda vracia aktuálnu oblasť obdlžníka.
        return new Rectangle(x, y, sirka, vyska);

    }

}
