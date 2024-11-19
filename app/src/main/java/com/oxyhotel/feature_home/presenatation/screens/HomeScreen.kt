package com.oxyhotel.feature_home.presenatation.screens

//import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.presenatation.AllBookingsScreenRoute
import com.oxyhotel.feature_home.presenatation.AllHotelsScreenRoute
import com.oxyhotel.feature_home.presenatation.HotelDetailsRoute
import com.oxyhotel.feature_home.presenatation.SearchHotelsScreenRoute
import com.oxyhotel.feature_home.presenatation.WishlistScreenRoute
import com.oxyhotel.feature_home.presenatation.composables.HotelLocationChipsSelector
import com.oxyhotel.feature_home.presenatation.composables.HotelSearch
import com.oxyhotel.feature_home.presenatation.composables.HotelShowSmall
import com.oxyhotel.feature_home.presenatation.composables.HotelShowSmallH
import com.oxyhotel.feature_home.presenatation.utils.UserLocation
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.ProfileViewModel


val chips = listOf("Recommended", "Popular", "Trending", "Nearby")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeStackViewModel: HomeStackViewModel,
    profileViewModel: ProfileViewModel
) {

    val state = homeStackViewModel.state.collectAsState()

    val pState = profileViewModel.state.collectAsState()

    val focusManager = LocalFocusManager.current

    val isVisible = remember {
        mutableStateOf(true)
    }

    var selectedValue by remember {
        mutableStateOf(chips[0])
    }

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                homeStackViewModel.updatePermissionStatus(it.values.all { dt -> dt })
            })


    LaunchedEffect(key1 = Unit) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context, it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            homeStackViewModel.updatePermissionStatus(true)
        } else {
            launcher.launch(permissions)
        }
    }

    LaunchedEffect(key1 = state.value.hasLocationPermission) {

        if (state.value.hasLocationPermission) {

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (location != null) {
                homeStackViewModel.updateUserLocation(
                    UserLocation(
                        latitude = location.latitude, longitude = location.longitude
                    )
                )
            }
        }

    }


    Screen(
        isScrollable = true, verticalArrangement = Arrangement.Top, padding = 0
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.bookings_icon),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(AllBookingsScreenRoute.route)
                })
            )

            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(WishlistScreenRoute.route) {
                            launchSingleTop = true
                        }
                    })
                )
            }

        }

        Text(
            text = "Hello, ${pState.value.userData?.name?.split(" ")?.get(0)}",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth()
        )

        HotelSearch(isEnabled = false, onClick = {
            navController.navigate(SearchHotelsScreenRoute.route) {
                launchSingleTop = true
            }
        })



        HotelLocationChipsSelector(
            locations = state.value.locations,
            selectedCity = state.value.selectedCity,
            hasUserLocation = state.value.hasLocationPermission,
        ) {
            homeStackViewModel.updateSelectedCity(it)
        }
        LazyRow(
            modifier = Modifier
                .animateContentSize()
        ) {

            items(state.value.showHotels.size) {

                HotelShowSmall(hotelName = state.value.showHotels[it].hotelName,
                    hotelAddress = state.value.showHotels[it].hotelAddress,
                    hotelPrice = state.value.showHotels[it].minPrice,
                    hotelRating = state.value.showHotels[it].reviews.sumOf { a -> a.ratingLevel } / (if (state.value.showHotels[it].reviews.size > 0) state.value.showHotels[it].reviews.size else 1),
                    hotelImageUri = state.value.showHotels[it].imageData[state.value.showHotels[it].imageData.keys.toMutableList()[0]]?.get(
                        0
                    ) ?: "",
                    isWishlist = state.value.showHotels[it].isWishlist,
                    onWishlist = { value ->
                        homeStackViewModel.updateBookMark(state.value.showHotels[it]._id, value)
                    }) {
                    navController.navigate("${HotelDetailsRoute.route}/id=${state.value.showHotels[it]._id}")
                }

            }

        }

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent", style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 19.sp, color = MaterialTheme.colorScheme.secondary
                )
            )

            Text(
                text = "See all", style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary
                ), modifier = Modifier.clickable(onClick = {
                    navController.navigate(AllHotelsScreenRoute.route) {
                        launchSingleTop = true
                    }
                })
            )
        }


        state.value.showHotels.map {
            HotelShowSmallH(hotelName = it.hotelName,
                hotelAddress = it.hotelAddress,
                hotelPrice = it.minPrice,
                hotelRating = it.reviews.sumOf { a -> a.ratingLevel } / (if (it.reviews.size > 0) it.reviews.size else 1),
                hotelNoOfRatings = it.reviews.size,
                hotelImageUri = it.imageData[it.imageData.keys.toMutableList()[0]]?.get(0) ?: "",
                isWishlist = it.isWishlist,
                onWishlist = { value ->
                    homeStackViewModel.updateBookMark(it._id, value)
                }) {
                navController.navigate("${HotelDetailsRoute.route}/id=${it._id}")
            }
        }


    }

}