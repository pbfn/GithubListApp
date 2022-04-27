package com.pedro_bruno.githublistapp

import com.pedro_bruno.githublistapp.domain.exceptions.EmptySearchException
import com.pedro_bruno.githublistapp.domain.exceptions.PageIsZeroException
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import com.pedro_bruno.githublistapp.domain.usecase.SearchGistListRemoteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class SearchGistListRemoteUseCaseTest {

    @Mock
    private lateinit var gistRepository: GistRepository
    private lateinit var subject: SearchGistListRemoteUseCase

    private val listGistMock: List<Gist> = listOf()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        subject = SearchGistListRemoteUseCase(
            gistRepository = gistRepository,
            scope = CoroutineScope(Dispatchers.IO)
        )
    }

    @Test
    fun `WHEN SUCCESS MUST RETURN LIST OF GISTS`() = runBlocking {
        stubOnSuccess()
        subject.run(
            SearchGistListRemoteUseCase.Params(
                page = 1,
                owner = "owner"
            )
        ).collect {
            assert(it == listGistMock)
        }
    }

    @Test(expected = PageIsZeroException::class)
    fun `WHEN THE PAGE IS LESS OR EQUAL TO ZERO MUST RETURN PAGE IS ZERO EXCEPTION`() {
        subject.run(
            SearchGistListRemoteUseCase.Params(
                page = 0,
                owner = "owner"
            )
        )
    }

    @Test(expected = EmptySearchException::class)
    fun `WHEN SEARCH IS EMPTY MUST RETURN PAGE IS ZERO EXCEPTION`() {
        subject.run(
            SearchGistListRemoteUseCase.Params(
                page = 10,
                owner = ""
            )
        )
    }

    private fun stubOnSuccess() {
        whenever(
            gistRepository.searchGistList(
                page = any(),
                owner = any()
            )
        ).thenAnswer {
            flowOf(listGistMock)
        }
    }
}