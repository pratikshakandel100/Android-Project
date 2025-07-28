package com.pratiksha.rojgarihub.presentation.auth.register

import android.widget.RadioButton
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.presentation.auth.UserType
import com.pratiksha.rojgarihub.ui.EyeClosedIcon
import com.pratiksha.rojgarihub.ui.EyeOpenedIcon
import com.pratiksha.rojgarihub.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onLoginClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
) {
    val context = LocalContext.current
    val keyBoaController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyBoaController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                RegisterAction.LoginClick -> onLoginClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }

    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

        Text(
            text = if (state.registerAs == UserType.EMPLOYER) "Register as Employer" else "Register as Job Seeker",
            fontSize = 24.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (state.registerAs == UserType.EMPLOYER) "Register to post jobs for hiring" else "Register to find your dream job",
            color = Color.Black,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Radio Buttons for role selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.registerAs == UserType.EMPLOYER,
                    onClick = { onAction(RegisterAction.RegisterAsChanged(UserType.EMPLOYER)) }
                )
                Text("Employer",modifier = Modifier.clickable{onAction(RegisterAction.RegisterAsChanged(UserType.EMPLOYER))})
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.registerAs == UserType.JOB_SEEKER,
                    onClick = { onAction(RegisterAction.RegisterAsChanged(UserType.JOB_SEEKER)) }
                )
                Text("Job Seeker", modifier = Modifier.clickable{
                    onAction(RegisterAction.RegisterAsChanged(UserType.JOB_SEEKER))
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.firstName,
            onValueChange = { onAction(RegisterAction.FirstNameChanged(it)) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.lastName,
            onValueChange = { onAction(RegisterAction.LastNameChanged(it)) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.EmailChanged(it)) },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = state.phone,
            onValueChange = { onAction(RegisterAction.PhoneChanged(it)) },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        if (state.registerAs == UserType.EMPLOYER) {
            OutlinedTextField(
                value = state.companyName,
                onValueChange = { onAction(RegisterAction.CompanyNameChanged(it)) },
                label = { Text("Company Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(RegisterAction.PasswordChanged(it)) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { onAction(RegisterAction.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isPasswordVisible) EyeOpenedIcon else EyeClosedIcon,
                        contentDescription = null
                    )
                }
            }
        )

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { onAction(RegisterAction.ConfirmPasswordChanged(it)) },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAction(RegisterAction.Submit) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
        Spacer(Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.login),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onAction(RegisterAction.LoginClick)
                            }
                        )
                    }
            )
        }
    }
}