package com.cold.transactiontracker.features.homescreen.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expenses: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            Text("Balance", style = MaterialTheme.typography.labelMedium)

            Text(
                text = "%.2f€".format(balance),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Income")
                    Text(
                        "+%.2f€".format(income),
                        color = Color(0xFF2E7D32) // green
                    )
                }

                Column {
                    Text("Expenses")
                    Text(
                        "-%.2f€".format(expenses),
                        color = Color(0xFFC62828) // red
                    )
                }
            }
        }
    }
}