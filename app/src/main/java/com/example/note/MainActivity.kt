package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.example.note.db.NoteDatabase
import com.example.note.db.NoteRepository
import com.example.note.navigation.NavItem
import com.example.note.navigation.Navigation

class MainActivity : ComponentActivity() {

    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NoteDatabase.getInstance(application).noteDao
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        setContent {
            Navigation(noteViewModel)
        }
    }

    companion object {
        var untitledCount = 0

        val items = listOf(
            NavItem(
                title = "List",
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List
            ),
            NavItem(
                title = "New Note",
                selectedIcon = Icons.Filled.Add,
                unselectedIcon = Icons.Outlined.Add
            )
        )
        val colors = mapOf(
            "OrangeRed" to Color(255,69,0),
            "Orange" to Color(255,165,0),
            "DarkOrange" to Color(255,140,0),

            "DeepSkyBlue" to Color(0,191,255),
            "DodgerBlue" to Color(30,144,255)

        )
    }
}
