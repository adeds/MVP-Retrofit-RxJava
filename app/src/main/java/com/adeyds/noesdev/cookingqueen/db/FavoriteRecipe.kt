package com.adeyds.noesdev.cookingqueen.db

data class FavoriteRecipe(val thumbnail: String?
                          , val ingredients: String?
                          , val href: String?
                          , val title : String?
) {

    companion object {
        const val DB_NAME: String = "Favorite.db"
        const val TABLE_FAVORITE_RESEP: String = "TABLE_RESEP"
        const val TITLE: String = "TITLE"
        const val HREF: String = "HREF"
        const val INGREDIENT: String = "INGREDIENT"
        const val THUMBNAIL: String = "THUMBNAIL"
    }
}