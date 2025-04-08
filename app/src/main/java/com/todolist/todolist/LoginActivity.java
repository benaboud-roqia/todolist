package com.todolist.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Déclaration des vues
    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView registerLink;
    ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);
        logoImageView = findViewById(R.id.logoImageView);

        // Gestion du clic sur le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Valeurs par défaut (à remplacer par une base de données réelle)
            String correctEmail = "user@example.com";
            String correctPassword = "password123";

            // Vérification si les champs sont remplis
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else if (email.equals(correctEmail) && password.equals(correctPassword)) {
                // Si l'email et le mot de passe sont corrects
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Redirection vers la page d'accueil (ex: HomeActivity)
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Pour fermer cette activité (LoginActivity)
            } else {
                // Si l'email ou le mot de passe est incorrect
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur le lien d'inscription
        registerLink.setOnClickListener(v -> {
            // Redirection vers l'écran d'inscription (ex: RegisterActivity)
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
