package com.scalefocus.cvmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The entry point of the program.
 *
 * @author mariyan.topalov
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class BiographyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiographyManagerApplication.class, args);
    }
}


