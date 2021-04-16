package input;

import movie.MovieGenre;
import output.OutputManager;
import java.io.IOException;

/**
 * The class which is intended to read Movie fields from the console.
 */
public class ConsoleMovieReader implements MovieReader {
    private InputManager inputManager;
    private OutputManager outputManager;
    private Validator validator;

    /**
     * @param inputManager the manager that inputs data
     * @param outputManager the manager that outputs data
     * @param validator the Validator entity to validate fields that are read
     */
    public ConsoleMovieReader(InputManager inputManager, OutputManager outputManager, Validator validator) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.validator = validator;
    }

    @Override
    public Integer readCoordinateX(){
        Integer x;
        outputManager.printMessage("Введите координату x: ");
        while (true) {
            try {
                x = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateCoordinateX(x))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printErrorMessage("Координата x должна быть значением типа Integer! Повторите ввод.");
            }
        }
        return x;
    }

    @Override
    public Double readCoordinateY() {
        Double y;
        outputManager.printMessage("Введите координату y: ");
        while (true) {
            try {
                y = Double.parseDouble(inputManager.readLine().trim());
                if (validator.validateCoordinateY(y))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printErrorMessage("Координата y должна быть значением типа Double! Повторите ввод.");
            }
        }
        return y;
    }

    @Override
    public int readOscarsCount() {
        int oscarsCount;
        outputManager.printMessage("Введите поле oscarsCount: ");
        while (true) {
            try {
                oscarsCount = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateOscarsCount(oscarsCount))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printErrorMessage("Поле oscarsCount должно быть значением типа int! Повторите ввод.");
            }
        }
        return oscarsCount;
    }

    @Override
    public long readGoldenPalmCount() {
        long goldenPalmCount;
        outputManager.printMessage("Введите поле goldenPalmCount: ");
        while (true) {
            try {
                goldenPalmCount = Long.parseLong(inputManager.readLine().trim());
                if (validator.validateGoldenPalmCount(goldenPalmCount))
                    break;
            } catch (NumberFormatException | IOException e) {
                outputManager.printErrorMessage("Поле goldenPalmCount должно быть значением типа long! Повторите ввод.");
            }
        }
        return goldenPalmCount;
    }

    @Override
    public String readTagline() throws IOException {
        String tagline;
        outputManager.printMessage("Введите поле tagline: ");
        while (true) {
            tagline = inputManager.readLine();
            tagline = tagline.trim();
            if (tagline.isEmpty()) {
                tagline = null;
                outputManager.printMessage("Полю tagline присвоено значение null.");
                break;
            }
            else if (validator.validateTagline(tagline))
                break;
        }
        return tagline;
    }

    @Override
    public MovieGenre readGenre() {
        MovieGenre genre;
        outputManager.printMessage("Введите поле genre: " + '\n' + "(возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER)");
        while (true) {
            try {
                genre = MovieGenre.valueOf(inputManager.readLine().trim());
                break;
            } catch (IllegalArgumentException | IOException e) {
                outputManager.printErrorMessage("Возможные варианты ввода: ACTION, DRAMA, ADVENTURE, THRILLER. Повторите ввод.");
            }
        }
        return genre;
    }
}
