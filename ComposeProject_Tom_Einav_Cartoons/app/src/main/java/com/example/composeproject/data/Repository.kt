package com.example.composeproject.data

import kotlin.collections.ArrayList
import com.example.composeproject.model.Character
import com.example.composeproject.model.CharacterType

class Repository {
    fun getAllCharacters(): ArrayList<Character>{
        return arrayListOf(
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
            ),
            Character(
                2,
                "Spongebob",
                "SpongeBob SquarePants (born July 14, 1986[8]) is the one of the ten main characters of the animated franchise of the same name. He was designed by the former marine biologist, Stephen Hillenburg. Hillenburg based SpongeBob on Bob the Sponge, a character he had created for his educational book \"The Intertidal Zone\" in the late 1980s.",
                "Tom Kenny",
                "https://static.wikia.nocookie.net/spongebob/images/d/d7/SpongeBob_stock_art.png/revision/latest/scale-to-width-down/350?cb=20190921125147",
                "https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/SpongeBob_SquarePants_character.svg/640px-SpongeBob_SquarePants_character.svg.png",
                CharacterType.NON_HUMAN
            ),
            Character(
                3,
                "Scooby-Doo",
                "Scooby-Doo is the eponymous character and protagonist of the animated television franchise of the same name, created in 1969 by the American animation company Hanna-Barbera.[1] He is a male Great Dane and lifelong companion of amateur detective Shaggy Rogers, with whom he shares many personality traits. He features a mix of both canine and human behaviors (reminiscent of other talking animals in Hanna-Barbera's series), and is treated by his friends more or less as an equal. He speaks in a slurred, dog-like voice. His catchphrase is \"Scooby-Dooby-Doo!\"",
                "Scott Innes",
                "https://upload.wikimedia.org/wikipedia/en/5/53/Scooby-Doo.png",
                "https://www.giantfreakinrobot.com/wp-content/uploads/2021/12/scoob.jpeg",
                CharacterType.ANIMAL
            ),
            Character(
                4,
                "Shrek",
                "Shrek is an anti-social and highly-territorial green ogre who loves the solitude of his swamp. His life is interrupted after the dwarfish Lord Farquaad of Duloc unknowingly exiles a vast number of fairy-tale creatures to Shrek's swamp. Angered by the intrusion, he decides to visit Farquaad and demand they be moved elsewhere. He reluctantly allows the talkative Donkey, who was exiled as well, to tag along and guide him to Duloc.",
                "Mike Myers",
                "https://static.wikia.nocookie.net/supermarioglitchy4/images/6/6a/ShrekTheSaviour.png/revision/latest?cb=20210420125208",
                "https://www.pacificlicensing.com/sites/default/files/brand/shrek.jpg",
                CharacterType.NON_HUMAN
            ),
            Character(
                5,
                "Oogway",
                "Master Oogway was an elderly tortoise and the previous senior master of the Jade Palace. He is credited as the founder of the Valley of Peace, the creator of kung fu, and the developer of the Dragon Warrior legend.",
                "Randall Duk Kim",
                "https://static.wikia.nocookie.net/kungfupanda/images/2/2e/Oogway-white.png/revision/latest/scale-to-width-down/350?cb=20160326153345",
                "https://i1.sndcdn.com/artworks-000233445481-u6h82e-t500x500.jpg",
                CharacterType.ANIMAL
            )
        )
    }
}