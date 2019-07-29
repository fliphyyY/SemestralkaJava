package ondrusek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Kocky extends Utvar2D {

    private final int kocky[][];
    private Color farba;

    /**
     * V konštruktore vytvoríme dvojrozmerne pole, ktoré reprezentuje hracie
     * kocky. Pozor, atribúty x a y neznamenájú súradnice kociek, ale počet prvkov poľa.
     * @param x
     * @param y
     * @param sirka
     * @param vyska
     */
    public Kocky(int x, int y, int sirka, int vyska) {

        super(x, y, sirka, vyska);
        this.kocky = new int[x][y];
        for (int[] kocky1 : kocky) {
            for (int j = 0; j < kocky[0].length; j++) {
                kocky1[j] = 10;
                vyberFarbu();
            }
        }
    }

    public int[][] getKocky() {
        return kocky;
    }

 
    /**
     * V metóde vykresliSa() pole vyplním farbou a ďalej zviditeľnim rozdelenie na jednotlivé kocky.
     * @param g
     */
    @Override
    public void vykresliSa(Graphics2D g) {

        for (int i = 0; i < kocky.length; i++) {

            for (int j = 0; j < kocky[0].length; j++) {
                if (kocky[i][j] != 0) {
                    g.setColor(farba);
                    g.fillRect(j * sirka + 100, i * vyska + 80, sirka, vyska);

                    g.setStroke(new BasicStroke(5));
                    g.setColor(Color.black);
                    g.drawRect(j * sirka + 100, i * vyska + 80, sirka, vyska);

                }

            }
        }

    }
   

  
    public void zmazKocku(int hodnota, int x, int y) {
        kocky[x][y] = hodnota;
    }
/**
 * Táto metóda vybéra nahodne farbu kociek pomocou generátora náhodných čísiel.
 */
    private void vyberFarbu() {
        Random generator = new Random();
        int nahodneCislo = generator.nextInt(7);

        switch (nahodneCislo) {
            case 0:
                farba = Color.pink;
                break;
            case 1:
                farba = Color.red;
                break;
            case 2:
                farba = Color.yellow;
                break;
            case 3:
                farba = Color.CYAN;
                break;
            case 4:
                farba = Color.white;
                break;
            case 5:
                farba = Color.orange;
                break;
            default:
                farba = Color.LIGHT_GRAY;
                break;

        }
    }

}
