package com.windows.splanner

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
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ElevationOverlay
import androidx.compose.material.Icon
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.BiasAlignment
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextAlign
import com.windows.resources.Res
import com.windows.splanner.UX.setWallpaper
import org.jetbrains.skia.Surface
import java.lang.module.ModuleFinder
import javax.swing.Icon

@Composable
fun StartupActivity(){ // id=0
    UX.setWallpaper("drawable/background.png")
    Row(
        modifier= Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().width(UX.MenuDpVal).weight(1f),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = "Добро пожаловать",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 48.dp),
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier.padding(bottom=12.dp,start=48.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                UX.transparentButtonWithImage(
                    "Emergency call",
                    Color.Black,
                    Icons.Default.Call,
                    {
                        TODO("")
                    }
                )
                UX.transparentButtonWithImage(
                    "Vision Settings",
                    Color.Black,
                    Icons.Default.Visibility,
                    {
                        TODO("")
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UX.transparentButtonWithImage(
                    "Настройки",
                    Color.Black,
                    Icons.Default.Settings,
                    {
                        TODO("")
                    }
                )
                Button(
                    onClick = {
                        Sandboxed_storage.mkdirs()
                        currentActivity=1 //mainactivity
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor=Color(0xFFF9B50A),
                        contentColor=Color.Black,
                    )
                ){
                    Text(
                        text = "Далее"
                    )
                }
            }
        }
    }
}