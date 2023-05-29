import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Helm extends Item {


    private String itemName;
    private String itemSet;
    private String itemModel;
    private int itemHP;
    private int itemShield;
    private int itemSpellPower;
    private int itemMagicResistance;
//    private double itemMana;   // short


    private String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Dragon's", "Hell's", "Crusher's", "Bone's", "Ghost"};
    private String[] helmTypes = {"Cap", "Helm", "Crown", "Mask", "Sallet"};
//    private String[] itemBonuses = {"Shield", "Health", "S.Pwr", "Resist"};

    public Helm() {
        super();
        this.setItemName();
        this.itemModel = "HELM";
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

    public int getItemShield() {
        return this.itemShield;
    }

    public int getItemSpellPower() {
        return this.itemSpellPower;
    }

    public int getItemMagicResistance() {
        return this.itemMagicResistance;
    }

//    public double getItemMana() {
//        return this.itemMana;
//    }


    private String setItemName() {
        Random rand = new Random();
        int randItemSet = rand.nextInt(itemSets.length);
        int randHelmType = rand.nextInt(helmTypes.length);
        this.itemSet = itemSets[randItemSet];
        this.itemName = itemSet + " " + helmTypes[randHelmType];
        return this.itemName;
    }

    @Override
    void setItemStats() {
        this.itemLVL = getItemLVL();
        this.itemHP = getItemLVL() * 2;
        this.itemShield = 0;
        this.itemSpellPower = 0;
//        this.itemMana = 0;
        this.itemMagicResistance = 0;

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

        ArrayList<String> itemBonus = new ArrayList<>(Arrays.asList("Shield", "Health", "S.Pwr", "Resist", "Shield", "Health", "S.Pwr" , "Resist"));
        Collections.shuffle(itemBonus);

        for (int i = 1; i <= numberOfItemBonuses; i++) {

//            int abilityForBoost = rand.nextInt(itemBonuses.length);
//            String chosenBonus = itemBonuses[abilityForBoost];

            if (itemBonus.get(0).equals("Health")) {
                this.itemHP += 1 + (getItemLVL() * 2) + (monsterLVLBonuses * getItemLVL());
            } else if (itemBonus.get(0).equals("Shield")) {
                this.itemShield += (int) Math.ceil((getItemLVL() + monsterLVLBonuses) * 0.5);
            } else if (itemBonus.get(0).equals("S.Pwr")) {
                this.itemSpellPower += getItemLVL() + monsterLVLBonuses;
//            } else if (itemBonus.get(0).equals("Mana")) {
//                this.itemMana += Math.ceil((getItemLVL() + monsterLVLBonuses) * 0.75);
//            }
            } else if (itemBonus.get(0).equals("Resist")){
                this.itemMagicResistance += (int)(getItemLVL() * 0.5)+ monsterLVLBonuses;
            }
            itemBonus.remove(itemBonus.get(0));

        }
        itemBonus.clear();
    }

    @Override
    void itemStatsForPrinting() {
        String whiteSpaces = "                              "; // 30 WHITESPACES
        // 12 SPACES FOR STAT + BONUS AMOUNT
        this.itemStat1 = "Health:" + whiteSpaces.substring(0, 5 - Integer.toString(getItemHP()).length()) + String.valueOf(getItemHP());
        this.itemStat2 = "Shield:" + whiteSpaces.substring(0, 5 - Integer.toString(getItemShield()).length()) + String.valueOf(getItemShield());
        this.itemStat3 = "S.Pwr:" + whiteSpaces.substring(0, 8 - Double.toString(getItemSpellPower()).length()) + String.valueOf(getItemSpellPower());
        this.itemStat4 = "Resist:" + whiteSpaces.substring(0, 5 - Integer.toString((int) getItemMagicResistance()).length()) + String.valueOf(getItemMagicResistance());
        this.itemStat5 = whiteSpaces.substring(0, 12);
        this.itemStat6 = whiteSpaces.substring(0, 12);
        this.itemStat7 = whiteSpaces.substring(0, 12);
        this.itemStat8 = whiteSpaces.substring(0, 12);
        this.itemStat9 = whiteSpaces.substring(0, 12);
    }

}
