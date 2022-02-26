package com.example.recyclerview.data

import com.example.recyclerview.models.Character

class DataSource {
    companion object {
        fun createDataset(): ArrayList<Character>{
            val list = ArrayList<Character>();

            list.add(
                Character(
                    0,
                    "Robert Parr - Mr. Incredible",
                    "Mr. Incredible is considered one of the most powerful Supers. During his early career, he was known for working alone, which was something that led him to push away Buddy Pine. He possesses the powers of enhanced strength and durability, as well as enhanced senses.",
                    "Craig T.Nelson",
                    "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-1uc5daw_96b0b8de_f68eb8aa.jpeg",
                    "https://upload.wikimedia.org/wikipedia/en/2/22/Mr._Incredible.png"
                )
            )
            list.add(
                Character(
                    1,
                    "Helen Parr - Elastigirl",
                    "Before marrying Mr. Incredible, Elastigirl seemed to have a strong opinion, stating that she both planned to break the \"glass ceiling\" (as Supers were a predominantly male profession) and would not settle down, at that time, in her firebrand ways as a married housewife. However, life had other plans for her as Mr. Incredible had managed to be so dazzling that she broke down and agreed to marry him. Shortly after their wedding, a chain reaction of events involving lawsuits results in Supers being outlawed by the U.S. government.",
                    "Holly Hunter",
                    "https://static.wikia.nocookie.net/disney/images/3/3a/Profile_-_Helen_Parr.jpeg/revision/latest/scale-to-width-down/516?cb=20200205215542",
                    "https://static.wikia.nocookie.net/the-incredibles/images/c/c4/Elastigirl_Transparent.png/revision/latest?cb=20220216212432"
                )
            )
            list.add(
                Character(
                    2,
                    "Dash Parr",
                    "Being the child of the family, Dash is, as described by Helen, \"a highly competitive boy, and a bit of a showoff\". Due to society rejecting Supers, he struggles to find a way to vent his energy, not being allowed to partake in sports as it could expose their identities and it would be unfair for other kids. As a result, he took to using his abilities to throw pranks without getting caught to test his limits, causing him to become somewhat of a problem child.",
                    "Spencer Fox",
                    "https://static.wikia.nocookie.net/disney/images/5/5e/Profile_-_Dash_Parr.jpeg/revision/latest/scale-to-width-down/516?cb=20190313155501",
                    "https://static.wikia.nocookie.net/pixar/images/a/a5/I2_-_Dash.png/revision/latest/top-crop/width/360/height/360?cb=20180621012105"
                )
            )
            list.add(
                Character(
                    3,
                    "Violet Parr",
                    "When Violet is first introduced, she is depicted as a gloomy, uncertain, overwhelmed, shy, and socially withdrawn girl who has a few self-esteem issues - preferring to hide behind her long hair, which seems to be reflected in her powers. She has a crush on one of her schoolmates named Tony Rydinger, but she is too shy to approach him and becomes invisible whenever he looks her way to avoid attention.",
                    "Sarah Vowell",
                    "https://static.wikia.nocookie.net/disney/images/e/e0/Profile_-_Violet_Parr.jpeg/revision/latest/scale-to-width-down/515?cb=20190313155232",
                    "https://static.wikia.nocookie.net/pixar/images/b/bc/I2_-_Violet_3.jpg/revision/latest?cb=20200929061711"
                )
            )
            list.add(
                Character(
                    4,
                    "Frozone",
                    "Frozone wields control over ice. He can generate ice from his fingertips but is limited by the amount of moisture in the air and his body. He has made water and milk solid and can freeze bullets in midair while congealing gun-wielding opponents. As a child, he was able to freeze a bowl of fruit punch merely through force of will, without the use of his usual ice blasts. He has limited airborne mobility, not necessarily complete levitation, but he can quickly maneuver along coasters of midair icicles (which deteriorate as he goes) which boost him in the air a good hundred or more feet. He can also take large bounds and leaps when in the air. In the movie, he skated across the air and hitchhiked onto a helicopter because of this.",
                    "Samuel L. Jackson",
                    "https://static.wikia.nocookie.net/disney/images/5/5c/Profile_-_Frozone.jpg/revision/latest/scale-to-width-down/516?cb=20211114020752",
                    "https://static.wikia.nocookie.net/pixar/images/e/ed/I2_-_Frozone_2.png/revision/latest?cb=20180621022456"
                )
            )
            list.add(
                Character(
                    5,
                    "Syndrome",
                    "As a young Buddy Pine, the boy who would become Syndrome aspired to become a superhero and this goal led him to beg Mr. Incredible to hire him as a sidekick IncrediBoy. Sadly, after Bob categorically and justifiably refused to grant Buddy's favor, Buddy returned home in disgrace and rejected the righteous path. He had stopped idolizing Mr. Incredible and became embittered, eventually descending into megalomania. Based on his memories of the events in flashbacks and the fact he did not remember Bomb Voyage's involvement in the incident, or that he (Buddy) caused the train accident as a result of his carelessness, Buddy only cared about fighting and beating up bad guys rather than actually protecting people. As a result, it's likely he was never truly hero material to begin with as he cared more about the glory rather than doing actual good deeds to make a difference.",
                    "Jason Lee",
                    "https://static.wikia.nocookie.net/disney/images/9/96/Profile_-_Syndrome.png/revision/latest/scale-to-width-down/516?cb=20200926220437",
                    "https://static.wikia.nocookie.net/pixar/images/b/b3/Syndrome.png/revision/latest?cb=20150215211805"
                )
            )
            list.add(
                Character(
                    6,
                    "Jack-Jack Parr",
                    "Jack-Jack acts just like a typical baby. He laughs and giggles a lot but cries whenever he is disturbed. Like many infants, Jack-Jack's emotions are unpredictable; and since his powers are stimulated by them, being with him when the baby uses his powers becomes very difficult when attempting to pacify or restrain him. For instance, Jack-Jack loves Mozart's music to the point where he is stimulated by it. He also becomes enraged if he is given a cookie and is not allowed more. One of the few things that can calm him down is his mother and the rest of his family.",
                    "Eli Fucile",
                    "https://static.wikia.nocookie.net/disney/images/2/28/Profile_-_Jack_Jack_Parr.jpeg/revision/latest/scale-to-width-down/516?cb=20190315053420",
                    "https://i.pinimg.com/originals/84/4b/a9/844ba972e3f60f3b2a56ee77db1de759.png"
                )
            )

            return list;
        }
    }
}