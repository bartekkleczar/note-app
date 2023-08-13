package com.example.note.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.note.composables.*
import com.example.note.constraints.*
import com.example.note.MainActivity
import com.example.note.db.Note
import com.example.note.sharedPreferences.SharedPreferencesManager
import com.example.note.ui.theme.Grey
import com.example.note.ui.theme.GreyLight
import com.example.note.ui.theme.Sec_MintGreen
import com.example.note.ui.theme.Ter_Yellow

@Composable
fun Navigation(noteViewModel: NoteViewModel, context: Context) {
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
                    nullable = false
                },
                navArgument("content") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ) { entry ->
            DetailScreen(
                title = entry.arguments?.getString("title"),
                content = entry.arguments?.getString("content"),
                navController,
                noteViewModel,
                context
            )
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
            contentColor = GreyLight
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = Sec_MintGreen
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                     selectedIconColor = Color.Black,
                                     selectedTextColor = Color.Black,
                                     indicatorColor = Grey,
                                     unselectedIconColor= Grey,
                                     unselectedTextColor= Grey
                                ),
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
fun DetailScreen(
    title: String?,
    content: String?,
    navController: NavController,
    noteViewModel: NoteViewModel,
    context: Context
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        containerColor = Color.Transparent,
        disabledTextColor = Color.Black,
        cursorColor = Color.Gray,
        errorCursorColor = Color.Gray,
        selectionColors = TextSelectionColors(
            handleColor = Color(30, 144, 255),
            backgroundColor = Color(0, 191, 255)
        ),
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

    val sharedPreferencesManager = SharedPreferencesManager(context)

    val oldTitle = title

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
                NavigationBar(
                    containerColor = Sec_MintGreen
                ){
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                indicatorColor = Grey,
                                unselectedIconColor= Grey,
                                unselectedTextColor= Grey
                            ),
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index

                                val untitled = sharedPreferencesManager.getInt(MainActivity.untitledCount, 0)
                                if (title == null || title == " ") {
                                    title = "Untitled $untitled"
                                    sharedPreferencesManager.saveInt(
                                        MainActivity.untitledCount,
                                        untitled + 1
                                    )
                                }
                                when (oldTitle) {
                                    title -> {
                                        when (oldTitle) {
                                            "" -> {
                                                noteViewModel.insert(
                                                    Note(title ?: "Untitled $untitled", content ?: "")
                                                )
                                            }

                                            else -> {
                                                noteViewModel.update(
                                                    Note(title ?: "Untitled $untitled", content ?: "")
                                                )
                                            }
                                        }
                                    }

                                    else -> {
                                        noteViewModel.insert(
                                            Note(title ?: "Untitled $untitled", content ?: "")
                                        )
                                        oldTitle?.let {
                                            noteViewModel.delete(Note(oldTitle, content ?: ""))
                                        }
                                    }
                                }
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
        ){}
    }
    var inputTitle by remember { mutableStateOf("Input Title") }
    if(inputTitle != "" || inputTitle != " "){
        inputTitle = ""
    }
    Text(
        text = inputTitle,
        modifier = Modifier.padding(top = 25.dp, start = 15.dp),
        fontSize = 35.sp, fontWeight = FontWeight.Bold,
        color = Color.Black
        )
    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = title ?: "Err",
        colors = textFieldColors,
        textStyle = TextStyle(fontSize = 35.sp),
        onValueChange = {
            title = it
            inputTitle = when(it){
                "" -> "Input Title"
                " " -> "Input Title"
                else -> ""
            }
        }
    )

    var inputContent by remember { mutableStateOf("Input Content") }
    if(inputContent != "" || inputContent != " "){
        inputContent = ""
    }
    Text(
        text = inputContent,
        modifier = Modifier.padding(top = 85.dp, start = 15.dp),
        fontSize = 25.sp, fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    TextField(
        modifier = Modifier.padding(top = 70.dp),
        value = content ?: "Err",
        colors = textFieldColors,
        textStyle = TextStyle(fontSize = 25.sp),
        onValueChange = {
            content = it
            inputContent = when(it){
                ("") -> "Input Title"
                (" ") -> "Input Title"
                else -> ""
            }
        }
    )
}