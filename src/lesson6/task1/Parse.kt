@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.NumberFormatException

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

fun monthStrToNumber(str: String): Int {
    return when (str) {
        "января" -> 1
        "февраля" -> 2
        "марта" -> 3
        "апреля" -> 4
        "мая" -> 5
        "июня" -> 6
        "июля" -> 7
        "августа" -> 8
        "сентября" -> 9
        "октября" -> 10
        "ноября" -> 11
        "декабря" -> 12
        else -> throw NumberFormatException()
    }
}

fun monthNumberToString(number: Int): String {
    return when (number) {
        1 -> "января"
        2 -> "февраля"
        3 -> "марта"
        4 -> "апреля"
        5 -> "мая"
        6 -> "июня"
        7 -> "июля"
        8 -> "августа"
        9 -> "сентября"
        10 -> "октября"
        11 -> "ноября"
        12 -> "декабря"
        else -> throw NumberFormatException()
    }
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""

    val day: Int
    val month: Int
    val year: Int
    try {
        day = parts[0].toInt()
        month = monthStrToNumber(parts[1])
        year = parts[2].toInt()
    } catch (e: Exception) {
        return ""
    }

    if (year < 0) return ""
    if (month < 1 || month > 12) return ""
    if (day < 1 || day > daysInMonth(month, year)) return ""

    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""

    val day: Int
    val month: Int
    val monthStr: String
    val year: Int
    try {
        day = parts[0].toInt()
        month = parts[1].toInt()
        monthStr = monthNumberToString(month)
        year = parts[2].toInt()
    } catch (e: Exception) {
        return ""
    }

    if (year < 0) return ""
    if (day < 1 || day > daysInMonth(month, year)) return ""

    return "$day $monthStr $year"
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var result = ""
    if (phone.contains("()")) return ""

    var hasOpenBracket = false
    var hasCloseBracket = false
    for (i in phone.indices) {
        when (phone[i]) {
            '+' -> {
                if (i > 0) return ""
                result += '+'
            }
            '-', ' ' -> continue
            '(' -> {
                if (hasOpenBracket) return ""
                hasOpenBracket = true
            }
            ')' -> {
                if (!hasOpenBracket) return ""
                if (hasCloseBracket) return ""
                hasCloseBracket = true
            }
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> result += phone[i]
            else -> return ""
        }
    }

    if (hasOpenBracket && !hasCloseBracket) return ""

    return result
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ")
    val jumpNumbers = mutableListOf<Int>()

    for (jump in parts) {
        try {
            val jumpNumber = jump.toInt()
            jumpNumbers.add(jumpNumber)
        } catch (e: Exception) {
            if (jump != "-" && jump != "%") return -1
        }
    }

    return jumpNumbers.maxOrNull() ?: -1
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val parts = jumps.split(" ")
    var result = -1
    var lastHeight = 0

    for (i in parts.indices) {
        if (i % 2 == 0) {
            try {
                lastHeight = parts[i].toInt()
            } catch (e: Exception) {
                return -1
            }
        } else {
            for (char in parts[i]) {
                if (char != '+' && char != '%' && char != '-') return -1
            }
            if (parts[i].last() == '+' && lastHeight > result) result = lastHeight
        }
    }

    return result
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val parts = expression.split(" ")
    var result = 0
    var lastMark = "+"

    for (i in parts.indices) {
        if (parts[i].isEmpty()) throw IllegalArgumentException()

        if (i % 2 == 0) {
            if (!parts[i].first().isDigit()) throw IllegalArgumentException()
            try {
                val number = parts[i].toInt()
                if (lastMark == "+") result += number
                else result -= number
            } catch (e: Exception) {
                throw IllegalArgumentException()
            }
        } else {
            if (parts[i] == "+" || parts[i] == "-") lastMark = parts[i]
            else throw IllegalArgumentException()
        }
    }

    return result
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.split(" ")
    var index = -1
    var prevWord = ""

    for (word in parts) {
        if (word.lowercase() == prevWord.lowercase()) return index
        index += prevWord.length + 1
        prevWord = word
    }

    return -1
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    var result: Pair<String, Double> = Pair("", 0.0)
    val products = description.split("; ")

    for (product in products) {
        val parts = product.split(" ")
        if (parts.size != 2) return ""
        try {
            val cost = parts[1].toDouble()
            if (cost < 0) return ""
            if (cost >= result.second) result = Pair(parts[0], cost)
        } catch (e: Exception) {
            return ""
        }
    }

    return result.first
}

fun romanStrToInt(str: String): Int? {
    return when (str) {
        "I" -> 1
        "IV" -> 4
        "V" -> 5
        "IX" -> 9
        "X" -> 10
        "XL" -> 40
        "L" -> 50
        "XC" -> 90
        "C" -> 100
        "CD" -> 400
        "D" -> 500
        "CM" -> 900
        "M" -> 1000
        else -> null
    }
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var result = 0
    var it = 0

    while (it < roman.length) {
        val oneSymbol: String = roman[it].toString()
        if (it + 1 < roman.length) {
            val twoSymbols: String = roman[it].toString() + roman[it + 1].toString()
            if (romanStrToInt(twoSymbols) == null) {
                if (romanStrToInt(oneSymbol) == null) return -1
                result += romanStrToInt(oneSymbol) ?: 0
                it += 1
            } else {
                result += romanStrToInt(twoSymbols) ?: 0
                it += 2
            }
        } else {
            if (romanStrToInt(oneSymbol) == null) return -1
            result += romanStrToInt(oneSymbol) ?: 0
            it += 1
        }
    }

    return result
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var openBracketCount = 0
    var closeBracketCount = 0
    for (command in commands) {
        when (command) {
            '<', '>', '+', '-', ' ' -> continue
            '[' -> openBracketCount += 1
            ']' -> {
                if (openBracketCount <= closeBracketCount) throw IllegalArgumentException()
                closeBracketCount += 1
            }
            else -> throw IllegalArgumentException()
        }
    }
    if (openBracketCount != closeBracketCount) throw IllegalArgumentException()

    val result = Array(cells) { 0 }
    var index = cells / 2
    var commandIt = 0
    var commandCount = 0
    while (commandIt < commands.length && commandCount < limit) {
        openBracketCount = 0
        closeBracketCount = 0
        when (commands[commandIt]) {
            '>' -> {
                if (index + 1 >= cells) throw IllegalStateException()
                index += 1
            }
            '<' -> {
                if (index - 1 < 0) throw IllegalStateException()
                index -= 1
            }
            '+' -> result[index] += 1
            '-' -> result[index] -= 1
            '[' -> if (result[index] == 0) {
                openBracketCount += 1
                do {
                    commandIt += 1
                    when (commands[commandIt]) {
                        '[' -> openBracketCount += 1
                        ']' -> closeBracketCount += 1
                    }
                } while (openBracketCount != closeBracketCount)
            }
            ']' -> if (result[index] != 0) {
                closeBracketCount += 1
                do {
                    commandIt -= 1
                    when (commands[commandIt]) {
                        '[' -> openBracketCount += 1
                        ']' -> closeBracketCount += 1
                    }
                } while (openBracketCount != closeBracketCount)
            }
        }

        commandIt += 1
        commandCount += 1
    }

    return result.toList()
}
