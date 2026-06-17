package com.example.bumiku.data.repository

import com.example.bumiku.data.api.RetrofitClientTask
import com.example.bumiku.data.firebase.FirebaseTaskService
import com.example.bumiku.data.model.Task

class TaskRepository(
    private val firebaseTaskService: FirebaseTaskService = FirebaseTaskService()
) {

    suspend fun getTasks(): List<Task> {
        return try {
            RetrofitClientTask.instance.getTasks()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTasksWithProgress(): List<Task> {
        val tasksFromGist = try {
            RetrofitClientTask.instance.getTasks()
        } catch (e: Exception) {
            emptyList()
        }

        if (tasksFromGist.isEmpty()) {
            return emptyList()
        }

        val doneTaskIds = try {
            firebaseTaskService.getDoneTaskIds()
        } catch (e: Exception) {
            emptyList()
        }

        return tasksFromGist.map { task ->
            task.copy(
                isDone = doneTaskIds.contains(task.id)
            )
        }
    }

    suspend fun saveTaskProgress(
        taskId: Int,
        done: Boolean
    ) {
        firebaseTaskService.saveTaskProgress(
            taskId = taskId,
            done = done
        )
    }
}