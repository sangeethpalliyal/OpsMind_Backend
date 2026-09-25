package com.opsmind.deployment;
import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.kafka.annotation.EnableKafka;
@SpringBootApplication @EnableKafka public class DeploymentServiceApplication{public static void main(String[] args){SpringApplication.run(DeploymentServiceApplication.class,args);}}
