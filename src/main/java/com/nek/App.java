package com.nek;

import com.nek.model.Player;
import com.nek.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class App {


    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepository) {
        return (args) -> {
            playerRepository
                    .save(new Player("player1", "player1@nek.com",
                            new BCryptPasswordEncoder().encode("player1")));
            playerRepository
                    .save(new Player("player2", "player2@nek.com",
                            new BCryptPasswordEncoder().encode("player2")));
            playerRepository
                    .save(new Player("player3", "player3@nek.com",
                            new BCryptPasswordEncoder().encode("player3")));
        };
    }

}
