import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw15.atm.AtmBoxImpl;
import ru.otus.hw15.atm.Banknote;
import ru.otus.hw15.atm.BanknoteCell;
import ru.otus.hw15.atm.BanknoteStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestAtm {

    AtmBoxImpl atm = new AtmBoxImpl("AlfaBank_123");

    @BeforeEach
    public void initSets() {

        Set<BanknoteCell> cells = new HashSet<>();


        cells.add(new BanknoteCell(Banknote.THOUSAND, 10));
        cells.add(new BanknoteCell(Banknote.TWO_HUNDRED, 5));
        cells.add(new BanknoteCell(Banknote.TEN, 0));
        cells.add(new BanknoteCell(Banknote.FIFTY, 0));
        BanknoteStorage banknoteStorage = new BanknoteStorage(cells);
        atm.mountStorageService(banknoteStorage);
        System.out.println("Initial values:");
        atm.showBalance();
        atm.showCellsBalanceService();

    }

    @Test
    public void bankAtmProcess() {

        Map<Banknote, Integer> moneyCrate = new HashMap<>();
        moneyCrate.put(Banknote.TEN, 2);
        moneyCrate.put(Banknote.FIFTY, 1);
        atm.putBanknotes(moneyCrate);

        System.out.println("\nUpdated state:");
        atm.showBalance();
        atm.showCellsBalanceService();

        moneyCrate.clear();
        try {

            moneyCrate = atm.getBanknotes(11050);
            System.out.println("\nMoney crate outcome:");
            moneyCrate.forEach((banknote, integer) -> System.out.println(banknote.getValue() + ": " + integer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nFinal state:");
        atm.showBalance();
        atm.showCellsBalanceService();

    }

}
