import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.AtmBoxImpl;
import ru.otus.atm.Banknote;
import ru.otus.atm.BanknoteStorage;

import java.util.HashMap;
import java.util.Map;


public class TestAtm {

    AtmBoxImpl atm = new AtmBoxImpl("AlfaBank_123");

    @BeforeEach
    public void InitSets() {
        BanknoteStorage banknoteStorage = new BanknoteStorage();
        banknoteStorage.store(Banknote.THOUSAND, 10);
        banknoteStorage.store(Banknote.TWO_HUNDRED, 5);
        atm.fillUpStorageService(banknoteStorage);

        System.out.println("Initial values:");
        atm.showBalance();
        atm.showCellsBalanceService();


    }

    @Test
    public void BankAtmProcess() {

        Map<Banknote, Integer> moneyCrate = new HashMap<>();
        moneyCrate.put(Banknote.TEN, 2);
        moneyCrate.put(Banknote.FIFTY, 1);
        atm.receiveBanknotes(moneyCrate);

        System.out.println("\nUpdated state:");
        atm.showBalance();
        atm.showCellsBalanceService();

        moneyCrate.clear();
        try {

            moneyCrate = atm.getBanknotes(11010);
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
