package com.memksim.gladchenko.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HhDao {

    @Insert
    suspend fun insertVacancy(vacancy: Vacancy)

    @Insert
    suspend fun insertCompany(company: Company)

    @Insert
    suspend fun insertHeadHunter(headHunter: HeadHunter)

    @Insert
    suspend fun insertJobSeeker(jobSeeker: JobSeeker)

    @Update
    suspend fun updateVacancy(vacancy: Vacancy)

    @Update
    suspend fun updateCompany(company: Company)

    @Update
    suspend fun updateHeadHunter(headHunter: HeadHunter)

    @Update
    suspend fun updateJobSeeker(jobSeeker: JobSeeker)

    @Delete
    suspend fun deleteVacancy(vacancy: Vacancy)

    @Delete
    suspend fun deleteCompany(company: Company)

    @Delete
    suspend fun deleteHeadHunter(headHunter: HeadHunter)

    @Delete
    suspend fun deleteJobSeeker(jobSeeker: JobSeeker)

    @Query("SELECT * FROM vacancy")
    suspend fun fetchVacancies(): List<Vacancy>

    @Query("SELECT * FROM vacancy WHERE id = :vacancyId")
    suspend fun fetchVacancyById(vacancyId: Int): Vacancy

    @Query("SELECT * FROM vacancy WHERE companyId = :companyId")
    suspend fun fetchVacanciesByCompanyId(companyId: Int): List<Vacancy>

    @Query("SELECT * FROM vacancy WHERE hunterId = :headHunterId")
    suspend fun fetchVacanciesByHeadHunterId(headHunterId: Int): List<Vacancy>

    @Query("SELECT * FROM company")
    suspend fun fetchCompanies(): List<Company>

    @Query("SELECT * FROM company WHERE id = :companyId")
    suspend fun fetchCompanyById(companyId: Int): Company

    @Query("SELECT * FROM head_hunter")
    suspend fun fetchHeadHunters(): List<HeadHunter>

    @Query("SELECT * FROM head_hunter WHERE id = :headHunterId")
    suspend fun fetchHeadHunterById(headHunterId: Int): HeadHunter

    @Query("SELECT * FROM head_hunter WHERE companyId = :companyId")
    suspend fun fetchHeadHuntersByCompanyId(companyId: Int): List<HeadHunter>

    @Query("SELECT * FROM job_seeker")
    suspend fun fetchJobSeekers(): List<JobSeeker>

    @Query("SELECT * FROM job_seeker WHERE id = :jobSeekerId")
    suspend fun fetchJobSeekerById(jobSeekerId: Int): JobSeeker

}