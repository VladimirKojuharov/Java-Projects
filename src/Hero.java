import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Hero {

    private final byte HERO_MAX_LVL = 10;
    private int heroCurrentLVL; // byte
    private int heroCurrentXP;  // short

    private String playerName;
    private HeroType heroType;

    public Weapon weapon;
    private String heroWeaponSet;
    private String heroWeaponName;
    private int heroWeaponLVL = 1;   // byte

    public Armor armor;
    private String heroArmorSet;
    private String heroArmorName;
    private int heroArmorLVL = 1;    // byte

    public Helm helm;
    private String heroHelmSet;
    private String heroHelmName;
    private int heroHelmLVL = 1;  // byte

    private double heroBaseHealth;  //byte
    private int heroMaxHealth;      // short
    private double heroBaseHealthReg; // float
    private int heroBaseArmor;      // byte
    private int heroBaseShield;
    private int heroBaseEvasionChance;   // byte
    private int heroBaseDamage; // byte
    private double heroBaseAttackSpeed; // float
    private int heroBaseCritChance;  // byte
    private int heroBaseStunChance;  // byte
    private double heroBaseMana;    // byte
    private double heroBaseManaReg;       // float               // ManaIncreaseRate; // acumulation // aggregationn
    private int heroBaseSpellPower;     // byte
    private int heroBaseMagicResistance; // byte

    private double currentHeroHealth;    // short
    private double heroHealthReg;    // short
    public int currentHeroArmor;    // byte
    private int heroShield;
    private int heroEvasionChance;   // byte
    private int heroMinDMG = heroBaseDamage;    // short
    private int heroMaxDMG = heroBaseDamage;    // short
    private double heroAttackSpeed;         // float
    private int heroCritChance;      // byte
    private int heroStunChance;      // byte
    private double currentHeroMana;  // short
    private double heroManaReg;      // short
    private int heroSpellPower;      // byte
    private int heroMagicResistance;     // byte
    private boolean heroStunStatus = false;
    public ArrayList<Spell> heroSpellList;
    public ArrayList<Spell> heroSpellListCopy;

    private String whithSpaces = "                              "; // 30 WhiteSpaces
    // charge - before Battle DMG
    // Life Drain - vampiric Strike
    private final static ArrayList<Spell> spellsForLearning = new ArrayList<>(3);



    public Hero(String playerName, HeroType heroType, Weapon weapon, Armor armor, Helm helm) {

        this.setPlayerName(playerName);
        this.heroCurrentLVL = 1;
        this.heroType = heroType;
        this.heroCurrentXP = 0;

        this.weapon = weapon;
        this.setHeroWeaponName(this.weapon);
        this.setHeroWeaponLVL(this.weapon);

        this.armor = armor;
        this.setHeroArmorName(this.armor);
        this.setHeroArmorLVL(this.armor);

        this.helm = helm;
        this.setHeroHelmName(this.helm);
        this.setHeroHelmLVL(this.helm);

        this.setHeroBaseStats();
        this.setHeroMaxHealth();
        this.setHeroCurrentHealth(heroBaseHealth + this.armor.getItemHP() + this.helm.getItemHP());
        this.setHeroShield(heroBaseShield + this.armor.getItemShield() + this.helm.getItemShield());
        this.setHeroHealthReg();
        this.setCurrentHeroArmor();
        this.setHeroEvasionChance();
        this.setHeroCurrentMana(heroBaseMana);

        this.changeHeroWeapon(weapon);
        this.setHeroMinDMG();
        this.setHeroMaxDMG(this.heroMaxDMG = getHeroBaseDamage() + getHeroWeaponMaxDMG());
        this.setHeroAttackSpeed(getHeroBaseAttackSpeed() + getHeroWeaponAttackSpeed());
        this.setHeroCritChance();
        this.setHeroStunChance();
        this.setHeroManaReg();
        this.setHeroStunStatus(heroStunStatus);
        this.heroSpellList = new ArrayList<>();
        this.heroSpellListCopy = new ArrayList<>();

    }


    public ArrayList<Spell> getHeroSpellList() {
        return this.heroSpellList;
    }

//    ------ GETTERS ------ GETTERS ------ GETTERS ------ GETTERS ------ GETTERS ------ GETTERS ------ GETTERS ------


    public void lvlUP() throws InterruptedException {
        if (this.heroCurrentLVL < HERO_MAX_LVL) {
            String whiteSpaces = "                              "; // 30 white spaces
            System.out.printf("%n%n _________________________________________________________________%n");
            System.out.println("/                                                                 \\");
            System.out.println("|                                                                 |");
            System.out.printf("|" + whiteSpaces.substring((int)Math.ceil(StartGame.player1.playerName.length()/2), 21) + "*****  %s LVL UP!  *****" +
                    whiteSpaces.substring((int)Math.ceil(((double)StartGame.player1.playerName.length()/2)), 22) + "|%n", StartGame.player1.playerName);
            System.out.println("|                                                                 |");
            System.out.printf("\\_________________________________________________________________/%n%n%n%n");
            Thread.sleep(2000);

            this.heroCurrentXP = getHeroXP() - (StartGame.player1.getHeroLVL() * 5);
            this.heroCurrentLVL++;
            this.setHeroBaseStats();

            if (this.getHeroCurrentHealth() < this.getHeroMaxHealth()) {
                setHeroCurrentHealth(getHeroCurrentHealth() + StartGame.player1.getHeroMaxHealth() * 0.2);
            }
            if (this.getHeroCurrentHealth() > this.getHeroMaxHealth()) {
                setHeroCurrentHealth(this.getHeroMaxHealth());
            }
            this.setHeroMaxHealth();
            this.setHeroHealthReg();
            this.setCurrentHeroArmor();
            this.setHeroShield(getHeroBaseShield() + this.armor.getItemShield() + this.helm.getItemShield());
            this.setHeroEvasionChance();
            this.setHeroMinDMG();
            this.setHeroMaxDMG(this.heroMaxDMG = getHeroBaseDamage() + getHeroWeaponMaxDMG());
            this.setHeroCritChance();
            this.setHeroAttackSpeed(getHeroBaseAttackSpeed() + getHeroWeaponAttackSpeed());
            this.setHeroStunChance();
            this.setHeroSpellPower();
            this.setHeroMagicResistance();
            this.setHeroCurrentMana(getHeroBaseMana()); //+ this.armor.getItemMana());
            this.setHeroManaReg();

            learNewSpell();


            Thread.sleep(3500L);
        }
    }

    public void learNewSpell() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String lvlUPPlayerSpellChoose = " ";

        generateSpellsForLearningWhenHeroLvlUPList();

        while ((!lvlUPPlayerSpellChoose.equals("1") && !lvlUPPlayerSpellChoose.equals("2") && !lvlUPPlayerSpellChoose.equals("3"))) {
            System.out.printf("Choose a magic to be learned, hero.%n%n");
            printSpellsForLearningWhenHeroLvlUPList();
            System.out.printf("%n(1) " + spellsForLearning.get(0).getSpellName() + "  " +" (2) " + spellsForLearning.get(1).getSpellName() + "  " +
                    "(3) " + spellsForLearning.get(2).getSpellName() + "%n");
            lvlUPPlayerSpellChoose = scanner.nextLine();
        }

        if (lvlUPPlayerSpellChoose.equals("1")) {
            this.heroSpellList.add(this.spellsForLearning.get(0));
            this.heroSpellListCopy.add(this.spellsForLearning.get(0));
//            StartGame.spellsList.remove(0);


        } else if (lvlUPPlayerSpellChoose.equals("2")) {
            this.heroSpellList.add(this.spellsForLearning.get(1));
            this.heroSpellListCopy.add(this.spellsForLearning.get(1));
//            StartGame.spellsList.remove(1);

        } else if (lvlUPPlayerSpellChoose.equals("3")) {
            this.heroSpellList.add(this.spellsForLearning.get(2));
            this.heroSpellListCopy.add(this.spellsForLearning.get(2));
//            StartGame.getSpellsList().remove(2);
        }
        spellsForLearning.clear();
        System.out.printf("%s learn the might %s magic.%n%n%n%n", StartGame.playerName, StartGame.player1.heroSpellList.get(StartGame.player1.getHeroSpellList().size()-1).getSpellName());
        Thread.sleep(2000L);
    }

    private void printSpellsForLearningWhenHeroLvlUPList(){
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow1() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow1() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow1() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow2() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow2() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow2() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow3() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow3() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow3() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow4() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow4() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow4() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow5() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow5() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow5() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow6() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow6() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow6() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow7() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow7() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow7() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow8() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow8() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow8() + whithSpaces.substring(0,8));
        System.out.println(spellsForLearning.get(0).getSpellDescriptionRow9() + whithSpaces.substring(0,8) + spellsForLearning.get(1).getSpellDescriptionRow9() +
                whithSpaces.substring(0,8) + spellsForLearning.get(2).getSpellDescriptionRow9() + whithSpaces.substring(0,8));
    }

    private void generateSpellsForLearningWhenHeroLvlUPList() {
        Collections.shuffle(StartGame.getSpellsList());
        for (int i = 0; i < 3; i++) {
            this.spellsForLearning.add(StartGame.getSpellsList().get(i));
        }
    }

    public HeroType getHeroType() {
        return this.heroType;
    }

    public int getHeroLVL() {
    return this.heroCurrentLVL;
    }

    public int getHeroWeaponLVL() { return this.heroWeaponLVL;}

    public int getHeroArmorLVL() { return this.heroArmorLVL;}

    public int getHeroHelmLVL() {
        return this.heroHelmLVL;
    }

    public double getHeroBaseHealth() {
        return this.heroBaseHealth;
    }

    public int getHeroMaxHealth() {
        return this.heroMaxHealth;
    }

    public double getHeroHealthReg() { return this.heroHealthReg; }

    public int getHeroBaseArmor() {
        return this.heroBaseArmor;
    }

    public int getHeroBaseShield() {
        return this.heroBaseShield;
    }

    public int getHeroBaseDamage() {
        return this.heroBaseDamage;
    }

    public double getHeroBaseAttackSpeed() {
        return this.heroBaseAttackSpeed;
    }

    public int getHeroBaseCritChance() {
        return this.heroBaseCritChance;
    }

    public int getHeroBaseStunChance() {
        return heroBaseStunChance;
    }

    public int getHeroBaseEvasionChance() {
        return this.heroBaseEvasionChance;
    }

    public double getHeroBaseMana() {
        return this.heroBaseMana;
    }

    public double getHeroBaseManaReg() { return  this.heroBaseManaReg; }

    public int getHeroBaseSpellPower() {
        return this.heroBaseSpellPower;
    }

    public int getHeroBaseMagicResistance() {
        return this.heroBaseMagicResistance;
    }

    public String showHeroBaseStats() {
        String heroStats = String.format(playerName + "%n*" + this.getHeroType() + "*%nLVL:   " + this.getHeroLVL() + "%nXP: " + this.getHeroXP() + "/" +
                this.getHeroLVL() * 5 + "%nHealt: " + this.getHeroCurrentHealth() +"/" + this.getHeroMaxHealth() + "%nArmor:  " + getHeroCurrentArmor() + "%nDMG:   " + getHeroBaseDamage() +
                "%nAS:   %.2f%nS.Pwr: " + getHeroSpellPower() + "%nM.Reg: %.1f%n%n", getHeroBaseAttackSpeed(), getHeroManaReg());
        return heroStats;
    }

    public String showHeroWeapon() {
        String showEquippedWeapon = this.weapon.showItemStats();
        return showEquippedWeapon;
    }

    public String getHeroWeaponName() { return this.heroWeaponName;}

    public String getHeroArmorName() { return this.heroArmorName;}

    public String getHeroHelmName() { return this.heroHelmName;}

    public int getHeroWeaponMinDMG() {
        return this.weapon.getItemMinDMG();
    }

    public int getHeroWeaponMaxDMG() {
        return this.weapon.getItemMaxDMG();
    }

    public double getHeroWeaponAttackSpeed() {
        return this.weapon.getItemAttackSpeed();
    }

    public int getHeroXP() {
        return this.heroCurrentXP;
    }

    public double getHeroCurrentHealth() {
        return this.currentHeroHealth;
    }

    public double getHeroBaseHealthReg() {
        return this.heroBaseHealthReg;
    }

    public int getHeroCurrentArmor() {
        return this.currentHeroArmor;
    }

    public int getHeroShield() {
        return this.heroShield;
    }

    public int getHeroMinDMG() { return this.heroMinDMG;}

    public int getHeroMaxDMG() { return this.heroMaxDMG;}

    public double getHeroAttackSpeed() {
        return this.heroAttackSpeed;
    }

    public int getHeroCritChance() {
        return this.heroCritChance;
    }

    public int getHeroStunChance() {
        return this.heroStunChance;
    }

    public int getHeroEvasionChance() {
        return this.heroEvasionChance;
    }

    public double getHeroCurrentMana() {
        return this.currentHeroMana;
    }

    public double getHeroManaReg() {
        return this.heroManaReg;
    }

    public int getHeroMagicResistance() {
        return this.heroMagicResistance;
    }

    public int getHeroSpellPower() {return this.heroSpellPower; }

    public String showEquippedHeroStats() {
        String heroStats = String.format(playerName + "%n*" + this.getHeroType() + "*%nLVL:   " + this.getHeroLVL() + "%nXP:   " + this.getHeroXP() +
                "/" + this.getHeroLVL() * 5 + "%nHealt: %.0f%nArmor:  " + getHeroCurrentArmor() + "%nDMG:   " + getHeroMinDMG() + "-" + getHeroMaxDMG() +
                "%nAS:    %.2f%nS.Pwr: " + getHeroSpellPower() + "%nM.Reg:  %.1f%n%n", Math.floor(this.getHeroCurrentHealth()), getHeroAttackSpeed(),
                Math.floor(this.getHeroManaReg()));
        return heroStats;
    }

    public boolean getHeroStunStatus() {
        return this.heroStunStatus;
    }


//    ------ SETTERS ------ SETTERS ------ SETTERS ------ SETTERS ------ SETTERS ------ SETTERS ------ SETTERS ------

    private void setPlayerName(String playerName) {
        this.playerName = playerName.trim();
    }

    private String setHeroWeaponName(Weapon weapon) {
        this.heroWeaponName = weapon.getItemName();
        return heroWeaponName;
    }

    private int setHeroWeaponLVL(Weapon weapon) {
        this.heroWeaponLVL = weapon.getItemLVL();
        return heroWeaponLVL;
    }

    private String setHeroArmorName(Armor armor) {
        this.heroArmorName = armor.getItemName();
        return heroArmorName;
    }

    private String setHeroHelmName(Helm helm) {
        this.heroHelmName = helm.getItemName();
        return heroHelmName;
    }

    private int setHeroArmorLVL(Armor armor) {
        this.heroArmorLVL = armor.getItemLVL();
        return heroArmorLVL;
    }

    private int setHeroHelmLVL(Helm helm) {
        this.heroHelmLVL = helm.getItemLVL();
        return heroHelmLVL;
    }

    public void changeHeroWeapon(Weapon weapon) {
        this.heroMinDMG = getHeroBaseDamage();
        this.heroMaxDMG = getHeroBaseDamage();
        this.heroAttackSpeed = getHeroBaseAttackSpeed();
        this.heroCritChance = getHeroBaseCritChance();
        this.heroStunChance = getHeroBaseStunChance();
        this.heroSpellPower = getHeroBaseSpellPower() + armor.getItemSpellPower();

        this.weapon = weapon;
        this.heroWeaponSet = weapon.getItemSet();
        this.setHeroWeaponName(this.weapon);
        this.heroWeaponLVL = weapon.itemLVL;
        this.heroMinDMG = getHeroBaseDamage() + getHeroWeaponMinDMG();
        this.heroMaxDMG = getHeroBaseDamage() + getHeroWeaponMaxDMG();
        this.heroAttackSpeed = getHeroBaseAttackSpeed() + getHeroWeaponAttackSpeed();
        this.heroCritChance = getHeroBaseCritChance() + weapon.getItemCriticalChance();
        this.heroStunChance = getHeroBaseStunChance() + weapon.getItemStunChance();
        this.heroSpellPower = getHeroBaseSpellPower() + weapon.getItemSpellPower() + armor.getItemSpellPower();
    }

    public void equipHeroArmor(Armor armor) {
        this.armor = armor;
        this.heroArmorSet = armor.getItemSet();
        this.setHeroArmorName(this.armor);
        this.heroArmorLVL = armor.getItemLVL();

        this.heroMaxHealth = (int)(getHeroBaseHealth() + armor.getItemHP());
        this.heroHealthReg = getHeroBaseHealthReg() + armor.getItemHPReg();
        this.currentHeroArmor = getHeroBaseArmor() + armor.getItemArmor();
        this.heroShield = getHeroShield() + armor.getItemShield();
        this.heroEvasionChance = getHeroBaseEvasionChance() + armor.getItemEvasionChance();
        this.currentHeroMana = getHeroBaseMana(); // + armor.getItemMana();
        this.heroManaReg = getHeroBaseManaReg() + armor.getItemManaReg();
        this.heroSpellPower = getHeroBaseSpellPower() + armor.getItemSpellPower();
        this.heroMagicResistance = getHeroBaseMagicResistance() + armor.getItemMagicResistance();
    }

    public void equipHeroHelm(Helm helm) {
        this.helm = helm;
        this.heroArmorSet = helm.getItemSet();
        this.setHeroHelmName(this.helm);
        this.heroHelmLVL = helm.getItemLVL();

        this.heroMaxHealth = (int)(getHeroBaseHealth() + armor.getItemHP() + helm.getItemHP());
        this.heroShield = getHeroBaseShield() + armor.getItemShield() + helm.getItemShield();
        this.heroSpellPower = getHeroBaseSpellPower() + armor.getItemSpellPower() + helm.getItemSpellPower();
        this.heroMagicResistance = getHeroBaseMagicResistance() + armor.getItemMagicResistance() + helm.getItemMagicResistance();
    }

    public void changeHeroArmor(Armor oldArmor, Armor armor) {
//        this.armor = oldArmor;
//        this.heroMaxHealth -= oldArmor.getItemHP();
//        this.heroHealthReg -= oldArmor.getItemHPReg();
//        this.currentHeroArmor -= oldArmor.getItemArmor();
//        this.heroShield -= oldArmor.getItemShield();
//        this.heroEvasionChance -= oldArmor.getItemEvasionChance();
//        this.currentHeroMana -= oldArmor.getItemMana();
//        this.heroManaReg -= oldArmor.getItemManaReg();
//        this.heroSpellPower -= oldArmor.getItemSpellPower();
//        this.heroMagicResistance -= oldArmor.getItemMagicResistance();

        this.heroArmorSet = armor.getItemSet();
        this.heroMaxHealth = getHeroMaxHealth() - oldArmor.getItemHP() + armor.getItemHP(); //  int)(getHeroBaseHealth() + this.armor.getItemHP()); // + Helm itemHP
        if (this.currentHeroHealth > this.heroMaxHealth) {
            this.currentHeroHealth = this.heroMaxHealth;
        }
        this.heroHealthReg = this.heroHealthReg - oldArmor.getItemHPReg() + armor.getItemHPReg(); // this.armor.getItemHPReg();
        this.currentHeroArmor = getHeroBaseArmor() + armor.getItemArmor();
        this.heroShield = this.heroShield - oldArmor.getItemShield() + armor.getItemShield();
        this.heroEvasionChance =  this.heroEvasionChance - oldArmor.getItemEvasionChance() + armor.getItemEvasionChance();
//        this.currentHeroMana = this.currentHeroMana - oldArmor.getItemMana() + armor.getItemMana();
        this.heroManaReg = this.heroManaReg - oldArmor.getItemManaReg() + armor.getItemManaReg();
        this.heroSpellPower = this.heroSpellPower - oldArmor.getItemSpellPower() + armor.getItemSpellPower();
        this.heroMagicResistance = this.heroMagicResistance - oldArmor.getItemMagicResistance() + armor.getItemMagicResistance();
        this.armor = armor;
        this.setHeroArmorName(this.armor);
        this.heroArmorLVL = armor.itemLVL;
    }

    public void changeHeroHelm(Helm oldHelm, Helm newHelm) {
        this.heroHelmSet = helm.getItemSet();
        this.heroMaxHealth = getHeroMaxHealth() - oldHelm.getItemHP() + newHelm.getItemHP();
        if (this.currentHeroHealth > this.heroMaxHealth) {
            this.currentHeroHealth = this.heroMaxHealth;
        }
        this.heroShield = this.heroShield - oldHelm.getItemShield() + newHelm.getItemShield();
        this.heroSpellPower = this.heroSpellPower - oldHelm.getItemSpellPower() + newHelm.getItemSpellPower();
        this.heroMagicResistance = this.heroMagicResistance - oldHelm.getItemMagicResistance() + newHelm.getItemMagicResistance();
        this.helm = newHelm;
        this.setHeroHelmName(this.helm);
        this.heroHelmLVL = newHelm.getItemLVL();
    }



    private void setHeroBaseStats() {
        if (this.heroType.equals(HeroType.WARRIOR)) {
            this.heroBaseHealth = 90 + (10 * getHeroLVL());
            this.heroBaseHealthReg = 0.2 + (getHeroLVL() * 0.8);
            this.heroBaseArmor = (getHeroLVL());
            this.heroBaseShield = 0;
            this.heroBaseEvasionChance = (int)(getHeroLVL() * 0.5);
            this.heroBaseDamage = 6 + (int)(1.5 * getHeroLVL());
            this.heroBaseAttackSpeed = 1.02 - 0.02 * getHeroLVL();
            this.heroBaseCritChance = 1 + (int)(getHeroLVL() * 0.5);
            this.heroBaseStunChance = getHeroLVL();
            this.heroBaseSpellPower = (int) Math.floor(getHeroLVL() / 3);
            this.heroBaseMagicResistance = getHeroLVL();
            this.heroBaseMana = 0;
            this.heroBaseManaReg = 1 + getHeroLVL() * 0.2;

        } else if (this.heroType.equals(HeroType.MAGE)) {
            this.heroBaseHealth = 65 + (5 * getHeroLVL());
            this.heroBaseHealthReg = getHeroLVL() * 0.6;
            this.heroBaseArmor = (int)(getHeroLVL() * 0.5);
            this.heroBaseShield = 4 + getHeroLVL();
            this.heroBaseEvasionChance = (int)(getHeroLVL() * 0.5);
            this.heroBaseDamage = 4 + (int)( 1 * getHeroLVL());
            this.heroBaseAttackSpeed = 1.02 - 0.02 * getHeroLVL();
            this.heroBaseCritChance = (int)(getHeroLVL() * 0.5);
            this.heroBaseStunChance = (int)(getHeroLVL() * 0.5);
            this.heroBaseSpellPower = 1 + getHeroLVL();
            this.heroBaseMagicResistance = getHeroLVL() * 2;
            this.heroBaseMana = 1 + (int)(getHeroLVL() * 0.25);
            this.heroBaseManaReg = 1 + (getHeroLVL() * 0.5);


        } else if (this.heroType.equals(HeroType.ROGUE)) {
            this.heroBaseHealth = 83 + (7 * getHeroLVL());
            this.heroBaseHealthReg = getHeroLVL() * 0.7;
            this.heroBaseArmor = 1 + (int)(getHeroLVL() * 0.5);
            this.heroShield = 0;
            this.heroBaseEvasionChance = (int)(getHeroLVL());
            this.heroBaseDamage = 6 + 1 * getHeroLVL();
            this.heroBaseAttackSpeed = 0.82 - 0.02 * getHeroLVL();
            this.heroBaseCritChance = 3 + getHeroLVL();
            this.heroBaseStunChance =  (int)(getHeroLVL() * 0.5);
            this.heroBaseSpellPower = (int) Math.floor(getHeroLVL() * 0.5);
            this.heroBaseMagicResistance = getHeroLVL();
            this.heroBaseMana = 0;
            this.heroBaseManaReg = 1 + getHeroLVL() * 0.3;


        }
    }

    public void setHeroXP() {
        if (StartGame.playerGameProgress % 5 != 0) {
            this.heroCurrentXP += StartGame.enemy.getMonsterLVL();
        } else {this.heroCurrentXP += StartGame.enemy.getMonsterLVL() + 2;}
    }

    public void setHeroCurrentHealth(double currentHeroHealth) {
        this.currentHeroHealth = currentHeroHealth;
    }

    public void setHeroMaxHealth() {
        this.heroMaxHealth = (int)getHeroBaseHealth() + this.armor.getItemHP() + this.helm.getItemHP();
    }

    public void setHeroHealthReg() {
        this.heroHealthReg = getHeroBaseHealthReg() + this.armor.getItemHPReg();
    }

    public void setCurrentHeroArmor() {
        this.currentHeroArmor = getHeroBaseArmor() + this.armor.getItemArmor();
    }

    public void setHeroShield(int currentHeroShield) {
        this.heroShield = currentHeroShield;
    }

    public void setHeroEvasionChance() {
        this.heroEvasionChance = getHeroBaseEvasionChance() + this.armor.getItemEvasionChance();
    }

    public void setHeroMinDMG() {
        this.heroMinDMG = getHeroBaseDamage() + getHeroWeaponMinDMG();
    }

    public void setHeroMaxDMG(int damage) {
        this.heroMaxDMG = damage;
    }

    public void setHeroAttackSpeed(double heroAttackSpeed) {
        this.heroAttackSpeed = heroAttackSpeed;
    }

    public void setHeroCritChance() {
        this.heroCritChance = getHeroBaseCritChance() + this.weapon.getItemCriticalChance();
    }

    public void setHeroStunChance() {
        this.heroStunChance = getHeroBaseStunChance() + this.weapon.getItemStunChance();
    }

    public void setHeroCurrentMana (double currnetHeroMana) {
        this.currentHeroMana = currnetHeroMana;
    }

    public void setHeroManaReg () {
        this.heroManaReg = getHeroBaseManaReg() + this.armor.getItemManaReg();
    }

    public void setHeroSpellPower() {
    this.heroSpellPower = getHeroBaseSpellPower() + this.weapon.getItemSpellPower() + this.armor.getItemSpellPower() + this.helm.getItemSpellPower();
}

    public void setHeroMagicResistance() {
        this.heroMagicResistance = getHeroBaseMagicResistance() + this.armor.getItemMagicResistance() + this.helm.getItemMagicResistance();
    }

    public boolean setHeroStunStatus(boolean isStun) {
        this.heroStunStatus = isStun;
        return heroStunStatus;
    }

}