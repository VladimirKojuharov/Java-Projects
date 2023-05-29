import java.util.Random;

public class Monster {

    private String monsterName;
    private int monsterLVL;
    public int monsterBaseHealth;
    private int monsterHealth;
    public int monsterHealthReg;
    private int monsterDamage;
    private double monsterSpeed;
    private int monsterArmor;
    private double monsterMana;
    public double monsterManaReg;
    public int monsterMagicResistance;
    private boolean monsterStunStatus = false;
    private int monsterSpellPower;
    public int monsterStunChance;
    public int monsterCritChance;
    public int monsterEvasionChance;

    public static boolean CursingCast = false;
    public static boolean skeletonArmorIncreasment = false;
    public static int hydraLifeTracer = 250;
    public static boolean lichKingPoisonActivation = false;


    String[] monsterNames = {"Troll       ", "Ogre        ", "Cyclops     ", "Lich        ", "Vampire     ", "Skeleton    ", "Werewolf    ", "Witch       "}; //, "Ork         ", "Goblin      " "Fling Snake", "Lizzardman",
//                        "Zombie", Destroyer, Dark Sear, Dark Willow, Disruptor, Invoker - призовател, Necromancer, Warlord, Doom Cultist, Blood Oracle,

    String[] championMonsterNames = {"Hell Monarch", "Infernal", "Dread Knight", "Abomination ", "Оblivion", "Terror", "Demon Lord", "Lich King"}; // "Succubus    "};

    String[] bossMonsterNames = {"Hydra", "Fenix", "Titan"}; // Black Dragon

    public Monster() {
        if (StartGame.playerGameProgress % 40 == 0) {
            this.createBossType();
        } else if (StartGame.playerGameProgress % 5 != 0) {
            this.createMonsterType();
        } else {
            this.createChampionMonsterType();
        }

        this.setMonsterLVL();    // alternative -->  playerGameProgres / 5 + 1
        this.setMonsterHealth(monsterHealth);
        if (StartGame.playerGameProgress % 40 == 0) {
            this.setBossMonsterStats(getMonsterName());
        } else if (StartGame.playerGameProgress % 5 != 0) {
            this.setMonsterStats(getMonsterName());
        } else {
            this.setChampionMonsterStats(getMonsterName());
        }
        this.setMonsterDMG(0);
        this.monsterBaseHealth = monsterHealth;

        this.setMonsterStunStatus(monsterStunStatus);
    }

    public String showMonsterStats() {
        String monsterStats = String.format(getMonsterName() + "%nLVL:   " + getMonsterLVL() + "%nHealt: " + getMonsterHealth() + "%nArmor: " +
                getMonsterArmor() + "%nDMG:   " + getMonsterDamage() + "%nAS:   " + getMonsterAttackSpeed() + "%n");
        return monsterStats;
    }

    public int getMonsterLVL() {
        return this.monsterLVL;
    }

    public int getMonsterHealth() {
        return this.monsterHealth;
    }

    public int getMonsterHealthReg() {
        return this.monsterHealthReg;
    }

    public int getMonsterBaseHealth() {
        return this.monsterBaseHealth;
    }

    public int getMonsterArmor() {
        return this.monsterArmor;
    }

    public int getMonsterEvasionChance() {
        return this.monsterEvasionChance;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public int getMonsterDamage() {
        return this.monsterDamage;
    }

    public double getMonsterAttackSpeed() {
        return this.monsterSpeed;
    }

    public int getMonsterCritChance() {
        return this.monsterCritChance;
    }

    public int getMonsterStunChance() {
        return this.monsterStunChance;
    }

    public double getMonsterMana() {
        return this.monsterMana;
    }

    public double getMonsterManaReg() {
        return this.monsterManaReg;
    }

    public int getMonsterSpellPower() {
        return this.monsterSpellPower;
    }

    public int getMonsterMagicResistance() {
        return this.monsterMagicResistance;
    }

    public boolean getMonsterStunStatus() {
        return this.monsterStunStatus;
    }


    private int setMonsterLVL() {
        Random rand = new Random();
        int advantageChance = rand.nextInt(10) + 1;
        if (advantageChance < 8) {
            monsterLVL = 1 + (StartGame.playerGameProgress / 5) + 0;
        } else if (8 <= advantageChance && advantageChance <= 9) {
            monsterLVL = 1 + (StartGame.playerGameProgress / 5) + 1;
        } else if (advantageChance == 10) {
            monsterLVL = 1 + (StartGame.playerGameProgress / 5) + 2;
        }

        return monsterLVL;
    }


    public String createMonsterType() {
        Random rand = new Random();
        int monster = rand.nextInt(monsterNames.length);
        this.monsterName = monsterNames[monster].trim();
        return this.monsterName;
    }

    public String createChampionMonsterType() {
        Random rand = new Random();
        int monster = rand.nextInt(championMonsterNames.length);
        this.monsterName = championMonsterNames[monster];
        return this.monsterName;
    }

    public String createBossType() {
        Random rand = new Random();
        int monster = rand.nextInt(bossMonsterNames.length);
        this.monsterName = bossMonsterNames[monster];
        return this.monsterName;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    public void setMonsterArmor(int armor) {
        this.monsterArmor += armor;
    }

    private void setMonsterDMG(int dmgChanging) {
        this.monsterDamage += dmgChanging;
    }

    public void setMonsterAttackSpeed(double changeAttackSpeed) {
        this.monsterSpeed = changeAttackSpeed;
    }

    public void setMonsterMana(double monsterManaReg) {
        this.monsterMana = monsterManaReg;
    }


    private void setMonsterStats(String monsterName) { // setMonsterStats(String monsterName) { // DMG
        Random rand = new Random();
        double attackSpeedCorrector = (rand.nextInt(1 + monsterLVL)) * 0.1;

        if (monsterName.trim().equals("Troll")) {

            this.monsterHealth = 12 + this.monsterLVL * 21 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 3 - (this.monsterLVL * 0.1) - attackSpeedCorrector;
            this.monsterCritChance = (int) (0.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = (this.monsterLVL - 1) * 2;
            this.monsterMagicResistance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 4.01 + 0.1 * this.monsterLVL + rand.nextInt(this.monsterLVL) * 0.1;

        } else if (monsterName.trim().equals("Ogre")) {
            this.monsterHealth = 9 + monsterLVL * 17 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2 - rand.nextInt(2);
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 7 + this.monsterLVL * 4;
            this.monsterSpeed = 2.05 - (this.monsterLVL * 0.05) - attackSpeedCorrector;
            this.monsterCritChance = (int) (0.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterSpellPower = this.monsterLVL * 2;
            this.monsterMagicResistance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2.51 + (0.25 * this.monsterLVL);

        } else if (monsterName.trim().equals("Cyclops")) {
            this.monsterHealth = 12 + this.monsterLVL * 12 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = (this.monsterLVL - 1) * 2;
            this.monsterEvasionChance = (int) (this.monsterLVL * 1.5);
            this.monsterDamage = 5 + this.monsterLVL * 3;
            this.monsterSpeed = 1.5 - attackSpeedCorrector;
            this.monsterCritChance = (int) (1.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = (this.monsterLVL - 1);
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 1.5 + (0.25 * this.monsterLVL);

        } else if (monsterName.trim().equals("Lich")) {
            this.monsterHealth = 13 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL + 1;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 4 + (this.monsterLVL * 3);
            this.monsterSpeed = 1.7 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 0.34);
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 1.2 + 0.3 * this.monsterLVL;

        } else if (monsterName.trim().equals("Vampire")) {
            this.monsterHealth = 13 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 4 + this.monsterLVL * 5;
            this.monsterHealthReg = (int) (this.monsterDamage * 0.5);
            this.monsterArmor = this.monsterLVL - 1;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 1.7 - attackSpeedCorrector;
            this.monsterCritChance = 3 * this.monsterLVL;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = (int) (this.monsterLVL * 1.5);
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Skeleton")) {
            this.monsterHealth = 6 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 5 + this.monsterLVL * 3;
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 1.8 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL * 3;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Werewolf")) {
            this.monsterHealth = 9 + this.monsterLVL * 17 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 4 + this.monsterLVL * 4;
            this.monsterHealthReg = this.monsterLVL * 2;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 2.223 - attackSpeedCorrector;
            this.monsterCritChance = 2 * this.monsterLVL;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Witch")) {
            this.monsterHealth = 19 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 3 + this.monsterLVL * 4;
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = 3 + this.monsterLVL;
            this.monsterSpeed = 1.6 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 0.5);
            this.monsterSpellPower = 3 + (this.monsterLVL * 2);
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 1 + this.monsterLVL;

        }

    }

    private void setChampionMonsterStats(String championMonsterStats) {
        Random rand = new Random();
        this.monsterLVL = StartGame.playerGameProgress / 5;

        if (championMonsterStats.trim().equals("Hell Monarch")) {
            this.monsterHealth = 25 + this.monsterLVL * 35 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 3 + (int) (this.monsterLVL * 1.5);
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 2.1 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 4 + this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 1.6);
            this.monsterMana = 0;
            this.monsterManaReg = 2.5 + 0.1 * this.monsterLVL + rand.nextInt(this.monsterLVL) * 0.1;
            this.monsterSpellPower = 1 + (this.monsterLVL * 3);
            this.monsterMagicResistance = 1 + (this.monsterLVL * 3);

        } else if (championMonsterStats.trim().equals("Infernal")) {
            this.monsterHealth = 25 + this.monsterLVL * 30 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 3;
            this.monsterEvasionChance = 2 + (int) (this.monsterLVL);
            this.monsterDamage = 15 + this.monsterLVL * 5;
            this.monsterSpeed = 2.5 - (0.1 * this.monsterLVL);
            this.monsterCritChance = 1 + this.monsterLVL;
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 0;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = 1 + (this.monsterLVL * 3);

        } else if (championMonsterStats.trim().equals("Dread Knight")) {
            this.monsterHealth = 20 + this.monsterLVL * 30 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = this.monsterLVL * 2;
            this.monsterArmor = 1 + this.monsterLVL * 3;
            this.monsterEvasionChance = 3 + this.monsterLVL * 2;
            this.monsterDamage = 5 + this.monsterLVL * 8;
            this.monsterSpeed = 1.6 - 0.05 * monsterLVL;
            this.monsterCritChance = 3 + (2 * this.monsterLVL);
            this.monsterStunChance = 1 + this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 1.3 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = (int) (this.monsterLVL * 0.5);
            this.monsterMagicResistance = this.monsterLVL * 2;

        } else if (championMonsterStats.trim().equals("Abomination")) {
            this.monsterHealth = 15 + this.monsterLVL * 35 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 3 + this.monsterLVL;
            this.monsterDamage = 6 + this.monsterLVL * 10;
            this.monsterSpeed = 2.7 - 0.15 * monsterLVL;
            this.monsterCritChance = 2 + this.monsterLVL;
            this.monsterStunChance = 3 + this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = this.monsterLVL;
            this.monsterMagicResistance = this.monsterLVL;

        } else if (championMonsterStats.trim().equals("Оblivion")) {
            this.monsterHealth = 12 + this.monsterLVL * 33 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 3;
            this.monsterEvasionChance = 6 + this.monsterLVL * 2;
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 2 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 1.5);
            this.monsterStunChance = (int) (this.monsterLVL * 0.5);
            this.monsterMana = 3;
            this.monsterManaReg = 2 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 3;

        } else if (championMonsterStats.trim().equals("Terror")) {
            this.monsterHealth = 20 + this.monsterLVL * 35 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 1 + this.monsterLVL * 2;
            this.monsterDamage = 10 + this.monsterLVL * 7;
            this.monsterSpeed = 2.8 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 1.5);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 3;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 2;

        } else if (championMonsterStats.trim().equals("Demon Lord")) {
            this.monsterHealth = 15 + this.monsterLVL * 25 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 4 + this.monsterLVL;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = 1 + (int) (this.monsterLVL * 2.5);
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 1.9 - 0.05 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 2);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2.501;
            this.monsterSpellPower = (this.monsterLVL * 3) + rand.nextInt(monsterLVL + 1);
            this.monsterMagicResistance = this.monsterLVL * 3;

        } else if (championMonsterStats.trim().equals("Lich King")) {
            this.monsterHealth = 20 + this.monsterLVL * 20 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 4;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 9 + this.monsterLVL * 4;
            this.monsterSpeed = 2.05 - 0.05 * monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 2);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 3;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 4;
        }

    }

    public void setBossMonsterStats(String bossType) {
        if (bossType.trim().equals("Hydra")) {
            this.monsterHealth = 500;
            this.monsterHealthReg = 15;
            this.monsterArmor = 25;
            this.monsterEvasionChance = 5;
            this.monsterDamage = 55;
            this.monsterSpeed = 1.8;
            this.monsterCritChance = 15;
            this.monsterStunChance = 5;
            this.monsterMana = 0;
            this.monsterManaReg = 0;
            this.monsterSpellPower = 15;
            this.monsterMagicResistance = 30;
        } else if (bossType.trim().equals("Fenix")) {
            this.monsterHealth = 400;
            this.monsterHealthReg = 10;
            this.monsterArmor = 15;
            this.monsterEvasionChance = 20;
            this.monsterDamage = 50;
            this.monsterSpeed = 1.5;
            this.monsterCritChance = 40;
            this.monsterStunChance = 5;
            this.monsterMana = 0;
            this.monsterManaReg = 13;
            this.monsterSpellPower = 50;
            this.monsterMagicResistance = 35;
        } else if (bossType.trim().equals("Titan")) {
            this.monsterHealth = 600;
            this.monsterHealthReg = 0;
            this.monsterArmor = 20;
            this.monsterEvasionChance = 10;
            this.monsterDamage = 65;
            this.monsterSpeed = 1.7;
            this.monsterCritChance = 10;
            this.monsterStunChance = 15;
            this.monsterMana = 5;
            this.monsterManaReg = 8;
            this.monsterSpellPower = 45;
            this.monsterMagicResistance = 30;
        }
    }

    public static void activateMonsterSpecialSkill(String monsterType) {
        if (monsterType.equals("Troll")) {
            if (StartGame.enemy.getMonsterAttackSpeed() > 2 && StartGame.enemy.getMonsterMana() >= 2 + StartGame.enemy.getMonsterLVL()) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (2 + StartGame.enemy.getMonsterLVL()));
                System.out.println("🕮 Troll cast BERSERK increasing his attack speed by 20%.");
                StartGame.enemy.setMonsterAttackSpeed(StartGame.enemy.getMonsterAttackSpeed() *0.8);
            }
            if (StartGame.enemy.getMonsterMana() >= 4 + StartGame.enemy.getMonsterMana()) {
                System.out.printf("🕮 Troll cast FIREBLAST dealing %d damage to %s.%n", (StartGame.enemy.getMonsterSpellPower() * 3) - StartGame.player1.getHeroMagicResistance(), StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (4 + StartGame.enemy.getMonsterLVL()));
            }
        } else if (monsterType.equals("Ogre")) {
            if (StartGame.enemy.getMonsterMana() >= 2 && StartGame.player1.getHeroAttackSpeed() < 3) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 2);
                StartGame.player1.setHeroAttackSpeed(StartGame.player1.getHeroAttackSpeed() * 1.2);
                if (StartGame.player1.getHeroAttackSpeed() > 3) {
                    StartGame.player1.setHeroAttackSpeed(3);
                }
                System.out.printf("Ogre cast SLOW decrease %s attack speed to %.2f.%n", StartGame.playerName, StartGame.player1.getHeroAttackSpeed() * 0.2);
            }
        } else if (monsterType.equals("Cyclops")) {
            if (StartGame.enemy.getMonsterMana() >= 2 + StartGame.enemy.monsterLVL) {

                //random - za stun chance

                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (2 + StartGame.enemy.monsterLVL));
                StartGame.player1.setHeroStunStatus(true);
                int cyclopsRockTrowDMG = 3 + StartGame.enemy.getMonsterLVL() + StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
                if (cyclopsRockTrowDMG < 0) {
                    cyclopsRockTrowDMG = 0;
                }
                System.out.printf("Cyclops trow giant rock at the hero dealing %d DMG and stunning him.%n", cyclopsRockTrowDMG);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - (3 + StartGame.enemy.getMonsterLVL()));
                StartGame.player1.setHeroStunStatus(true);
            }
        } else if (monsterType.equals("Lich")) {
            if (StartGame.enemy.getMonsterMana() >= 3) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 3);
                int lichSorrowRippleDMG = (2 * StartGame.enemy.getMonsterSpellPower()) - StartGame.player1.getHeroMagicResistance();
                System.out.printf("🕮 Lich cast SORROW RIPPLE dealing %d DMG to %s.%n", lichSorrowRippleDMG, StartGame.playerName);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - (3 + StartGame.enemy.getMonsterLVL()));
            }
        } else if (monsterType.equals("Skeleton")) {
            if (CursingCast == false) {
                CursingCast = true;
                System.out.printf("Skeleton CURS the %s to deliver minimum damage.%n", StartGame.playerName);
                StartGame.player1.setHeroMaxDMG(StartGame.player1.getHeroMinDMG());
            }
            if (skeletonArmorIncreasment == false) {
                if (StartGame.enemy.getMonsterHealth() < (int) (5 + (StartGame.enemy.getMonsterLVL() * 8))) {
                    System.out.printf("Skeleton gain %d armor. %n", StartGame.enemy.getMonsterLVL() * 2);
                    StartGame.enemy.setMonsterArmor(StartGame.enemy.getMonsterLVL() * 2);
                    skeletonArmorIncreasment = true;
                }
            }
        } else if (monsterType.equals("Werewolf")) {
            if (StartGame.enemy.getMonsterAttackSpeed() >= 1.70) {
                System.out.println("Werewolf become furious increasing his attack speed by 15% and armor by 1.");
                StartGame.enemy.setMonsterAttackSpeed(StartGame.enemy.getMonsterAttackSpeed() * 0.85);
                StartGame.enemy.setMonsterArmor(1);
            }
        } else if (monsterType.equals("Witch")) {
            int witchSpiritExtraxtDMG = StartGame.enemy.getMonsterLVL() + StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
            if (witchSpiritExtraxtDMG < 1) {
                witchSpiritExtraxtDMG = 1;
            }
            if (StartGame.enemy.getMonsterMana() >= 3 + (int)(StartGame.enemy.getMonsterLVL() * 0.5)){
                System.out.printf("🕮 Witch cast SPIRIT EXTRACT to %s dealing %d damage.%n", StartGame.playerName, witchSpiritExtraxtDMG);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - witchSpiritExtraxtDMG);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (4 + StartGame.enemy.getMonsterLVL()));
            }
            if (StartGame.enemy.getMonsterMana() >= 2 + (int)(StartGame.enemy.getMonsterLVL() * 0.5)) {
                System.out.printf("🕮 Witch cast HEALING and restore %d health.%n", 2 + (StartGame.enemy.getMonsterLVL() * 2));
                StartGame.enemy.setMonsterHealth(StartGame.enemy.getMonsterHealth() + (2 + StartGame.enemy.getMonsterLVL() * 2));
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (3 + StartGame.enemy.getMonsterLVL()));
            }
        } else if (monsterType.equals("Оblivion")) {
            int oblivionManaBurnDMG = 2 + StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
            if (oblivionManaBurnDMG < 1) {
                oblivionManaBurnDMG = 1;
            }
            int oblivionManaBurning = 2 + (int) (StartGame.enemy.getMonsterLVL() * 0.5);
            if (StartGame.enemy.getMonsterMana() >= 2 + StartGame.enemy.getMonsterLVL()) {
                System.out.printf("🕮 Oblivion cast MANA BURN deal %d damage and burning %d mana of %s.%n", oblivionManaBurnDMG, oblivionManaBurning, StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (3 + StartGame.enemy.getMonsterLVL()));
                StartGame.player1.setHeroCurrentMana(StartGame.player1.getHeroCurrentMana() - oblivionManaBurning);
                if (StartGame.player1.getHeroCurrentMana() < 1) {
                    StartGame.player1.setHeroCurrentMana(0);
                }
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - oblivionManaBurnDMG);
            }
        } else if (monsterType.equals("Infernal")) {
            int infernalBurningDMG = 2 + StartGame.enemy.getMonsterLVL() - (int) (StartGame.player1.getHeroMagicResistance() * 0.5);
            if (infernalBurningDMG < 1) {
                infernalBurningDMG = 1;
            }
            System.out.printf("Infernal burn %s deal him %d damage.%n", StartGame.playerName, infernalBurningDMG);
            StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - infernalBurningDMG);

        } else if (monsterType.equals("Abomination")) {
            int abominationLifeDrainDMG = 3 + StartGame.enemy.getMonsterLVL() + StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
            if (abominationLifeDrainDMG < 1) {
                abominationLifeDrainDMG = 1;
            }
            if (StartGame.enemy.getMonsterMana() >= 2 + StartGame.enemy.getMonsterLVL()) {
                int abominationHealthInTheBegining = StartGame.enemy.getMonsterHealth();
                System.out.printf("🕮 Abomination cast LIFE DRAIN dealing %d Health Point from %s.%n", abominationLifeDrainDMG, StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (2 + StartGame.enemy.getMonsterLVL()));
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - abominationLifeDrainDMG);
                StartGame.enemy.setMonsterHealth(StartGame.enemy.getMonsterHealth() + abominationLifeDrainDMG);
                if (StartGame.enemy.getMonsterHealth() > abominationHealthInTheBegining) {
                    StartGame.enemy.setMonsterHealth(abominationHealthInTheBegining);
                }
            }
        } else if (monsterType.equals("Dread Knight")) {
            if (StartGame.enemy.getMonsterMana() >= 5 + StartGame.enemy.getMonsterHealth() + (int) (StartGame.player1.getHeroMagicResistance() * 0.2)) {
                System.out.printf("🕮 Dread Knight cast BLIND to %s.%n", StartGame.playerName);
                StartGame.player1.setHeroStunStatus(true);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (5 + StartGame.enemy.getMonsterHealth() + (int) (StartGame.player1.getHeroMagicResistance() * 0.2)));
            }
        } else if (monsterType.equals("Hell Monarch")) {
            int blackDragonBurningFlamesDMG = StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
            if (blackDragonBurningFlamesDMG < 1) {
                blackDragonBurningFlamesDMG = 1;
            }
            if (StartGame.enemy.getMonsterMana() >= 5) {
                System.out.printf("🕮 Hell Monarch cast BURNING FLAMES dealing %d damage to %s.%n", blackDragonBurningFlamesDMG, StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - (5 + StartGame.enemy.getMonsterLVL()));
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - blackDragonBurningFlamesDMG);
            }
        } else if (monsterType.equals("Terror")) {
            if (StartGame.enemy.getMonsterMana() >= 3) {
                int terrorFreezingNovaDMG = StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
                if (terrorFreezingNovaDMG < 1) {
                    terrorFreezingNovaDMG = 1;
                }
                System.out.printf("🕮 Terror cast FREEZING NOVA dealing %d damage and reducing %s Attack Speed by 0.1%n", terrorFreezingNovaDMG, StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 3);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - terrorFreezingNovaDMG);
                StartGame.player1.setHeroAttackSpeed(StartGame.player1.getHeroAttackSpeed() + 0.1);
            }
        } else if (monsterType.equals("Demon Lord")) {
            int demonLordHellhoundDMG = 7 + StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
            if (demonLordHellhoundDMG < 1) {
                demonLordHellhoundDMG = 1;
            }
            if (StartGame.enemy.getMonsterMana() >= 5) {
                System.out.printf("🕮 Demon Lord Summon HELLHOUND that dealing %d to %s.%n", demonLordHellhoundDMG, StartGame.playerName);
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 5);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - demonLordHellhoundDMG);
            }

        } else if (monsterType.equals("Lich King")) {
            int lichKingPoisonDMG = 0;
            int lichKingTotalPoisonDMG = 0;
            if (StartGame.enemy.getMonsterMana() >= 6) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 6);
                lichKingPoisonDMG = StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance();
                if (lichKingPoisonDMG < 1) {
                    lichKingPoisonDMG = 1;
                }
                lichKingTotalPoisonDMG += lichKingPoisonDMG;
                System.out.printf("🕮 Lich King cast %d POISON to %s (total Poison %d).%n", lichKingPoisonDMG, StartGame.playerName, lichKingTotalPoisonDMG);
                lichKingPoisonActivation = true;
            }
            if (lichKingPoisonActivation == true) {
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - lichKingTotalPoisonDMG);
                System.out.printf("Poison take %d damage.%n", lichKingTotalPoisonDMG);
            }

        } else if (monsterType.equals("Hydra")) {
            if (StartGame.enemy.getMonsterHealth() <= hydraLifeTracer) {
                System.out.println("Another head grows on the HYDRA. Hers damage increase by 10.");
                StartGame.enemy.setMonsterDMG(10);
                hydraLifeTracer = 125;
            } else if (StartGame.enemy.getMonsterHealth() <= hydraLifeTracer) {
                System.out.println("Another head grows on the HYDRA. Hers damage increase by 10.");
                StartGame.enemy.setMonsterDMG(10);
                hydraLifeTracer = 0;
            }
        } else if (monsterType.equals("Fenix")) {
            if (StartGame.enemy.getMonsterMana() >= 10) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 10);
                int fenixBreath = (StartGame.enemy.getMonsterSpellPower() * 2) - StartGame.player1.getHeroMagicResistance();
                if (fenixBreath < 1) {
                    fenixBreath = 1;
                }
                System.out.printf("🕮 Fenix cast FENIX BREATH dealing %d DMG to %s%n", fenixBreath, StartGame.playerName);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - fenixBreath);
            }
        } else if (monsterType.equals("Titan")) {
            if (StartGame.enemy.getMonsterMana() >= 10) {
                StartGame.enemy.setMonsterMana(StartGame.enemy.getMonsterMana() - 10);
                int lightningBoltDMG = (StartGame.enemy.getMonsterSpellPower() - StartGame.player1.getHeroMagicResistance());
                if (lightningBoltDMG < 1) {
                    lightningBoltDMG = 1;
                }
                System.out.printf("🕮 The greatest Titan cast LIGHTNING BOLT dealing %d DMG to %s%n", lightningBoltDMG, StartGame.playerName);
                StartGame.player1.setHeroCurrentHealth(StartGame.player1.getHeroCurrentHealth() - lightningBoltDMG);
            }
        }
    }


    public boolean setMonsterStunStatus(boolean isStunned) {
        this.monsterStunStatus = isStunned;
        return monsterStunStatus;
    }

}