package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {

//        BufferedReader reader = new BufferedReader(new FileReader(inputName));
//        List<Integer> list = new ArrayList<>();
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            list.add(Integer.parseInt(line));
//        }
//        reader.close();
//        List<Integer> delta = new ArrayList<>();
//        for (int i=0;i<list.size()-1;i++) {
//            delta.add(list.get(i+1)-list.get(i));
//        }
//
//        Integer maxcurrent =0,maxglobal = 0,number1 = list.get(0), number2=0,i1=0,i2=0;
//        Integer icon,jcon = null;
//        for (int x = 0 ;x<delta.size()-1;x++) {
//            maxcurrent = Math.max(delta.get(x),maxcurrent+delta.get(x));
//            if (maxcurrent > maxglobal) {
//                maxglobal = maxcurrent;
//                jcon = x+2;
//            }
//        }
//        icon=jcon;
//        Integer per=2;
//        while(maxglobal!=0) {
//            maxglobal-=delta.get(jcon-per);
//            per++;
//            icon-=1;
//        }
//
//        return new Pair<>(icon,jcon);
        throw new NotImplementedError();
    }


    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     */

    // Трудоемкость O(menNumber)
    // Ресурсоемкость O(1)
    static public int josephTask(int menNumber, int choiceInterval) {
        if (menNumber<=0 || choiceInterval<=0) return -1;
        int res=0;
        for (int i=1;i<=menNumber;i++) res=(res+choiceInterval)%i;
        return res+1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */

    // Трудоемкость O(first.length() * second.length())
    // Ресурсоемкость O(second.length())
    static public String longestCommonSubstring(String first, String second) {
        List<Integer> listfrist = new ArrayList<>();
        Integer max=0,j1 = null;
        for (int j=0;j<second.length();j++){
            if(first.charAt(0)==second.charAt(j)){
                listfrist.add(j,1);
            } else {
                listfrist.add(j,0);
            }
            if (max<listfrist.get(j)) {
                max=listfrist.get(j);
                j1=j;
            }
        }
        Integer previous=-1,previushelper;

            for (int i=1;i<first.length();i++){
            for (int j=0;j<second.length();j++){
                previushelper=listfrist.get(j);
                if (first.charAt(i)==second.charAt(j)) {
                    if (j != 0) {
                        listfrist.set(j, previous + 1);
                    } else {
                        listfrist.set(0, 0);
                    }
                } else {
                    listfrist.set(j,0);
                }
                    previous=previushelper;
                    if (max<listfrist.get(j)) {
                        max=listfrist.get(j);
                        j1=j;
                    }
            }
        }
        if (max==0) return "";
            StringBuilder s = new StringBuilder();
            Integer k =j1-max+1;
            while (k<=j1){
                s.append(second.charAt(k));
                k++;
            }
            return s.toString();
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
