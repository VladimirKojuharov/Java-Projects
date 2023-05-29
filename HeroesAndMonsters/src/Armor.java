import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Armor extends Item {

    private String itemName;
    private String itemSet;
    private String itemModel;
    private int itemArmor;     // byte
    private int itemShield;    // byte
    private int itemHP;    // byte
    private double itemHPReg; // float
    private int itemEvasionChance; // byte
//    private double itemMana;   // short
    private double itemManaReg;    // short
    private int itemSpellPower;    // byte
    private int itemMagicResistance;   // byte

    private String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Dragon's", "Crusher's", "Hell's", "Bone's", "Ghost"};
    private String[] armorTypes = {"Plate", "Armor", "Vestry", "Mail"};
//    private String[] itemBonuses = {"Shield", "Health", "HP.Reg", /*"Armor",*/ "Evasion", "Mana", "M.Reg", "S.Pwr", "Resist"};

    public Armor() {
        super();
        this.setItemName();
        this.itemModel = "ARMOR";
        this.setItemStats();
        this.itemBonusesGenerator();
        this.itemStatsForPrinting();

    }

    public String getItemModel() {
        return this.itemModel;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemSet() {
        return this.itemSet;
    }

    public int getItemHP() {
        return this.itemHP;
    }

    public double getItemHPReg() {
        return this.itemHPReg;
    }

    public int getItemArmor() {
        return this.itemArmor;
    }

    public int getItemShield() {
        return this.itemShield;
    }

    public int getItemEvasionChance() {
        return this.itemEvasionChance;
    }

    public double getItemManaReg() {
        return this.itemManaReg;
    }

    public int getItemSpellPower() {
        return this.itemSpellPower;
    }

    public int getItemMagicResistance() {
        return this.itemMagicResistance;
    }


    private String setItemName() {
        Random rand = new Random();
        int randItemSet = rand.nextInt(itemSets.length);
        int randArmorType = rand.nextInt(armorTypes.length);
        this.itemSet = itemSets[randItemSet];
        this.itemName = itemSet + " " + armorTypes[randArmorType];
        return this.itemName;
    }

    @Override
    void setItemStats() {
        this.itemArmor = getItemLVL();
        this.itemShield = 0;
        this.itemHP = getItemLVL() * 5;
        this.itemHPReg = 0;
        this.itemEvasionChance = 0;
        this.itemSpellPower = 0;
        this.itemMagicResistance = 0;
        this.itemManaReg = getItemLVL() * 0.5;
    }

    @Override
    public void itemBonusesGenerator() {
        Random rand = new Random();
        int numberOfItemBonuses = 2 + this.itemLVL / 3;
        double monsterLVLBonuses;

        if (StartGame.playerGameProgress == 0) {
            monsterLVLBonuses = 0;
        } else {
            if (StartGame.playerGameProgress % 5 == 0) {    //BOSS BONUS
                monsterLVLBonuses = 2;
            } else {
                monsterLVLBonuses = (StartGame.enemy.getMonsterLVL() - (1 + StartGame.playerGameProgress / 5));
            }
        }

        ArrayList<String> itemBonus = new ArrayList<>(Arrays.asList("Shield", "Health", "HP.Reg", "Evasion", "S.Pwr", "Resist", "M.Reg"));
        Collections.shuffle(itemBonus);

        for (int i = 1; i <= numberOfItemBonuses; i++) {

//            int abilityForBoost = rand.nextInt(itemBonuses.length);
//            String chosenBonus = itemBonuses[abilityForBoost];
            itemBonus.get(0);

            if (itemBonus.get(0).equals("Shield")) {
                this.itemShield += getItemLVL() + monsterLVLBonuses;
            } else if (itemBonus.get(0).equals("Health")) {
                this.itemHP += 1 + (getItemLVL() * 4) + (monsterLVLBonuses * getItemLVL());
            } else if (itemBonus.get(0).equals("HP.Reg")) {
                this.itemHPReg += ((getItemLVL() * 0.3) + (monsterLVLBonuses * getItemLVL() * 0.15));
            } else if (itemBonus.get(0).equals("Evasion")) {
                this.itemEvasionChance += getItemLVL() + monsterLVLBonuses;
            } else if (itemBonus.get(0).equals("S.Pwr")) {
                this.itemSpellPower += getItemLVL() + monsterLVLBonuses;
            } else if (itemBonus.get(0).equals("Resist")) {
                this.itemMagicResistance += getItemLVL() + monsterLVLBonuses;
            } else if (itemBonus.get(0).equals("M.Reg")) {
                this.itemManaReg += (getItemLVL() * 0.5) + monsterLVLBonuses * 0.1;
            }
            itemBonus.remove(itemBonus.get(0));
        }

    }

    @Override
    void itemStatsForPrinting() {
        String whiteSpaces = "                              "; // 30 WHITESPACES
        // 12 SPACES FOR STAT + BONUS AMOUNT
        this.itemStat1 = "Armor:" + whiteSpaces.substring(0, 6 - Integer.toString(getItemArmor()).length()) + String.valueOf(getItemArmor());
        this.itemStat2 = "Health:" + whiteSpaces.substring(0, 5 - Integer.toString(getItemHP()).length()) + String.valueOf(getItemHP());
        this.itemStat3 = "Shield:" + whiteSpaces.substring(0, 5 - Integer.toString(getItemShield()).length()) + String.valueOf(getItemShield());
        this.itemStat4 = String.format("HP.Reg: %.2f", getItemHPReg());
        this.itemStat5 = "Evsn:" + whiteSpaces.substring(0, 7 - Integer.toString(getItemEvasionChance()).length()) + String.valueOf(getItemEvasionChance());
        this.itemStat6 = "S.Pwr:" + whiteSpaces.substring(0, 8 - Double.toString(getItemSpellPower()).length()) + String.valueOf(getItemSpellPower());
        this.itemStat7 = "Resist:" + whiteSpaces.substring(0, 5 - Integer.toString(getItemMagicResistance()).length()) + String.valueOf(getItemMagicResistance());
        this.itemStat8 = "M.Reg:" + whiteSpaces.substring(0, 6 - Double.toString(getItemManaReg()).length()) + String.valueOf(getItemManaReg());
        this.itemStat9 = whiteSpaces.substring(0, 12);
    }


}
