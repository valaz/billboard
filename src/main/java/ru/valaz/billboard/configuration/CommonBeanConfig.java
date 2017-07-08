package ru.valaz.billboard.configuration;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeanConfig {

    @Bean
    public StrongPasswordEncryptor strongEncryptor() {
        return new StrongPasswordEncryptor();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
