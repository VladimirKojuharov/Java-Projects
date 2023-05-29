import java.util.Random;

public abstract class Item {

    public int itemLVL;
    public String itemName;
    public String itemModel;
    public String itemSet;
    public String itemStat1;
    public String itemStat2;
    public String itemStat3;
    public String itemStat4;
    public String itemStat5;
    public String itemStat6;
    public String itemStat7;
    public String itemStat8;
    public String itemStat9;

//    private String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Justice's", "Dragon's", "Hell's", "Crusher's", "Bone's", "Ghost"};
//    private String[] weaponType = {"Axe", "Hammer", "Sword", "Bow", "Staff", "Crossbow", "Scepter"};
//    private String[] armorTypes = {"Plate", "Armor", "Vestry", "Ring Mail", "Full Plate"};


    public Item() {
        this.setItemLVL();
        this.itemStat1 = "0";
        this.itemStat2 = "0";
        this.itemStat3 = "0";
        this.itemStat4 = "0";
        this.itemStat5 = "0";
        this.itemStat6 = "0";
        this.itemStat7 = "0";
        this.itemStat8 = "0";
        this.itemStat9 = "0";
    }


    public int getItemLVL() {
        return this.itemLVL;
    }

    String getItemName() {
        return this.itemName;
    }

    String getItemSet() {
        return this.itemSet;
    }

    String getItemModel() {
        return this.itemModel;
    }


    public void setItemLVL() {
        Random random = new Random();
        if (StartGame.playerGameProgress == 0) {
            this.itemLVL = 1;
        } else {
            int itemRaretyChance = random.nextInt(100) + 1 + ((StartGame.enemy.getMonsterLVL() - StartGame.player1.getHeroLVL()) * 5);

            if (StartGame.playerGameProgress % 5 == 0) {            // BOSS UNIT
                this.itemLVL = 1 + (StartGame.playerGameProgress / 5);
            } else {  // NORMAL UNIT
                if (itemRaretyChance < 90) {
                    this.itemLVL = 1 + (StartGame.playerGameProgress / 5);
                } else if (itemRaretyChance >= 90 /*&& itemRaretyChance <= 100*/) {
                    this.itemLVL = 2 + (StartGame.playerGameProgress / 5);
//                } else if (itemRaretyChance > 100) {
//                    this.itemLVL = 3 + (StartGame.playerGameProgress / 5);
                }
            }
        }
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    abstract void setItemStats();

    abstract void itemBonusesGenerator();

    abstract void itemStatsForPrinting();

}