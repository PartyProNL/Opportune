package me.partypronl.opportune

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.partypronl.opportune.data.Company
import me.partypronl.opportune.data.CompanyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var companies by remember { mutableStateOf<List<Company>>(listOf()) }
    companies = listOf(Company("ING", "Amsterdam", CompanyState.UNFIT), Company("Belastingdienst", "Utrecht", CompanyState.APPLYING))

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    val options = CompanyState.entries.toTypedArray()
    var selectedOption by remember { mutableStateOf<CompanyState?>(null) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    if(showBottomSheet) {
        CreateBottomSheet(sheetState,
            onDismiss = {
                showBottomSheet = false
            },
            onCreateAttempt = { company ->
                return@CreateBottomSheet null
            }
        )
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
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
                onActiveChange = {
                    if(it) {
                        if(!showBottomSheet) searchActive = it
                    } else {
                        searchActive = it
                    }
                                 },
                placeholder = { Text("Search...") },
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                modifier = if(searchActive) Modifier.fillMaxWidth() else Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = innerPadding.calculateTopPadding(), bottom = 8.dp),
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
                modifier = Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding()).padding(horizontal = 16.dp)
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
                            Text(option.text)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                for(company in companies) {
                    CompanyCard(company, navController)
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

@Composable
fun CompanyCard(company: Company, navController: NavController) {
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, "Location",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface)

                Text(
                    text = company.location,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.labelLarge
                )

                VerticalDivider(
                    modifier = Modifier.height(8.dp).padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Icon(Icons.Default.CheckCircle, "Match",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface)

                Text(
                    text = company.state.text,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview
//fun CreateBottomSheetPreview() {
//    val sheetState = rememberStandardBottomSheetState (
//        initialValue = SheetValue.Expanded
//    )
//
//    CreateBottomSheet(sheetState,
//        onDismiss = {},
//        onCreateAttempt = { return@CreateBottomSheet null })
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBottomSheet(sheetState: SheetState, onDismiss: () -> Unit, onCreateAttempt: (company: Company) -> String?) {
    var companyName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Add company",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Company information",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                placeholder = { Text("Google") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Name") },
            )

            OutlinedTextField(
                value = locationName,
                onValueChange = { locationName = it },
                placeholder = { Text("Amsterdam") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Location") },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, "Location")
                }
            )

            Text(
                text = "State",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) { Text("Unfit") }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) { Text("Interested") }
            }
        }
    }
}