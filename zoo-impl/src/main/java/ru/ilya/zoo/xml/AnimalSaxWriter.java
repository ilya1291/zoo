package ru.ilya.zoo.xml;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.ilya.zoo.model.Animal;

import javax.xml.stream.XMLStreamWriter;

@RequiredArgsConstructor
public class AnimalSaxWriter implements AutoCloseable {

    private final XMLStreamWriter writer;

    @SneakyThrows
    public void writeRootElement() {
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("animals");
    }

    @SneakyThrows
    public void write(Animal animal) {
        writer.writeStartElement("animal");

            writer.writeStartElement("name");
            writer.writeCharacters(animal.getName());
            writer.writeEndElement();

            writer.writeStartElement("birthDate");
            writer.writeCharacters(animal.getBirthDate().toString());
            writer.writeEndElement();

            writer.writeStartElement("kindName");
            writer.writeCharacters(animal.getKind().getName());
            writer.writeEndElement();

            writer.writeStartElement("keeperId");
            writer.writeCharacters(String.valueOf(animal.getKeeper().getId()));
            writer.writeEndElement();

            writer.writeStartElement("cageId");
            writer.writeCharacters(String.valueOf(animal.getCageId()));
            writer.writeEndElement();

        writer.writeEndElement();
    }

    @SneakyThrows
    public void writeEndRootElement() {
        writer.writeEndElement();
    }

    @Override
    @SneakyThrows
    public void close() {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }
}
