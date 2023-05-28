package com.bts.mypaint.di

import com.bts.mypaint.data.database.MyPaintDatabase
import com.bts.mypaint.data.repository.AuthRepoImpl
import com.bts.mypaint.data.repository.AuthRepository
import com.bts.mypaint.data.repository.ConnectionToServer
import com.bts.mypaint.data.repository.databaseReppo.DbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepoImpl(get(), get(), get()) }
    single { ConnectionToServer(get()) }
    single { DbRepository(get()) }
    single { MyPaintDatabase.getMyPaintDatabase(androidContext(), get()) }
}