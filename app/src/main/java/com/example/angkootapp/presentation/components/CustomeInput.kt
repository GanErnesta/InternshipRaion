package com.example.angkootapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R
import com.example.angkootapp.ui.theme.primaryColor
import com.example.angkootapp.ui.theme.primaryTextColor
import com.example.angkootapp.ui.theme.secondTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputField(
    label: String,
    placeholder: String,
    leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    passwordVisible: Boolean = true,
    onPasswordToggle: (() -> Unit)? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefix: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            color = if (isError) MaterialTheme.colorScheme.error else Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            isError = isError,
            prefix = prefix?.let { { Text(it) } },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    tint = if (isError) MaterialTheme.colorScheme.error else primaryColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (isPassword && onPasswordToggle != null) {
                    val icon = if (passwordVisible) R.drawable.eyeopen else R.drawable.eyeclose
                    IconButton(onClick = onPasswordToggle) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "Toggle Password",
                            tint = if (isError) MaterialTheme.colorScheme.error else Color.Gray,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            },
            supportingText = {
                if (isError && supportingText != null) {
                    Text(
                        text = supportingText,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                autoCorrectEnabled = !isPassword,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF7F8F9),
                unfocusedContainerColor = Color(0xFFF7F8F9),
                errorContainerColor = Color(0xFFF7F8F9),
                focusedBorderColor = Color(0xFF2CB9D1),
                unfocusedBorderColor = Color(0xFFE8ECF4),
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedTextColor = Color(0xFF0F4C5C),
                unfocusedTextColor = Color(0xFF0F4C5C),
                errorTextColor = Color(0xFF0F4C5C),
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray,
                focusedPrefixColor = primaryColor,
                unfocusedPrefixColor = primaryColor,
                cursorColor = Color(0xFF2CB9D1),
                errorCursorColor = MaterialTheme.colorScheme.error
            )
        )
    }
}