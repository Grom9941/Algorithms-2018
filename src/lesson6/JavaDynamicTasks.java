package lesson6;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int[][] lengths = new int[first.length() + 1][second.length() + 1];

        for (int i = 1; i < first.length() + 1; i++) {
            for (int j = 1; j < second.length() + 1; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    lengths[i][j] = lengths[i - 1][j - 1] + 1;
                else
                    lengths[i][j] = Math.max(lengths[i][j - 1], lengths[i - 1][j]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int x = first.length(), y = second.length();
             x != 0 && y != 0; ) {
            if (first.charAt(x - 1) == second.charAt(y - 1)) {
                sb.append(first.charAt(x - 1));
                x--;
                y--;
            } else if (lengths[x][y - 1] > lengths[x - 1][y]) {
                y--;
            } else if (lengths[x][y - 1] < lengths[x - 1][y]) {
                x--;
            } else x--;
        }
        return sb.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(inputName));

        List<String> array = new ArrayList<>();
        int numberLine = 0;
        String line;

        while ((line=reader.readLine()) != null) {
            numberLine++;
            array.add(line);
        }
        reader.close();

        int numberWeigth = array.get(0).split(" ").length;
        int[][] matrix = new int[numberLine][numberWeigth];

        numberLine=0;
        for (String str: array) {
                for (int i = 0; i < str.split(" ").length; i++) {
                    matrix[numberLine][i] = str.charAt(2 * i) - 48;
                }
                numberLine++;
        }

        int numberTop;int numberLeft;int numberDiagonal;
        byte trueAll;

        for (int i=0;i<numberLine;i++){
            for (int j=0;j<numberWeigth;j++){
                trueAll=0;
                if (i-1>=0) numberTop= matrix[i-1][j]; else {numberTop=Integer.MAX_VALUE;trueAll++;}
                if (j-1>=0) numberLeft= matrix[i][j-1]; else {numberLeft=Integer.MAX_VALUE;trueAll++;}
                if (i-1>=0 && j-1>=0) numberDiagonal= matrix[i-1][j-1]; else {numberDiagonal=Integer.MAX_VALUE;trueAll++;}

                if (trueAll!=3) {
                    matrix[i][j] = matrix[i][j] + Math.min(Math.min(numberTop, numberLeft), numberDiagonal);
                }
            }
        }
        return matrix[numberLine-1][numberWeigth-1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
