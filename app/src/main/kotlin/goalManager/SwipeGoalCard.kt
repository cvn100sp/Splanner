package com.windows.splanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import kotlin.math.roundToInt
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.platform.LocalDensity

@Composable
fun SwipeGoalCard(
    goal: Goal,
    isRevealed: Boolean,
    onRevealChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit
) {
    val actionWidth = 176.dp

    val density = LocalDensity.current
    val actionWidthPx = with(density) {
        actionWidth.toPx()
    }

    val offsetX = remember(goal.id, actionWidthPx) {
        Animatable(
            if (isRevealed) -actionWidthPx else 0f
        )
    }

    val scope = rememberCoroutineScope()

    var dragOffset by remember(goal.id) {
        mutableStateOf(0f)
    }

    val latestRevealed by rememberUpdatedState(isRevealed)
    val latestRevealChange by rememberUpdatedState(onRevealChange)

    // Синхронизация с состоянием остальных карточек
    LaunchedEffect(isRevealed) {
        offsetX.animateTo(
            targetValue = if (isRevealed) {
                -actionWidthPx
            } else {
                0f
            },
            animationSpec = tween(220)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clipToBounds()
            .pointerInput(goal.id, actionWidthPx) {

                detectHorizontalDragGestures(

                    onDragStart = {
                        dragOffset = offsetX.value

                        scope.launch {
                            offsetX.stop()
                        }
                    },

                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()

                        dragOffset = (
                                dragOffset + dragAmount
                                ).coerceIn(-actionWidthPx, 0f)

                        scope.launch {
                            offsetX.snapTo(dragOffset)
                        }
                    },

                    onDragEnd = {
                        val shouldOpen =
                            dragOffset < -actionWidthPx / 2

                        val target = if (shouldOpen) {
                            -actionWidthPx
                        } else {
                            0f
                        }

                        if (shouldOpen != latestRevealed) {
                            // Состояние изменится, LaunchedEffect
                            // выполнит анимацию от текущей позиции.
                            latestRevealChange(shouldOpen)
                        } else {
                            // Состояние не изменилось:
                            // самостоятельно доводим до края.
                            scope.launch {
                                offsetX.animateTo(
                                    targetValue = target,
                                    animationSpec = tween(220)
                                )
                            }
                        }
                    },

                    onDragCancel = {
                        scope.launch {
                            offsetX.animateTo(
                                targetValue = if (latestRevealed) {
                                    -actionWidthPx
                                } else {
                                    0f
                                },
                                animationSpec = tween(220)
                            )
                        }
                    }
                )
            }
    ) {

        // Кнопки действий
        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.End
        ) {

            Column(
                modifier = Modifier
                    .width(actionWidth / 2)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            21.dp
                        )
                    )
                    .background(Color.Green)
                    .clickable {
                        onArchive()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Archive,
                    contentDescription = "Архивировать",
                    tint = Color.White
                )

                Text(
                    "Архив",
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .width(actionWidth / 2)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            21.dp
                        )
                    )
                    .background(Color(0xFFE53935))
                    .clickable {
                        onDelete()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = Color.White
                )

                Text(
                    "Удалить",
                    color = Color.White
                )
            }
        }

        // Карточка
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        0
                    )
                }
                .fillMaxWidth()
        ) {
            UX.GoalCard(
                goal = goal,
                onClick = onClick
            )
        }
    }
}