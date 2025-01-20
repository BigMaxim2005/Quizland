package com.kb_ra_2_3.quizland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.kb_ra_2_3.quizland.levels.Level1;

public class GameLevelsActivity extends AppCompatActivity {

    private Button btBack;
    private TextView tvLevel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_levels);

        // Скрываем нижнюю панель навигации
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Инициализируем кнопку "назад"
        btBack = findViewById(R.id.bt_back);
        btBack.setOnClickListener(v -> onBackPressed());

        // Инициализируем кнопку "level_1"
        tvLevel1 = findViewById(R.id.tv_level_1);
        tvLevel1.setOnClickListener(v -> {
            // Переход на экран уровня №1
            Intent intentLevel1 = new Intent(GameLevelsActivity.this, Level1.class);
            startActivity(intentLevel1);


        });
    }
}