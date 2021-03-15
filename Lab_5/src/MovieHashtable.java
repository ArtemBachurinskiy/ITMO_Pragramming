import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * This class contains:
 * 1) A collection of Movie entities
 * 2) Commands for modifying the collection
 * 3) A File entity, made for writing Movie entities from collection to file and vice versa
 */
public class MovieHashtable {
    Hashtable<String, Movie> Movies;
    Date date;
    File file;

    MovieHashtable (String filename) throws IOException, ParserConfigurationException, SAXException {

        Movies = new Hashtable<>();
        date = new Date();
        file = new File(filename);

        // если файла с таким именем не существует
        if (!file.exists()) {
            try {
                boolean ret = file.createNewFile();
                if (ret)
                    System.err.println("Файла с таким именем не существует. Файл с заданным именем будет создан автоматически.");
                else {
                    System.err.println("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                    file = new File("Movies.xml");
                }
            }
            catch (IOException | SecurityException e) {
                    System.err.println("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                    file = new File("Movies.xml");
            }
        }
        else {
            // отсутсвуют права доступа
            if (!file.canRead()) {
                System.err.println("Отсутствуют права доступа на чтение файла.");
                System.exit(1);
            }
            if (!file.canWrite()) {
                System.err.println("Отсусвуют права доступа на запись в файл.");
                System.exit(1);
            }

            // Чтение данных из файла XML: совместное использование DOM и java.util.Scanner
            if (file.length() != 0) {

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

                    String key;

                    Integer id;
                    String movie_name;
                    Coordinates coordinates;
                    java.time.LocalDateTime creationDate;
                    int oscarsCount;
                    long goldenPalmCount;
                    String tagline;
                    MovieGenre genre;
                    Person operator;

                    String operator_name;
                    java.time.ZonedDateTime birthday;
                    Integer weight;
                    String passportID;
                    Color hairColor;

                    NodeList elements = document.getElementsByTagName("element");
                    for (int i = 0; i < elements.getLength(); i++) {
                        Element element = (Element) elements.item(i);

                        // ---

                        Node key_node = element.getElementsByTagName("key").item(0);  //.item(0) нужен, чтобы вернуть Node, а не NodeList
                        key = key_node.getTextContent();

                        // ---

                        Node movie_node = element.getElementsByTagName("movie").item(0);
                        Element movie = (Element) movie_node;

                        Node id_node = movie.getElementsByTagName("id").item(0);
                        id = Integer.parseInt(id_node.getTextContent());

                        Node movie_name_node = movie.getElementsByTagName("name").item(0);
                        movie_name = movie_name_node.getTextContent();

                        Node coordinates_node = movie.getElementsByTagName("coordinates").item(0);
                        Element coordinates_element = (Element) coordinates_node;
                        Node x_node = coordinates_element.getElementsByTagName("x").item(0);
                        Integer x = Integer.parseInt(x_node.getTextContent());
                        Node y_node = coordinates_element.getElementsByTagName("y").item(0);
                        Double y = Double.parseDouble(y_node.getTextContent());
                        coordinates = new Coordinates(x, y);

                        Node creationDate_node = movie.getElementsByTagName("creationDate").item(0);
                        creationDate = LocalDateTime.parse(creationDate_node.getTextContent());

                        Node oscarsCount_node = movie.getElementsByTagName("oscarsCount").item(0);
                        oscarsCount = Integer.parseInt(oscarsCount_node.getTextContent());

                        Node goldenPalmCount_node = movie.getElementsByTagName("goldenPalmCount").item(0);
                        goldenPalmCount = Long.parseLong(goldenPalmCount_node.getTextContent());

                        Node tagline_node = movie.getElementsByTagName("tagline").item(0);
                        tagline = tagline_node.getTextContent();

                        Node genre_node = movie.getElementsByTagName("genre").item(0);
                        genre = MovieGenre.valueOf(genre_node.getTextContent());

                        // ---

                        Node operator_node = movie.getElementsByTagName("operator").item(0);
                        Element operator_element = (Element) operator_node;

                        Node operator_name_node = operator_element.getElementsByTagName("name").item(0);
                        operator_name = operator_name_node.getTextContent();

                        Node birthday_node = operator_element.getElementsByTagName("birthday").item(0);
                        birthday = ZonedDateTime.parse(birthday_node.getTextContent());

                        Node weight_node = operator_element.getElementsByTagName("weight").item(0);
                        weight = Integer.parseInt(weight_node.getTextContent());

                        Node passportID_node = operator_element.getElementsByTagName("passportID").item(0);
                        passportID = passportID_node.getTextContent();

                        Node hairColor_node = operator_element.getElementsByTagName("hairColor").item(0);
                        hairColor = Color.valueOf(hairColor_node.getTextContent());

                        operator = new Person(operator_name, birthday, weight, passportID, hairColor);
                        Movie movieFromXML = new Movie(id, movie_name, coordinates, creationDate, oscarsCount, goldenPalmCount, tagline, genre, operator);

                        Movies.put(key, movieFromXML);
                    }
                } catch (IOException | SAXException | IllegalArgumentException e) {
                    System.err.println("Содержимое файла не в формате xml.");
                }
            }
        }
    }

    /**
     * help() method
     */
    void help() {
        System.out.println("Справка по доступным командам!\n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "history : вывести последние 9 команд (без их аргументов)\n" +
                "max_by_golden_palm_count : вывести любой объект из коллекции, значение поля goldenPalmCount которого является максимальным\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_field_ascending_golden_palm_count : вывести значения поля goldenPalmCount всех элементов в порядке возрастания");
    }

    /**
     * info() method
     */
    void info() {
        System.out.println("Информация о данной коллекции : \n" +
                "Тип : " + Movies.getClass() + '\n' +
                "Дата и время иниацилизации : " + date.toString() + '\n' +
                "Количество элементов : " + Movies.size());
    }

    /**
     * show() method
     */
    void show() {
        if (Movies.size() == 0)
            System.err.println("Коллекция пуста.");
        else {
            System.out.println("Элементы коллекции : ");
            for (Map.Entry<String, Movie> element : Movies.entrySet()) {
                System.out.println(element.getKey() + " > " + element.getValue());
            }
        }
    }

    /**
     * insert() method
     * @param key The key in the hashtable
     * @param bufferedReader A BufferedReader object
     */
    void insert(String key, BufferedReader bufferedReader) throws IOException {
        boolean start = true;
        for (Map.Entry element : Movies.entrySet())
            if (key.equals(element.getKey())) {
                start = false;
                break;
            }
        if (start) {
            // Значение поля id всегда уникально и генерируется автоматически (новое id всегда на 1 больше, чем максимальное id в коллекции)
            Integer id = 0;
            Set<String> set = Movies.keySet();
            for (String string : set) {
                if (id < Movies.get(string).getId())
                    id = Movies.get(string).getId();
            }
            id++;

            Coordinates coordinates = new Coordinates(x_input(bufferedReader), y_input(bufferedReader));
            java.time.LocalDateTime creationDate = LocalDateTime.now();
            int oscarsCount = oscarsCount_input(bufferedReader);
            long goldenPalmCount = goldenPalmCount_input(bufferedReader);
            String tagline = tagline_input(bufferedReader);
            MovieGenre genre = genre_input(bufferedReader);

            // Создаём оператора
            String name = name_input(bufferedReader);
            java.time.ZonedDateTime birthday = birthday_input(bufferedReader);
            Integer weight = weight_input(bufferedReader);
            String passportID = passportID_input(bufferedReader);
            Color hairColor = hairColor_input(bufferedReader);

            Person operator = new Person(name, birthday, weight, passportID, hairColor);

            Movie movie = new Movie(id, key, coordinates, creationDate, oscarsCount, goldenPalmCount, tagline, genre, operator);
            Movies.put(key, movie);
        }
        else
            System.err.println("В коллекции уже есть элемент с ключом \'" + key + "\'.");
    }

    /**
     * update() method
     * @param id The id of the Movie
     * @param bufferedReader A BufferedReader object
     */
    void update(Integer id, BufferedReader bufferedReader) throws IOException {
        boolean found = false;
        Set<String> set = Movies.keySet();
        for (String string : set) {
            if (Movies.get(string).getId().equals(id)) {
                found = true;
                System.out.println("Значение какого поля элемента коллекции с id = " + id + " вы хотели бы обновить?\n" +
                        "(поля, доступные для обновления: coordinates, oscarsCount, goldenPalmCount, tagline, genre, operator)");
                label_1 :
                while (true)
                {
                    String field = bufferedReader.readLine();
                    switch (field)
                    {
                        case "coordinates":
                            Movies.get(string).setCoordinates(new Coordinates(x_input(bufferedReader), y_input(bufferedReader)));
                            System.out.println("Новое значение поля " + field + " успешно установлено!");
                            break label_1;
                        case "oscarsCount":
                            int oscarsCount = oscarsCount_input(bufferedReader);
                            Movies.get(string).setOscarsCount(oscarsCount);
                            System.out.println("Новое значение поля " + field + " успешно установлено!");
                            break label_1;
                        case "goldenPalmCount":
                            long goldenPalmCount = goldenPalmCount_input(bufferedReader);
                            Movies.get(string).setGoldenPalmCount(goldenPalmCount);
                            System.out.println("Новое значение поля " + field + " успешно установлено!");
                            break label_1;
                        case "tagline":
                            String tagline = tagline_input(bufferedReader);
                            Movies.get(string).setTagline(tagline);
                            System.out.println("Новое значение поля " + field + " успешно установлено!");
                            break label_1;
                        case "genre":
                            MovieGenre genre = genre_input(bufferedReader);
                            Movies.get(string).setGenre(genre);
                            System.out.println("Новое значение поля " + field + " успешно установлено!");
                            break label_1;
                        case "operator":
                            Person p = Movies.get(string).getOperator();
                            System.out.println("Значение какого поля оператора вы бы хотели обновить? " + '\n' +
                                    "(поля, доступные для обновления: name, birthday, weight, passportID, hairColor)");
                            label_2 :
                            while (true) {
                                String field2 = bufferedReader.readLine();
                                switch (field2) {
                                    case "name":
                                        String name = name_input(bufferedReader);
                                        p.setName(name);
                                        System.out.println("Новое значение поля " + field2 + " (оператора) успешно установлено!");
                                        break label_2;
                                    case "birthday":
                                        java.time.ZonedDateTime birthday = birthday_input(bufferedReader);
                                        p.setBirthday(birthday);
                                        System.out.println("Новое значение поля " + field2 + " (оператора) успешно установлено!");
                                        break label_2;
                                    case "weight":
                                        Integer weight = weight_input(bufferedReader);
                                        p.setWeight(weight);
                                        System.out.println("Новое значение поля " + field2 + " (оператора) успешно установлено!");
                                        break label_2;
                                    case "passportID":
                                        String passportID = passportID_input(bufferedReader);
                                        p.setPassportID(passportID);
                                        System.out.println("Новое значение поля " + field2 + " (оператора) успешно установлено!");
                                        break label_2;
                                    case "hairColor":
                                        Color hairColor = hairColor_input(bufferedReader);
                                        p.setHairColor(hairColor);
                                        System.out.println("Новое значение поля " + field2 + " (оператора) успешно установлено!");
                                        break label_2;
                                    default:
                                        System.err.println("Такого поля нет. Повторите ввод." + '\n' +
                                                "(поля, доступные для обновления: name, birthday, weight, passportID, hairColor)");
                                        break;
                                }
                            }
                            Movies.get(string).setOperator(p);
                            break label_1;
                        default:
                            System.err.println("Такого поля нет. Повторите ввод." + '\n' +
                                    "(поля, доступные для обновления: coordinates, oscarsCount, goldenPalmCount, tagline, genre, operator)");
                            break;
                    }
                }
            }
        }
        if (!found)
            System.err.println("Элемента коллекции с id = " + id + " нет.");
    }

    /**
     * remove_key() method
     * @param key The key in the hashtable
     */
    void remove_key(String key) {
        boolean removed = false;
        Set<String> set = Movies.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            if (key.equals(string)) {
                iterator.remove();
                System.out.println("Элемент успешно удалён!");
                removed = true;
            }
        }
        if (!removed)
            System.err.println("В коллекции нет элемента с ключом \'" + key + "\'!");
    }

    /**
     * clear() method
     */
    void clear() {
        Movies.clear();
    }

    /**
     * save() method
     */
    void save() throws TransformerException, ParserConfigurationException, FileNotFoundException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document document = builder.newDocument();

        Element root = document.createElement("elements");
        document.appendChild(root);

        for (Map.Entry<String, Movie> entry : Movies.entrySet()) {

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
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
    }

    /**
     * execute_script() method
     */
    void execute_script() {

    }

    /**
     * exit() method
     */
    void exit() {}

    /**
     * remove_greater() method
     * @param key The key in the hashtable
     */
    void remove_greater(String key) {
        // удалить из коллекции все элементы, превышающие заданный,
        // т.е. выше него по списку, но узнать какие это эл-ты мы сможем только выполнив команду show()
        boolean found = false;
        Set<String> set = Movies.keySet();
        for (String string : set)
            if (string.equals(key)) {
                found = true;
                break;
            }
        if (found) {
            boolean removed = false;
            Set<String> setToRetain = Movies.keySet();

            Iterator<String> iterator = setToRetain.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                if (!key.equals(string)) {
                    iterator.remove();
                    removed = true;
                } else break;
            }
            if (removed)
                System.out.println("Элементы успешно удалены!");
        }
        else
            System.err.println("В коллекции нет элемента с ключом \'" + key + "\'!");
    }

    /**
     * remove_lower() method
     * @param key The key in the hashtable
     */
    void remove_lower(String key) {
        // удалить из коллекции все элементы, меньшие, чем заданный,
        // т.е. ниже него по списку, но узнать какие это эл-ты мы сможем только выполнив команду show()
        Set<String> setToRetain = Movies.keySet();
        boolean found = false;
        boolean removed = false;

        Iterator<String> iterator = setToRetain.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            if (key.equals(string))
                found = true;
            if (found && !key.equals(string)) {
                iterator.remove();
                removed = true;
            }
        }
        if(!found)
            System.err.println("В коллекции нет элемента с ключом \'" + key + "\'!");
        if(removed)
            System.out.println("Элементы успешно удалены!");
    }

    /**
     * max_by_golden_palm_count() method
     */
    void max_by_golden_palm_count() {
        if (Movies.size() > 0) {
            long maxGoldenPalmCount = 0;
            String key = null;
            Set<String> set = Movies.keySet();
            for (String string : set) {
                if (Movies.get(string).getGoldenPalmCount() > maxGoldenPalmCount) {
                    maxGoldenPalmCount = Movies.get(string).getGoldenPalmCount();
                    key = string;
                }
            }
            System.out.println(Movies.get(key));
        }
        else
            System.err.println("В коллекции нет элементов!");
    }

    /**
     * print_ascending() method
     */
    void print_ascending() {
        System.out.println("Выводим объекты в порядке возрастания (ключи по алфавиту): ");
        Set<String> set = Movies.keySet();
        TreeSet<String> treeSet = new TreeSet<>(set);
        for (String string : treeSet) {
            System.out.println(string + " > " + Movies.get(string).toString());
        }
    }

    /**
     * print_field_ascending_golden_palm_count() method
     */
    void print_field_ascending_golden_palm_count() {
        ArrayList<Long> golden_palms = new ArrayList<>();
        Set<String> set = Movies.keySet();
        for (String string : set) {
            golden_palms.add(Movies.get(string).getGoldenPalmCount());
        }

        Long t;
        boolean changeDone = true;
        while (changeDone) {
            changeDone = false;
            for (int i = 0; i < golden_palms.size() - 1; i++)
                if (golden_palms.get(i) > golden_palms.get(i + 1)) {
                    t = golden_palms.get(i + 1);
                    golden_palms.set(i + 1, golden_palms.get(i));
                    golden_palms.set(i, t);
                    changeDone = true;
                }
        }
        for (Long l : golden_palms)
            System.out.println(l);
    }

    private Integer x_input(BufferedReader b) throws IOException {
        Integer x;
        System.out.println("Введите координату x: ");
        while (true) {
            try {
                x = Integer.parseInt(b.readLine());
                if (x > -268)
                    break;
                else
                    System.err.println("Значение координаты х должно быть больше -268! Повторите ввод.");
            } catch (NumberFormatException e) {
                System.err.println("Координата x должна быть значением типа Integer! Повторите ввод.");
            }
        }
        return x;
    }
    private Double y_input(BufferedReader b) throws IOException {
        Double y;
        System.out.println("Введите координату y: ");
        while (true) {
            try {
                y = Double.parseDouble(b.readLine());
                if (y <= 477)
                    break;
                else
                    System.err.println("Значение координаты y должно быть меньше или равным 477! Повторите ввод.");
            } catch (NumberFormatException e) {
                System.err.println("Координата y должна быть значением типа Double! Повторите ввод");
            }
        }
        return y;
    }
    private int oscarsCount_input(BufferedReader b) throws IOException {
        int oscarsCount;
        System.out.println("Введите поле oscarsCount: ");
        while (true) {
            try {
                oscarsCount = Integer.parseInt(b.readLine());
                if (oscarsCount > 0)
                    break;
                else
                    System.err.println("Значение поля oscarsCount должно быть больше 0! Повторите ввод.");
            } catch (NumberFormatException e) {
                System.err.println("Поле oscarsCount должно быть значением типа int! Повторите ввод.");
            }
        }
        return oscarsCount;
    }
    private long goldenPalmCount_input(BufferedReader b) throws IOException {
        long goldenPalmCount;
        System.out.println("Введите поле goldenPalmCount: ");
        while (true) {
            try {
                goldenPalmCount = Long.parseLong(b.readLine());
                if (goldenPalmCount > 0)
                    break;
                else
                    System.err.println("Значение поля goldenPalmCount должно быть больше 0! Повторите ввод.");
            } catch (NumberFormatException e) {
                System.err.println("Поле goldenPalmCount должно быть значением типа long! Повторите ввод.");
            }
        }
        return goldenPalmCount;
    }
    private String tagline_input(BufferedReader b) throws IOException {
        String tagline;
        System.out.println("Введите поле tagline: ");
        while (true) {
            tagline = b.readLine();
            tagline = tagline.trim();
            if (tagline.isEmpty()) {//|| tagline.isBlank()) {
                tagline = null;
                System.out.println("Полю tagline присвоено значение null.");
                break;
            }
            else if (tagline.length() <= 214)
                break;
            else
                System.err.println("Длина строки tagline не должна быть больше 214! Повторите ввод.");
        }
        return tagline;
    }
    private MovieGenre genre_input(BufferedReader b) throws IOException {
        MovieGenre genre;
        System.out.println("Введите поле genre: " + '\n' +
                "(возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER)");
        while (true) {
            try {
                genre = MovieGenre.valueOf(b.readLine());
                break;
            } catch (IllegalArgumentException e) {
                System.err.println("Возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER. Повторите ввод.");
            }
        }
        return genre;
    }
    private String name_input(BufferedReader b) throws IOException {
        String name;
        System.out.println("Введите поле name (оператора): ");
        while (true) {
            name = b.readLine();
            name = name.trim();
            if (name.isEmpty()) //|| name.isBlank())
                System.err.println("Поле name не может быть пустой строкой или пробелом! Повторите ввод.");
            else break;
        }
        return name;
    }
    private java.time.ZonedDateTime birthday_input(BufferedReader b) throws IOException {
        java.time.ZonedDateTime birthday;
        System.out.println("Введите поле birthday (оператора) в формате \"yyyy-MM-dd\": ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                LocalDate localDate = LocalDate.parse(b.readLine(), dateTimeFormatter);
                birthday = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.of("Europe/Paris"));
                break;
            }
            catch (DateTimeParseException e) {
                System.err.println("Формат даты: \"yyyy-MM-dd\". Повторите ввод.");
            }
        }
        return birthday;
    }
    private Integer weight_input(BufferedReader b) throws IOException {
        Integer weight;
        System.out.println("Введите поле weight (оператора): ");
        while (true) {
            try {
                weight = Integer.parseInt(b.readLine());
                if (weight > 0)
                    break;
                else
                    System.err.println("Значение поля weight должно быть больше 0! Повторите ввод.");
            }
            catch (NumberFormatException e) {
                System.err.println("Поле weight должно быть значением типа Integer! Повторите ввод.");
            }
        }
        return weight;
    }
    private String passportID_input(BufferedReader b) throws IOException {
        String passportID;
        System.out.println("Введите поле passportID (оператора): ");
        while (true) {
            passportID = b.readLine();
            passportID = passportID.trim();
            if (passportID.isEmpty()) // passportID.isBlank() ||
                System.err.println("Поле passportID не может быть пустым! Повторите ввод.");
            else if (passportID.length() <= 30)
                break;
            else
                System.err.println("Длина строки passportID не должна быть больше 30! Повторите ввод.");
        }
        return passportID;
    }
    private Color hairColor_input(BufferedReader b) throws IOException {
        Color hairColor;
        System.out.println("Введите поле hairColor (оператора): " + '\n' +
                "(возможные варианты ввода: YELLOW, ORANGE, WHITE)");
        while (true) {
            try {
                hairColor = Color.valueOf(b.readLine());
                break;
            } catch (IllegalArgumentException e) {
                System.err.println("Возможные варианты ввода: YELLOW, ORANGE, WHITE. Повторите ввод.");
            }
        }
        return hairColor;
    }
}
