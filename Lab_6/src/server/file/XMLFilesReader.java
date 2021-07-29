package server.file;

import server.collection.CollectionManager;
import client.movie.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import general.output.OutputManager;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Класс, предназначенный для считывания данных в формате XML и их записи в коллекцию.
 */
public class XMLFilesReader implements FilesReader {
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    XMLFilesReader(OutputManager outputManager, CollectionManager collectionManager) {
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder XMLString = new StringBuilder();
        while (scanner.hasNextLine()) {
            XMLString.append(scanner.nextLine()).append("\r\n");
        }

        try {
            ErrorHandler eh = new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {}

                @Override
                public void error(SAXParseException exception) throws SAXException {}

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {}
            };
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            builder.setErrorHandler(eh);
            org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(XMLString.toString())));

            NodeList elements = document.getElementsByTagName("element");
            for (int i = 0; i < elements.getLength(); i++) {
                Element element = (Element) elements.item(i);

                // ---

                Node key_node = element.getElementsByTagName("key").item(0);  //.item(0) нужен, чтобы вернуть Node, а не NodeList
                String key = key_node.getTextContent();

                // ---

                Node movie_node = element.getElementsByTagName("movie").item(0);
                Element movie = (Element) movie_node;

                Node id_node = movie.getElementsByTagName("id").item(0);
                Integer id = Integer.parseInt(id_node.getTextContent());

                Node movie_name_node = movie.getElementsByTagName("name").item(0);
                String movie_name = movie_name_node.getTextContent();

                Node coordinates_node = movie.getElementsByTagName("coordinates").item(0);
                Element coordinates_element = (Element) coordinates_node;
                Node x_node = coordinates_element.getElementsByTagName("x").item(0);
                Integer x = Integer.parseInt(x_node.getTextContent());
                Node y_node = coordinates_element.getElementsByTagName("y").item(0);
                Double y = Double.parseDouble(y_node.getTextContent());
                Coordinates coordinates = new Coordinates(x, y);

                Node creationDate_node = movie.getElementsByTagName("creationDate").item(0);
                LocalDateTime creationDate = LocalDateTime.parse(creationDate_node.getTextContent());

                Node oscarsCount_node = movie.getElementsByTagName("oscarsCount").item(0);
                int oscarsCount = Integer.parseInt(oscarsCount_node.getTextContent());

                Node goldenPalmCount_node = movie.getElementsByTagName("goldenPalmCount").item(0);
                long goldenPalmCount = Long.parseLong(goldenPalmCount_node.getTextContent());

                Node tagline_node = movie.getElementsByTagName("tagline").item(0);
                String tagline = tagline_node.getTextContent();

                Node genre_node = movie.getElementsByTagName("genre").item(0);
                MovieGenre genre = MovieGenre.valueOf(genre_node.getTextContent());

                // ---

                Node operator_node = movie.getElementsByTagName("operator").item(0);
                Element operator_element = (Element) operator_node;

                Node operator_name_node = operator_element.getElementsByTagName("name").item(0);
                String operator_name = operator_name_node.getTextContent();

                Node birthday_node = operator_element.getElementsByTagName("birthday").item(0);
                ZonedDateTime birthday = ZonedDateTime.parse(birthday_node.getTextContent());

                Node weight_node = operator_element.getElementsByTagName("weight").item(0);
                Integer weight = Integer.parseInt(weight_node.getTextContent());

                Node passportID_node = operator_element.getElementsByTagName("passportID").item(0);
                String passportID = passportID_node.getTextContent();

                Node hairColor_node = operator_element.getElementsByTagName("hairColor").item(0);
                Color hairColor = Color.valueOf(hairColor_node.getTextContent());

                // ---

                Person operatorFromXML = new Person(operator_name, birthday, weight, passportID, hairColor);
                Movie movieFromXML = new Movie(id, movie_name, coordinates, creationDate, oscarsCount, goldenPalmCount, tagline, genre, operatorFromXML);
                collectionManager.insertMovie(key, movieFromXML);
            }
        } catch (IOException | SAXException | IllegalArgumentException | ParserConfigurationException e) {
            outputManager.printlnErrorMessage("Содержимое файла не в формате xml.");
        }
    }
}
