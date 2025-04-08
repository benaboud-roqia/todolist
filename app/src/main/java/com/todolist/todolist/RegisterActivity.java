package com.todolist.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Déclaration des vues
    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Button registerButton;
    TextView loginLink;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialisation des vues
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        // Initialisation de la base de données SQLite
        databaseHelper = new DatabaseHelper(this);

        // Gestion du clic sur le bouton d'inscription
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Validation des entrées
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Ajouter l'utilisateur à la base de données
                boolean isAdded = databaseHelper.addUser(email, password);
                if (isAdded) {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    // Redirection vers la page de connexion après l'inscription
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email already exists. Please use another email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Gestion du clic sur le lien de connexion
        loginLink.setOnClickListener(v -> {
            // Redirige vers l'écran de connexion
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
