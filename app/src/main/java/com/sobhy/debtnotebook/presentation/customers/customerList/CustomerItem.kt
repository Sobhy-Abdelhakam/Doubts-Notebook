package com.sobhy.debtnotebook.presentation.customers.customerList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sobhy.debtnotebook.domain.model.Customer

@Composable
fun CustomerItem(
    customer: Customer,
    modifier: Modifier = Modifier,
    onCustomerClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCustomerClick(customer.id)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(customer.name, style = MaterialTheme.typography.titleMedium)
//            customer.notes?.let { note ->
//                Text(note)
//            }
        }
    }
}