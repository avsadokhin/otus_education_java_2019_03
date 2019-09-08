package ru.otus.hw15.banknote;

public class BanknoteCell {
    private Banknote banknote;
    private int count;

    public BanknoteCell(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0;
    }
    public BanknoteCell(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }


    public Banknote getBanknote() {
        return banknote;
    }


    public int getCount() {
        return count;
    }

    void increaseBanknoteCount(int putCnt){
        count= count + putCnt;
    }
    void decreaseBanknoteCount(int getCnt){
        count= count - getCnt;
    }

    public void setCount(int count)  {
        this.count = count;
    }  

}
