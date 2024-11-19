package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.ui.theme.OxyhotelsTheme
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthDateInput(
    selectedDate: String,
    onDateChange: (String) -> Unit,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    placeholderText: String,
) {


    var isVisible by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = if (selectedDate.isEmpty()) Calendar.getInstance().timeInMillis else dateToMillis(
            selectedDate
        )
    )

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis != null) {
            val date = millisToFormattedDate(datePickerState.selectedDateMillis!!)
            onDateChange(date)
            isVisible = false
        }
    }



    if (isVisible) {


        DatePickerDialog(
            onDismissRequest = {
                isVisible = false
            },
            confirmButton = {

            },
            shape = RoundedCornerShape(10.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            )
        ) {

            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "Choose Birth Date",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 20.sp, color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(
                            horizontal = 15.dp, vertical = 15.dp
                        )
                    )
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    dayContentColor = MaterialTheme.colorScheme.secondary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    selectedDayContentColor = MaterialTheme.colorScheme.secondary,
                    todayDateBorderColor = MaterialTheme.colorScheme.primary,
                    todayContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    }


    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .widthIn(max = 350.dp, min = 320.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable(onClick = { isVisible = true })
            .padding(horizontal = 15.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(20.dp)
                    .height(20.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            text = selectedDate.ifEmpty { placeholderText },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        if (trailingIcon != null) {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp, start = 10.dp)
                    .width(20.dp)
                    .height(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


fun millisToFormattedDate(millis: Long): String {
    return try {
        val date = Date(millis)
        val zone = TimeZone.getTimeZone("Asia/Kolkata")
        val formatter = java.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        formatter.timeZone = zone
        formatter.format(date)
    } catch (err: Exception) {
        println(err)
        "Errors"
    }
}

fun dateToMillis(selectedDate: String): Long {
    val formatter = java.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = formatter.parse(selectedDate) ?: throw IllegalArgumentException()
    return date.time
}

@Preview(widthDp = 500)
@Composable
fun PreviewAuthDateInput() {

    val selectedDate: String = "16-04-2024"

    OxyhotelsTheme {

        Screen(
            isScrollable = false, padding = 0
        ) {
            AuthDateInput(
                selectedDate = selectedDate, onDateChange = {

                }, placeholderText = "Date"
            )
        }

    }
}
