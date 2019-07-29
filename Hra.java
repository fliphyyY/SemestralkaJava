package ondrusek;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Najroziahlejšia trieda z celého projektu. Táto triedu dedí z triedy Jframe,
 * čo je trieda, ktorá predstavuje hlavne okno. Ďalej implementuje rozhranie
 * KeyListener, čo znamená, že má schopnosť reagovať na zmačknutie klávesnice.
 * Rozhranie ActioListener má jedinú metódu actionPerformed(ActionEvent e)
 * pomocou ktorej naprogramujeme, čo sa má robiť, ak sa vykoná nejaká akcia.
 * Ďalej tu máme metódy pre zápis do súboru a čítanie zo súboru.
 */
public class Hra extends JPanel implements KeyListener, ActionListener {

    private Timer casovac; // Časovač
    private int delay = 5; // Oneskorenie časovača.
    private int kockyPocet = 30; //Celkový počet kociek na začiatku hry.
    private int score = 0; //Skóre, ktoré je na začiatku každej hry a v priebehu hrania sa mení.
    private ArrayList<Integer> tabulka = new ArrayList<>(); // ArrayList na uloženie score.
    private boolean hranie = false; //Premenna pomocou ktorej definujem či hra beží alebo nie.

    public Hra() {

        addKeyListener(this); // KeyListener registruje zmačknutia klávesnice.
        this.setBackground(Color.black); // Farba hracej plochy.
        this.setPreferredSize(new Dimension(1000, 800)); //Veľkosť okna, horizontálne * vertikálne.
        this.setFocusable(true); // Musí byť true, inač nám ovladanie z klávesníce nebude fungovať.
        this.casovac = new Timer(delay, this); //Vytvoríme novy objekt, časovač.

        try {
            nacitat();
        } catch (IOException | ClassNotFoundException e) {
        }
        casovac.start();

    }

    /**
     * Vytváram novú inštanciu obdlžníka, lopty a kociek. Zároveň nastavujem x,y
     * súradnice, šírku a výška u všetkých menovaných objektoch.
     */
    Lopta lopta = new Lopta(487, 500, 25, 25);
    Kocky kocky = new Kocky(3, 10, 80, 60);
    Obdlznik obdlznik = new Obdlznik(435, 760, 140, 10);
    

    public int getScore() {
        return score;
    }

    /**
     * V metóde paintComponent() najkôr zavolám rodičovskú metódu, ktorá mi
     * vykreslí pozadie. Potom zavolám metódu vykresliSa(g) a nakreslím
     * obdlžník,loptu a kocky. Obdlžník rozpohybujeme tak, že mu budeme meniť x
     * a y súradnicu a znova ho nakreslíme na hraciu plochu.
     */
    @Override

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // kreslím útvary
        obdlznik.vykresliSa((Graphics2D)g);
        lopta.vykresliSa((Graphics2D)g);
        kocky.vykresliSa((Graphics2D) g);

        // okraje
        g.setColor(Color.RED);
        g.fillRect(0, 0, 3, 800);
        g.fillRect(0, 0, 1000, 3);
        g.fillRect(998, 0, 3, 800);

        // skóre
        g.setColor(Color.GREEN);
        g.setFont(new Font("Courier", Font.BOLD, 30));
        g.drawString("" + score, 20, 30);

        if (this.hranie == false) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("High score: " + highScore(), 400, 350);
            g.drawString("Hru začnete zmačknutím klávesy A alebo klávesy D", 260, 400);
            g.drawString("Ovládanie odlžníka: A - pohyb doľava", 290, 600);
            g.drawString(" D - pohyb doprava", 475, 650);

        }

        if (this.kockyPocet <= 0) { //Ak je počet kociek nulový, tak potom sa hra skončila a hráč vyhral.
            this.casovac.stop();
            this.hranie = false;

            // výpis textu pre výhru
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier", Font.BOLD, 60));
            g.drawString("Vyhral si !!!", 340, 400);
            g.drawString("Score: " + getScore(), 320, 500);
            try {
                zapisat(getScore());
            } catch (IOException e) {
                g.drawString("Chyba pri ukladaní", 340, 550);
            }
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("Stlačte medzernik na reštart", 365, 600);
        }

        if (lopta.getY() >= 800) { //V tejto podmienke sa overuje, že či je lopta za dolným okrajom hracej plochy. Ak áno tak hra sa skončila, lebo sa lopta dostala mimo hracej plochy.

            this.hranie = false;

            //výpis textu pre prehru
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier", Font.BOLD, 40));
            g.drawString("Hra sa skončila", 350, 400);
            g.drawString("Score: " + getScore(), 375, 500);
            try {
                zapisat(getScore());
            } catch (IOException e) {
                g.drawString("Chyba pri ukladaní", 340, 550);
            }
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("Stlačte medzernik na reštart", 365, 600);
        }

    }

    /**
     * Vracia najvyššie dosiahnuté score.
     */
    private int highScore() {

        if (tabulka.isEmpty()) {
            return 0;

        } else {
            return tabulka.get(0);
        }

    }

    /**
     * Prebehneme ArrayList a usporiadame zapísane údaje od najvyššieho po
     * najmenší.
     */
    private void zapisat(int score) throws IOException {
        this.tabulka.add(score);
        int pom;
        for (int i = 0; i < tabulka.size() - 1; i++) {
            for (int j = i + 1; j < tabulka.size(); j++) {
                if (tabulka.get(i) < tabulka.get(j)) {
                    pom = tabulka.get(i);
                    tabulka.set(i, tabulka.get(j));
                    tabulka.set(j, pom);
                }
            }
        }
        ulozit();
    }

    /**
     * Zapisovanie do súboru.
     */
    private void ulozit() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("save.bin")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tabulka);
            oos.close();
        }
    }

    /**
     * Načítavanie zo súboru.
     */
    private void nacitat() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("save.bin"); ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.tabulka = (ArrayList<Integer>) ois.readObject();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Hra reaguje iba na tri klávesy klávesy A,D a Space. Klávesy A,D slúžia na
     * pohyb obdlžníka. Klávesa Space na reštartovanie hry.
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

        /**
         * Ak zmačknem klávesu D, tak sa zavola metóda doprava(), ktorá mi
         * posunie obdlžník doprava a ak zmačknem klávesu A, tak sa zavolá
         * metóda doľava() a obdlžník sa posunie doľava.
         */
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.hranie = true;
            obdlznik.doprava();
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.hranie = true;
            obdlznik.doľava();
        }

        /**
         * Klávesu Space zmačknem iba vtedy, keď prehrám alebo vyhrám. Po jej
         * zmačknutí sa mi znova vykreslia všetke útvary na hracej ploche, ale
         * iba vtedy, keď sa lopta dostane za dolný okraj panelu alebo keď je
         * počet kociek nulový, čiže som vyhral. Zároveň sa mi nastavia aj
         * atribúty na pôvodný stav.
         */
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (this.hranie == false && lopta.getY() >= 800 || this.score == 30000) {
                this.hranie = false;
                this.lopta = new Lopta(487, 500, 25, 25);
                this.obdlznik = new Obdlznik(435, 760, 140, 10);
                this.score = 0;
                this.kockyPocet = 30;
                this.kocky = new Kocky(3, 10, 80, 60);
                this.casovac.start();
                repaint();

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Časovač nám vytvára udalosti ActionEvent. Keď ju vytvorí, tak sa zavolá
     * metoda actionPerformed a v nej sa vykoná to čo je naprogramované.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.hranie == true) {
            casovac.start();
            lopta.loptaPohyb();
            if (obdlznik.getOkraje().intersects(lopta.getOkraje()) && lopta.getLoptaY() > 0) {
                lopta.odrazLoptaY();
            }

            for (int i = 0; i < kocky.getKocky().length; i++) {
                for (int j = 0; j < kocky.getKocky()[0].length; j++) {
                    if (kocky.getKocky()[i][j] != 0) {

                        int kockaX = j * kocky.sirka + 100;
                        int kockaY = i * kocky.vyska + 80;
                        int kockaSirka = kocky.sirka;
                        int kockaVyska = kocky.vyska;

                        Rectangle r = new Rectangle(kockaX, kockaY, kockaSirka, kockaVyska);
                        if (lopta.getOkraje().intersects(r)) {

                            kocky.zmazKocku(0, i, j);
                            kockyPocet--;
                            score = score + 1000;

                            if (lopta.getX() + 24 <= r.x || lopta.getX() + 1 >= r.x + r.width) {
                                lopta.odrazLoptaX();
                            } else {

                                lopta.odrazLoptaY();
                            }

                            break;

                        }

                    }
                }

            }
            repaint();
        }
    }

}
