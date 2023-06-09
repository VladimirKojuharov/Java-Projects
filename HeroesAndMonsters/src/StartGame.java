import java.util.ArrayList;
import java.util.Scanner;

public class StartGame {


    public static final int ALL_GAME_LEVELS = 40;
    public static String playerName;
    public static Hero player1;
    public static Monster enemy = new Monster();
    public Armor armor;
    public Helm helm;
    private String hero = " ";
    private Weapon club = new Weapon("Club");
    boolean gameOn = true;
    public static int playerGameProgress;
    public static boolean gameStatusOn = true;
    public static ArrayList<Spell> spellsList = new ArrayList<>();

    private String[] spellsNameList = {"HEAL", "FIREBALL", "STONE SKIN", "MANA BURN", /*"BLIND",*/ "BLIZZARD", "SLOW", "STUN STRIKE", "HASTE", "LIFE DRAIN"};

    Scanner scanner = new Scanner(System.in);

    public StartGame() {    // DEFAUT CONSTRUCTOR
    }

    public void runGame() throws InterruptedException {
        while (gameOn) {
            this.playerGameProgress = 0;
            this.setPlayerName();
            Armor armor = new Armor();
            this.armor = armor;
            Helm helm = new Helm();
            this.helm = helm;
            this.setHero();
            Weapon basicWeapon = new Weapon("club");
            player1.changeHeroWeapon(basicWeapon);
            player1.equipHeroArmor(armor);
            player1.equipHeroHelm(helm);
            generateSpellsList();
            StartGame.player1.learNewSpell();
            StartGame.player1.learNewSpell();

            while (gameStatusOn == true && player1.getHeroCurrentHealth() >= 1 && playerGameProgress < ALL_GAME_LEVELS) {
                pickTheEnemy();
                if (gameStatusOn == false) {
                    return;
                }
                Fight.Battle();

            }
            String playAgain = " ";
            while (!playAgain.toUpperCase().equals("Y") && !playAgain.toUpperCase().equals("N")) {
                System.out.println("PLAY AGAIN? (Y/N)");
                playAgain = scanner.nextLine();
                System.out.printf("%n%n%n%n%n%n");
                if (playAgain.toUpperCase().equals("N")) {
                    gameOn = false;
                }
            }
        }
    }

    public void generateSpellsList() {
        spellsList.clear();
        for (int i = 0; i < spellsNameList.length; i++) {

            Spell spell = new Spell(spellsNameList[i]);  // Array.get(spellsNameList, i).toString()

            this.spellsList.add(spell);
        }
    }


    public static ArrayList<Spell> getSpellsList() {
        return spellsList;
    }

    private Hero setHero() {
        System.out.printf("%n%n%n%n%n%n%n%n");
        System.out.printf("    " + playerName + " NOW IS THE TIME TO SEE YOUR POSSIBLE HERO SPECIALIZATION. CHOOSE WISELY!%n");
        this.hero = " ";
        while (!(this.hero.equals("1") || this.hero.equals("2") || this.hero.equals("3"))) {
            System.out.println();
            System.out.println("                                   SELECT YOUR HERO:");
            System.out.println();
            System.out.println("                        *1*               *2*                 *3*  ");
            System.out.println("                       MAGE             WARRIOR              ROGUE");
            System.out.println("    Health:           70 (+5)          100 (+10)             90 (+7)");
            System.out.println("    HP Reg:          0.6 (+0.6)          1 (+0.8)           0.7 (+0.7)");
            System.out.println("    Armor:             0 (+0.5)          1 (+1)               1 (+0.5)");
            System.out.println("    Shield:            5 (+1)            1 (+0)               1 (+0)");
            System.out.println("    DMG:               8 (+0.8)         12 (+2)              10 (+1)");
            System.out.println("    Attack Speed:    1.0 (-0.02)       1.0 (-0.02)          0.8 (-0.02)");
            System.out.println("    Crit. Chance:    0.5 (+0.5)        1.5 (+0.5)             3 (+1)");
            System.out.println("    Stun Chance:     0.5 (+0.5)          1 (+1)             0.5 (+0.5) ");
            System.out.println("    Spell Ppower:      1 (+1)         0.33 (+0.33)          0.5 (+0.5)");
            System.out.println("    Magic Resist:      2 (+2)            1 (+1)               1 (+1)");
            System.out.println("    Mana:              1 (+0.25)         0 (+0)               0 (+0)");
            System.out.println("    M.Reg/Sec:       1.5 (+0.5)        1.2 (+0.2)           1.3 (+0.3)");
            System.out.println();
            System.out.println("     (1)Mage   (2)Warrior   (3)Rogue:  ");  // (4) Random
            Scanner scanner = new Scanner(System.in);
            hero = scanner.nextLine();
        }
        Weapon weapon = new Weapon("Club");
        if (hero.equals("1")) {
            player1 = new Hero(playerName, HeroType.MAGE, club, armor, helm);


        } else if (hero.equals("2")) {
            player1 = new Hero(playerName, HeroType.WARRIOR, club, armor, helm);
        } else if (hero.equals("3")) {
            player1 = new Hero(playerName, HeroType.ROGUE, club, armor, helm);
        }
//        System.out.println(player1.showEquippedHeroStats());
        return player1;
    }

    public void showAllEnemies(Monster monster1, Monster monster2, Monster monster3) {
        String whiteSpaces = "                                    "; //36 WHITE SPACES
        System.out.println("    *I*                  *II*                 *III*");
        System.out.println(monster1.getMonsterName() + whiteSpaces.substring(0, 22 - monster1.getMonsterName().length()) + monster2.getMonsterName() + whiteSpaces.substring(0, 22 - monster2.getMonsterName().length()) + monster3.getMonsterName() + whiteSpaces.substring(0, 33 - monster1.getMonsterName().length()) + StartGame.playerName);
        System.out.println("LVL:     " + whiteSpaces.substring(0, 2 - Integer.toString(monster1.getMonsterLVL()).length()) + monster1.getMonsterLVL() + "           LVL:     " + whiteSpaces.substring(0, 2 - Integer.toString(monster2.getMonsterLVL()).length()) + monster2.getMonsterLVL() + "           LVL:     " + whiteSpaces.substring(0, 2 - Integer.toString(monster3.getMonsterLVL()).length()) + monster3.getMonsterLVL() + whiteSpaces.substring(0, 21) + "LVL:          " + StartGame.player1.getHeroLVL() + "     XP:   " + whiteSpaces.substring(0, 5 - Integer.toString(StartGame.player1.getHeroXP()).length() - Integer.toString(StartGame.player1.getHeroLVL() * 5).length()) + StartGame.player1.getHeroXP() + "/" + StartGame.player1.getHeroLVL() * 5);
        System.out.printf("Health:  " + monster1.getMonsterHealth() + "           Health:  " + monster2.getMonsterHealth() + "           Health:  " + monster3.getMonsterHealth() + whiteSpaces.substring(0, 21) + "Health:   %.0f/%d     HP.Reg: %.1f%n", StartGame.player1.getHeroCurrentHealth(), StartGame.player1.getHeroMaxHealth(), StartGame.player1.getHeroHealthReg());
        System.out.println("Armor:" + whiteSpaces.substring(0, 5 - Integer.toString(monster1.getMonsterArmor()).length()) + monster1.getMonsterArmor() + whiteSpaces.substring(0, 11) +
                "Armor:" + whiteSpaces.substring(0, 5 - Integer.toString(monster2.getMonsterArmor()).length()) + monster2.getMonsterArmor() + whiteSpaces.substring(0, 11) +
                "Armor:" + whiteSpaces.substring(0, 5 - Integer.toString(monster3.getMonsterArmor()).length()) + monster3.getMonsterArmor() + whiteSpaces.substring(0, 21) +
                "Armor:        " + StartGame.player1.getHeroCurrentArmor() + "     Evsn:    " + StartGame.player1.getHeroEvasionChance() + "%");
        System.out.println("DMG:" + whiteSpaces.substring(0, 7 - Integer.toString(monster1.getMonsterDamage()).length()) + monster1.getMonsterDamage() +
                whiteSpaces.substring(0, 11) + "DMG:" + whiteSpaces.substring(0, 7 - Integer.toString(monster2.getMonsterDamage()).length()) + monster2.getMonsterDamage() +
                whiteSpaces.substring(0, 11) + "DMG:" + whiteSpaces.substring(0, 7 - Integer.toString(monster3.getMonsterDamage()).length()) + monster3.getMonsterDamage() +
                whiteSpaces.substring(0, 21) + "DMG:      " + player1.getHeroMinDMG() + "-" + player1.getHeroMaxDMG() + "     Crit:    " + StartGame.player1.getHeroCritChance() + "%");
        System.out.printf("AS:    %.2f           AS:    %.2f           AS:    %.2f                     AS:        %.2f" + "     Stun:    %d%%%n", monster1.getMonsterAttackSpeed(), monster2.getMonsterAttackSpeed(), monster3.getMonsterAttackSpeed(), StartGame.player1.getHeroAttackSpeed(), StartGame.player1.getHeroStunChance());
        System.out.println("S.Pwr:" + whiteSpaces.substring(0, 5 - Integer.toString(monster1.getMonsterSpellPower()).length()) + monster1.getMonsterSpellPower() +
                whiteSpaces.substring(0, 11) +
                "S.Pwr:" + whiteSpaces.substring(0, 5 - Integer.toString(monster2.getMonsterSpellPower()).length()) + monster2.getMonsterSpellPower() +
                whiteSpaces.substring(0, 11) +
                "S.Pwr:" + whiteSpaces.substring(0, 5 - Integer.toString(monster2.getMonsterSpellPower()).length()) + monster2.getMonsterSpellPower() +
                whiteSpaces.substring(0, 21) +
                "S.Pwr:" + whiteSpaces.substring(0, 9 - Integer.toString(StartGame.player1.getHeroSpellPower()).length()) + StartGame.player1.getHeroSpellPower() +
                whiteSpaces.substring(0, 5) +
                "M.Rst:" + whiteSpaces.substring(0, 5 - Integer.toString(StartGame.player1.getHeroBaseMagicResistance()).length()) + StartGame.player1.getHeroMagicResistance());
        System.out.printf("M.Reg:  %.1f           M.Reg:  %.1f           M.Reg:  %.1f                     M.Reg:      %.1f     Mana:     %.0f%n", monster1.getMonsterManaReg(), monster2.getMonsterManaReg(), monster3.getMonsterManaReg(), StartGame.player1.getHeroManaReg(), StartGame.player1.getHeroCurrentMana());

    }

    public Monster pickTheEnemy() {
        playerGameProgress++;
        System.out.printf("                        LEVEL %d/%d%n%n", playerGameProgress, ALL_GAME_LEVELS);
        Monster monster1 = new Monster();
        Monster monster2 = new Monster();
        Monster monster3 = new Monster();
        Scanner scanner = new Scanner(System.in);
        String pick = " ";


        while (!pick.equals("1") && !pick.equals("2") && !pick.equals("3") && !pick.toUpperCase().equals("E") && !pick.toUpperCase().equals("END")) {
            showAllEnemies(monster1, monster2, monster3);
            System.out.printf("%nPICK MONSTER TO FIGHT WITH HIM (1/2/3):");
            System.out.printf("%n(1) " + monster1.getMonsterName().trim() + " LVL " + monster1.getMonsterLVL() + "  (2) " + monster2.getMonsterName().trim() +
                    " LVL " + monster2.getMonsterLVL() + "  (3) " + monster3.getMonsterName().trim() + " LVL " + monster3.getMonsterLVL() + "    (E)/(END) Exit Game" + "%n");
            // (4) RANDOM ENEMY
            pick = scanner.nextLine();
        }

        if (pick.equals("1")) {
            enemy = monster1;
        } else if (pick.equals("2")) {
            enemy = monster2;
        } else if (pick.equals("3")) {
            enemy = monster3;
        } else if (pick.toUpperCase().equals("E") || pick.toUpperCase().equals("END")) {
            setGameStatus("END");
        }

        return enemy;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setGameStatus(String gameOff) {
        if (gameOff.toUpperCase().equals("END")) {
            gameStatusOn = false;
        }
    }

    private String setPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%n%n               HELLO HERO!%n      LET'S GO TO NEW ADVENTURE!!!%n    PLEASE ENTER YOUR GLORIOUS NAME:%n                ");
        playerName = scanner.nextLine();
        while (playerName.trim().equals("") || playerName.length() > 20) {
            System.out.printf("%n%n%n%n%n%n%n PLEASE ENTER YOUR GLORIOUS NAME (1-20 symbols):%n    ");
            this.playerName = scanner.nextLine();
        }
        return this.playerName;
    }

}