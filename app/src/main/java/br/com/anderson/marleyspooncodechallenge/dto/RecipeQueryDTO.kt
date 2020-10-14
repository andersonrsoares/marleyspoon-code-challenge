package br.com.anderson.marleyspooncodechallenge.dto

class RecipeQueryDTO(id: String) : QueryDTO(QUERY) {
    companion object {
        private val QUERY = "query recipe(\$id: String!){\n" +
            "  recipe(id: \$id){\n" +
            "\t\t\t\tchef{\n" +
            "          name\n" +
            "        }\n" +
            "        description\n" +
            "      \tsys{\n" +
            "          id\n" +
            "        }\n" +
            "      \ttitle,\n" +
            "      \tphoto{\n" +
            "          url\n" +
            "        }\n" +
            "      \ttagsCollection{\n" +
            "          items{\n" +
            "            name\n" +
            "          }\n" +
            "      }\n" +
            "    \n" +
            "  }\n" +
            "}"
    }

    init {
        setVariable("id", id)
    }
}
