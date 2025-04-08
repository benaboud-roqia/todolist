package com.todolist.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;

public class HomeActivity extends AppCompatActivity {

    // Déclaration des vues
    ListView taskListView;
    Button addTaskButton;
    DatabaseHelper databaseHelper;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialisation des vues
        taskListView = findViewById(R.id.taskListView);
        addTaskButton = findViewById(R.id.addTaskButton);

        // Initialisation de la base de données
        databaseHelper = new DatabaseHelper(this);

        // Récupérer l'email de l'utilisateur connecté
        userEmail = getIntent().getStringExtra("userEmail");

        // Afficher les tâches de l'utilisateur connecté
        displayTasks();

        // Ajouter une nouvelle tâche
        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);
        });
    }

    // Méthode pour afficher les tâches de l'utilisateur
    @SuppressLint("Range")
    private void displayTasks() {
        // Récupérer les tâches de l'utilisateur depuis la base de données
        Cursor cursor = databaseHelper.getTasks(userEmail);
        if (cursor != null && cursor.getCount() > 0) {
            String[] taskTitles = new String[cursor.getCount()];
            int index = 0;

            // Remplir le tableau des titres de tâches
            while (cursor.moveToNext()) {
                taskTitles[index++] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_TITLE));
            }

            // Créer un adaptateur pour afficher les tâches dans le ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskTitles);
            taskListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No tasks found.", Toast.LENGTH_SHORT).show();
        }
    }
}
