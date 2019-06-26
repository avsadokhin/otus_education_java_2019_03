import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.dao.User;
import ru.otus.dbservice.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ExecutorTest {
    private DataSource dataSource;

    @BeforeAll
    public static void init() throws SQLException {
        User user = new User("Mihail", 23);

        DataSource dataSource = new DataSourceH2();
        DbService<User> dbService = new DbServiceImpl<>(dataSource, User.class);
        dbService.createMeta();
        //dbService.create(user);
    }

    @Test
    void run(){

    }
}
