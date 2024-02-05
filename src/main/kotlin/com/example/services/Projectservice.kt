package com.example.services

import com.example.database.repository.ProjectRepository
import com.example.model.Project
import com.example.model.callModel.cProject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import org.bson.types.ObjectId

class Projectservice() {
   private val projectrepository = ProjectRepository()
    suspend fun addProject(cproject : cProject) : String
    {
        val project = Project(
            Id = ObjectId().toString(),
            Name = cproject.name,
            Author = cproject.author
        )
       return projectrepository.addproject(project)
    }

    suspend fun deleteProject(projectid : String) : Long
    {
       return projectrepository.deleteproject(projectid)
    }

    suspend fun updateProject(projecttoupdate : Project) : Long
    {
        return  projectrepository.updateproject(projecttoupdate)
    }

   suspend fun getAllProject() : List<Project>
    {
        return projectrepository.getAllProject()
    }
}