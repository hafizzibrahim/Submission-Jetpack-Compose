package com.example.jetcodeapp.data

import com.example.jetcodeapp.model.CodeData
import com.example.jetcodeapp.model.CodeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CodeRepository {

    private val codeItem = mutableListOf<CodeItem>()
    private val favoriteCode = mutableListOf<String>()

    init {
        if (codeItem.isEmpty()) {
            CodeData.code.forEach {
                codeItem.add(CodeItem(it, 0))
            }
        }
    }

    fun getSortedAndGroupedCode(): Flow<Map<Char, List<CodeItem>>> {
        return flow {
            val sortedCode = codeItem.sortedBy { it.item.codeName }
            val groupedCodes = sortedCode.groupBy { it.item.codeName[0] }
            emit(groupedCodes)
        }
    }

    fun getCodeItemById(codeId: String): CodeItem {
        return codeItem.first {
            it.item.id == codeId
        }
    }

    fun searchCodes(query: String): Flow<List<CodeItem>> {
        return flow {
            val filteredCodes= codeItem.filter {
                it.item.codeName.contains(query, ignoreCase = true)
            }
            emit(filteredCodes)
        }
    }

    fun getFavoriteCodes(): Flow<List<CodeItem>> {
        return flow {
            val favoriteCodeItems = codeItem.filter { it.item.id in favoriteCode }
            emit(favoriteCodeItems)
        }
    }

    fun addToFavorites(codeId: String) {
        if (!favoriteCode.contains(codeId)) {
            favoriteCode.add(codeId)
        }
    }

    fun removeFromFavorites(codeId: String) {
        favoriteCode.remove(codeId)
    }

    fun isFavorite(codeId: String): Boolean {
        return favoriteCode.contains(codeId)
    }

    companion object {
        @Volatile
        private var instance: CodeRepository? = null

        fun getInstance(): CodeRepository = instance ?: synchronized(this) {
            CodeRepository().apply {
                instance = this
            }
        }
    }
}