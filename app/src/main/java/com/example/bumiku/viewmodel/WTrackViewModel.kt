package com.example.bumiku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bumiku.data.model.Task
import com.example.bumiku.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WTrackViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val data = repository.getTasksWithProgress()

            if (data.isEmpty()) {
                _errorMessage.value =
                    "Data task gagal dimuat. Periksa koneksi internet atau URL task.json."
            } else {
                _allTasks.value = data
            }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun getTaskById(taskId: Int): Task? {
        return _allTasks.value.find {
            it.id == taskId
        }
    }

    fun toggleTask(taskId: Int) {
        val task = _allTasks.value.find {
            it.id == taskId
        } ?: return

        updateTaskStatus(
            taskId = taskId,
            done = !task.isDone
        )
    }

    fun completeTask(
        taskId: Int,
        onSuccess: () -> Unit = {}
    ) {
        updateTaskStatus(
            taskId = taskId,
            done = true,
            onSuccess = onSuccess
        )
    }

    private fun updateTaskStatus(
        taskId: Int,
        done: Boolean,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isUpdating.value = true
            _errorMessage.value = null

            try {
                repository.saveTaskProgress(
                    taskId = taskId,
                    done = done
                )

                _allTasks.value = _allTasks.value.map { task ->
                    if (task.id == taskId) {
                        task.copy(isDone = done)
                    } else {
                        task
                    }
                }

                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Gagal menyimpan progress task."
            } finally {
                _isUpdating.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
