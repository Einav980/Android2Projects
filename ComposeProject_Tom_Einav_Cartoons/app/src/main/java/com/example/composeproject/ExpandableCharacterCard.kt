package com.example.composeproject

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.composeproject.ui.theme.Shapes
import com.example.composeproject.model.Character
import com.example.composeproject.model.CharacterType

@Composable
fun CharacterImage(url: String){
    Box(
        modifier = Modifier
            .height(128.dp)
            .width(128.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.LightGray)),
        contentAlignment = Alignment.Center
    ){
        val painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(300)
            }
        )
        val painterState = painter.state
        Image(painter = painter, contentDescription = "Image")
        if(painterState is ImagePainter.State.Loading){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CharacterCardText(text: String, weight: FontWeight, fontSize: TextUnit, padding: Dp = 0.dp, color: Color = Color.Black){
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = weight,
        color = color,
        modifier = Modifier
            .padding(PaddingValues(top = 5.dp))
    )
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCharacterCard(character: Character) {
    var expandableState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if(expandableState) 180f else 0f)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutLinearInEasing
                )
            )
            .padding(5.dp),
            shape = Shapes.medium,
            elevation = 5.dp,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        expandableState = !expandableState
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = character.name,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandableState = !expandableState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if(expandableState){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceAround

                    ){
                        CharacterImage(character.image1)
                        CharacterImage(character.image2)
                    }
                    CharacterCardText(text = character.description, weight = FontWeight.Normal, fontSize = 16.sp, color = Color.Gray)
                    CharacterCardText(text = character.dubber, weight = FontWeight.SemiBold, fontSize = 20.sp, padding = 5.dp)
                }
            }
            }
        }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun ExpandableCardPreview(){
    var character =  Character(
        0,
        "Robert Parr - Mr. Incredible",
        "Mr. Incredible is considered one of the most powerful Supers. During his early career, he was known for working alone, which was something that led him to push away Buddy Pine. He possesses the powers of enhanced strength and durability, as well as enhanced senses.",
        "Craig T.Nelson",
        "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-1uc5daw_96b0b8de_f68eb8aa.jpeg",
        "https://upload.wikimedia.org/wikipedia/en/2/22/Mr._Incredible.png",
        CharacterType.HUMAN
    )
    ExpandableCharacterCard(character = character)
}