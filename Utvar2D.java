package ondrusek;

import java.awt.Graphics2D;

/**
 * Toto je abstraktná trieda Utvar2D, od ktorej dedia traja potomkovia.
 *
 */
public abstract class Utvar2D {

    protected int x;  // x-ová súradnica.
    protected int y;  // y-nová súradnica.
    protected int sirka;
    protected int vyska;

    public Utvar2D(int x, int y, int sirka, int vyska) {

        this.x = x;
        this.y = y;
        this.sirka = sirka;
        this.vyska = vyska;

    }
    
   public void vykresliSa(Graphics2D g) {
       
   }
   
    
}
