import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.hw15.dao.User;
import ru.otus.hw15.dbservice.DataSourceH2;
import ru.otus.hw15.dbservice.DbService;
import ru.otus.hw15.dbservice.DbServiceImpl;
import ru.otus.hw15.entity.EntityException;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorUserTest {
    static DataSource dataSource = new DataSourceH2();
    static DbService<User> dbServiceUser;

    @BeforeAll
    static void initDbMeta() throws Exception {
        dbServiceUser = new DbServiceImpl<>(dataSource, User.class);

        dbServiceUser.deleteMeta();

        dbServiceUser.createMeta();
    }


    @Test
    void createAndLoadNewUser() throws Exception {
        User user1 = new User("User1", 10);
        dbServiceUser.create(user1);

        User userLoaded;
        userLoaded = dbServiceUser.load(user1.getId(), User.class);
        Assertions.assertThat(user1).isEqualToComparingFieldByField(userLoaded);
    }

    @Test
    void updateAndLoadNewUser() throws Exception {
        User user1 = new User("User2", 20);
        dbServiceUser.create(user1);
        user1.setName("User2_Updated");
        user1.setAge(21);
        dbServiceUser.update(user1);

        User userLoaded;
        userLoaded = dbServiceUser.load(user1.getId(), User.class);
        Assertions.assertThat(user1).isEqualToComparingFieldByField(userLoaded);
    }

    @Test
    void updateNotExistingUser() throws Exception {
        User user1 = new User("User3", 30);
        dbServiceUser.create(user1);
        user1.setId((long) 30);
        user1.setName("User3_Updated");

        assertThrows(EntityException.class, () -> dbServiceUser.update(user1));
    }

    @Test
    void loadNotExistingUser() throws Exception {
        User user1 = new User("User4", 30);
        dbServiceUser.create(user1);

        user1.setId((long) 40);

        User userLoaded;
        userLoaded = dbServiceUser.load(user1.getId(), User.class);
        System.out.println(userLoaded);

        assertNull(userLoaded);
    }

}

