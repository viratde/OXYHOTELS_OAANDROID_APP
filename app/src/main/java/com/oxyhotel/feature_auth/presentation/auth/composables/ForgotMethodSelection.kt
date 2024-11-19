package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ForgotMethodSelection(
    isSelected:Boolean=false,
    painterResourceId:Int,
    methodText:String,
    detailText:String,
    onClick:() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .width(310.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .let {
                if(isSelected){
                    it.border(1.dp,MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                }else{
                    it
                }
            }
            .clickable(onClick = {onClick()})
            .padding(horizontal = 25.dp, vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        
        Row(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = painterResourceId),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .width(23.dp)
                    .height(23.dp),
            )
        }
        
        Column (
            modifier = Modifier.padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
                ){

            Text(
                text = methodText,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = detailText,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                color = MaterialTheme.colorScheme.primary
            )

        }
        
    }
}

