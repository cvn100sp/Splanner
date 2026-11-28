package com.windows.splanner

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.*

object UX {
    val MenuDpVal = 200.dp
    val HeaderDpVal = 50.dp
    @Composable
    fun SectionButton(
        text: String,
        backgroundColor: Color = MaterialTheme.colors.primary,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(MenuDpVal-24.dp)
                .height(56.dp),

            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 36.dp,
                bottomEnd = 36.dp
            ),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isSelected) Color.Blue else Color.Transparent,
                contentColor = MaterialTheme.colors.onPrimary
            ),

            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 3.dp
            ),

            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 0.dp
            )

        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    @Composable
    fun transparentButtonWithImage(
        text: String,
        textColor: Color,
        icon: ImageVector,
        onClick: () -> Unit
    ){
        Button(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor=Color.Transparent,
                contentColor=textColor
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ){
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Spacer(Modifier.padding(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
        }
    }

    @Composable
    fun setWallpaper(
        resourcePath: String
    ) {
        Image(
            painter = painterResource(resourcePath),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun transparentButton(
        text: String,
        textColor: Color,
        onClick: () -> Unit
    ) {
        Button(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor=Color.Transparent,
                contentColor=textColor
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ){
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }
    @Composable
    fun GoalCard(
        goal: Goal,
        onClick: () -> Unit
    ) {
        val shape = RoundedCornerShape(20.dp)

        val hoverInteraction = remember {
            MutableInteractionSource()
        }

        val isHovered by hoverInteraction.collectIsHoveredAsState()

        val elevation by animateDpAsState(
            targetValue = if (isHovered) 6.dp else 2.dp,
            animationSpec = tween(180)
        )

        val surfaceColor = MaterialTheme.colors.surface

        val cardColor by animateColorAsState(
            targetValue = if (isHovered) {
                androidx.compose.ui.graphics.lerp(
                    surfaceColor,
                    MaterialTheme.colors.onSurface,
                    0.07f
                )
            } else {
                surfaceColor
            },
            animationSpec = tween(180)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .hoverable(hoverInteraction),
            shape = shape,
            elevation = elevation,
            backgroundColor = cardColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = onClick
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = goal.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = goal.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Прогресс", fontSize = 13.sp)

                    Text(
                        "${(goal.progress * 100).toInt()}%",
                        fontWeight = FontWeight.Bold
                    )
                }

                LinearProgressIndicator(
                    progress = goal.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = Color(0xFF4285F4),
                    backgroundColor = Color.LightGray
                )

                Text(
                    text = "Срок: ${goal.deadline}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
    @Composable
    fun LiquidGlassButton(
        text: String,
        icon: ImageVector,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.height(48.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF386AEB),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 0.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.sp,
                    maxLines = 1
                )
            }
        }
    }
}
