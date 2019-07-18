package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if (!question.validate(answer)) {
            return "${question.invalidCause}\n${question.question}" to status.color
        }

        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            when (status) {
                Status.CRITICAL -> {
                    status = Status.NORMAL
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                }
                else -> {
                    status = status.nextStatus()
                    "Это неправильный ответ\n${question.question}" to status.color
                }
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status = values()[(this.ordinal + 1) % values().size]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion() = PROFESSION

            override val invalidCause: String
                get() = "Имя должно начинаться с заглавной буквы"

            override fun validate(answer: String): Boolean {
                return if (answer.isNotEmpty()) answer[0].isUpperCase() else false
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion() = MATERIAL

            override val invalidCause: String
                get() = "Профессия должна начинаться со строчной буквы"

            override fun validate(answer: String): Boolean {
                return if (answer.isNotEmpty()) answer[0].isLowerCase() else false
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion() = BDAY

            override val invalidCause: String
                get() = "Материал не должен содержать цифр"

            override fun validate(answer: String): Boolean {
                return !answer.contains(Regex("""[0-9]"""))
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion() = SERIAL

            override val invalidCause: String
                get() = "Год моего рождения должен содержать только цифры"

            override fun validate(answer: String): Boolean {
                return !answer.contains(Regex("""[^0-9]"""))
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion() = IDLE

            override val invalidCause: String
                get() = "Серийный номер содержит только цифры, и их 7"

            override fun validate(answer: String): Boolean {
                return !answer.contains(Regex("""[^0-9]""")) && answer.length == 7
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion() = IDLE

            override val invalidCause: String
                get() = ""

            override fun validate(answer: String): Boolean = true
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): Boolean
        abstract val invalidCause: String
    }


}