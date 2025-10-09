package com.ahrorovk.tasbihfarzun.app.navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    openLoginScreen: (String) -> Unit,
    openRegistrationScreen: () -> Unit,
    openSettingsScreen: () -> Unit,
    openDonationsLink: () -> Unit,
    openGithubPage: () -> Unit,
    shareApp: () -> Unit,
    sendEmail: () -> Unit,
    logOut: () -> Unit,
    isReg: Boolean = false
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(42.dp).clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = com.ahrorovk.core.R.drawable.fajr),
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "FindJob",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Divider()
        if (!isReg) {
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier=Modifier.padding(top=4.dp, start = 14.dp)) {
                Text(
                    text = "Авторизация",
                    modifier = Modifier
                        .clickable {
                            openLoginScreen("its_Sign_in")
                        }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Регистрация",
                    modifier = Modifier
                        .clickable {
                            openRegistrationScreen()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        DrawerItem(
            "Настройки",
            icon = Icons.Default.Settings,
            onClick = {
                openSettingsScreen()
            },
        )
        DrawerItem(
            "Пожертвования",
            icon = Icons.Default.VolunteerActivism,
            onClick = {
                openDonationsLink()
            },
        )
        DrawerItem(
            "Внести вклад",
            icon = Icons.Default.Favorite,
            onClick = {
                openGithubPage()
            },
        )
        Divider()
        DrawerItem(
            "Поделиться",
            icon = Icons.Default.Share,
            onClick = {
                shareApp()
            },
        )
        DrawerItem(
            "Фидбек",
            icon = Icons.Default.Chat,
            onClick = {
                sendEmail()
            },
        )
        DrawerItem(
            "Оцените",
            icon = Icons.Default.Star,
            onClick = { },
        )
        DrawerItem(
            "Выход",
            icon = Icons.Default.Logout,
            onClick = logOut,
        )
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clickable {
            onClick()
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .clip(RoundedCornerShape(6.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }

}