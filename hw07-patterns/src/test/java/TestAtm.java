import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestAtm {

    AtmBoxImpl atm1 = new AtmBoxImpl("AlfaBank_1");
    AtmBoxImpl atm2 = new AtmBoxImpl("AlfaBank_2");

    @BeforeEach
    public void bankAtm1Processing() {

        System.out.println("___________________________________________");
        Set<BanknoteCell> cells = new HashSet<>();

        cells.add(new BanknoteCell(Banknote.THOUSAND, 10));
        cells.add(new BanknoteCell(Banknote.TWO_HUNDRED, 5));
        cells.add(new BanknoteCell(Banknote.TEN, 0));
        cells.add(new BanknoteCell(Banknote.FIFTY, 0));

        BanknoteStorage banknoteStorage = new BanknoteStorage(cells);
        atm1.mountStorageService(banknoteStorage);

        System.out.println("Initial values for Atm: " + atm1.getAtmCode());
        atm1.showBalance();
        atm1.showCellsBalanceService();


        Map<Banknote, Integer> moneyCrate = new HashMap<>();
        moneyCrate.put(Banknote.TEN, 2);
        moneyCrate.put(Banknote.FIFTY, 1);
        atm1.putBanknotes(moneyCrate);

        System.out.println("\nUpdated state:");
        atm1.showBalance();
        atm1.showCellsBalanceService();

        moneyCrate.clear();
        try {

            moneyCrate = atm1.getBanknotes(11050);
            System.out.println("\nMoney crate outcome:");
            moneyCrate.forEach((banknote, integer) -> System.out.println(banknote.getValue() + ": " + integer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nFinal state:");
        atm1.showBalance();
        atm1.showCellsBalanceService();
    }


    @BeforeEach
    public void bankAtm2Processing() {

        System.out.println("___________________________________________");
        Set<BanknoteCell> cells = new HashSet<>();

        cells.add(new BanknoteCell(Banknote.THOUSAND, 10));
        cells.add(new BanknoteCell(Banknote.TWO_HUNDRED, 10));
        cells.add(new BanknoteCell(Banknote.TEN, 0));
        cells.add(new BanknoteCell(Banknote.FIFTY, 0));

        BanknoteStorage banknoteStorage = new BanknoteStorage(cells);
        atm2.mountStorageService(banknoteStorage);

        System.out.println("Initial values for Atm: " + atm2.getAtmCode());
        atm2.showBalance();
        atm2.showCellsBalanceService();

        Map<Banknote, Integer> moneyCrate = new HashMap<>();
        moneyCrate.put(Banknote.TEN, 2);
        moneyCrate.put(Banknote.FIFTY, 1);
        atm2.putBanknotes(moneyCrate);

        System.out.println("\nUpdated state:");
        atm2.showBalance();
        atm2.showCellsBalanceService();

        moneyCrate.clear();
        try {

            moneyCrate = atm2.getBanknotes(11050);
            System.out.println("\nMoney crate outcome:");
            moneyCrate.forEach((banknote, integer) -> System.out.println(banknote.getValue() + ": " + integer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nFinal state:");
        atm2.showBalance();
        atm2.showCellsBalanceService();
    }


    @Test
    public void departmentAtm() {

        DepartmentAtm departmentAtm = new DepartmentAtm();
        departmentAtm.registerAtmObserver(atm1);
        departmentAtm.registerAtmObserver(atm2);
        System.out.println("____Department Atm processing____");
        departmentAtm.printAllAtmBalance();
        departmentAtm.resetAllAtmState();
        departmentAtm.printAllAtmBalance();
        System.out.println("_________End processing__________");


    }

}
