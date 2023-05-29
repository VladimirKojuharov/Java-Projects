import java.util.Random;
import java.util.Scanner;

public class Fight<privte> {
    private static int turnCounter = 0;
    private final static int AFTER_BATTLE_PRIZE_QUANTITY = 4;
    private static Item[] test = new Item[AFTER_BATTLE_PRIZE_QUANTITY + 1];

    private static String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Justice's", "Dragon's", "Hell's", "Crusher's", "Bone's", "Ghost"};
    private static String[] armorTypes = {"Plate", "Armor", "Vestry", "Ring Mail", "Full Plate"};

    public static String chosenSpellForCasting;
    private static String whitеSpaces = "                                            "; //44 WHITESPACES

    private static int heroAttackDMG;
    private static int monsterAttackDMG;
    public static double heroSpeed;
    public static double enemySpeed;
    private static int currentWeaponDMG;
    private static Random rand = new Random();
    private static double criticalDmgCoeficient;
    private static int critStrike;
    private static int stunStrike;
    private static int evasionCheck;


    public static void Battle() throws InterruptedException {
        heroSpeed = StartGame.player1.getHeroBaseAttackSpeed() + StartGame.player1.getHeroWeaponAttackSpeed();
        enemySpeed = StartGame.enemy.getMonsterAttackSpeed();
        Random rand = new Random();

        setActualSpellListForCurrentBattle();

        while (StartGame.player1.getHeroCurrentHealth() >= 1 && StartGame.enemy.getMonsterHealth() >= 1) {

            turnCounter++;
//            System.out.printf("%n%n%n%n              TURN: " + turnCounter + "%n%.2f:                       %.2f:" + "%n", heroSpeed, enemySpeed);
            combatTurnOverview();
            /*HERO TURN*/
            if (heroSpeed <= enemySpeed) {
                if (checkHeroStunSatus() == true) {
                } else {        // --------------> HERO MAKE ATTACK <---------------
                    openHeroSpellBookIfHasManaForMagic();
                    checkHeroForCurs();
                    CheckForHeroCriticalAttackForCurrentTurn();
                    calculateHeroAttackDMG();
                    duringBattleHeroHealthRegain();
                    monsterEvasionCheck();
                    setHeroManaForTheEndOfCurrentTurn();

                    if (StartGame.enemy.getMonsterHealth() < 1) {
                        heroWinConsequence();
                        getItemPrize();
                    } else {
                        heroSpeed += StartGame.player1.getHeroAttackSpeed();
                    }
                }
            } else {             //  ENEMY speed is > hero speed
                if (checkMonsterStunSatus() == true) {
                } else {    // --------------> ENEMY MAKE ATTACK <---------------
                    Monster.activateMonsterSpecialSkill(StartGame.enemy.getMonsterName());
                    checkForMonsterCriticalStrike();
//                    int critStrike = rand.nextInt(100) + 1;
//                    double criticalDMG;
//                    if (critStrike <= StartGame.enemy.getMonsterCritChance()) {
//                        criticalDMG = 1.5;
//                    } else {
//                        criticalDMG = 1;
//                    }
                    calculateMonsterAttackDMG();
//                    int monsterDMG = (int) (StartGame.enemy.getMonsterDamage() * criticalDmgCoeficient) - StartGame.player1.getHeroCurrentArmor();
//                    if (monsterDMG < 1) {
//                        monsterDMG = 1;
//                    }
//                    if (criticalDmgCoeficient == 1.5) {
//                        System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG CRITICAL strike%n", StartGame.enemy.getMonsterType().trim(), monsterDMG);
//                    } else {
//                        System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG%n", StartGame.enemy.getMonsterType().trim(), monsterDMG);
//                    }
//                    Thread.sleep(1500L);
                    heroEvasionCheck();
//                    int heroEvasionCheck = rand.nextInt(100) + 1;
//                    if (heroEvasionCheck > StartGame.player1.getHeroEvasionChance()) {
//                        /* HERO TAKE DMG */
//                        StartGame.player1.setHeroShield(StartGame.player1.getHeroShield() - monsterAttackDMG);
//                        if (StartGame.player1.getHeroShield() < 0) {
//                            StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() + StartGame.player1.getHeroShield());
//                            StartGame.player1.setHeroShield(0);
//                        }
//
//
//                        int stunChance = rand.nextInt(100) + 1;
//                        /* STUN CHECK */
//                        if (stunChance <= StartGame.enemy.getMonsterStunChance()) {
//                            StartGame.player1.setHeroStunStatus(true);
//                            System.out.printf(whitеSpaces.substring(0, 28) + "%s STUN %s!%n", StartGame.enemy.getMonsterType().trim(), StartGame.playerName);
//                            Thread.sleep(2000L);
//                        }
//                    } else {
//                        System.out.println("Hero EVADE the attack!");
//                        Thread.sleep(2000L);
//                    }
                    monsterHealthRegain();
                    monsterManaRegain();

                    if (StartGame.player1.getHeroCurrentHealth() < 1) {
                        monsterWinConsuquence();
//                        StartGame.player1.setHeroCurrentHealth(0);
//                        enemySpeed += StartGame.enemy.getMonsterAttackSpeed();
//                        System.out.printf("%n%n%n%n       %s break the hero!" + "%n", StartGame.playerName);
//                        Thread.sleep(1500L);
//                        combatTurnOverview();
//                        System.out.printf(StartGame.enemy.getMonsterType().trim() + " win the battle!!!%n%n");
//                        System.out.printf("                 ### PROGRESS %d/%d ###%n%n", StartGame.playerGameProgress, StartGame.ALL_GAME_LEVELS);
//                        Thread.sleep(2000L);
                    } else {
                        enemySpeed += StartGame.enemy.getMonsterAttackSpeed();
                    }
                }
            }
        }
        turnCounter = 0;
    }

    private static void setActualSpellListForCurrentBattle() {
        for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {     // SET SPELLS DMG, MANA COST, EFECT
            StartGame.player1.getHeroSpellList().get(i).setSpellStats();
            StartGame.player1.getHeroSpellList().get(i).setSpellManaCost();
            StartGame.player1.getHeroSpellList().get(i).setSpellDMG();
//            StartGame.player1.getHeroSpellList().get(i).setSpellCastingMessage(); //StartGame.player1.getHeroSpellList().get(i).getSpellName()
        }
    }

    private static boolean checkHeroStunSatus() throws InterruptedException {
        if (StartGame.player1.getHeroStunStatus() == true) {
            StartGame.player1.setHeroStunStatus(false);
            heroSpeed += StartGame.player1.getHeroAttackSpeed();
            System.out.println("Hero is back from stun!");
            Thread.sleep(2000L);
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkMonsterStunSatus() throws InterruptedException {
        if (StartGame.enemy.getMonsterStunStatus() == true) {
            StartGame.enemy.setMonsterStunStatus(false);
            enemySpeed += StartGame.enemy.getMonsterAttackSpeed();
            System.out.printf(whitеSpaces.substring(0, 28) + "%s is back from the STUN!%n", StartGame.enemy.getMonsterName().trim());
            Thread.sleep(2000L);
            return true;
        } else {
            return false;
        }
    }

    private static void openHeroSpellBookIfHasManaForMagic() throws InterruptedException {
        int cheepestHeroMagic = Integer.MAX_VALUE; /*SHOW SPELL BOOK*/
        for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
            if (cheepestHeroMagic > StartGame.player1.getHeroSpellList().get(i).getSpellManaCost()) {
                cheepestHeroMagic = StartGame.player1.getHeroSpellList().get(i).getSpellManaCost();
            }
        }
        if (cheepestHeroMagic <= (int) StartGame.player1.getHeroCurrentMana()) {
            printHeroSpellBook();
            heroCastSpell();
        }
    }

    private static void checkHeroForCurs() {
        if (Monster.CursingCast == false) {
            currentWeaponDMG = (StartGame.player1.getHeroWeaponMinDMG() + rand.nextInt(0 + StartGame.player1.getHeroWeaponMaxDMG() -
                    StartGame.player1.getHeroWeaponMinDMG()));
        } else {
            currentWeaponDMG = StartGame.player1.getHeroWeaponMinDMG();
        }
    }

    private static void CheckForHeroCriticalAttackForCurrentTurn() throws InterruptedException {
        critStrike = rand.nextInt(100) + 1;
        if (critStrike <= StartGame.player1.getHeroCritChance()) {
            criticalDmgCoeficient = 2;
            System.out.printf("%s make CRITICAL strike %d DMG%n", StartGame.playerName, StartGame.player1.getHeroBaseDamage() + (int) (criticalDmgCoeficient * currentWeaponDMG) - StartGame.enemy.getMonsterArmor());
            Thread.sleep(2000L);
        } else {
            criticalDmgCoeficient = 1;
            System.out.printf("%s make %d DMG%n", StartGame.playerName, StartGame.player1.getHeroBaseDamage() + currentWeaponDMG - StartGame.enemy.getMonsterArmor());
            Thread.sleep(2000L);
        }
    }

    private static void checkForHeroStunStrike() throws InterruptedException {
        stunStrike = rand.nextInt(100) + 1;
        if (stunStrike <= StartGame.player1.getHeroStunChance()) {
            StartGame.enemy.setMonsterStunStatus(true);
            System.out.printf("%s STUND the %s%n", StartGame.playerName, StartGame.enemy.getMonsterName().trim());
            Thread.sleep(2000L);
        }
    }

    public static void printHeroSpellBook() {
        Scanner scanner = new Scanner(System.in);
        chosenSpellForCasting = " ";
        while (!chosenSpellForCasting.equals("1") && !chosenSpellForCasting.equals("2") && !chosenSpellForCasting.equals("3") && !chosenSpellForCasting.equals("4") &&
                !chosenSpellForCasting.equals("5") && !chosenSpellForCasting.equals("6") && !chosenSpellForCasting.equals("7") && !chosenSpellForCasting.equals("8") &&
                !chosenSpellForCasting.equals("9") && !chosenSpellForCasting.equals("10") && !chosenSpellForCasting.equals("11") && !chosenSpellForCasting.equals("12") &&
                !chosenSpellForCasting.equals("0")) {

            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow1() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow2() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow3() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow4() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow5() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow6() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.print(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow7() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow8() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
//                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow9() + whitеSpaces.substring(0, 8));
                    System.out.printf("    MANA COST: %d" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.getHeroSpellList().get(i).getSpellManaCost()).length()) + whitеSpaces.substring(0, 8), StartGame.player1.getHeroSpellList().get(i).getSpellManaCost());
                }
            }
            System.out.println();
            System.out.println();

            if (StartGame.player1.getHeroSpellList().size() >= 6) {
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow1() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow2() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow3() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow4() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow5() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow6() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.print(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow7() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow8() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
//                    System.out.printf(StartGame.player1.getHeroSpellList().get(i).getSpellDescriptionRow9() + whitеSpaces.substring(0, 8));
                    System.out.printf("    MANA COST: %d" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.getHeroSpellList().get(i).getSpellManaCost()).length()) + whitеSpaces.substring(0, 8), StartGame.player1.getHeroSpellList().get(i).getSpellManaCost());
                }
                System.out.println();


            }

            for (int i = 0; i < StartGame.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf("(" + (i + 1) + ") " + StartGame.player1.getHeroSpellList().get(i).getSpellName() + "    ");
                }
            }
            System.out.println();
            if (StartGame.player1.getHeroSpellList().size() >= 6) {
                for (int i = 6; i < StartGame.player1.getHeroSpellList().size(); i++) {
                    System.out.printf("(" + (i + 1) + ") " + StartGame.player1.getHeroSpellList().get(i).getSpellName() + "    ");
                }
            }
            System.out.println();
            System.out.printf("(0) Close Spell Book                HERO MANA: %.0f%n", StartGame.player1.getHeroCurrentMana());
            chosenSpellForCasting = scanner.nextLine();
        }


    }

    public static void heroCastSpell() throws InterruptedException {
        if (chosenSpellForCasting.equals("1")) {
            if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(0).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(0).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(0).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(0).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(0).makeSpellEffect(StartGame.player1.getHeroSpellList().get(0).getSpellName());
                StartGame.player1.getHeroSpellList().remove(0);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("2")) {
            if (StartGame.player1.getHeroSpellList().size() < 2) {
                System.out.println("There is no magic (2)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(1).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(1).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(1).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(1).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(1).makeSpellEffect(StartGame.player1.getHeroSpellList().get(1).getSpellName());
                StartGame.player1.getHeroSpellList().remove(1);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("3")) {
            if (StartGame.player1.getHeroSpellList().size() < 3) {
                System.out.println("There is no magic (3)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(2).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(2).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(2).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(2).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(2).makeSpellEffect(StartGame.player1.getHeroSpellList().get(2).getSpellName());
                StartGame.player1.getHeroSpellList().remove(2);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("4")) {
            if (StartGame.player1.getHeroSpellList().size() < 4) {
                System.out.println("There is no magic (4)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(3).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(3).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(3).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(3).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(3).makeSpellEffect(StartGame.player1.getHeroSpellList().get(3).getSpellName());
                StartGame.player1.getHeroSpellList().remove(3);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("5")) {
            if (StartGame.player1.getHeroSpellList().size() < 5) {
                System.out.println("There is no magic (5)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(4).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(4).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(4).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(4).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(4).makeSpellEffect(StartGame.player1.getHeroSpellList().get(4).getSpellName());
                StartGame.player1.getHeroSpellList().remove(4);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("6")) {
            if (StartGame.player1.getHeroSpellList().size() < 6) {
                System.out.println("There is no magic (6)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(5).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(5).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(5).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(5).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(5).makeSpellEffect(StartGame.player1.getHeroSpellList().get(5).getSpellName());
                StartGame.player1.getHeroSpellList().remove(5);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("7")) {
            if (StartGame.player1.getHeroSpellList().size() < 7) {
                System.out.println("There is no magic (7)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(6).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(0).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(6).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(6).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(6).makeSpellEffect(StartGame.player1.getHeroSpellList().get(6).getSpellName());
                StartGame.player1.getHeroSpellList().remove(6);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("8")) {
            if (StartGame.player1.getHeroSpellList().size() < 8) {
                System.out.println("There is no magic (8)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(7).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(7).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(7).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(7).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(7).makeSpellEffect(StartGame.player1.getHeroSpellList().get(7).getSpellName());
                StartGame.player1.getHeroSpellList().remove(7);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("9")) {
            if (StartGame.player1.getHeroSpellList().size() < 9) {
                System.out.println("There is no magic (8)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(8).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(8).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(8).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(8).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(8).makeSpellEffect(StartGame.player1.getHeroSpellList().get(8).getSpellName());
                StartGame.player1.getHeroSpellList().remove(8);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("10")) {
            if (StartGame.player1.getHeroSpellList().size() < 10) {
                System.out.println("There is no magic (10)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(9).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(9).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(9).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(9).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(9).makeSpellEffect(StartGame.player1.getHeroSpellList().get(9).getSpellName());
                StartGame.player1.getHeroSpellList().remove(9);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("11")) {
            if (StartGame.player1.getHeroSpellList().size() < 11) {
                System.out.println("There is no magic (11)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(10).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(10).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(10).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(10).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(10).makeSpellEffect(StartGame.player1.getHeroSpellList().get(10).getSpellName());
                StartGame.player1.getHeroSpellList().remove(10);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("12")) {
            if (StartGame.player1.getHeroSpellList().size() < 2) {
                System.out.println("There is no magic (12)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (StartGame.player1.getHeroCurrentMana() >= StartGame.player1.getHeroSpellList().get(11).getSpellManaCost()) {
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - StartGame.player1.getHeroSpellList().get(11).getSpellManaCost());
                StartGame.player1.getHeroSpellList().get(11).setSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(11).getSpellCastingMessage();
                StartGame.player1.getHeroSpellList().get(11).makeSpellEffect(StartGame.player1.getHeroSpellList().get(11).getSpellName());
                StartGame.player1.getHeroSpellList().remove(11);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        }
        chosenSpellForCasting = " ";
        System.out.println();
        Thread.sleep(2000L);
        combatTurnOverview();

    }

    public static void combatTurnOverview() throws InterruptedException {
        System.out.printf("%n%n%n%n              TURN: " + turnCounter + "%n%.2f:                       %.2f:" + "%n", heroSpeed, enemySpeed);
        System.out.printf(StartGame.getPlayerName() + whitеSpaces.substring(0, 28 - StartGame.playerName.length()) + StartGame.enemy.getMonsterName().trim() + "%n");
        System.out.printf("HP:" + whitеSpaces.substring(0, 9 - (Integer.toString((int) StartGame.player1.getHeroCurrentHealth()).length() + 1 + Integer.toString((int) StartGame.player1.getHeroMaxHealth()).length())) +
                "%.0f/%d                HP:   %d/%d%n", Math.floor(StartGame.player1.getHeroCurrentHealth()), StartGame.player1.getHeroMaxHealth(), StartGame.enemy.getMonsterHealth(), StartGame.enemy.getMonsterBaseHealth());
        System.out.printf("HP.Reg: %.2f                " + "HP.Reg:  " + StartGame.enemy.getMonsterHealthReg() + "%n", StartGame.player1.getHeroHealthReg());
        System.out.printf("Armor:     " + StartGame.player1.getHeroCurrentArmor() + "                " + "Armor:    " + StartGame.enemy.getMonsterArmor() + "%n");
        System.out.println("Shield:    " + StartGame.player1.getHeroShield());
        System.out.printf("Evsn%%:     %d                Evsn%%     %d%n", StartGame.player1.getHeroEvasionChance(), StartGame.enemy.getMonsterEvasionChance());
        System.out.printf("DMG:" + whitеSpaces.substring(0, 8 - (Integer.toString(StartGame.player1.getHeroMinDMG()).length() + 1 + Integer.toString(StartGame.player1.getHeroMaxDMG()).length())) +
                StartGame.player1.getHeroMinDMG() + "-" + StartGame.player1.getHeroMaxDMG() + "                " + "DMG:     " + StartGame.enemy.getMonsterDamage() + "%n");
        System.out.printf("AS:     %.2f                AS:    %.2f%n", StartGame.player1.getHeroAttackSpeed(), StartGame.enemy.getMonsterAttackSpeed());
        System.out.printf("Crit%%:     %d                Crit%%     %d%n", StartGame.player1.getHeroCritChance(), StartGame.enemy.getMonsterCritChance());
        System.out.printf("Stun%%:     %d                Stun%%     %d%n", StartGame.player1.getHeroStunChance(), StartGame.enemy.getMonsterStunChance());
        System.out.printf("S.Pwr:     %d                S.Pwr:    %d%n", StartGame.player1.getHeroSpellPower(), StartGame.enemy.getMonsterSpellPower());
        System.out.printf("M.Rst:     %d                M.Rst:    %d%n", StartGame.player1.getHeroMagicResistance(), StartGame.enemy.getMonsterMagicResistance());
        System.out.printf("Mana:      %.0f                Mana:     %.0f%n", Math.floor(StartGame.player1.getHeroCurrentMana()), Math.floor(StartGame.enemy.getMonsterMana()));
        System.out.printf("M.Reg:   %.1f                M.Reg:  %.1f%n%n", StartGame.player1.getHeroManaReg(), StartGame.enemy.getMonsterManaReg());
        Thread.sleep(1000);
    }

    private static void duringBattleHeroHealthRegain() {       // double maxHeroHealth = StartGame.player1.getHeroBaseHealth() + .... item bonuses;
        StartGame.player1.setHeroCurrentHealth((StartGame.player1.getHeroCurrentHealth() + (StartGame.player1.getHeroHealthReg()) * StartGame.player1.getHeroAttackSpeed()));
        if (StartGame.player1.getHeroCurrentHealth() > /* maxHeroHealth */ StartGame.player1.getHeroMaxHealth()) {
            StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroMaxHealth());
        }
    }

    private static int calculateHeroAttackDMG() {
        heroAttackDMG = StartGame.player1.getHeroBaseDamage() + (int) (criticalDmgCoeficient * currentWeaponDMG); // base DMG + (Crit * min:max weapon DMG)
        return heroAttackDMG;
    }

    private static void monsterEvasionCheck() throws InterruptedException {
        evasionCheck = rand.nextInt(100) + 1; /* EVASION CHECK */
        if (evasionCheck > StartGame.enemy.getMonsterEvasionChance()) {
            StartGame.enemy.setMonsterHealth(StartGame.enemy.getMonsterHealth() + StartGame.enemy.getMonsterArmor() - calculateHeroAttackDMG());

            checkForHeroStunStrike();

        } else {
            System.out.println(whitеSpaces.substring(0, 28) + "The Monster EVADE the Strike!");
            Thread.sleep(2000L);
        }
    }

    private static void heroEvasionCheck() throws InterruptedException {
        evasionCheck = rand.nextInt(100) + 1;
        if (evasionCheck > StartGame.player1.getHeroEvasionChance()) {
            /* HERO TAKE DMG */
            heroTakeDMG();
            monsterStunStrike();

        } else {
            System.out.println("Hero EVADE the attack!");
            Thread.sleep(2000L);
        }

    }

    private static void monsterStunStrike() throws InterruptedException {
        stunStrike = rand.nextInt(100) + 1;
        /* STUN CHECK */
        if (stunStrike <= StartGame.enemy.getMonsterStunChance()) {
            StartGame.player1.setHeroStunStatus(true);
            System.out.printf(whitеSpaces.substring(0, 28) + "%s STUN %s!%n", StartGame.enemy.getMonsterName().trim(), StartGame.playerName);
            Thread.sleep(2000L);
        }
    }

    private static void heroTakeDMG() {
        StartGame.player1.setHeroShield(StartGame.player1.getHeroShield() - monsterAttackDMG);
        if (StartGame.player1.getHeroShield() < 0) {
            StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() + StartGame.player1.getHeroShield());
            StartGame.player1.setHeroShield(0);
        }
    }

    private static void monsterHealthRegain() {
        StartGame.enemy.setMonsterHealth(StartGame.enemy.getMonsterHealth() + StartGame.enemy.getMonsterHealthReg());
        if (StartGame.enemy.getMonsterHealth() > StartGame.enemy.getMonsterBaseHealth()) {
            StartGame.enemy.setMonsterHealth(StartGame.enemy.getMonsterBaseHealth());
        }
    }

    private static void monsterManaRegain() {
        StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() + StartGame.enemy.getMonsterManaReg());
    }

    private static void monsterWinConsuquence() throws InterruptedException {
        StartGame.player1.setHeroCurrentHealth(0);
        enemySpeed += StartGame.enemy.getMonsterAttackSpeed();
        System.out.printf("%n%n%n%n       %s break the hero!" + "%n", StartGame.enemy.getMonsterName());
        Thread.sleep(1500L);
        combatTurnOverview();
        System.out.printf(StartGame.enemy.getMonsterName().trim() + " win the battle!!!%n%n");
        System.out.printf("                 ### PROGRESS %d/%d ###%n%n", StartGame.playerGameProgress, StartGame.ALL_GAME_LEVELS);
        Thread.sleep(2000L);
    }


    private static void setHeroManaForTheEndOfCurrentTurn() {
        StartGame.player1.setHeroCurrentMana((StartGame.player1.getHeroCurrentMana() + (StartGame.player1.getHeroManaReg()) * StartGame.player1.getHeroAttackSpeed()));
    }

    private static void afterBattleHeroStatsRestore() {
        StartGame.player1.setHeroMaxHealth();
        StartGame.player1.setHeroHealthReg();
        StartGame.player1.setCurrentHeroArmor();
        StartGame.player1.setHeroShield(StartGame.player1.getHeroBaseShield() + StartGame.player1.armor.getItemShield());
        StartGame.player1.setHeroEvasionChance();
        StartGame.player1.setHeroMinDMG();
        StartGame.player1.setHeroMaxDMG(StartGame.player1.getHeroBaseDamage() + StartGame.player1.weapon.getItemMaxDMG());
        StartGame.player1.setHeroCritChance();
        StartGame.player1.setHeroAttackSpeed(StartGame.player1.getHeroBaseAttackSpeed() + StartGame.player1.getHeroWeaponAttackSpeed());
        StartGame.player1.setHeroStunChance();
        StartGame.player1.setHeroSpellPower();
        StartGame.player1.setHeroMagicResistance();
        StartGame.player1.setHeroManaReg();
        StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroBaseMana());//+ StartGame.player1.armor.getItemMana());
    }

    private static void afterBattleHeroSpellBookRestore() {
        StartGame.player1.heroSpellList.clear();
        for (int i = 0; i < StartGame.player1.heroSpellListCopy.size(); i++) {
            StartGame.player1.heroSpellList.add(StartGame.player1.heroSpellListCopy.get(i));
        }
    }

    private static void afterBattleMonsterSkillRestore() {
        Monster.CursingCast = false;
        Monster.skeletonArmorIncreasment = false;
        Monster.hydraLifeTracer = 250;
        Monster.lichKingPoisonActivation = false;
    }

    private static void generateItemPrize() {
        Random rand = new Random();
        String[] itemModels = {"WEAPON", "ARMOR", "HELM"};
//        Item[] prizes = new Item[AFTER_BATTLE_PRIZE_QUANTITY];

        for (int i = 1; i < AFTER_BATTLE_PRIZE_QUANTITY + 1; i++) {
            int generateItemRandomModel = rand.nextInt(itemModels.length);
            String itemModel = itemModels[generateItemRandomModel];

            if (itemModel.equals("WEAPON")) {
                test[i] = new Weapon();
                test[i].setItemModel("WEAPON");
            } else if (itemModel.equals("ARMOR")) {
                test[i] = new Armor();
                test[i].setItemModel("ARMOR");
            } else if (itemModel.equals("HELM")) {
                test[i] = new Helm();
                test[i].setItemModel("HELM");
            }
        }
    }

    private static void printRandomItemPrizes() {
        System.out.println("Prize I:                  Prize II:                 Prize III:                Prize IV:                       HERO WEAPON:          HERO ARMOR:          HERO HELM:");
        System.out.printf("%s" + whitеSpaces.substring(test[1].getItemName().length(), 26) + "%s" + whitеSpaces.substring(test[2].getItemName().length(), 26) + "%s" +
                        whitеSpaces.substring(test[3].getItemName().length(), 26) + "%s" + whitеSpaces.substring(test[4].getItemName().length(), 26) + "      %s" +
                        whitеSpaces.substring(StartGame.player1.getHeroWeaponName().length(), 22) + "%s" + whitеSpaces.substring(StartGame.player1.getHeroArmorName().length(), 21) + "%s%n", test[1].getItemName(), test[2].getItemName(),
                test[3].getItemName(), test[4].getItemName(), StartGame.player1.getHeroWeaponName(), StartGame.player1.getHeroArmorName(), StartGame.player1.getHeroHelmName());
        System.out.printf("LVL: %d                    LVL: %d                    LVL: %d                    LVL: %d                          LVL: %d" +
                        whitеSpaces.substring(Integer.toString(StartGame.player1.getHeroLVL()).length(), 17) + "LVL: %d" + "               LVL: %d%n", test[1].getItemLVL(), test[2].getItemLVL(),
                test[3].getItemLVL(), test[4].getItemLVL(), StartGame.player1.getHeroWeaponLVL(), StartGame.player1.getHeroArmorLVL(), StartGame.player1.getHeroHelmLVL());

        System.out.printf(test[1].itemStat1 + whitеSpaces.substring(0, 14) + test[2].itemStat1 + whitеSpaces.substring(0, 14) + test[3].itemStat1 + whitеSpaces.substring(0, 14) + test[4].itemStat1 + whitеSpaces.substring(0, 20) +
                "DMG:" + whitеSpaces.substring(0, 8 - (Integer.toString(StartGame.player1.getHeroWeaponMinDMG()).length() + 1 + Integer.toString(StartGame.player1.getHeroWeaponMaxDMG()).length())) + "%d-%d" + whitеSpaces.substring(0, 10) +
                "Armor:" + whitеSpaces.substring(0, 7 - Integer.toString(StartGame.player1.armor.getItemArmor()).length()) + "%s" +  whitеSpaces.substring(0, 8) +
                "Health:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.helm.getItemHP()).length()) + "%s%n", StartGame.player1.getHeroWeaponMinDMG(), StartGame.player1.getHeroWeaponMaxDMG(), StartGame.player1.armor.getItemArmor(), StartGame.player1.helm.getItemHP());
        System.out.printf(test[1].itemStat2 + whitеSpaces.substring(0, 14) + test[2].itemStat2 + whitеSpaces.substring(0, 14) + test[3].itemStat2 + whitеSpaces.substring(0, 14) + test[4].itemStat2 + whitеSpaces.substring(0, 20) +
                "AS:" + whitеSpaces.substring(0, 5) + "%.2f" + whitеSpaces.substring(0, 10) +
                "Health:" + whitеSpaces.substring(0, 8 - Double.toString(StartGame.player1.armor.getItemHP()).length()) + "%d" + whitеSpaces.substring(0,8) +
                "Shield:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.helm.getItemShield()).length()) + "%d%n"
                , StartGame.player1.weapon.getItemAttackSpeed(), StartGame.player1.armor.getItemHP(), StartGame.player1.helm.getItemShield());
        System.out.printf(test[1].itemStat3 + whitеSpaces.substring(0, 14) + test[2].itemStat3 + whitеSpaces.substring(0, 14) + test[3].itemStat3 + whitеSpaces.substring(0, 14) + test[4].itemStat3 + whitеSpaces.substring(0, 42) +
                "Shield:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.armor.getItemShield()).length()) + StartGame.player1.armor.getItemShield() +
                whitеSpaces.substring(0, 8) + "S.Pwr:" + whitеSpaces.substring(0, 7 - Integer.toString(StartGame.player1.helm.getItemSpellPower()).length()) + StartGame.player1.helm.getItemSpellPower() + "%n");
        System.out.printf(test[1].itemStat4 + whitеSpaces.substring(0, 14) + test[2].itemStat4 + whitеSpaces.substring(0, 14) + test[3].itemStat4 + whitеSpaces.substring(0, 14) + test[4].itemStat4 + whitеSpaces.substring(0, 20) +
                "Crit%%:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.weapon.getItemCriticalChance()).length()) + "%d" + whitеSpaces.substring(0, 10) +
                "HP.Reg:  %.2f" + whitеSpaces.substring(0, 8) + "Resist:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.helm.getItemMagicResistance()).length()) +
                StartGame.player1.helm.getItemMagicResistance() + "%n", StartGame.player1.weapon.getItemCriticalChance(), StartGame.player1.armor.getItemHPReg());
        System.out.printf(test[1].itemStat5 + whitеSpaces.substring(0, 14) + test[2].itemStat5 + whitеSpaces.substring(0, 14) + test[3].itemStat5 + whitеSpaces.substring(0, 14) + test[4].itemStat5 + whitеSpaces.substring(0, 20) +
                "Stun%%:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.weapon.getItemStunChance()).length()) + "%d" + whitеSpaces.substring(0, 10) +
                "Evsn%%:" + whitеSpaces.substring(0, 7 - Integer.toString(StartGame.player1.armor.getItemEvasionChance()).length()) + "%d%n", StartGame.player1.weapon.getItemStunChance(), StartGame.player1.armor.getItemEvasionChance());
        System.out.printf(test[1].itemStat6 + whitеSpaces.substring(0, 14) + test[2].itemStat6 + whitеSpaces.substring(0, 14) + test[3].itemStat6 + whitеSpaces.substring(0, 14) + test[4].itemStat6 + whitеSpaces.substring(0, 20) +
                "S.Pwr:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.weapon.getItemSpellPower()).length()) + "%d" + whitеSpaces.substring(0, 10) +
                "S.Pwr:" + whitеSpaces.substring(0, 7 - Integer.toString(StartGame.player1.armor.getItemSpellPower()).length()) + "%d%n", StartGame.player1.weapon.getItemSpellPower(), StartGame.player1.armor.getItemSpellPower());
        System.out.printf(test[1].itemStat7 + whitеSpaces.substring(0, 14) + test[2].itemStat7 + whitеSpaces.substring(0, 14) + test[3].itemStat7 + whitеSpaces.substring(0, 14) + test[4].itemStat7 + whitеSpaces.substring(0, 20) +
                whitеSpaces.substring(0, 22) + "Resist:" + whitеSpaces.substring(0, 6 - Integer.toString(StartGame.player1.armor.getItemMagicResistance()).length()) + "%d%n", StartGame.player1.armor.getItemMagicResistance());
        System.out.printf(test[1].itemStat8 + whitеSpaces.substring(0, 14) + test[2].itemStat8 + whitеSpaces.substring(0, 14) + test[3].itemStat8 + whitеSpaces.substring(0, 14) + test[4].itemStat8 + whitеSpaces.substring(0, 20) +
                whitеSpaces.substring(0, 22) + "M.Reg:" + whitеSpaces.substring(0, 4) + "%s%n", String.valueOf(StartGame.player1.armor.getItemManaReg()).substring(0, 3));

        //+ whitеSpaces.substring(0, 22)) + "Mana:" + whitеSpaces.substring(0, 8 - Integer.toString((int) StartGame.player1.armor.getItemMana()).length()) + "%d%n", (int) StartGame.player1.armor.getItemMana());
//        System.out.printf(test[1].itemStat9 + whitеSpaces.substring(0, 14) + test[2].itemStat9 + whitеSpaces.substring(0, 14) + test[3].itemStat9 + whitеSpaces.substring(0, 14) + test[4].itemStat9 + whitеSpaces.substring(0, 20) +
//                whitеSpaces.substring(0, 22) + "M.Reg:" + whitеSpaces.substring(0, 4) + "%s%n", String.valueOf(StartGame.player1.armor.getItemManaReg()).substring(0, 3));

        System.out.printf("PICK YOUR PRIZE:%n");
        System.out.printf("(1) %s  (2) %s  (3) %s  (4) %s   (0) No One%n", test[1].getItemName(), test[2].getItemName(), test[3].getItemName(), test[4].getItemName());
    }

    private static void heroWinConsequence() throws InterruptedException {
        StartGame.enemy.setMonsterHealth(0);
        heroSpeed += StartGame.player1.getHeroBaseAttackSpeed() + StartGame.player1.getHeroWeaponAttackSpeed();
        System.out.printf("%n%n%n%n        The %s is fallen! " + "%n", StartGame.enemy.getMonsterName());
        Thread.sleep(1500L);
//        System.out.printf("%.2f:                       %.2f:" + "%n", StartGame.enemy.getMonsterType().trim(), heroSpeed, enemySpeed);
        combatTurnOverview();
        System.out.printf(StartGame.playerName + " win the battle!%n%n%n");
        Thread.sleep(1500L);
        afterBattleHeroStatsRestore();
        afterBattleHeroSpellBookRestore();
        afterBattleMonsterSkillRestore();
        StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroBaseMana()); //+ StartGame.player1.armor.getItemMana());
        StartGame.player1.setHeroXP();
        if (StartGame.player1.getHeroXP() >= (StartGame.player1.getHeroLVL() * 5)) {
            StartGame.player1.lvlUP();
        }
    }

    private static void getItemPrize() throws InterruptedException {
        generateItemPrize();  // <-----------------------------------------------------

        Scanner scanner = new Scanner(System.in);
        String prizePick = " ";

        while (!prizePick.equals("1") && !prizePick.equals("2") && !prizePick.equals("3") && !prizePick.equals("4") && !prizePick.equals("0") && !prizePick.toUpperCase().equals("E") && !prizePick.toUpperCase().equals("END")) {

            printRandomItemPrizes();        //<---------------------------------------------
            prizePick = scanner.nextLine();

        }
        if (prizePick.equals("0")) {
            return;
        } else if (prizePick.toUpperCase().equals("E")) {
            StartGame.setGameStatus("END");
        } else if (prizePick.toUpperCase().equals("END")) {
            StartGame.setGameStatus("END");
        } else {
            int pickedPrizeItemPosition = Integer.parseInt(prizePick);

//            if (prizePick.equals("1")) {
            if (test[pickedPrizeItemPosition].getItemModel().equals("WEAPON")) {
                if (test[pickedPrizeItemPosition] instanceof Weapon) {
                    Weapon prizeItem = (Weapon) test[pickedPrizeItemPosition];
                    StartGame.player1.changeHeroWeapon(prizeItem);
                }
            } else if (test[pickedPrizeItemPosition].getItemModel().equals("ARMOR")) {
                if (test[pickedPrizeItemPosition].getItemModel().equals("ARMOR")) {
                    if (test[pickedPrizeItemPosition] instanceof Armor) {
                        Armor prizeItem = (Armor) test[pickedPrizeItemPosition];
                        StartGame.player1.changeHeroArmor(StartGame.player1.armor, prizeItem);
                    }
                }
            } else if (test[pickedPrizeItemPosition].getItemModel().equals("HELM")) {
                if (test[pickedPrizeItemPosition].getItemModel().equals("HELM")) {
                    if (test[pickedPrizeItemPosition] instanceof Helm) {
                        Helm prizeItem = (Helm) test[pickedPrizeItemPosition];
                        StartGame.player1.changeHeroHelm(StartGame.player1.helm, prizeItem);
                    }
                }
            }

//            } else if (prizePick.equals("2")) {
//                if (test[2].getItemModel().equals("WEAPON")) {
//                    if (test[2] instanceof Weapon) {
//                        Weapon prizeItem2 = (Weapon) test[2];
//                        StartGame.player1.changeHeroWeapon(prizeItem2);
//                    }
//                } else if (test[1].getItemModel().equals("ARMOR")) {
//                    if (test[2].getItemModel().equals("ARMOR")) {
//                        if (test[2] instanceof Armor) {
//                            Armor prizeItem2 = (Armor) test[2];
//                            StartGame.player1.changeHeroArmor(StartGame.player1.armor, prizeItem2);
//                        }
//                    }
//                }
//            } else if (prizePick.equals("3")) {
//                if (test[3].getItemModel().equals("WEAPON")) {
//                    if (test[3] instanceof Weapon) {
//                        Weapon prizeItem3 = (Weapon) test[3];
//                        StartGame.player1.changeHeroWeapon(prizeItem3);
//                    }
//                } else if (test[1].getItemModel().equals("ARMOR")) {
//                    if (test[3].getItemModel().equals("ARMOR")) {
//                        if (test[3] instanceof Armor) {
//                            Armor prizeItem3 = (Armor) test[3];
//                            StartGame.player1.changeHeroArmor(StartGame.player1.armor, prizeItem3);
//                        }
//                    }
//                }
//            } else if (prizePick.equals("4")) {
//                if (test[4].getItemModel().equals("WEAPON")) {
//                    if (test[4] instanceof Weapon) {
//                        Weapon prizeItem4 = (Weapon) test[4];
//                        StartGame.player1.changeHeroWeapon(prizeItem4);
//                    }
//                } else if (test[1].getItemModel().equals("ARMOR")) {
//                    if (test[4].getItemModel().equals("ARMOR")) {
//                        if (test[4] instanceof Armor) {
//                            Armor prizeItem4 = (Armor) test[4];
//                            StartGame.player1.changeHeroArmor(StartGame.player1.armor, prizeItem4);
//                        }
//                    }
//                }
            System.out.printf("%n%n%n");

            StartGame.player1.showEquippedHeroStats();
            Thread.sleep(2000);
        }

    }

    private static double checkForMonsterCriticalStrike() {
        critStrike = rand.nextInt(100) + 1;
        if (critStrike <= StartGame.enemy.getMonsterCritChance()) {
            criticalDmgCoeficient = 1.5;
        } else {
            criticalDmgCoeficient = 1;
        }
        return criticalDmgCoeficient;
    }

    private static void calculateMonsterAttackDMG() throws InterruptedException {
        monsterAttackDMG = (int) (StartGame.enemy.getMonsterDamage() * criticalDmgCoeficient) - StartGame.player1.getHeroCurrentArmor();
        if (monsterAttackDMG < 1) {
            monsterAttackDMG = 1;
        }
        if (criticalDmgCoeficient == 1.5) {
            System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG CRITICAL strike%n", StartGame.enemy.getMonsterName().trim(), monsterAttackDMG);
        } else {
            System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG%n", StartGame.enemy.getMonsterName().trim(), monsterAttackDMG);
        }
        Thread.sleep(1500L);
    }
}