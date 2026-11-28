package com.windows.splanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun GoalDatePicker(
    deadline: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    var showCalendar by remember {
        mutableStateOf(false)
    }

    var displayedMonth by remember(deadline) {
        mutableStateOf(
            (deadline ?: LocalDate.now()).withDayOfMonth(1)
        )
    }

    val dateFormatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale("ru", "RU")
    )

    val monthFormatter = DateTimeFormatter.ofPattern(
        "LLLL yyyy",
        Locale("ru", "RU")
    )

    val firstDayOffset =
        displayedMonth.withDayOfMonth(1).dayOfWeek.value - 1

    val calendarDays = buildList<LocalDate?> {
        repeat(firstDayOffset) {
            add(null)
        }

        for (day in 1..displayedMonth.lengthOfMonth()) {
            add(displayedMonth.withDayOfMonth(day))
        }
    }

    Box {
        // Кнопка с текущей датой
        TextButton(
            onClick = {
                showCalendar = true
            }
        ) {
            Text(
                text = deadline?.format(dateFormatter)
                    ?: "Выбрать дату",
                fontSize = 16.sp
            )
        }

        // Всплывающий календарь
        DropdownMenu(
            expanded = showCalendar,
            onDismissRequest = {
                showCalendar = false
            }
        ) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Переключение месяцев
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            displayedMonth =
                                displayedMonth.minusMonths(1)
                        }
                    ) {
                        Text("‹", fontSize = 24.sp)
                    }

                    Text(
                        text = displayedMonth.format(monthFormatter),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    TextButton(
                        onClick = {
                            displayedMonth =
                                displayedMonth.plusMonths(1)
                        }
                    ) {
                        Text("›", fontSize = 24.sp)
                    }
                }

                // Дни недели
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
                        .forEach { day ->
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colors.onSurface.copy(
                                        alpha = 0.6f
                                    )
                                )
                            }
                        }
                }

                // Числа календаря
                calendarDays.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        week.forEach { date ->

                            if (date == null) {
                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(36.dp)
                                )
                            } else {

                                val selected = date == deadline

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(36.dp)
                                        .padding(1.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (selected) {
                                                MaterialTheme.colors.primary
                                            } else {
                                                Color.Transparent
                                            }
                                        )
                                        .clickable {
                                            onDateSelected(date)
                                            showCalendar = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = date.dayOfMonth.toString(),
                                        fontSize = 13.sp,
                                        color = if (selected) {
                                            MaterialTheme.colors.onPrimary
                                        } else {
                                            MaterialTheme.colors.onSurface
                                        },
                                        fontWeight = if (selected) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Normal
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Divider()

                // Быстрый переход к сегодняшнему дню
                TextButton(
                    onClick = {
                        val today = LocalDate.now()
                        displayedMonth = today.withDayOfMonth(1)
                        onDateSelected(today)
                        showCalendar = false
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Сегодня")
                }
            }
        }
    }
}