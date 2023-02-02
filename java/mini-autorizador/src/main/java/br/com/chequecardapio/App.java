package br.com.chequecardapio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 //Utilize-se do endereço http://localhost:8080/swagger-ui/index.html
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
