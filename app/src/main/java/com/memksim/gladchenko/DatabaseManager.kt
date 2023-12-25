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
        //Подходящие вакансии
        val matchedVacancies = mutableListOf<Vacancy>()
        //все вакансии
        val vacancies = dao.fetchVacancies()
        //проход по списку всех вакансий
        vacancies.forEach { vacancy ->
            //навыки, необходимые для вакансии
            //Преобразуем строку с навыками в массив навыков. Делим по пробелу
            val requiredSkills = vacancy.skills.lowercase().split(' ')
            //совпадения по навыкам счетчик
            var matchesCounter = 0
            //проход по необходимым навыкам
            requiredSkills.forEach { skill ->
                //Если в навыках пользователя есть необходимый навык то прибавляем 1 к счетчику
                if (jobSeekerSkills.contains(skill)) matchesCounter++
            }
            // Если счетчик больше 1 то добавляем вакансию в подходящие
            if (matchesCounter >= 1) matchedVacancies.add(vacancy)

        }
        return matchedVacancies
    }

}