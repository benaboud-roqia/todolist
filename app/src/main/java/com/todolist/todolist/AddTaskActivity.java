package com.todolist.todolist;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    // Déclaration des vues
    EditText taskTitleEditText, taskDescriptionEditText, taskDueDateEditText;
    Spinner taskStatusSpinner, taskCategorySpinner;
    Button saveTaskButton;
    DatabaseHelper databaseHelper;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialisation des vues
        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskDueDateEditText = findViewById(R.id.taskDueDateEditText);
        taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        taskCategorySpinner = findViewById(R.id.taskCategorySpinner);
        saveTaskButton = findViewById(R.id.saveTaskButton);

        // Initialisation de la base de données
        databaseHelper = new DatabaseHelper(this);

        // Récupérer l'email de l'utilisateur
        userEmail = getIntent().getStringExtra("userEmail");

        // Remplir le Spinner du statut de la tâche
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusSpinner.setAdapter(statusAdapter);

        // Remplir le Spinner de la catégorie de la tâche
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskCategorySpinner.setAdapter(categoryAdapter);

        // Gestion du clic sur le bouton de sauvegarde de la tâche
        saveTaskButton.setOnClickListener(v -> {
            String title = taskTitleEditText.getText().toString().trim();
            String description = taskDescriptionEditText.getText().toString().trim();
            String dueDate = taskDueDateEditText.getText().toString().trim();
            String status = taskStatusSpinner.getSelectedItem().toString();
            String category = taskCategorySpinner.getSelectedItem().toString();

            // Vérification des entrées
            if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Ajouter la tâche à la base de données
                boolean isTaskAdded = databaseHelper.addTask(title, description, dueDate, status, category, userEmail);
                if (isTaskAdded) {
                    Toast.makeText(this, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
                    // Retourner à l'écran d'accueil
                    Intent intent = new Intent(AddTaskActivity.this, HomeActivity.class);
                    intent.putExtra("userEmail", userEmail);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add task. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
