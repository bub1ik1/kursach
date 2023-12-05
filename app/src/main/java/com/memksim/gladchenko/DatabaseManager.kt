package com.memksim.gladchenko

import com.memksim.cursach.data.Company
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.data.HhDao
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

}