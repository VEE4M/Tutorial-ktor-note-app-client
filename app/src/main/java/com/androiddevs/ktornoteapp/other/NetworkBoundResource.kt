package com.androiddevs.ktornoteapp.other

import kotlinx.coroutines.flow.*
import java.lang.Exception

/**
 *Veli-Matti Tikkanen, 3.7.2021
 */


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    emit(Resource.loading(null))
    val data = query().first()
    val flow = if(shouldFetch(data)){
        emit(Resource.loading(data))
        try{
            val fetchedResult = fetch()
            saveFetchResult(fetchedResult)
            query().map { Resource.success(it) }
        }catch (t: Throwable){
            onFetchFailed(t)
            query().map {
                Resource.error("Couldn't reach srever. It might be down", it)
            }
        }
    }else{
        query().map { Resource.success(it) }
    }
    emitAll(flow)
}
