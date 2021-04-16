package collection;

import movie.Movie;
import movie.MovieBuilder;
import output.OutputManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * The class that contains a collection of Movie entities and methods to manage it.
 */
public class CollectionManager {
    private Hashtable<String, Movie> Movies;
    private final LocalDate creationDate;
    private OutputManager outputManager;
    private MovieBuilder movieBuilder;

    /**
     * The CollectionManager constructor.
     * @param outputManager the manager that outputs data
     * @param movieBuilder builds a new entity of Movie
     */
    public CollectionManager(OutputManager outputManager, MovieBuilder movieBuilder) {
        Movies = new Hashtable<>();
        creationDate = LocalDate.now();
        this.outputManager = outputManager;
        this.movieBuilder = movieBuilder;
    }

    /**
     * Method that inserts a new Movie element in the collection.
     * @param argument the key of the collection's element and, at the same time, the name of the Movie
     * @throws IOException if an IOException occurs
     */
    public void insertMovie(String argument) throws IOException {
        Movies.put(argument, movieBuilder.buildMovie(getFreeId(), argument));
    }

    /**
     * Method that inserts a new Movie element in the collection.
     * @param key the key of the collection's element
     * @param movie the Movie entity
     */
    public void insertMovie(String key, Movie movie) {
        Movies.put(key, movie);
    }

    /**
     * @return the size of the collection
     */
    public int getCollectionSize() {
        return Movies.size();
    }

    /**
     * @return the type of the collection
     */
    public String getCollectionType() {
        return Movies.getClass().toString();
    }

    /**
     * @return the collection's creation date
     */
    public String getCollectionCreationDate() {
        return creationDate.toString();
    }

    /**
     * @return an ArrayList of collection's elements
     */
    public ArrayList<Map.Entry<String, Movie>> getCollectionElements () {
        return new ArrayList<>(Movies.entrySet());
    }

    /**
     * @return a Set of collection's keys
     */
    public Set<String> getCollectionKeySet() {
        return Movies.keySet();
    }

    /**
     * Clears the collection.
     */
    public void ClearCollection() {
        Movies.clear();
        outputManager.printMessage("Коллекция успешно очищена!");
    }

    /**
     * Generates automatically a unique id field.
     * The newly generated id is always one more (+1) than the biggest id value in the collection.
     * @return a newly generated id field
     */
    private int getFreeId() {
        Integer id = 0;
        Set<String> set = Movies.keySet();
        for (String string : set) {
            if (id < Movies.get(string).getId())
                id = Movies.get(string).getId();
        }
        id++;
        return id;
    }

    /**
     * Removes an element from the collection by its key.
     * @param key the key of the element to delete
     */
    public void remove_key(String key) {
        if (Movies.containsKey(key)) {
            Movies.remove(key);
            outputManager.printMessage("Элемент успешно удалён!");
        }
        else
            outputManager.printErrorMessage("В коллекции нет элемента с ключом \'" + key + "\'!");
    }
}
