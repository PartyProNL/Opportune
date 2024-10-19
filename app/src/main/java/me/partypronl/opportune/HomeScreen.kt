package me.partypronl.opportune

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.partypronl.opportune.data.Company
import me.partypronl.opportune.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var companies by remember { mutableStateOf<List<Company>>(listOf()) }
    companies = listOf(Company("ING", "Amsterdam"), Company("Belastingdienst", "Utrecht"))

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    val options = listOf("Unfit", "Maybe", "Applying")
    var selectedOption by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {},
                active = searchActive,
                onActiveChange = { searchActive = it },
                placeholder = { Text("Search...") },
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                modifier = if(searchActive) Modifier.fillMaxWidth() else Modifier.fillMaxWidth().padding(16.dp, 8.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            ) {
//                Search results and such
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)
            ) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    for((index, option) in options.withIndex()) {
                        SegmentedButton(
                            selected = selectedOption == option ,
                            onClick = {
                                selectedOption = if(selectedOption == option) null else option
                                      },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size)
                        ) {
                            Text(option)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                for(company in companies) {
                    Card(
                        elevation = CardDefaults.elevatedCardElevation(),
                        modifier = Modifier.fillMaxWidth().clickable {
                            navController.navigate(route = MainActivity.DetailPage(company.name))
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Text(
                                text = company.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Row {
                                Text(
                                    text = company.location,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.primary, shape = ShapeDefaults.Large)
                                        .padding(8.dp, 4.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun HomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}