/**
 * ToDoListKotlin.kt
 * Prosta aplikacja konsolowa w Kotlinie umożliwiająca tworzenie i zarządzanie listą zadań.
 *
 * Funkcje:
 * - Wyświetlanie listy zadań
 * - Dodawanie nowych zadań
 * - Oznaczanie zadań jako wykonane
 */

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val tasks = mutableListOf<Task>()

    println("📌 LISTA ZADAŃ")

    while (true) {
        println(
            """
            |-------------------------
            |1. Wyświetl wszystkie zadania
            |2. Dodaj nowe zadanie
            |3. Oznacz zadanie jako wykonane
            |4. Zakończ
            |-------------------------
            |Wybierz opcję (1-4):
            """.trimMargin()
        )

        when (scanner.nextLine().trim()) {
            "1" -> showTasks(tasks)
            "2" -> addTask(scanner, tasks)
            "3" -> completeTask(scanner, tasks)
            "4" -> {
                println("👋 Do zobaczenia!")
                return
            }

            else -> println("❌ Nieprawidłowa opcja.")
        }
    }
}

/**
 * Funkcja wyświetlająca listę zadań.
 */
fun showTasks(tasks: List<Task>) {
    if (tasks.isEmpty()) {
        println("📭 Brak zadań.")
    } else {
        println("\n📋 Zadania:")
        tasks.forEach {
            val status = if (it.done) "✅" else "⏳"
            println("${it.id}. [$status] ${it.description}")
        }
    }
}

/**
 * Funkcja dodająca nowe zadanie.
 */
fun addTask(scanner: Scanner, tasks: MutableList<Task>) {
    print("Wprowadź treść zadania: ")
    val description = scanner.nextLine()
    val id = (tasks.maxOfOrNull { it.id } ?: 0) + 1
    tasks.add(Task(id, description, false))
    println("🆕 Zadanie dodane.")
}

/**
 * Funkcja oznaczająca zadanie jako wykonane.
 */
fun completeTask(scanner: Scanner, tasks: MutableList<Task>) {
    print("Podaj numer zadania do oznaczenia jako wykonane: ")
    val input = scanner.nextLine()
    val id = input.toIntOrNull()

    val task = tasks.find { it.id == id }
    if (task != null) {
        task.done = true
        println("✅ Zadanie oznaczone jako wykonane.")
    } else {
        println("❌ Nie znaleziono zadania o podanym numerze.")
    }
}

/**
 * Klasa danych reprezentująca zadanie.
 */
data class Task(val id: Int, val description: String, var done: Boolean)
