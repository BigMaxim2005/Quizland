package com.kb_ra_2_3.quizland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Скрываем нижнюю панель навигации
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Инициализируем кнопку старт
        btStart = findViewById(R.id.bt_start);

        // Вешаем слушатель нажатий
        btStart.setOnClickListener(v -> {
            // Переход на экран выбора уровня
            Intent mainActivityIntent = new Intent(MainActivity.this, GameLevelsActivity.class);
            startActivity(mainActivityIntent);
        });
    }
}