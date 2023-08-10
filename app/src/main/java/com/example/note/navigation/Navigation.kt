package com.example.note.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.note.NoteViewModel
import com.example.note.Composables.*
import com.example.note.Constraints.*
import com.example.note.MainActivity

@Composable
fun Navigation(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController, noteViewModel)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Err"
                    nullable = true
                }
            )
        ) { entry ->
            DetailScreen(name = entry.arguments?.getString("name"), navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val items = MainActivity.items
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.layoutId("surface"),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(Screen.DetailScreen.withArgs(" "))
                                },
                                label = {
                                    Text(text = item.title)
                                },
                                alwaysShowLabel = true, // wybrana ikona sie podnosi
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            )
                        }
                    }
                }
            )
            {

            }
        }
        val pad = 15.dp
        Title(
            text = "Notes",
            modifier = Modifier
                .padding(start = pad, top = pad, end = pad)
                .layoutId("title")
        )
        Notes(
            modifier = Modifier
                .layoutId("column")
                .padding(start = pad, top = pad, end = pad),
            noteViewModel = noteViewModel,
            navController
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(name: String?, navController: NavController) {
    val items = MainActivity.items
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(1)
    }
    Surface(
        modifier = Modifier.layoutId("surface"),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                when (selectedItemIndex) {
                                    0 -> navController.navigate(Screen.MainScreen.route)
                                    1 -> navController.navigate(Screen.DetailScreen.withArgs(" "))
                                }
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = true, // wybrana ikona sie podnosi
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            }
        )
        {

        }
    }

    val pad = 15.dp
    Title(
        text = name ?: "Err",
        modifier = Modifier
            .padding(start = pad, top = pad, end = pad)
            .layoutId("title")
    )
}