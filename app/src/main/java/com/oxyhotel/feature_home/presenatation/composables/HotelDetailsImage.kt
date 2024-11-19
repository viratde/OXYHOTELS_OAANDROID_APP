package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.constants.Constant
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotelDetailSImageSwiper(
    images: MutableList<String>,
    imageWidth: Int = LocalConfiguration.current.screenWidthDp,
) {


    var activeIndex by remember {
        mutableStateOf(0)
    }


    val lifecycleOwner = rememberCoroutineScope()
    val offsetXState = remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    var offsetX by remember { mutableStateOf(0f) }
    val maxOffsetPx = remember {
        with(density) { ((images.size - 1) * imageWidth).dp.toPx() }
    }

    fun onDrag(delta: Float, index: Int) {
        offsetX += delta
        offsetX = offsetX.coerceIn(-maxOffsetPx, 0f)
        offsetXState.value = offsetX / maxOffsetPx
        println("${offsetXState.value} $activeIndex")
    }

    fun moveToIndex(index: Int) {
        activeIndex = index
        lifecycleOwner.launch {
            animate(
                initialValue = offsetX,
                targetValue = -(maxOffsetPx * index.toFloat() / (images.size - 1).toFloat()),
                animationSpec = spring()
            ) { value, _ ->
                offsetX = value
                offsetXState.value = offsetX / maxOffsetPx
            }
        }
    }


    fun shouldRoundUp(value: Float, activeValue: Int): Boolean {
        return if (activeValue > value) {
            value % 1 < 0.2
        } else {
            value % 1 >= 0.2
        }

    }

    fun onDragEnd(velocity: Float) {

        val indexValue = 1.0f.div(images.size - 1)

        val newValue = (-offsetXState.value / indexValue)

        val newIndex = if (shouldRoundUp(newValue, activeIndex)) {
            ceil(newValue).toInt()
        } else {
            floor(newValue).toInt()
        }

        moveToIndex(newIndex)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
    ) {

        images.mapIndexed { index, it ->
            HotelDetailsImage(
                uri = "${Constant.domain}$it",
                offset = offsetX.roundToInt() + ((imageWidth * density.density).roundToInt()) * index,
                index = index,
                onDrag = ::onDrag,
                onDragEnd = ::onDragEnd,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun HotelDetailsImage(
    uri: String,
    offset: Int,
    index: Int,
    onDrag: (Float, Int) -> Unit,
    onDragEnd: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {

    val draggableState = rememberDraggableState(onDelta = { delta -> onDrag(delta, index) })


    val painter = rememberAsyncImagePainter(
        model = uri.toUri()
    )

    Image(
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .offset { IntOffset(offset, 0) }
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = { onDragEnd(it) }
            )
    )
}