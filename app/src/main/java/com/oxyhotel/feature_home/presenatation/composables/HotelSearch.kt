package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.R

@Composable
fun HotelSearch(
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    search: String = "",
    onSearchChange: (String) -> Unit = {}
) {

    if (!isEnabled) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = { onClick() })
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = if (search.isEmpty()) "Search" else "",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                )
            }
        }
    } else {

        BasicTextField(
            value = search,
            onValueChange = { onSearchChange(it) },
            singleLine = true,
            enabled = isEnabled,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 13.sp
            ),
            cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White)),
            decorationBox = {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(horizontal = 12.dp, vertical = 14.dp)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = if (search.isEmpty()) "Search" else "",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp)
                        )
                        it()
                    }
                }

            }
        )
    }
}