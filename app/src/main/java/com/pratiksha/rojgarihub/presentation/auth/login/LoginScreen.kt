package com.pratiksha.rojgarihub.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.ui.EyeClosedIcon
import com.pratiksha.rojgarihub.ui.EyeOpenedIcon
import com.pratiksha.rojgarihub.ui.ObserveAsEvents
import com.pratiksha.rojgarihub.ui.theme.RojgariHubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onSuccessfulLogin: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            LoginEvent.LoginSuccess -> {
                Toast.makeText(context, R.string.login_successful, Toast.LENGTH_SHORT).show()
                onSuccessfulLogin()
            }
        }
    }
    LoginScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                LoginAction.RegisterClicked -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            modifier = Modifier
                .size(80.dp)
                .padding(top = 40.dp),
            tint = Color.Blue
        )

        Text("Employer Portal", fontSize = 24.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
        Text("Sign in to find your dream job", color = Color.Black, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(LoginAction.EmailChanged(it)) },
            label = { Text("Email Address") },
            placeholder = { Text("Enter your email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            singleLine = true,
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (state.isPasswordVisible) EyeOpenedIcon else EyeClosedIcon
                IconButton(onClick = { onAction(LoginAction.TogglePasswordVisibility) }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.rememberMe,
                onCheckedChange = { onAction(LoginAction.ToggleRememberMe) }
            )
            Text("Remember me")
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { onAction(LoginAction.ForgotPasswordClicked) }) {
                Text("Forgot password?", color = Color.Blue)
            }
        }

        Button(
            onClick = { onAction(LoginAction.SignInClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Sign In as Employee")
        }

        HorizontalDivider(Modifier)


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account?")
            TextButton(onClick = {onAction(LoginAction.RegisterClicked)}) {
                Text("Sign up for free", color = Color.Blue)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview(){
    RojgariHubTheme {
        LoginScreen(
            state = LoginState(
                email = "Pratiksha@gmail.com",
                password = "Pratiksha@123",
                isPasswordVisible = true,
                canLogin = true,
                isLoggingIn = false
            ),
            onAction = {}
        )
    }
}
