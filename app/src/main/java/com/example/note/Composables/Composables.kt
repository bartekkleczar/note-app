package com.example.note.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.note.NoteViewModel
import com.example.note.db.Note
import com.example.note.navigation.Screen
import com.example.note.preview

@Composable
fun Title(text: String, modifier: Modifier) {
    Row(
        modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp
        )
    }
}

@Composable
fun Notes(modifier: Modifier, noteViewModel: NoteViewModel, navController: NavController) {
    //val notesList by noteViewModel.Notes.collectAsState(initial = emptyList())
    val notesList = listOf(Note("Dupa", "Podobno londyńska policja potrafi zawsze rozróżnić pośród wyławianych z Tamizy trupów ciała tych, którzy utopili się przez nieszczęśliwą miłość, od tych, którzy uczynili to z powodu długów. Kochanowie niemal zawsze mają pokaleczone palce od rozpaczliwego czepiania się filarów mostu. Za to dłużnicy idą na dno jak betonowe kloce, nie stawiając oporu i nie zmieniając w ostatniej chwili zdania."))
    LazyColumn(
        modifier = modifier
    ) {
        items(notesList) { item ->
            Column(
                Modifier.height(100.dp).fillMaxWidth(0.9f).clickable {
                    navController.navigate(Screen.DetailScreen.withArgs(item.title))
                }
            ){
                Text(
                    text = preview(item.title),
                    fontWeight = FontWeight.Bold, fontSize = 30.sp
                    )
                Text(
                    text = preview(item.content),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}