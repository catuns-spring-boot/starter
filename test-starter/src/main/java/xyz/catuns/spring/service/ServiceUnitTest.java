package xyz.catuns.spring.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public abstract class ServiceUnitTest<Service> {

    @InjectMocks
    protected Service service;
}
