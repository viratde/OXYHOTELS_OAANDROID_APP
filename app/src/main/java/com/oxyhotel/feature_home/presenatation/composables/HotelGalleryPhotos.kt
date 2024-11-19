package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.constants.Constant

@Composable
fun HotelGalleryPhotos(
    images: List<String> = listOf()
) {

    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Gallery",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        LazyRow(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {

            items(images.size) {

                val painter = rememberAsyncImagePainter(
                    model = "${Constant.domain}${images[it]}".toUri()
                )

                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .width(140.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }

    }

}