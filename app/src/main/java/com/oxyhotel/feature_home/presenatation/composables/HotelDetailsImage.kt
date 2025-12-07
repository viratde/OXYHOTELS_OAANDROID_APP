package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.constants.Constant


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotelDetailSImageSwiper(
    images: MutableList<String>,
) {

    if (images.isNotEmpty()) {
        val pagerState = rememberPagerState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = images.size,
                modifier = Modifier.fillMaxSize()
            ) {
                HotelDetailsImage(
                    uri = "${Constant.domain}$it",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}


@Composable
fun HotelDetailsImage(
    uri: String,
    modifier: Modifier = Modifier,
) {

    val painter = rememberAsyncImagePainter(
        model = uri.toUri()
    )

    Image(
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}