package com.example.eni_shop.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.eni_shop.R
import com.example.eni_shop.destination.EniShopArticleListDestination
import com.example.eni_shop.destination.EniShopDestination
import com.example.eni_shop.ui.common.TopBar
import com.example.eni_shop.ui.vm.ArticleFormViewModel

@Composable
fun ArticleFormScreen(
    navHostController: NavHostController,
    navigationIcon: @Composable () -> Unit
) {

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                navigationIcon = navigationIcon
            )
        }
    ) {
        ArticleForm(
            navHostController = navHostController,
            modifier = Modifier
                .padding(it)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit,
    value: String = "",
    enabled: Boolean = true,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {


    Surface(
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = label, fontSize = 24.sp)
            TextField(
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                trailingIcon = trailingIcon
            )
        }
    }
}

@Composable
fun ArticleForm(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    articleFormViewModel: ArticleFormViewModel = viewModel(factory = ArticleFormViewModel.Factory)
) {

    val title by articleFormViewModel.name.collectAsState()
    val price by articleFormViewModel.price.collectAsState()
    val description by articleFormViewModel.description.collectAsState()
    val categories by articleFormViewModel.categories.collectAsState()
    val category by articleFormViewModel.category.collectAsState()


    val context = LocalContext.current

    Column(modifier = modifier) {
        CustomTextField(
            label = stringResource(id = R.string.hello),
            value = title,
            onValueChange = {
                articleFormViewModel.setName(it)
            }
        )
        CustomTextField(
            label = "Description",
            value = description,
            onValueChange = {
                articleFormViewModel.setDescription(it)
            }
        )
        CustomTextField(
            label = "Prix",
            value = price,
            onValueChange = {
                articleFormViewModel.setPrice(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        DropDowMenuCategories(
            categories = categories,
            value = category,
            //lors du clic sur un item de la liste, je l'affiche dans le champs catégorie
            onMenuItemClick = {
                articleFormViewModel.setCategory(it)
            }
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                articleFormViewModel.addArticle()
                Toast.makeText(context, "L'article ${title} a été enregistré !", Toast.LENGTH_LONG)
                    .show()
                navHostController.navigate(EniShopArticleListDestination.route) {
                    launchSingleTop = true
                    popUpTo(navHostController.graph.findStartDestination().id)
                }
            }) {
                Text(text = "Enregistrer")
            }
        }

    }
}

@Composable
fun DropDowMenuCategories(
    value: String,
    categories: List<String>,
    onMenuItemClick: (String) -> Unit
) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        CustomTextField(
            label = "Catégorie",
            onValueChange = {},
            value = value,
            modifier = Modifier.clickable {
                expanded = !expanded
            },
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Down"
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(item.replaceFirstChar {
                            it.uppercase()
                        })
                    },
                    onClick = {
                        onMenuItemClick(item)
                        expanded = false
                    }
                )
            }

        }
    }
}

@Composable
@Preview
fun FormPreview() {
    //DropDowMenuCategories()
}