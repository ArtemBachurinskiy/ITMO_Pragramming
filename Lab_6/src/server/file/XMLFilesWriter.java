package server.file;

import general.output.OutputManager;
import server.collection.CollectionManager;
import client.movie.Movie;
import org.w3c.dom.Element;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Класс, предназначенный для записи данных из коллекции в файл формата XML.
 */
public class XMLFilesWriter implements FilesWriter{
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private OutputManager outputManager;

    public XMLFilesWriter (CollectionManager collectionManager, FileManager fileManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.outputManager = outputManager;
    }

    @Override
    public void writeToFile() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document document = builder.newDocument();

        Element root = document.createElement("elements");
        document.appendChild(root);

        for (Map.Entry<String, Movie> entry : collectionManager.getCollectionElements()) {
            // Уровень вложенности 0
            Element element = document.createElement("element");
            root.appendChild(element);


            // Уровень вложенности 1 (a)
            Element key = document.createElement("key");
            key.setTextContent(entry.getKey());
            element.appendChild(key);


            // Уровень вложенности 1 (б)
            Element movie = document.createElement("movie");
            element.appendChild(movie);


            // Уровень вложенности 2
            Element id = document.createElement("id");
            id.setTextContent(String.valueOf(entry.getValue().getId()));
            movie.appendChild(id);

            Element name_movie = document.createElement("name");
            name_movie.setTextContent(entry.getValue().getName());
            movie.appendChild(name_movie);

            Element coordinates = document.createElement("coordinates");
            movie.appendChild(coordinates);
            Element x = document.createElement("x");
            x.setTextContent(String.valueOf(entry.getValue().getCoordinates().getX()));
            coordinates.appendChild(x);
            Element y = document.createElement("y");
            y.setTextContent(String.valueOf(entry.getValue().getCoordinates().getY()));
            coordinates.appendChild(y);

            Element creationDate = document.createElement("creationDate");
            creationDate.setTextContent(entry.getValue().getCreationDate().toString());
            movie.appendChild(creationDate);

            Element oscarsCount = document.createElement("oscarsCount");
            oscarsCount.setTextContent(String.valueOf(entry.getValue().getOscarsCount()));
            movie.appendChild(oscarsCount);

            Element goldenPalmCount = document.createElement("goldenPalmCount");
            goldenPalmCount.setTextContent(String.valueOf(entry.getValue().getGoldenPalmCount()));
            movie.appendChild(goldenPalmCount);

            Element tagline = document.createElement("tagline");
            tagline.setTextContent(entry.getValue().getTagline());
            movie.appendChild(tagline);

            Element genre = document.createElement("genre");
            genre.setTextContent(entry.getValue().getGenre().toString());
            movie.appendChild(genre);

            Element operator = document.createElement("operator");
            movie.appendChild(operator);


            // Уровень вложенности 3
            Element name_operator = document.createElement("name");
            name_operator.setTextContent(entry.getValue().getOperator().getName());
            operator.appendChild(name_operator);

            Element birthday = document.createElement("birthday");
            birthday.setTextContent(entry.getValue().getOperator().getBirthday().toString());
            operator.appendChild(birthday);

            Element weight = document.createElement("weight");
            weight.setTextContent(String.valueOf(entry.getValue().getOperator().getWeight()));
            operator.appendChild(weight);

            Element passportID = document.createElement("passportID");
            passportID.setTextContent(entry.getValue().getOperator().getPassportID());
            operator.appendChild(passportID);

            Element hairColor = document.createElement("hairColor");
            hairColor.setTextContent(entry.getValue().getOperator().getHairColor().toString());
            operator.appendChild(hairColor);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(fileManager.getFile())));

        outputManager.printlnMessage("Элементы успешно сохранены!");
    }
}
