package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.repository.LinkServiceTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(properties = {"app.database-access-type=jpa"})
public class JpaLinkServiceTest extends LinkServiceTest {

}
