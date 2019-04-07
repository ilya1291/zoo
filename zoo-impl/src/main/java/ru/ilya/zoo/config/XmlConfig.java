package ru.ilya.zoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;

@Configuration
public class XmlConfig {

    @Bean
    public SAXParser saxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        return factory.newSAXParser();
    }

    @Bean
    public XMLOutputFactory xmlOutputFactory() {
        return XMLOutputFactory.newInstance();
    }
}
