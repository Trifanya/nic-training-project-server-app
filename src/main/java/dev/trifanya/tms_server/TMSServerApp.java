package dev.trifanya.tms_server;

import org.apache.ibatis.annotations.Mapper;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(basePackages = {"dev.trifanya.tms_server.mybatis.mapper"}, annotationClass = Mapper.class)
public class TMSServerApp {

	public static void main(String[] args) {
		SpringApplication.run(TMSServerApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
