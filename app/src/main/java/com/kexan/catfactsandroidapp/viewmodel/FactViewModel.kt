package com.kexan.catfactsandroidapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kexan.catfactsandroidapp.dto.Fact
import com.kexan.catfactsandroidapp.repository.FactRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import repository.FactRepository
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class FactViewModel @Inject constructor(
    private val repository: FactRepository
) : ViewModel() {

    private val cached = repository
        .data
        .cachedIn(viewModelScope)

    val data: Flow<PagingData<Fact>> = cached

    var currentFact = Fact()

    suspend fun getRandomFact(amount: Int) = viewModelScope.launch {
        repository.getRandomFact(amount)
    }

    suspend fun getFactById(_id: String) = viewModelScope.launch {
        repository.getFactById(_id)
    }

    fun holdFact(fact: Fact) {
        currentFact = fact
    }
}