package com.example.database.repository

import com.example.database.connection.Connection
import com.example.model.Project
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson
import org.bson.types.ObjectId

interface ProjectInterface {
    suspend fun addproject(project: Project) : String
    suspend fun deleteproject(projectid : String): Long
    suspend fun updateproject(projecttoupdate : Project):Long
    suspend fun getAllProject() : List<Project>
}

class ProjectRepository :ProjectInterface{
    lateinit var query : Bson
    lateinit var updates: Bson
    lateinit var options : UpdateOptions
    val database = Connection.getDatabase()
    val collection = database.getCollection<Project>(collectionName = "project")


    override suspend fun addproject(project: Project) : String {
        collection.insertOne(project).also {
            return "inserted id ${it.insertedId}"
        }
    }

    override suspend fun deleteproject(projectid: String) : Long{
        query = Filters.eq(Project::Id.name, projectid)
        collection.deleteOne(query).also {
            return it.deletedCount
        }
    }

    override suspend fun updateproject(projecttoupdate: Project)  : Long{

        query = Filters.eq(Project::Id.name, projecttoupdate.Id)
        updates = Updates.combine(
            Updates.set(Project::Name.name,projecttoupdate.Name),
            Updates.set(Project::Author.name,projecttoupdate.Author),
            Updates.set(Project::Members.name,projecttoupdate.Members),
            Updates.set(Project::Ticketlists.name,projecttoupdate.Ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }

    }

    override suspend fun getAllProject(): List<Project> {
        return collection.find().toList()
    }




}