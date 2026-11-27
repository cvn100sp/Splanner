package com.windows.splanner

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.annotation.ColorInt
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ElevationOverlay
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.ui.BiasAlignment
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextAlign
import com.fuse.splanner.resources.Res
import org.jetbrains.skia.Surface
import java.lang.classfile.attribute.ModuleRequireInfo
import java.lang.module.ModuleFinder
import kotlin.Int
import kotlin.String

@Composable
fun MainActivity() { //id=1
    var currentSelection by remember {
        mutableStateOf(1)
    }
    Row(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxHeight()
                .width(UX.MenuDpVal)
                .background(Color.LightGray),
            contentAlignment = Alignment.TopStart
        ) {
            Column (
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text="Меню",
                    fontSize = 24.sp,
                    modifier = Modifier.width(UX.MenuDpVal)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(20.dp))
                UX.SectionButton(
                    "Активные цели",
                    MaterialTheme.colors.primary,
                    currentSelection == 1,
                    {
                        currentSelection=1
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                UX.SectionButton(
                    "Архив",
                    MaterialTheme.colors.primary,
                    currentSelection == 2,
                    {
                        currentSelection=2
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.TopStart
        ){
            when(currentSelection){
                1 -> selection_active_tasks()
                2 -> selection_archived_tasks()
            }
        }
    }
}
@Composable
fun selection_active_tasks() {
    UX.GoalCard(
        Goal(
            1,
            "1 задача",
            "первый тест",
            0.3f,
            "2 дек"
        ),
        {}
    )
}
@Composable
fun selection_archived_tasks() {

    UX.GoalCard(
        Goal(
            2,
            "2 задача",
            "второй тест",
            0.7f,
            "5 дек"
        ),
        {}
    )
}