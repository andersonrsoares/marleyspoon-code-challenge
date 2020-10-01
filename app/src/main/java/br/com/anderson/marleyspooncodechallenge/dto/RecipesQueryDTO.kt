package br.com.anderson.marleyspooncodechallenge.dto


class RecipesQueryDTO(skip:Int = 0, limit:Int = 100) : QueryDTO(QUERY) {
    companion object {
        private val QUERY = "query RecipeCollection(\$skip: Int!, \$limit: Int!){\n" +
                "  recipeCollection(skip: \$skip,limit:\$limit){\n" +
                "    total,\n" +
                "    items{\n" +
                "      \tsys{\n" +
                "          id\n" +
                "        }\n" +
                "      \ttitle,\n" +
                "      \tphoto{\n" +
                "          url\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "}"
    }

    init {
        setVariable("skip",skip)
        setVariable("limit", limit)
    }
}