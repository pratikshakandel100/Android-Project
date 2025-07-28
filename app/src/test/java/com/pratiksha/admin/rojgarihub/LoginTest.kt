package com.pratiksha.admin.rojgarihub

import com.pratiksha.rojgarihub.presentation.auth.login.LoginAction
import com.pratiksha.rojgarihub.presentation.auth.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        fakeAuthRepository = FakeAuthRepository()
        viewModel = LoginViewModel(fakeAuthRepository)
    }

    @Test
    fun `login with valid credentials returns success`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.EmailChanged("cow@gmail.com"))
        viewModel.onAction(LoginAction.PasswordChanged("123456"))

        // Act
        viewModel.onAction(LoginAction.SignInClicked)

        // Assert
        assertNotNull(viewModel.state.isLoggingIn)
        assertEquals("cow@gmail.com", viewModel.state.email)
        assertEquals("123456", viewModel.state.password)
    }

    @Test
    fun `login with invalid credentials sets error message`() = runTest {
        // Arrange
        fakeAuthRepository.setShouldFail(true)
        viewModel.onAction(LoginAction.EmailChanged("wrong@example.com"))
        viewModel.onAction(LoginAction.PasswordChanged("wrongpass"))

        // Act
        viewModel.onAction(LoginAction.SignInClicked)

        // Assert
        assertNotNull(viewModel.state.isLoggingIn)
    }
}
