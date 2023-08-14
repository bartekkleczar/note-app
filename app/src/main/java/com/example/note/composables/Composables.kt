package com.example.note.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.note.NoteViewModel
import com.example.note.db.Note
import com.example.note.navigation.Screen
import com.example.note.preview
import com.example.note.ui.theme.Grey
import com.example.note.ui.theme.Pri_White
import com.example.note.ui.theme.Sec_MintGreen

@Composable
fun Title(text: String, modifier: Modifier) {
    Row(
        modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            color = Color.Black
        )
    }
}

@Composable
fun Notes(modifier: Modifier, noteViewModel: NoteViewModel, navController: NavController) {
    val notesList by noteViewModel.Notes.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier.background(color = Color.Transparent, shape = RoundedCornerShape(15.dp))
    ) {
        items(notesList) { item ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.DetailScreen.withArgs(
                                item.title,
                                item.content
                            )
                        )
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column {
                        Text(
                            text = preview(item.title),
                            fontWeight = FontWeight.Bold, fontSize = 30.sp,
                            color = Color.Black
                        )
                        Text(
                            text = preview(item.content),
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                    Button(
                        onClick = { noteViewModel.delete(Note(item.title, item.content)) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Sec_MintGreen,
                            contentColor = Pri_White,
                            disabledContainerColor = Sec_MintGreen,
                            disabledContentColor = Pri_White
                        )
                        ) {
                        Text(
                            text = "DELETE",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}