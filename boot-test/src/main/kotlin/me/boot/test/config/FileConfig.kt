package me.boot.test.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootConfiguration
@PropertySources(
    PropertySource("file:D:\\Develop\\Java\\Projects\\kotlin\\boot-demo\\tomcat.properties"),
    PropertySource("file:D:\\Develop\\Java\\Projects\\kotlin\\boot-demo\\jdbc.properties")
)
class FileConfig