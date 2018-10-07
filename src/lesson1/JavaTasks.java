package lesson1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        List<Integer> list = new ArrayList<>();
        String line;
        Integer number = 0;
        while ((line = reader.readLine()) != null) {
            if (line.split(":").length != 3) throw new IllegalArgumentException();
            for ( String split : line.split(":")){
                if (split.length()!=2) throw new IllegalArgumentException();
                number = number*60 + Integer.parseInt(split);
            }
            if (number>87900) throw new IllegalArgumentException();
            list.add(number);
            number = 0;
            }
        reader.close();

        int[] array = list.stream().mapToInt(i -> i).toArray();

        Sorts.quickSort(array);

        int second,minute,hour;
        String s,m,h;
        FileWriter writer = new FileWriter(outputName);

        for (Integer i : array) {
            hour = i/3600;
            h = String.valueOf(hour);
            if (hour<10) h = "0" + h;
            minute = (i - 3600*hour)/60;
            m = String.valueOf(minute);
            if (minute<10) m = "0" + m;
            second = i - (hour*3600) - (minute*60);
            s = String.valueOf(second   );
            if (second<10) s = "0" + s;
            writer.write(h + ":" + m + ":" + s +"\n");
        }
        writer.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        List<String> list = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.split(" ").length != 5 || line.split(" - ").length != 2 ||
                    !line.split(" ")[4].matches("[-+]?\\d+")) throw new IllegalArgumentException();

            list.add(line.split(" - ")[1] + " - " + line.split(" - ")[0]);
        }
        reader.close();

        Collections.sort(list);
        for (int i = 0;i<list.size()-1;i++){
                while ((i+1 <= list.size()-1) &&(list.get(i).split("-")[0].equals(list.get(i + 1).split("-")[0]))) {
                    list.set(i, list.get(i) + "," + list.get(i + 1).split("-")[1]);
                    list.remove(i+1);
                }
        }
        FileWriter writer = new FileWriter(outputName);

        for (String i : list) {
            writer.write(i +"\n");
        }
        writer.close();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        List<Double> list = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            list.add(Double.parseDouble(line));
        }
        reader.close();

        Collections.sort(list);
        FileWriter writer = new FileWriter(outputName);

        for (Double i : list) {
            writer.write(i +"\n");
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {

    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {

//        for (int i = 0; i<first.length;i++){
//            second[i] = first[i];
//        }
//
//        Sorts.insertionSort(second);
    }
}
