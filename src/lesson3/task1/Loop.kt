@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.abs
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int =
    when {
        abs(n) < 10 -> 1
        else -> digitNumber(abs(n) / 10) + 1
    }

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n <= 0) return 0
    if (n == 1) return 1
    if (n == 2) return 1

    var result = 0
    var prevFib = 1
    var prevPrevFib = 1
    for (i in 3..n) {
        result = prevFib + prevPrevFib
        prevPrevFib = prevFib
        prevFib = result
    }

    return result
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (isPrime(n)) return n

    for (i in 2..n) {
        if (n % i == 0) return i
    }

    return n
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    if (isPrime(n)) return 1

    var it = n - 1
    do {
        if (n % it == 0) return it
        it -= 1
    } while (it > 0)

    return 1
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var result = 0
    var nextX = x
    while (nextX != 1) {
        result += 1

        if (nextX % 2 == 0) nextX /= 2
        else nextX = 3 * nextX + 1
    }

    return result
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val maxNumber = max(m, n)
    for (i in maxNumber..(m * n) step maxNumber) {
        if (i % m == 0 && i % n == 0) return i
    }

    return m * n
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    if (m == 1 || n == 1) return true
    if (m % 2 == 0 && n % 2 == 0) return false

    val minNumber = min(m, n)
    if (m % minNumber == 0 && n % minNumber == 0) return false

    for (i in 3..sqrt(minNumber.toDouble()).toInt() step 2) {
        if (m % i == 0 && n % i == 0) return false
    }

    return true
}

private fun power(base: Int, degree: Int): Int {
    var result = 1
    for (i in 1..degree) result *= base

    return result
}

private fun power(base: Double, degree: Int): Double {
    var result = 1.0
    for (i in 1..degree) result *= base

    return result
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    if (n < 10) return n

    val digitNumber = digitNumber(n)
    var result = 0
    var newNumber = n
    for (i in 1..digitNumber) {
        result += (newNumber % 10) * power(10, digitNumber - i)
        newNumber /= 10
    }

    return result
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var number = abs(n)
    if (number < 10) return true

    for (i in 1..(digitNumber(n) + 1) / 2) {
        val firstDigit = n / power(10, digitNumber(n) - i) % 10
        val lastDigit = number % 10
        if (firstDigit != lastDigit) return false
        number /= 10
    }

    return true
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    if (n < 10) return false

    val digit = n % 10
    var newNumber = n / 10
    for (i in 2..digitNumber(n)) {
        if ((newNumber % 10) != digit) return true
        newNumber /= 10
    }

    return false
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    var value = x
    while (value >= 2 * PI) value -= 2 * PI
    while (value <= -2 * PI) value += 2 * PI

    if (abs(value) < eps) return value

    var result = value
    var degree = 3
    var sign = -1
    var nextMember: Double
    do {
        nextMember = sign * power(value, degree) / factorial(degree)
        result += nextMember
        degree += 2
        sign *= -1
    } while (abs(nextMember) >= eps)

    return result
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    var value = x
    while (value >= 2 * PI) value -= 2 * PI
    while (value <= -2 * PI) value += 2 * PI

    if (1 < eps) return 1.0

    var result = 1.0
    var degree = 2
    var sign = -1
    var nextMember: Double
    do {
        nextMember = sign * power(value, degree) / factorial(degree)
        result += nextMember
        degree += 2
        sign *= -1
    } while (abs(nextMember) >= eps)

    return result
}

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    if (n < 1) return -1

    var digitNumber = 0
    var number = 1
    var member: Int
    do {
        member = sqr(number)
        digitNumber += digitNumber(member)
        number += 1
    } while (digitNumber < n)

    for (i in 0 until digitNumber - n) {
        member /= 10
    }

    return member % 10
}

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if (n < 1) return -1

    var digitNumber = 0
    var number = 1
    var member: Int
    do {
        member = fib(number)
        digitNumber += digitNumber(member)
        number += 1
    } while (digitNumber < n)

    for (i in 0 until digitNumber - n) {
        member /= 10
    }

    return member % 10
}
