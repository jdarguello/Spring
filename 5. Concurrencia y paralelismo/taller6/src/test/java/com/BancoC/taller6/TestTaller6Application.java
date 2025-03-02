package com.BancoC.taller6;

import org.springframework.boot.SpringApplication;

public class TestTaller6Application {

	public static void main(String[] args) {
		SpringApplication.from(Taller6Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
