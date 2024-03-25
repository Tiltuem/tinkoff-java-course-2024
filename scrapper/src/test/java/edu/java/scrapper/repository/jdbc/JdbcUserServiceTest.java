package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.repository.UserServiceTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"app.database-access-type=jdbc"})
public class JdbcUserServiceTest extends UserServiceTest {

}
