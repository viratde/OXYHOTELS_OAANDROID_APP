package com.oxyhotel.feature_home.presenatation.screens

import android.graphics.RectF
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.presentation.common.HeaderWithBackButton
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.ReviewScreenRoute
import com.oxyhotel.feature_home.presenatation.composables.RowPairText
import com.oxyhotel.feature_home.presenatation.states.ProfileState
import com.oxyhotel.ui.theme.secondary
import kotlinx.coroutines.delay

@Composable
fun BookingDetailsScreen(
    bookingStorage: BookingStorage,
    hotelStorage: HotelStorage,
    profileState: State<ProfileState>,
    navController: NavController
) {

    var url by remember { mutableStateOf("") }

    val localUri = LocalUriHandler.current

    LaunchedEffect(key1 = Unit) {
        while (true) {
            if (bookingStorage.hasCheckedOut || bookingStorage.isCancelled || bookingStorage.hasNotShown) {
                url = "${Constant.domain}${
                    hotelStorage.imageData[hotelStorage.imageData.keys.toMutableList()[0]]?.get(0)
                }"
                break
            }
            url =
                "${Constant.domain}/api/getBookingQR?bookingId=${bookingStorage.bookingId}&time=${System.currentTimeMillis()}"
            delay(10000)
        }
    }
    val isImageLoading = rememberSaveable() {
        mutableStateOf(false)
    }

    val painter = rememberAsyncImagePainter(
        model = url,
        onState = {
            isImageLoading.value = it.painter == null
        },
    )

    val painterState = painter.state

    Screen(
        isScrollable = false,
        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp),
            color = MaterialTheme.colorScheme.background
        )
        HeaderWithBackButton(
            text = "Ticket",
        ) {
            navController.popBackStack()
        }


        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxSize()
                .clip(customShape)
                .border(1.dp, MaterialTheme.colorScheme.secondary, customShape)
                .background(MaterialTheme.colorScheme.onBackground)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = hotelStorage.hotelName,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 27.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp)
                )


                Box(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                        .width(190.dp)
                        .height(190.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (painterState is AsyncImagePainter.State.Loading) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                                .width(190.dp)
                                .height(190.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp),
                                color = Color.White
                            )
                        }
                    }
                    Image(
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                            .width(190.dp)
                            .height(190.dp)
                    )
                }

                if (bookingStorage.hasCheckedIn && !bookingStorage.hasCheckedOut) {
                    Text(
                        text = "You are now Checked In.\n Scan for CheckOut",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 5.dp)
                    )
                } else if (bookingStorage.hasCheckedOut) {
                    Text(
                        text = "You are now Checked Out.\n Click to Rate",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                navController.navigate(ReviewScreenRoute.route + "/bookingId=${bookingStorage._id}")
                            })
                            .padding(top = 10.dp, bottom = 5.dp)
                    )
                } else if (!bookingStorage.isCancelled && !bookingStorage.hasNotShown) {
                    Text(
                        text = "Scan for check in.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 5.dp)
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                secondary,
                                Offset(0f, 0f),
                                Offset(size.width, 0f),
                                strokeWidth = 5f,
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(
                                        5f,
                                        5f
                                    )
                                )
                            )
                        }
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Column(
                        modifier = Modifier
                            .padding(end = 15.dp)
                    ) {
                        RowPairText(
                            placeholder = "Name",
                            value = profileState.value.userData?.name ?: ""
                        )

                        RowPairText(
                            placeholder = "Check In Time",
                            value = "12:00"
                        )

                        RowPairText(
                            placeholder = "Check In",
                            value = bookingStorage.checkIn.toString()
                        )

                        RowPairText(
                            placeholder = "Rooms",
                            value = bookingStorage.bookedRooms.values.toList().map { it }.flatten()
                                .size.toString()
                        )
                        RowPairText(
                            placeholder = "Amount",
                            value = bookingStorage.amount.toString()
                        )
                    }

                    Column(
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        RowPairText(
                            placeholder = "Phone",
                            value = profileState.value.userData?.phoneNumber ?: ""
                        )
                        RowPairText(
                            placeholder = "Check Out Time",
                            value = "11:00"
                        )

                        RowPairText(
                            placeholder = "Check Out",
                            value = bookingStorage.checkOut
                        )

                        RowPairText(
                            placeholder = "Guest",
                            value = bookingStorage.bookedRooms.values.toList().map { it }.flatten()
                                .sum().toString()
                        )
                        RowPairText(
                            placeholder = "",
                            value = ""
                        )
                    }

                }



                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {

                    OutlinedButton(
                        onClick = {
                            localUri.openUri("tel:+919155596559")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                }

            }


        }

    }

}


val customShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData =
            "M1 21C1 9.95 9.95 1 21 1h298c11.05 0 20 8.95 20 20v227.98c0 4.24-1.35 8.37-3.85 11.8l-7.1 9.72c-5.25 7.2-5.12 17 0.33 24.04l6.44 8.3c2.7 3.51 4.18 7.82 4.18 12.25V574c0 11.05-8.95 20-20 20H21c-11.05 0-20-8.95-20-20V320.63c0-7.58 4.71-14.51 8.8-20.9 2.57-4 4.7-9.59 4.7-17.08 0-8.46-2.7-14.78-5.7-19.2C4.89 257.7 1 251.27 1 244.3V21Z"
        val androidPath = PathParser.createPathFromPathData(pathData)
        val bounds = RectF()
        androidPath.computeBounds(bounds, true)
        val matrix = android.graphics.Matrix().apply {
            postScale(
                size.width / bounds.width(),
                size.height / bounds.height()
            )
        }
        androidPath.transform(matrix)
        return Outline.Generic(
            androidPath.asComposePath()
        )
    }
}





