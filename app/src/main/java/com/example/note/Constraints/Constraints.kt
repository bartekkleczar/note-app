package com.example.note.Constraints

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet

val constraints = ConstraintSet {
    val title = createRefFor("title")
    val column = createRefFor("column")
    val surface = createRefFor("surface")

    val topLine = createGuidelineFromTop(0.dp)
    val contentLine = createGuidelineFromTop(200.dp)

    constrain(title) {
        top.linkTo(topLine)
        start.linkTo(parent.start)
    }
    constrain(column) {
        top.linkTo(title.bottom)
        start.linkTo(parent.start)
    }
    constrain(surface) {
        bottom.linkTo(parent.bottom)
    }
}