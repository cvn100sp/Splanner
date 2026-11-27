package com.windows.splanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            shape = RoundedCornerShape(20.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
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
}
data class Goal(
    val id: Int,
    val title: String,
    val description: String,
    val progress: Float,
    val deadline: String
)