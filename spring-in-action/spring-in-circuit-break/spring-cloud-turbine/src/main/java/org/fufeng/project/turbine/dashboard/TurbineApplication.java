package org.fufeng.project.turbine.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author LuoCY
 */
@SpringBootApplication
@EnableTurbine
public class TurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineApplication.class, args);
	}

}
