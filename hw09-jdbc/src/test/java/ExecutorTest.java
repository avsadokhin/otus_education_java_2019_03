import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.dao.Account;
import ru.otus.dao.User;
import ru.otus.dbservice.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ExecutorTest {
    private DataSource dataSource;

    @BeforeAll
    public static void init() throws SQLException {
        User user = new User("sdsd111", 24);
        Account account = new Account("TestAccount", 123.34);
        DataSource dataSource = new DataSourceH2();

        DbService<User> dbService = new DbServiceImpl<>(dataSource, User.class);
        DbService<Account> dbServiceAcc = new DbServiceImpl<>(dataSource, Account.class);

        System.out.println(user);

        dbService.deleteMeta();
        dbServiceAcc.deleteMeta();
        dbService.createMeta();
        dbServiceAcc.createMeta();



        dbService.create(user);
        dbServiceAcc.create(account);

        User user2;
        user2 = dbService.load(1, User.class);
        System.out.println(user2);


        user2.setAge(46);

        User user3 = new User("sdsd111", 999);
        user3.setId((long) 1);
        dbService.update(user3);


        Account account2 = account;
        account2.setType("xzzzzzzz");
        account2.setId(45);

        dbServiceAcc.update(account2);

    }

    @Test
    void run(){

    }
}

