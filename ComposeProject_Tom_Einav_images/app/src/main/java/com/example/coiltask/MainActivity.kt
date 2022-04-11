package com.example.coiltask

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.State
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.coiltask.model.CustomImage
import com.example.coiltask.ui.theme.CoilCardColor
import com.example.coiltask.ui.theme.CoilTaskTheme
import com.example.coiltask.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val images = arrayListOf<CustomImage>(
            CustomImage(
                "Red Tree",
                "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
            ),
            CustomImage(
                "Sunset",
                "https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q="
            ),
            CustomImage(
                "Error Image",
                "aaa"
            ),
            CustomImage(
                "Wolf",
                "https://images.theconversation.com/files/27934/original/k7ntgp6q-1374594697.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1200&h=900.0&fit=crop"
            ),
            CustomImage(
                "Puppy",
                "https://www.akc.org/wp-content/uploads/2017/11/Golden-Retriever-Puppy.jpg"
            ),
            CustomImage(
                "Eye",
                "https://media.springernature.com/w735h400/nature-cms/uploads/cms/pages/7531/top_item_image/Eye_hero-1a07d0e7d224bdb3db50da7eb6f6d53c.jpg"
            ),
            CustomImage(
                "Desert",
                "https://th-thumbnailer.cdn-si-edu.com/h-bun2rAGQCTzVJvCy-fJPZB-wE=/fit-in/1600x0/https://tf-cmsv2-smithsonianmag-media.s3.amazonaws.com/filer/f2/94/f294516b-db3d-4f7b-9a60-ca3cd5f3d9b2/fbby1h_1.jpg"
            )
        )


        setContent {
            CoilTaskTheme {
                // A surface container using the 'background' color from the theme
                LazyColumn( modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    items(images) { image ->
                        CoilCard(image = image)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun CoilCard(image: CustomImage) {
    val painter = rememberImagePainter(data = image.url,
        builder = {
            error(R.drawable.error)
            crossfade(500)
        })
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        backgroundColor = CoilCardColor,
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        val painterState = painter.state
        Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.weight(4f).fillMaxWidth() , contentAlignment = Alignment.Center){
                Image(painter = painter, contentDescription = "image",contentScale = ContentScale.Fit , modifier = Modifier.fillMaxSize())
                if(painterState is AsyncImagePainter.State.Loading){
                    CircularProgressIndicator(strokeWidth = 3.dp)
                }
            }
            Box(Modifier.weight(2f).fillMaxWidth(), contentAlignment = Alignment.Center){
                Text(
                    text = image.name,
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoilTaskTheme {
    }
}
