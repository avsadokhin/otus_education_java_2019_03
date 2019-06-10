package ru.otus.atm;

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

    public void putBanknote(int putCnt){
        count= count + putCnt;
    }
    public void getBanknote(int getCnt){
        count= count - getCnt;
    }


}
