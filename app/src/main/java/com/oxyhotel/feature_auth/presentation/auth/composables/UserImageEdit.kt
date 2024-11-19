package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun UserImageEdit(
    painterResourceId: Int
) {

    Box {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(120.dp)
                .height(120.dp)
        )
//        Image(
//            painter = painterResource(id = R.drawable.edit_icon),
//            contentDescription = "",
//            modifier = Modifier.width(28.dp).height(28.dp)
//                .offset { IntOffset(100.dp.toPx().toInt(), 100.dp.toPx().toInt()) }
//        )
    }
}