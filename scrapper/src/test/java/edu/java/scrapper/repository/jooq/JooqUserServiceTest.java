package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.repository.UserServiceTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"app.database-access-type=jooq"})
public class JooqUserServiceTest extends UserServiceTest {
}
