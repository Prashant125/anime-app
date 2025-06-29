package dev.prashant.anime.presentation.characters.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.prashant.anime.domain.models.Characters

@Composable
fun CharacterListItem(modifier: Modifier = Modifier, character: Characters,onClick:(Int) -> Unit) {
    Column(
        modifier = modifier
            .clickable {  onClick(character.id) }
            .padding(8.dp)
            .fillMaxWidth()
            .height(350.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(270.dp),
            contentScale = ContentScale.FillHeight
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListItemPreview(modifier: Modifier = Modifier) {
    CharacterListItem(modifier = modifier,
        character = Characters(
            id = 12,
            image = "",
            name = "prash"
        ),{

        }
    )
}