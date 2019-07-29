package ondrusek;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Trieda Lopta je potomok triedy Utvar2D.
 */
public class Lopta extends Utvar2D {
    
    private int loptaX = -1; // Atribútom LoptaX definujeme horizontálny smer lopty a odraz od okraja hracej plochy, obdlžníka alebo kocky.
    private int loptaY = -3; // Atribútom LoptaY definujeme vertikálny smer lopty a odraz lopty od okraja hracej plochy, obdlžníka alebo kocky.

    public Lopta(int x, int y, int sirka, int vyska) {
        super(x, y, sirka, vyska);

    }

    @Override
    public void vykresliSa(Graphics2D g) {   // Parameter Graphics2D predstavuje graficky kontext na kreslenie ľubovoľného grafického útvaru.

        // Kreslíme loptu
        g.setColor(Color.LIGHT_GRAY);    // V tejto metóde si nastavíme farbu ktorou budeme kresliť loptu.
        g.drawOval(x, y, sirka, vyska);  // Pomocou tejto metódy si nakreslíme loptu.
        g.fillOval(x, y, sirka, vyska);  //Pomocou tejto metódy si loptu vyplníme.

    }

    public void loptaPohyb() { // V tejto metóde definujeme ako sa lopta pohybuje po hracej ploche.

        x = x + loptaX; // K x-ovej súradnici pripočítavame atribút smerX a tým meníme polohu lopty.
        y = y + loptaY; // K y-novej súradnici pripočítavame atribút smerY a tým meníme polohu lopty.

        if (x < 0 || x > 975) { // V tejto podmienke testujeme, že kde je x-ová súradníca lopty, ak je na okraji hracej plochy, tak sa odrazí, ale v opačnom smere.
            loptaX = -loptaX;

        } else if (y < 0) { // V tejto podmienke testujeme, že kde je y-nová súradníca lopty, neplatí pre spodny okraj hracej plochy

            loptaY = -loptaY;
        }
    }

    public void odrazLoptaY() { //Odraz lopty verikálne, ale v opačnom smere.

        loptaY = -loptaY;
    }

    public void odrazLoptaX() {  // Odraz lopty horizontálne, ale v opačnom smere. 

        loptaX = -loptaX;
    }

    public Rectangle getOkraje() {  // Táto metoda vracia aktuálnu oblasť lopty.
        return new Rectangle(x, y, sirka, vyska);

    }

    /**
     * Get metódy.
     * @return 
     */
    public int getX() {
        return x;
    }

    public int getLoptaY() {
        return loptaY;
    }

    public int getloptaX() {
        return loptaX;
    }

    public int getY() {
        return y;
    }
    
}
