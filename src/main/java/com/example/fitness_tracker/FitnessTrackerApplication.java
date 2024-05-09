package com.example.fitness_tracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.fitness_tracker"})
@ComponentScan("com.example.fitness_tracker")
@EnableAspectJAutoProxy
@EnableCaching
@Slf4j
public class FitnessTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessTrackerApplication.class, args);
		log.info(
				" " +
						"\n" +
						"\n" +
						":::'###::::'##:::::'##:'########::'######:::'#######::'##::::'##:'########:\n" +
						"::'## ##::: ##:'##: ##: ##.....::'##... ##:'##.... ##: ###::'###: ##.....::\n" +
						":'##:. ##:: ##: ##: ##: ##::::::: ##:::..:: ##:::: ##: ####'####: ##:::::::\n" +
						"'##:::. ##: ##: ##: ##: ######:::. ######:: ##:::: ##: ## ### ##: ######:::\n" +
						" #########: ##: ##: ##: ##...:::::..... ##: ##:::: ##: ##. #: ##: ##...::::\n" +
						" ##.... ##: ##: ##: ##: ##:::::::'##::: ##: ##:::: ##: ##:.:: ##: ##:::::::\n" +
						" ##:::: ##:. ###. ###:: ########:. ######::. #######:: ##:::: ##: ########:\n" +
						"..:::::..:::...::...:::........:::......::::.......:::..:::::..::........::"
		);
	}

}
