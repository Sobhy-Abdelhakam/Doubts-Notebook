package com.example.doubtsnotebook.presentation.customers.customerdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.doubtsnotebook.domain.model.Transaction
import com.example.doubtsnotebook.domain.model.TransactionType.*

@Composable
fun TransactionItem(
    txn: Transaction,
    modifier: Modifier = Modifier
) {
    val typeText = when(txn.type){
        PURCHASE -> "Purchase"
        PAYMENT -> "Payment"
    }
    val amountColor = if (txn.type == PURCHASE) Color.Red else Color.Green

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = typeText, style = MaterialTheme.typography.bodyLarge)
                txn.description?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            }
            Text(txn.amount.toString(), color = amountColor, style = MaterialTheme.typography.bodyLarge)
        }
    }
}