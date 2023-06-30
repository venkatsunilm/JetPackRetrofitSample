/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.venkat.digitalclinic.apiservice.di

import com.venkat.digitalclinic.apiservice.data.remote.IPatientDetailsService
import com.venkat.digitalclinic.apiservice.data.remote.IPatientService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun providePatientService(retrofit: Retrofit): IPatientService {
        return retrofit.create(IPatientService::class.java)
    }

    @Provides
    fun providePatientDetailsService(retrofit: Retrofit): IPatientDetailsService {
        return retrofit.create(IPatientDetailsService::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://example/api/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

