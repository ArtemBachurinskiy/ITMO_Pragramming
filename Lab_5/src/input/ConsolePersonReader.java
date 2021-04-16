package input;

import movie.Color;
import output.OutputManager;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The class which is intended to read Person fields from the console.
 */
public class ConsolePersonReader implements PersonReader {
    private InputManager inputManager;
    private OutputManager outputManager;
    private Validator validator;

    /**
     * @param inputManager the manager that inputs data
     * @param outputManager the manager that outputs data
     * @param validator the Validator entity to validate fields that are read
     */
    public ConsolePersonReader(InputManager inputManager, OutputManager outputManager, Validator validator) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.validator = validator;
    }

    @Override
    public String readPersonName() throws IOException {
        String name;
        outputManager.printMessage("Введите поле name (оператора): ");
        do {
            name = inputManager.readLine().trim();
        } while (!validator.validatePersonName(name));
        return name;
    }

    @Override
    public ZonedDateTime readBirthday() {
        java.time.ZonedDateTime birthday;
        outputManager.printMessage("Введите поле birthday (оператора) в формате \"yyyy-MM-dd\": ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                LocalDate localDate = LocalDate.parse(inputManager.readLine().trim(), dateTimeFormatter);
                birthday = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.of("Europe/Paris"));
                break;
            }
            catch (DateTimeParseException | IOException e) {
                outputManager.printErrorMessage("Формат даты: \"yyyy-MM-dd\". Повторите ввод.");
            }
        }
        return birthday;
    }

    @Override
    public Integer readWeight() {
        Integer weight;
        outputManager.printMessage("Введите поле weight (оператора): ");
        while (true) {
            try {
                weight = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateWeight(weight))
                    break;
            }
            catch (NumberFormatException | IOException e) {
                outputManager.printErrorMessage("Поле weight должно быть значением типа Integer! Повторите ввод.");
            }
        }
        return weight;
    }

    @Override
    public String readPassportID() throws IOException {
        String passportID;
        outputManager.printMessage("Введите поле passportID (оператора): ");
        do {
            passportID = inputManager.readLine().trim();
        } while (!validator.validatePassportID(passportID));
        return passportID;
    }

    @Override
    public Color readHairColor() {
        Color hairColor;
        outputManager.printMessage("Введите поле hairColor (оператора): " + '\n' +
                "(возможные варианты ввода: YELLOW, ORANGE, WHITE)");
        while (true) {
            try {
                hairColor = Color.valueOf(inputManager.readLine().trim());
                break;
            } catch (IllegalArgumentException | IOException e) {
                outputManager.printErrorMessage("Возможные варианты ввода: YELLOW, ORANGE, WHITE. Повторите ввод.");
            }
        }
        return hairColor;
    }
}
