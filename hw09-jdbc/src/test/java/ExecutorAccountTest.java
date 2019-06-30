import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.dao.Account;
import ru.otus.dao.User;
import ru.otus.dbservice.DataSourceH2;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbServiceImpl;
import ru.otus.entity.EntityException;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorAccountTest {
    static DataSource dataSource = new DataSourceH2();
    static DbService<Account> dbServiceAccount;

    @BeforeAll
    static void initDbMeta() throws Exception {
        dbServiceAccount = new DbServiceImpl<>(dataSource, Account.class);

        dbServiceAccount.deleteMeta();

        dbServiceAccount.createMeta();
    }


    @Test
    void createAndLoadNewUser() throws Exception {
        Account acc1 = new Account("Acc1", 10.50);
        dbServiceAccount.create(acc1);

        Account accLoaded;
        accLoaded = dbServiceAccount.load(acc1.getId(), Account.class);
        Assertions.assertThat(acc1).isEqualToComparingFieldByField(accLoaded);
    }

    @Test
    void updateAndLoadNewUser() throws Exception {
        Account acc1 = new Account("Acc2", 20.40);
        dbServiceAccount.create(acc1);
        acc1.setType("type1");
        acc1.setRest(21.0);
        dbServiceAccount.update(acc1);

        Account accLoaded;
        accLoaded = dbServiceAccount.load(acc1.getId(), Account.class);
        Assertions.assertThat(acc1).isEqualToComparingFieldByField(accLoaded);
    }

    @Test
    void updateNotExistingUser() throws Exception {
        Account acc1 = new Account("Acc2", 30.10);
        dbServiceAccount.create(acc1);
        acc1.setType("type2");
        acc1.setId((long) 30);

        assertThrows(EntityException.class, () -> dbServiceAccount.update(acc1));
    }

    @Test
    void loadNotExistingUser() throws Exception {
        Account acc1 = new Account("Acc2", 40.23);
        dbServiceAccount.create(acc1);

        acc1.setId((long) 50);

        Account accLoaded;
        accLoaded = dbServiceAccount.load(acc1.getId(), Account.class);
        System.out.println(accLoaded);

        assertNull(accLoaded);
    }

}

