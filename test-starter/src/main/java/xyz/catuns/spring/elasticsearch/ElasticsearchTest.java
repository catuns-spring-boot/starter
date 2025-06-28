package xyz.catuns.spring.elasticsearch;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ElasticsearchTestConfiguration.class)
public class ElasticsearchTest {
}
