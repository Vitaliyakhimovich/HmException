package Assistance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Checker.ParsingDataStringException;
import Checker.DataChecker;
import View.View;

public class Assistance<V extends View> {

    private DataChecker checker;
    private V view;

    public Assistance(V v) {
        view = v;
    }

    public void start() {
        boolean working = true;
        do {
            String input = view.getInput(
                    "Введите данные через пробел в формате \n"
                            + "Фамилия Имя Отчество ДатаРождения НомерТелефона Пол \n"
                            + "или Exit для выхода из программы: \n");
            if (input.equals("Exit")) {
                working = false;
                break;
            } else {
                // множественные пробелы заменяем на одинарные
                String[] splitedInput = input.replaceAll("\\s+", " ").split(" ");

                int inputDataCount = checkInputDataCount(splitedInput.length);
                if (inputDataCount == -1) {
                    view.printOutput("Введено мало данных. Должно быть " + DataChecker.dataCount
                            + " элементов, разделённых пробелом:\n Филия Имя Отчество ДатаРождения НомерТелефона Пол\n");
                } else if (inputDataCount == 0) {
                    view.printOutput("Введено много данных. Должно быть " + DataChecker.dataCount
                            + ", разделённых пробелом:\n Фамилия Имя Отчество ДатаРождения НомерТелефона Пол)\n");
                } else {
                    try {
                        checker = new DataChecker();
                        checker.CheckData(splitedInput);
                        writePersonData(checker);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParsingDataStringException e) {
                        view.printOutput(e.getMessage());
                    }
                }
            }
        } while (working);
    }

    // Проверка количества введенных данных
    private int checkInputDataCount(int inputDataCount) {
        if (inputDataCount < DataChecker.dataCount) {
            return -1;
        } else if (inputDataCount > DataChecker.dataCount) {
            return 0;
        } else {
            return inputDataCount;
        }
    }

    // Создаём новый файл
    private void writePersonData(DataChecker data) throws IOException {
        // Здесь указываем путь к папке для записи файлов
        File filepath = new File("src\\Data\\" + data.getLastName());
        try (FileWriter fw = new FileWriter(filepath, true)) {
            fw.append(data.toString() + "\n");
        } catch (IOException e) {
            throw e;
        }
    }
}