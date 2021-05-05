package entitiesManager.enemiesController;

import java.awt.*;
import java.util.LinkedList;

import bullets.EnemyBullet;
import entities.*;
import flags.Flag;
import sounds.PlaySound;
import sounds.SoundAssets;
import sounds.SoundsManager;
import states.GameState;

//klasa zarzadza wszystkimi wrogami na raz
public class EnemiesController {

    //zmienna okreslajaca ile razy przeciwnicy byli restartowani
    private final int level;

    //indeks przeciwnika do zmiany widocznosci
    private int visibilityIndex = 0;

    private Flag deathFlag = null;

    //najnizej polozony przeciwnik na osi Y w danym momencie
    private Enemy lowestEnemy;

    //lista zawierajaca wrogow, sa oni dodawani po kolei kolumnami
    public LinkedList<Enemy> enemies = new LinkedList<>();

    //lista zawierajaca pociski wrogow
    public LinkedList<EnemyBullet> bullets = new LinkedList<>();

    private float xSpeed = 0.3f; //predkosc poruszania sie wszystkich przeciwnikow w osi x

    private float yJump = 10f; //predkosc poruszania sie wszystkich przeciwnikow w osi y
    private final float MAX_Y;

    private float addxSpeed = 0.1f; //stala dodawana do predkosci przeciwnikow
    private final float MAX_SPEED = 4f;

    private final GameState state;

    private SoundsManager enemiesSounds; //zarzadzanie dzwiekami ogolnymi jako calosc dla przeciwnikow, nie poszczegolnie dla kazdego


    //ustawienie kierunku poruszania sie wrogow
    private boolean direction = true; //jesli true, wrogowie przesuwaja sie w prawo....

    public EnemiesController(GameState state,int level) {
        this.state = state;
        this.level = level;

        MAX_Y = state.height - 240;

        enemiesSounds = new SoundsManager(SoundAssets.invaderSounds, 1000); //dzwieki przy zmianie animacji przeciwnikow, zmiana co sekunde

        //tworzenie domyslnych wrogow, domyslnie 9 w osi x, a 4 o osi y
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 4; j++) {

                //wrogowie sa tworzeni co stala odleglosc 100 pikseli w osi x (dodatkowo +1, zeby pierwszy przeciwnik nie byl na osi x = 0,
                //co triggeruje zmiane kierunku poruszania) i osi y (dodatkowo +80 w osi y)
                //nadawany jest numer id (zalezny od i, czyli numeru kolumny) dla kazdego rzedu, do rozpoznawania ktory wrog jest ostatni w rzedzie

                //osmiornice na 2 pierwszych wierszach
                if (j == 0) {
                    enemies.add((Enemy) new Headcrab(i * 100 + 1, j * 100 + 50, i, state, level)); //dodawanie kraba
                }
                else if(j == 1){
                    enemies.add((Enemy) new Octopus(i * 100 + 1, j * 100 + 50, i, state, level)); //dodawanie osmiornicy
                }
                else{
                    enemies.add((Enemy) new Monster(i * 100 + 1, j * 100 + 50, i, state, level)); //dodanie zwyklych potworow na pozostalych wierszach
                }
            }

        //dawanie mozliwosci strzelania wrogom na dole,
        //sprawdzanie czy wrog jest na dole na podstawie id, kazdy rzad ma swoje id, wiec jesli kolejny przeciwnik
        //ma inne id niz obecnie sprawdzany, to musi byc on ostatni w rzedzie
        for (int i = 0; i < enemies.size(); i++) {
            if (i < enemies.size() - 1) {
                if (enemies.get(i).getId() != enemies.get(i + 1).getId())
                    enemies.get(i).canShoot = true;
            }
        }
        enemies.getLast().canShoot = true; //ostatni przeciwnik w liscie zawsze jest na dole rzedu, wiec zawsze moze strzelac

        lowestEnemy = getLowestEnemy();

        updateLevelSettings();
    }

    //updatetuje inne zmienne w zaleznosci od poziomu level
    private void updateLevelSettings(){
        xSpeed += (2 * level / 10.);
        addxSpeed += level / 10.;
        yJump += level;
    }


    //zmiana widocznosci przeciwnikow dla efektu tworzenia ich po kolei
    private void updateVisibility() {
        //zmiana co 4/100 sekundy
        if (state.getScore().getCounter() % 4 == 0) {
            enemies.get(visibilityIndex).isVisible = true;
            visibilityIndex++;
        }
    }

    // zmiana szybkosci animacji na podstawie ilosci przeciwnikow wedlug wzoru dopasowanego do ilosci przeciwnikow,
    // im wiecej przeciwnikow jest, tym mocniej przyspiesza
    private int enemiesAnimation(int x) {
        double y = 6 * (Math.log10(x) / Math.log10(2)); //6log2(x)

        return (int) y;
    }

    // zwraca wroga najnizej na ekranie w danym momencie
    private Enemy getLowestEnemy() {
        if (enemies.isEmpty())
            return null;

        Enemy lowest = enemies.get(0);

        for (Enemy enemy : enemies) {
            if (enemy.getY() > lowest.getY())
                lowest = enemy;
        }
        return lowest;
    }


    // obliczenia dokonywane, jesli zasygnalizowana jest zmiana ilosci przeciwnikow
    public void enemiesSizeChanged() {

        //dzwiek trafienia wroga
        PlaySound.play(SoundAssets.enemyShot);

        deathFlag = new Flag(500); // zatrzymywanie wszystkich wrogow w ruchu do skonczenia flagi

        //aktualizowanie mozliwosci strzelania przeciwnikom, ktorzy sa na dole rzedu
        for (int i = 0; i < enemies.size(); i++) {
            if (i < enemies.size() - 1) {
                // sprawdzanie czy wrog jest na dole
                if (enemies.get(i).getId() != enemies.get(i + 1).getId())
                    enemies.get(i).canShoot = true;
            }

            // zwiekszanie prawdopodobienstwa oddania strzalu wszystkim przeciwnikom
            enemies.get(i).changeProbability();

            // zmiana szybkosci animacji wszystkich przeciwnikow
            enemies.get(i).animation.changeSpeed(enemies.get(i).animation.getSpeed() - enemiesAnimation(enemies.size()));
        }
        // zmiana szybkosci dzwiekow wszystkich przeciwnikow, jest dopasowana do szybkosci animacji
        enemiesSounds.changeSpeed(enemies.getFirst().animation.getSpeed());

        enemies.getLast().canShoot = true; //ostatni przeciwnik w liscie zawsze jest na dole rzedu, wiec zawsze moze strzelac

        lowestEnemy = getLowestEnemy();
    }

    public void update() {
        //jesli nie ma przeciwnikow
    	if(enemies.isEmpty()){
    	    state.getEM().restartEnemies();
            return;
        }

        //zmiana widocznosci przeciwnikow dla efektu tworzenia ich po kolei
        if (!enemies.getLast().isVisible) {
            updateVisibility();
            return;
        }

		// zmiana ruchu przeciwnikow lewo/prawo i ruch w dol
        //jesli pierwszy przeciwnik dotyka lewej strony okienka
        if (enemies.getFirst().getX() <= 0) {
            direction = true; //zmiana kierunku poruszania

            lowestEnemy = getLowestEnemy();

            //przesuniecie przeciwnikow, jesli czy zaden przeciwnik nie jest trafiony (bez tego powoduje bug)
            if (lowestEnemy.getY() + yJump < MAX_Y && deathFlag == null) {
                for (Enemy enemy : enemies)
                    enemy.move(0, yJump);
            }

            if (xSpeed < MAX_SPEED && deathFlag == null)
                xSpeed += addxSpeed; //dodawanie predkosci, jesli czy zaden przeciwnik nie jest trafiony (bez tego powoduje bug)
        }
        //jesli ostatni (calkowicie z prawej) przeciwnik dotyka prawej strony okienka
        else if (enemies.getLast().getX() + enemies.getLast().width >= state.width) {
            direction = false; //zmiana kierunku poruszania

            lowestEnemy = getLowestEnemy();

            //przesuniecie przeciwnikow, jesli czy zaden przeciwnik nie jest trafiony (bez tego powoduje bug)
            if (lowestEnemy.getY() + yJump < MAX_Y && deathFlag == null) {
                for (Enemy enemy : enemies)
                    enemy.move(0, yJump);
            }

            if (xSpeed < MAX_SPEED && deathFlag == null)
                xSpeed += addxSpeed; //dodawanie predkosci, jesli czy zaden przeciwnik nie jest trafiony (bez tego powoduje bug)
        }


        // ruch wszystkich wrogow w lewo/prawo w zaleznosci od kierunku poruszania, jesli skonczyla sie animacja trafienia
        if (deathFlag == null) {
            for (Enemy enemy : enemies) {
                if (direction)
                    enemy.move(xSpeed, 0);
                else
                    enemy.move(-xSpeed, 0);
            }
        }

        //updatewanie animacji trafienia, jesli jakikolwiek przeciwnik jest trafiony i sprawdzanie czy minela animacja trafienia
        if (deathFlag != null) {
            deathFlag.update();

            if (!deathFlag.isRunning()) {
                deathFlag = null;
            }
        }

        //update przeciwnikow
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        //update dzwiekow
        enemiesSounds.update();

        // updatuje pociski wrogow
        for (int i = 0; i < bullets.size(); i++)
            bullets.get(i).update();
    }


    public void render(Graphics g) {
        //renderuje wszystkich wrogow
        for (Enemy enemy : enemies) {
            if (enemy.isVisible)
                enemy.render(g);
        }

        //renderuje pociski wrogow
        for (EnemyBullet bullet : bullets)
            bullet.render(g);


        //Granica do ktorej schodza wrogowie na dol
        //g.setColor(Color.GREEN);
        //g.fillRect(10, (int) state.getEM().getPlayer().getY() - 140, 10, 10);
    }

    public int getLevel(){return level;}
}
