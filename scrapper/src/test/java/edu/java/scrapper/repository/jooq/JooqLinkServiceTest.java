package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.repository.LinkServiceTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"app.database-access-type=jooq"})
public class JooqLinkServiceTest extends LinkServiceTest {

}
