import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw15.atmbox.AtmBoxImpl;
import ru.otus.hw15.department.DepartmentAtm;
import ru.otus.hw15.banknote.Banknote;
import ru.otus.hw15.banknote.BanknoteCell;
import ru.otus.hw15.banknote.BanknoteStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
    public void departmentAtm1_2() {

        DepartmentAtm departmentAtm = new DepartmentAtm();
        departmentAtm.registerAtmObserver(atm1);
        departmentAtm.registerAtmObserver(atm2);
        System.out.println("____Department Atm processing____");
        departmentAtm.printAllAtmBalance();
        departmentAtm.resetAllAtmState();
        assertEquals(atm1.getBalance(), 11000);
        assertEquals(atm2.getBalance(), 12000);
        departmentAtm.printAllAtmBalance();
        System.out.println("_________End processing__________");


    }

    @Test
    public void departmentAtm3() throws Exception {
        AtmBoxImpl atm3 = new AtmBoxImpl("AlfaBank_3");
        Set<BanknoteCell> cells2 = new HashSet<>();

        cells2.add(new BanknoteCell(Banknote.THOUSAND, 10));
        BanknoteStorage banknoteStorage = new BanknoteStorage(cells2);
        atm3.mountStorageService(banknoteStorage);

        DepartmentAtm departmentAtm = new DepartmentAtm();
        departmentAtm.registerAtmObserver(atm3);
        System.out.println("____Department Atm processing____");
        departmentAtm.printAllAtmBalance();

        atm3.getBanknotes(2000);
        departmentAtm.printAllAtmBalance();
        assertEquals(atm3.getBalance(), 8000);

        departmentAtm.resetAllAtmState();
        departmentAtm.printAllAtmBalance();
        assertEquals(atm3.getBalance(), 10000);

        atm3.getBanknotes(2000);
        departmentAtm.printAllAtmBalance();
        assertEquals(atm3.getBalance(), 8000);

        departmentAtm.resetAllAtmState();
        departmentAtm.printAllAtmBalance();
        assertEquals(atm3.getBalance(), 10000);
        System.out.println("_________End processing__________");
    }

}
