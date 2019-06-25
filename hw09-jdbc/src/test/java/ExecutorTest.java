import org.junit.jupiter.api.BeforeAll;
import ru.otus.dao.User;
import ru.otus.dbservice.DataSourceH2;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ExecutorTest {
    private DataSource dataSource;

    @BeforeAll
    private void init() throws SQLException {
        User user = new User("Mihail", 23);

        DataSource dataSource = new DataSourceH2();
        DbService<User> dbService = new DbServiceImpl<>(dataSource);
        dbService.create(user);



    }
}
