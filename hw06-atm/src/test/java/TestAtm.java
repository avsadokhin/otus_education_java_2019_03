import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.Banknote;
import ru.otus.atm.CellStorage;
import ru.otus.atm.MoneyBox;


public class TestAtm {
    CellStorage cellStorage = new CellStorage();

    @BeforeEach
    public void InitSets() {


        cellStorage.put(Banknote.THOUSAND,10);
        cellStorage.put(Banknote.TWO_HUNDRED,5);
        cellStorage.put(Banknote.THOUSAND,2);

        AtmImpl atm = new AtmImpl(cellStorage);



      /*  atm.put(cells);
        atm.get(1500);
        atm.printBalance();
        atm.printCellsBallance();*/
    }

    @Test
    public void PrintCellBalance(){
        System.out.println(cellStorage.getCellBalance(Banknote.TWO_HUNDRED));
    }

}
