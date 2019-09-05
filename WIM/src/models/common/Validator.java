package models.common;

import static commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class Validator <E> {
    private static final String IS_EMPTY_ERROR_MESSAGE = "%s can not be empty.";

    public static <N extends Number> void validateLength(
            int length, N min, N max, String msg){
        if(length < min.doubleValue() || length > max.doubleValue()){
            throw new IllegalArgumentException(String.format(msg, min, max));
        }
    }

    public static <N extends Number> void validateValueOfNumber(int value, int min, int max, String msg){
        if(value < min || value > max){
            throw new IllegalArgumentException(String.format(msg, min, max));
        }
    }

    public  static <E> void checkForEmptyValue(E element, String header){
        if(element.toString().trim().isEmpty()){
            throw new IllegalArgumentException(String.format(IS_EMPTY_ERROR_MESSAGE, header)
            );
        }
    }

    public static void checkForCorrectNumberOfArguments(int actualNumber, int correctNumber) {
        if (actualNumber != correctNumber) {
            throw new IllegalArgumentException(
                    String.format(
                            INVALID_NUMBER_OF_ARGUMENTS,
                            correctNumber,
                            actualNumber)
            );
        }
    }
}
