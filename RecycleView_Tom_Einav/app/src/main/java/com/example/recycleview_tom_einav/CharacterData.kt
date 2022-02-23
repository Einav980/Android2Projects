package com.example.recycleview_tom_einav

class CharacterData( newName :String , newVersion: String , newId: Int , newImage: Int){

    private var name : String
    private var version : String
    private var id : Int
    private var image : Int


    init{
        name = newName
        version =newVersion
        id= newId
        image = newImage
    }

    // getter setter name
    fun getName (): String {
        return name
    }
    fun setName (newName : String) {
        name = newName
    }

    // getter setter version
    fun getVersion (): String {
        return version
    }
    fun setVersion (newVersion : String) {
        version = newVersion
    }

    // getter setter id
    fun getId (): Int? {
        return id
    }
    fun setId (newId : Int) {
        id = newId
    }

    // getter setter image
    fun getImage() : Int? {
        return  image
    }
    fun setImage(newImage : Int){
        image = newImage
    }




}