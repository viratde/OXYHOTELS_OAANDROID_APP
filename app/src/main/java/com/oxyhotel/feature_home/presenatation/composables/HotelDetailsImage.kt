package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.constants.Constant


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotelDetailSImageSwiper(
    images: List<String>,
    modifier: Modifier = Modifier,
) {

    if (images.isNotEmpty()) {
        val pagerState = rememberPagerState {
            images.size
        }

        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) {
            HotelDetailsImage(
                uri = "${Constant.domain}${images[it]}",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}


@Composable
fun HotelDetailsImage(
    uri: String,
    modifier: Modifier = Modifier,
) {

    val painter = rememberAsyncImagePainter(
        model = uri
    )

    Image(
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )

}