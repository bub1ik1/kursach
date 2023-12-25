package com.memksim.gladchenko

import android.util.Log
import com.memksim.gladchenko.data.Company
import com.memksim.gladchenko.data.HeadHunter
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.data.HhDao
import com.memksim.gladchenko.data.Vacancy
import javax.inject.Inject

class DatabaseManager @Inject constructor(
    private val dao: HhDao
) {
    suspend fun getJobSeekers() = dao.fetchJobSeekers()

    suspend fun getJobSeekerById(id: Int) = dao.fetchJobSeekerById(id)

    suspend fun saveNewJobSeeker(jobSeeker: JobSeeker) = dao.insertJobSeeker(jobSeeker)

    suspend fun deleteJobSeeker(jobSeeker: JobSeeker) = dao.deleteJobSeeker(jobSeeker)

    suspend fun getVacancies() = dao.fetchVacancies()

    suspend fun getVacanciesByCompany(companyId: Int) = dao.fetchVacanciesByCompanyId(companyId)

    suspend fun getCompanies() = dao.fetchCompanies()

    suspend fun getHeadHunters() = dao.fetchHeadHunters()

    suspend fun saveNewCompany(company: Company) = dao.insertCompany(company)

    suspend fun deleteCompany(company: Company) = dao.deleteCompany(company)

    suspend fun getHeadHuntersByCompany(companyId: Int) =
        dao.fetchHeadHuntersByCompanyId(companyId)

    suspend fun saveNewVacancy(vacancy: Vacancy) = dao.insertVacancy(vacancy)

    suspend fun saveNewHeadHunter(headHunter: HeadHunter) = dao.insertHeadHunter(headHunter)

    suspend fun getHeadHunterById(headHunterId: Int) = dao.fetchHeadHunterById(headHunterId)

    suspend fun getVacancyById(vacancyId: Int) = dao.fetchVacancyById(vacancyId)

    suspend fun deleteVacancy(vacancy: Vacancy) = dao.deleteVacancy(vacancy)

    suspend fun getCompanyById(companyId: Int) = dao.fetchCompanyById(companyId)

    suspend fun getVacanciesBySkills(jobSeekerSkills: List<String>): List<Vacancy> {
        val matchedVacancies = mutableListOf<Vacancy>()
        val vacancies = dao.fetchVacancies()
        Log.d("CHMO", "job seeker skills: $jobSeekerSkills")
        vacancies.forEach { vacancy ->
            val requiredSkills = vacancy.skills.lowercase().split(' ')
            Log.d("CHMO", "requiredSkills: $requiredSkills")
            var matchesCounter = 0
            requiredSkills.forEach { skill ->
                Log.d("CHMO", "skill: $skill, contains: ${jobSeekerSkills.contains(skill)}")

                if (jobSeekerSkills.contains(skill)) matchesCounter++
            }
            if (matchesCounter >= 1) matchedVacancies.add(vacancy)
            Log.d("CHMO", "--step: $matchedVacancies")

        }
        return matchedVacancies
    }

}