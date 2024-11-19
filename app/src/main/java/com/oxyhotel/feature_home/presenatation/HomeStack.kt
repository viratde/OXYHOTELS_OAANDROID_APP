package com.oxyhotel.feature_home.presenatation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.screens.AllBookingsScreen
import com.oxyhotel.feature_home.presenatation.screens.AllHotelsScreen
import com.oxyhotel.feature_home.presenatation.screens.BookingDetailsScreen
import com.oxyhotel.feature_home.presenatation.screens.BookingScreen
import com.oxyhotel.feature_home.presenatation.screens.FillAccountDetailsScreen
import com.oxyhotel.feature_home.presenatation.screens.HomeScreen
import com.oxyhotel.feature_home.presenatation.screens.HotelDetailsScreen
import com.oxyhotel.feature_home.presenatation.screens.ReviewScreen
import com.oxyhotel.feature_home.presenatation.screens.SearchHotelScreen
import com.oxyhotel.feature_home.presenatation.screens.WishlistHotelScreen
import com.oxyhotel.feature_home.presenatation.viewmodels.AllBookingsViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.BookingViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.ProfileViewModel

interface HomeStackRoute {
    val route: String
}

object HomeScreenRoute : HomeStackRoute {
    override val route: String = "Home Screen Route"
}

object HotelDetailsRoute : HomeStackRoute {
    override val route: String = "Hotel Details Route"
}

object BookingScreenRoute : HomeStackRoute {
    override val route: String = "Booking Screen Route"
}

object AllBookingsScreenRoute : HomeStackRoute {
    override val route: String = "All Bookings Screen Route"
}

object WishlistScreenRoute : HomeStackRoute {
    override val route: String = "Wishlist Screens Route"
}

object AllHotelsScreenRoute : HomeStackRoute {
    override val route: String = "All Hotels Screen Route"
}

object SearchHotelsScreenRoute : HomeStackRoute {
    override val route: String = "Search Hotels Screen Route"
}

object ProfileScreenRoute : HomeStackRoute {
    override val route: String = "Profile Screen Route"
}

object BookingDetailsScreenRoute : HomeStackRoute {
    override val route: String = "Booking Details Screen Route"
}

object ReviewScreenRoute : HomeStackRoute {
    override val route: String = "Review Screen Route"
}

fun NavGraphBuilder.homeStack(
    navController: NavController,
    homeStackViewModel: HomeStackViewModel,
    allBookingViewModel: AllBookingsViewModel,
    profileViewModel: ProfileViewModel
) {

    navigation(startDestination = HomeScreenRoute.route, route = "Home") {

        composable(HomeScreenRoute.route) {
            HomeScreen(
                homeStackViewModel = homeStackViewModel,
                navController = navController,
                profileViewModel = profileViewModel
            )
        }

        composable(SearchHotelsScreenRoute.route) {
            SearchHotelScreen(
                navController = navController,
                homeStackViewModel = homeStackViewModel
            )
        }

        composable(ProfileScreenRoute.route) {

            FillAccountDetailsScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )

        }

        composable(WishlistScreenRoute.route) {

            WishlistHotelScreen(
                homeStackViewModel = homeStackViewModel,
                navController = navController
            )
        }

        composable(AllHotelsScreenRoute.route) {
            AllHotelsScreen(
                navController = navController,
                homeStackViewModel = homeStackViewModel
            )
        }

        composable(AllBookingsScreenRoute.route) {

            AllBookingsScreen(
                allBookingsViewModel = allBookingViewModel,
                homeStackViewModel = homeStackViewModel,
                navController = navController
            )

        }

        composable(
            "${HotelDetailsRoute.route}/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val hotelId = it.arguments?.getString("id")
            val state = homeStackViewModel.state.collectAsState()
            var hotelStorage: HotelStorage? = null

            if (hotelId != null) {
                hotelStorage = state.value.hotelsData.find { hotelData ->
                    hotelData._id == hotelId
                }
            }

            if (hotelStorage == null) {
                navController.navigate(HomeScreenRoute.route) {
                    launchSingleTop = true
                }
                return@composable
            }
            HotelDetailsScreen(
                hotelStorage = hotelStorage,
                navController = navController
            )
        }


        composable(
            "${BookingScreenRoute.route}/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val hotelId = it.arguments?.getString("id")
            val state = homeStackViewModel.state.collectAsState()
            var hotelStorage: HotelStorage? = null

            if (hotelId != null) {
                hotelStorage = state.value.hotelsData.find { hotelData ->
                    hotelData._id == hotelId
                }
            }

            if (hotelStorage == null) {
                navController.navigate(HomeScreenRoute.route) {
                    launchSingleTop = true
                }
                return@composable
            }
            val bookingViewModel: BookingViewModel = hiltViewModel()
            bookingViewModel.setRooms(hotelStorage!!)
            BookingScreen(
                hotelStorage = hotelStorage!!,
                navController = navController,
                bookingViewModel = bookingViewModel
            )
        }

        composable("${BookingDetailsScreenRoute.route}/bookingId={bookingId}") {
            val bookingId = it.arguments?.getString("bookingId")
            val state = allBookingViewModel.state.collectAsState()
            val hState = homeStackViewModel.state.collectAsState()
            var bookingStorage: BookingStorage? = null

            if (bookingId != null) {
                bookingStorage = state.value.bookings.find { book ->
                    book._id == bookingId
                }
            }
            if (bookingStorage == null) {
                navController.navigate(AllBookingsScreenRoute.route) {
                    launchSingleTop = true
                }
                return@composable
            }
            val hotelStorage = hState.value.hotelsData.find { hotel ->
                hotel._id == bookingStorage!!.hotelId
            }

            val pState = profileViewModel.state.collectAsState()
            if (hotelStorage != null) {
                BookingDetailsScreen(
                    bookingStorage = bookingStorage,
                    hotelStorage = hotelStorage,
                    profileState = pState,
                    navController = navController

                )
            }
        }

        composable("${ReviewScreenRoute.route}/bookingId={bookingId}") { arguments ->

            val bookingId = arguments.arguments?.getString("bookingId")
            val state = allBookingViewModel.state.collectAsState()
            val hState = homeStackViewModel.state.collectAsState()
            var bookingStorage: BookingStorage? = null

            if (bookingId != null) {
                bookingStorage = state.value.bookings.find { book ->
                    book._id == bookingId
                }
            }
            if (bookingStorage == null) {
                navController.navigate(AllBookingsScreenRoute.route) {
                    launchSingleTop = true
                }
                return@composable
            }
            val hotelStorage = hState.value.hotelsData.find { hotel ->
                hotel._id == bookingStorage!!.hotelId
            }

            if (hotelStorage != null) {
                ReviewScreen(
                    hotelStorage = hotelStorage,
                    reviewViewModel = hiltViewModel(),
                    bookingId = bookingStorage!!.bookingId,
                    navController = navController
                )
            }

        }

    }

}