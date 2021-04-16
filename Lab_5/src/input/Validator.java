package input;

import output.OutputManager;

/**
 * The class that validates fields of Movie and Person.
 */
public class Validator {
    private OutputManager outputManager;

    /**
     * @param outputManager the manager that outputs data
     */
    public Validator (OutputManager outputManager) {
        this.outputManager = outputManager;
    }

    /**
     * @param x coordinate x
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateCoordinateX (Integer x) {
        if (x > -268)
           return true;
        outputManager.printErrorMessage("Значение координаты х должно быть больше -268! Повторите ввод.");
        return false;
    }

    /**
     * @param y coordinate y
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateCoordinateY (Double y) {
        if (y <= 477)
            return true;
        outputManager.printErrorMessage("Значение координаты y должно быть меньше или равным 477! Повторите ввод.");
        return false;
    }

    /**
     * @param oscarsCount int oscarsCount
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateOscarsCount (int oscarsCount) {
        if (oscarsCount > 0)
            return true;
        outputManager.printErrorMessage("Значение поля oscarsCount должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param goldenPalmCount long goldenPalmCount
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateGoldenPalmCount (long goldenPalmCount) {
        if (goldenPalmCount > 0)
            return true;
        outputManager.printErrorMessage("Значение поля goldenPalmCount должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param tagline String tagline
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateTagline (String tagline) {
        if (tagline.length() <= 214)
            return true;
        outputManager.printErrorMessage("Длина строки tagline не должна быть больше 214! Повторите ввод.");
        return false;
    }

    /**
     * @param name String name
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validatePersonName (String name) {
        name = name.trim();
        if (!name.isEmpty())
            return true;
        outputManager.printErrorMessage("Поле name не может быть пустой строкой или пробелом! Повторите ввод.");
        return false;
    }

    /**
     * @param weight Integer weight
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validateWeight (Integer weight) {
        if (weight > 0)
            return true;
        outputManager.printErrorMessage("Значение поля weight должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param passportID String passportID
     * @return true if it is ok;
     *         otherwise false
     */
    boolean validatePassportID (String passportID) {
        passportID = passportID.trim();
        if (passportID.isEmpty()) {
            outputManager.printErrorMessage("Поле passportID не может быть пустым! Повторите ввод.");
            return false;
        }
        else if (passportID.length() <= 30)
            return true;
        outputManager.printErrorMessage("Длина строки passportID не должна быть больше 30! Повторите ввод.");
        return false;
    }
}
