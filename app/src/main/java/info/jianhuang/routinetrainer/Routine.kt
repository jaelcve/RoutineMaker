package info.jianhuang.routinetrainer

data class Routine(val title: String, val description: String, val image: Int)

    fun getSampleRoutines(): List<Routine>{
        return listOf(
            Routine("Go for a walk",
                "nice walk to get ready for your day",
                R.drawable.walking),
            Routine("Drink water",
                "Drink water to get hydrated",
                R.drawable.water
            )
        )
}