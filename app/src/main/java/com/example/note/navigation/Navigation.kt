package com.example.note.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.note.db.Note

@Composable
fun Navigation(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController, noteViewModel)
        }
        composable(
            route = Screen.DetailScreen.route + "/{title}" + "/{content}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("content") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->
            DetailScreen(title = entry.arguments?.getString("title"), content = entry.arguments?.getString("content"), navController, noteViewModel)
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
                                    navController.navigate(Screen.DetailScreen.withArgs(" ", " "))
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
            navController = navController
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(title: String?, content: String?, navController: NavController, noteViewModel: NoteViewModel) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        containerColor = Color.Transparent,
        disabledTextColor = Color.Black,
        cursorColor = Color.Gray,
        errorCursorColor = Color.Gray,
        selectionColors = TextSelectionColors(handleColor = Color(30,144,255), backgroundColor = Color(0,191,255)),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Black,
        disabledIndicatorColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.Black,
        disabledLeadingIconColor = Color.Black,
        errorLeadingIconColor = Color.Black,
        focusedTrailingIconColor = Color.Black,
        unfocusedTrailingIconColor = Color.Black,
        disabledTrailingIconColor = Color.Black,
        errorTrailingIconColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        disabledLabelColor = Color.Black,
        errorLabelColor = Color.Black,
        placeholderColor = Color.Black,
        disabledPlaceholderColor = Color.Black,
        focusedSupportingTextColor = Color.Black,
        unfocusedSupportingTextColor = Color.Black,
        disabledSupportingTextColor = Color.Black,
        errorSupportingTextColor = Color.Black
    )

    var title by remember {
        mutableStateOf(title)
    }

    var content by remember {
        mutableStateOf(content)
    }

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

                                val untitled = MainActivity.untitledCount
                                if(title == null || title == " " ) {
                                    title = "Untitled $untitled"
                                    MainActivity.untitledCount++
                                }
                                noteViewModel.insert(Note(title ?: "Untitled $untitled", content ?: ""))


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

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = title ?: "Err",
        colors = textFieldColors,
        textStyle = TextStyle(fontSize = 35.sp),
        onValueChange = {
            title = it
        })

    TextField(
        modifier = Modifier.padding(top = 70.dp),
        value = content ?: "Err",
        colors = textFieldColors,
        textStyle = TextStyle(fontSize = 25.sp),
        onValueChange = {
            content = it
        })
}