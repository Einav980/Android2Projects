package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.data.Repository
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.model.Character
import com.example.composeproject.model.CharacterType
import com.example.composeproject.ui.theme.AppBarBlue
import com.example.composeproject.ui.theme.LightCyan

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var repository = Repository()
        var characters = repository.getAllCharacters()
        setContent {
            ComposeProjectTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CustomAppBar(text = "Cartoon Characters")
                    CharactersRecyclerView(characters = characters)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersRecyclerView(characters: ArrayList<Character>){
    val context = LocalContext.current
    LazyColumn{
        val groupedCharacters = characters.groupBy { it.section }
        groupedCharacters.forEach{ (section, sectionCharacters) ->
            stickyHeader {
                Text(
                    text = makeTitle(section.name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(LightCyan)
                        .fillMaxWidth()
                        .padding(5.dp)
                )
            }

            items(
                items = sectionCharacters,
                itemContent = {
                    ExpandableCharacterCard(character = it)
                }
            )
        }
    }
}

@Composable
fun CustomAppBar(text: String){
    TopAppBar(
        title = {
            Text(
                text = text,
                color = Color.White
            )
        },
        backgroundColor = AppBarBlue
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var characters = arrayListOf<Character>(
        Character(
            0,
            "Robert Parr - Mr. Incredible",
            "Mr. Incredible is considered one of the most powerful Supers. During his early career, he was known for working alone, which was something that led him to push away Buddy Pine. He possesses the powers of enhanced strength and durability, as well as enhanced senses.",
            "Craig T.Nelson",
            "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-1uc5daw_96b0b8de_f68eb8aa.jpeg",
            "https://upload.wikimedia.org/wikipedia/en/2/22/Mr._Incredible.png",
            CharacterType.HUMAN
        ),
        Character(
            1,
            "Helen Parr - Elastigirl",
            "Before marrying Mr. Incredible, Elastigirl seemed to have a strong opinion, stating that she both planned to break the \"glass ceiling\" (as Supers were a predominantly male profession) and would not settle down, at that time, in her firebrand ways as a married housewife. However, life had other plans for her as Mr. Incredible had managed to be so dazzling that she broke down and agreed to marry him. Shortly after their wedding, a chain reaction of events involving lawsuits results in Supers being outlawed by the U.S. government.",
            "Holly Hunter",
            "https://static.wikia.nocookie.net/disney/images/3/3a/Profile_-_Helen_Parr.jpeg/revision/latest/scale-to-width-down/516?cb=20200205215542",
            "https://static.wikia.nocookie.net/the-incredibles/images/c/c4/Elastigirl_Transparent.png/revision/latest?cb=20220216212432",
            CharacterType.HUMAN
        )
    )
    ComposeProjectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomAppBar(text = "Cartoon Characters")
            CharactersRecyclerView(characters = characters)
        }
    }
}

fun makeTitle(text: String): String{
    return text.replace("_", " ").toLowerCase().capitalize()
}