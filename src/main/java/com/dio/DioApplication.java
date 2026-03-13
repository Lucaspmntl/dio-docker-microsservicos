package com.dio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.net.InetAddress;
import java.util.UUID;

@SpringBootApplication
@RestController
public class DioApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DioApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String randomStr = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            int randomId = (int) (Math.random() * 1000);

            String sql = "INSERT INTO data (StudentID, Name, Surname, Address, City, Host) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, randomId, randomStr, randomStr, randomStr, randomStr, hostName);

            return "<h1>Novo registro criado com sucesso!</h1>" +
                    "<p>Quem atendeu essa requisicao foi o container Spring Boot: <strong>" + hostName + "</strong></p>";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}