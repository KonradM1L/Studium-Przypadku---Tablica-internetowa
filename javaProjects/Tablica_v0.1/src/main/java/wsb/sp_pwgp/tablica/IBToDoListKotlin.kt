/**
 * ToDoListKotlin.kt
 * Rozbudowana konsolowa aplikacja listy zadań w Kotlinie.
 *
 * Nowe funkcje:
 * - Zadania mają termin wykonania i priorytet
 * - Zapis i odczyt z pliku (zadania.txt)
 */

import java.io.File
import java.time.LocalDate
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val tasks = loadTasks()

    println("📌 LISTA ZADAŃ Z PRIORYTETEM I TERMINEM")

    while (true) {
        println(
            """
            |-------------------------
            |1. Wyświetl zadania
            |2. Dodaj zadanie
            |3. Oznacz zadanie jako wykonane
            |4. Zapisz i zakończ
            |-------------------------
            |Wybierz opcję (1-4):
            """.trimMargin()
        )

        when (scanner.nextLine().trim()) {
            "1" -> showTasks(tasks)
            "2" -> addTask(scanner, tasks)
            "3" -> completeTask(scanner, tasks)
            "4" -> {
                saveTasks(tasks)
                println("💾 Zadania zapisane. Do zobaczenia!")
                return
            }

            else -> println("❌ Nieprawidłowa opcja.")
        }
    }
}

// === FUNKCJE GŁÓWNE ===

fun showTasks(tasks: List<Task>) {
    if (tasks.isEmpty()) {
        println("📭 Brak zadań.")
    } else {
        println("\n📋 Lista zadań:")
        tasks.sortedBy { it.deadline }.forEach {
            val status = if (it.done) "✅" else "⏳"
            println("${it.id}. [$status] ${it.description} | termin: ${it.deadline} | priorytet: ${it.priority}")
        }
    }
}

fun addTask(scanner: Scanner, tasks: MutableList<Task>) {
    print("Opis zadania: ")
    val description = scanner.nextLine()

    print("Termin (rrrr-mm-dd): ")
    val deadlineInput = scanner.nextLine()
    val deadline = try {
        LocalDate.parse(deadlineInput)
    } catch (e: Exception) {
        println("❌ Nieprawidłowa data. Ustawiono na dzisiaj.")
        LocalDate.now()
    }

    print("Priorytet (wysoki/średni/niski): ")
    val priority = scanner.nextLine().lowercase().ifBlank { "średni" }

    val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
    tasks.add(Task(newId, description, false, deadline, priority))
    println("🆕 Zadanie dodane.")
}

fun completeTask(scanner: Scanner, tasks: MutableList<Task>) {
    print("Numer zadania do oznaczenia jako wykonane: ")
    val id = scanner.nextLine().toIntOrNull()

    val task = tasks.find { it.id == id }
    if (task != null) {
        task.done = true
        println("✅ Zadanie oznaczone jako wykonane.")
    } else {
        println("❌ Nie znaleziono takiego zadania.")
    }
}

// === ZAPIS I ODCZYT ===

fun saveTasks(tasks: List<Task>) {
    val file = File("javaProjects/zadania.txt")
    file.printWriter().use { out ->
        tasks.forEach {
            out.println("${it.id}|${it.description}|${it.done}|${it.deadline}|${it.priority}")
        }
    }
}

fun loadTasks(): MutableList<Task> {
    val file = File("javaProjects/zadania.txt")
    if (!file.exists()) return mutableListOf()

    return file.readLines().mapNotNull { line ->
        val parts = line.split("|")
        if (parts.size == 5) {
            Task(
                id = parts[0].toInt(),
                description = parts[1],
                done = parts[2].toBoolean(),
                deadline = LocalDate.parse(parts[3]),
                priority = parts[4]
            )
        } else null
    }.toMutableList()
}

// === MODEL ===

data class Task(
    val id: Int,
    val description: String,
    var done: Boolean,
    val deadline: LocalDate,
    val priority: String
)
