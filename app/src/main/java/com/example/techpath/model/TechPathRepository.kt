package com.example.techpath.model

import com.example.techpath.gemini.generativeModel

object TechPathRepository {
    suspend fun generateCareerPath(
        areaOfInterest: String,
        skillsAlreadyHave: String,
        currentEducationLevel: String,
        priorWorkExperience: String,
        workEnvironment: String,
        learningPreference: String
    ): String {
        return generativeModel(
            "Give me a career pathway to become $areaOfInterest," +
                    "The skills I already have are $skillsAlreadyHave," +
                    "I am currently pursuing $currentEducationLevel," +
                    "My prior work experience is $priorWorkExperience," +
                    "The work environment I prefer is $workEnvironment," +
                    "I prefer to learn through $learningPreference"

        )
    }
}