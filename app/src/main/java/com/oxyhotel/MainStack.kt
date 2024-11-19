package com.oxyhotel

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oxyhotel.feature_home.presenatation.AllBookingsScreenRoute
import com.oxyhotel.feature_home.presenatation.HomeScreenRoute
import com.oxyhotel.feature_home.presenatation.ProfileScreenRoute
import com.oxyhotel.feature_home.presenatation.SearchHotelsScreenRoute
import com.oxyhotel.feature_home.presenatation.homeStack
import com.oxyhotel.feature_home.presenatation.viewmodels.AllBookingsViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.ProfileViewModel


@Composable
fun MainStack(
    homeStackViewModel: HomeStackViewModel,
    allBookingsViewModel: AllBookingsViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    val state = homeStackViewModel.state.collectAsState()
    val context = LocalContext.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            homeStackViewModel.clearMessage()
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Column(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "Home") {
                homeStack(
                    navController = navController,
                    homeStackViewModel = homeStackViewModel,
                    allBookingViewModel = allBookingsViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}


val bottomBarContents = listOf(
    Triple("Home", R.drawable.home, HomeScreenRoute),
    Triple("Search", R.drawable.search, SearchHotelsScreenRoute),
    Triple("Bookings", R.drawable.bookings_icon, AllBookingsScreenRoute),
    Triple("Profile", R.drawable.profile, ProfileScreenRoute),
)


@Composable
fun BottomBar(navController: NavController) {

    val currentBackStackEntry: State<NavBackStackEntry?> =
        navController.currentBackStackEntryAsState()

    var isEnabled by remember {
        mutableStateOf(false)
    }

    var routeName by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = currentBackStackEntry.value?.destination?.route) {
        val route = currentBackStackEntry.value?.destination?.route
        if (route != null) {
            routeName = route
            isEnabled = bottomBarContents.map { it.third.route }.contains(routeName)
        }
    }

    AnimatedVisibility(visible = isEnabled) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                .shadow(
                    4.dp,
                    RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
                    true,
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomBarContents.map {

                Column(
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(it.third.route) {
                            launchSingleTop = true
                        }
                    }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Icon(
                        painter = painterResource(id = it.second),
                        contentDescription = "",
                        tint = if (routeName == it.third.route) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.6f
                        )
                    )

                    Text(
                        text = it.first, style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary
                        ), modifier = Modifier.padding(top = 3.dp)
                    )

                }
            }
        }
    }
}

