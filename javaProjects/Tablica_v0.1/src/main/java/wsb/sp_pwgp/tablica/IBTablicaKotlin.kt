/**
 * TablicaKotlin.kt
 * Prosta aplikacja konsolowa w języku Kotlin, która symuluje działanie tablicy ogłoszeń ("Tablica").
 *
 * Funkcje:
 * - Wyświetlanie listy przykładowych postów
 * - Możliwość dodania nowych postów przez użytkownika
 * - Przykład użycia klas danych, obsługi wejścia oraz składni języka Kotlin
 *
 * Autor: [Twoje Imię]
 * Data: [Dzisiejsza Data]
 */

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val posts = mutableListOf(
        Post(1, "Witaj na tablicy!", "To jest pierwszy post."),
        Post(2, "Nowa funkcja", "Dodano wersję Kotlin! 🎉")
    )

    while (true) {
        println("\n📋 TABLICA WIADOMOŚCI")
        posts.forEach { println("\n#${it.id} - ${it.title}\n${it.content}") }

        println("\n➕ Czy chcesz dodać nowy post? (tak/nie): ")
        val response = scanner.nextLine().trim().lowercase()

        if (response == "tak") {
            print("Tytuł: ")
            val title = scanner.nextLine()

            print("Treść: ")
            val content = scanner.nextLine()

            val newId = (posts.maxOfOrNull { it.id } ?: 0) + 1
            posts.add(Post(newId, title, content))

            println("\n✅ Post dodany!")
        } else {
            println("👋 Do widzenia!")
            break
        }
    }
}

/**
 * Klasa danych reprezentująca pojedynczy post na tablicy.
 */
data class Post(val id: Int, val title: String, val content: String)

/**
* Dodałem funkcję dla tablicy w języku Kotlin
*/
