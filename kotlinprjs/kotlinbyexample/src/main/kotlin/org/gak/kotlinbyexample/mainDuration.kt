import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toKotlinDuration

// https://www.baeldung.com/kotlin/duration-class-tutorial

fun mainDuration() {
    var dr: Duration
    val tenMinutes = 10.minutes
    val tenSeconds = 10.seconds
    val tenHours = 10.hours
    val tenDays = 10.days
    val tenMillis = 10.milliseconds
    val tenMicros = 10.microseconds
    val tenNanos = 10.nanoseconds

    val zero = Duration.ZERO
    val infinite = Duration.INFINITE

    val tenMinutesAlso = 10.toDuration(DurationUnit.MINUTES)
    val tenSecondsAlso = 10.toDuration(DurationUnit.SECONDS)


    val tenMinDuration = Duration.parseIsoString("PT10M")
    val tenDaysAlso = Duration.parseIsoString("P10D")
    val tenDaysAndOneHour = Duration.parseIsoString("P10DT1H")
    val tenDaysWithAllUnits = Duration.parseIsoString("P10DT1H5M7S")


    assertEquals(10L, tenMinutes.inWholeMinutes)
    assertEquals(600L, tenMinutes.inWholeSeconds)

    assertEquals("PT10S", tenSeconds.toIsoString())
    assertEquals("PT241H", tenDaysAndOneHour.toIsoString())

    val seventyMinutes = 70.minutes
    val asStr = seventyMinutes.toComponents { hrs, min, sec, nanos -> "${hrs}:${min}" }
    assertEquals("1:10", asStr)


    val fiveHours = 5.hours
    val fiveHoursPlusTenMin = tenMinutes + fiveHours
    assertEquals(310L, fiveHoursPlusTenMin.inWholeMinutes)
    val fiveHoursMinusTenMin = fiveHours - tenMinutes
    assertEquals(290L, fiveHoursMinusTenMin.inWholeMinutes)
    val timesMinutes = tenMinutes.times(3)
    assertEquals(30L, timesMinutes.inWholeMinutes)
    val sixSecs = tenMinutes.div(100)
    assertEquals(6, sixSecs.inWholeSeconds)


    assertTrue { fiveHours > tenMinutes }
    assertFalse { fiveHours < tenMinutes }
    assertTrue { fiveHours == 300.minutes }


    val datetime1 = LocalDateTime.now()
    val datetime2 = LocalDateTime.now().minusDays(1).minusHours(1)
    val duration = java.time.Duration.between(datetime2, datetime1).toKotlinDuration()
    val expectedDuration = 25.hours
    // assertEquals(expectedDuration, duration)


    val elapsedTime = kotlin.time.measureTime {
        Thread.sleep(510)
    }
    println(elapsedTime)

    mainRunDelay()
}

fun mainRunDelay() = runBlocking {
        val delayDuration = 1000.milliseconds
        println("Task will execute after a delay of $delayDuration")
        delay(delayDuration)
        println("Task executed")
}