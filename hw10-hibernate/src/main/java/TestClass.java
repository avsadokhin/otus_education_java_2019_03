import org.hibernate.cfg.Configuration;
import ru.otus.dao.EntityDao;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbUserHibernateServiceImpl;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;

import java.util.Arrays;

public class TestClass {
    public static void main(String[] args) {
        DbService<User, Long> dbUserService;
        EntityDao<User,Long> entityUserDao;

        System.out.println("Start");


        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        dbUserService = new DbUserHibernateServiceImpl(configuration);

        User user1 = new User("Alex", 20);
      /*  user1.setAddress(new AddressDataSet("Lenina 20"));
        user1.setPhoneList(Arrays.asList(new PhoneDataSet("901"),new PhoneDataSet("801")));
*/
     //   dbUserService.deleteMeta();
        dbUserService.create(user1);


    }
}
