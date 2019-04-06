package ru.ilya.zoo.xml;

import lombok.RequiredArgsConstructor;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.time.LocalDate;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class AnimalSaxHandler extends DefaultHandler {

    private final Consumer<Animal> action;

    private Animal animal;

    private boolean bname;
    private boolean bbirthDate;
    private boolean bkindName;
    private boolean bkeeperId;
    private boolean bcageId;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("animal")) {
            animal = new Animal();
        }

        if (qName.equalsIgnoreCase("name")) {
            bname = true;
        }
        if (qName.equalsIgnoreCase("birthDate")) {
            bbirthDate = true;
        }
        if (qName.equalsIgnoreCase("kindName")) {
            bkindName = true;
        }
        if (qName.equalsIgnoreCase("keeperId")) {
            bkeeperId = true;
        }
        if (qName.equalsIgnoreCase("cageId")) {
            bcageId = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length);
        if (bname) {
            animal.setName(value);
            bname = false;
        }
        if (bbirthDate) {
            animal.setBirthDate(LocalDate.parse(value));
            bbirthDate = false;
        }
        if (bkindName) {
            animal.setKind(new Kind().setName(value));
            bkindName = false;
        }
        if (bkeeperId) {
            animal.setKeeper(new Keeper().setId(Long.valueOf(value)));
            bkeeperId = false;
        }
        if (bcageId) {
            animal.setCageId(Long.valueOf(value));
            bcageId = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("animal")) {
            action.accept(animal);
        }
    }
}
