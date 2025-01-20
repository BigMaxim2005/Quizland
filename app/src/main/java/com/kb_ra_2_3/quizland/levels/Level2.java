package com.kb_ra_2_3.quizland.levels;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.kb_ra_2_3.quizland.R;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    private Button btBack;
    private TextView tvLevelNumber, tvLeftCard, tvRightCard, tvLeftCardText, tvRightCardText;
    private Slider slider;

    private int leftNumCard;
    private int rightNumCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // Вызов стартового диалога
        initStartDialog();

        // Инициализация интерфейса
        initUI();

        // Установка текста номера уровня
        tvLevelNumber.setText(R.string.level_2);

        // Инициализация карточек
        initCards();
    }

    private void initUI() {
        btBack = findViewById(R.id.bt_back);
        tvLevelNumber = findViewById(R.id.tv_level_number);
        tvLeftCard = findViewById(R.id.tv_left_card);
        tvRightCard = findViewById(R.id.tv_right_card);
        tvLeftCardText = findViewById(R.id.tv_left_number_text);
        tvRightCardText = findViewById(R.id.tv_right_number_text);
        slider = findViewById(R.id.slider);

        // Обработка нажатия на кнопку "Назад"
        btBack.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initCards() {
        updateCards();

        // Обработка нажатий на карточки
        View.OnTouchListener touchListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v.getId() == R.id.tv_left_card) {
                    checkAnswer(true);
                } else if (v.getId() == R.id.tv_right_card) {
                    checkAnswer(false);
                }
            }
            return true;
        };

        tvLeftCard.setOnTouchListener(touchListener);
        tvRightCard.setOnTouchListener(touchListener);
    }

    private void updateCards() {
        String[] textArray = getResources().getStringArray(R.array.textArrayLevel2);
        Random random = new Random();

        leftNumCard = random.nextInt(textArray.length);
        rightNumCard = random.nextInt(textArray.length);

        while (leftNumCard == rightNumCard) {
            rightNumCard = random.nextInt(textArray.length);
        }

        tvLeftCard.setText(String.valueOf(leftNumCard));
        tvRightCard.setText(String.valueOf(rightNumCard));

        tvLeftCardText.setText(textArray[leftNumCard]);
        tvRightCardText.setText(textArray[rightNumCard]);

        resetCardStyles();
    }

    private void resetCardStyles() {
        tvLeftCard.setBackgroundResource(R.drawable.tv_style_white_40);
        tvRightCard.setBackgroundResource(R.drawable.tv_style_white_40);
    }

    private void checkAnswer(boolean isLeftCard) {
        int selectedValue = isLeftCard ? leftNumCard : rightNumCard;
        int otherValue = isLeftCard ? rightNumCard : leftNumCard;

        boolean isCorrect = selectedValue > otherValue;

        if (isCorrect) {
            slider.setValue(Math.min(slider.getValue() + 10, slider.getValueTo()));
            (isLeftCard ? tvLeftCard : tvRightCard).setBackgroundResource(R.drawable.tv_style_green_40);
        } else {
            slider.setValue(Math.max(slider.getValue() - 5, 0));
            (isLeftCard ? tvLeftCard : tvRightCard).setBackgroundResource(R.drawable.tv_style_red_40);
        }

        if (slider.getValue() >= slider.getValueTo()) {
            initEndDialog();
        } else {
            tvLeftCard.postDelayed(this::updateCards, 500);
        }
    }

    public void initStartDialog() {
        Dialog dialogStart = new Dialog(this);
        dialogStart.requestWindowFeature(Window.FEATURE_NO_TITLE); // Отключаем название диалога
        dialogStart.setContentView(R.layout.preview_dialog); // Выбор макета для диалогового окна

        // Скрываем нижнюю панель навигации
        Window w = dialogStart.getWindow();
        w.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ConstraintLayout constraintLayout = dialogStart.findViewById(R.id.my_dialog_constraint);
        constraintLayout.setBackgroundResource(R.drawable.im_back_dialog_level); // Выбор заднего фона для диалогового окна

        ImageView ivDialog = dialogStart.findViewById(R.id.image_view);
        ivDialog.setImageResource(R.drawable.two_cards_level); // Выбор картинки с карточками для уровня №1

        TextView tvDescription = dialogStart.findViewById(R.id.tv_description);
        tvDescription.setText(getResources().getString(R.string.exercise_level1)); // Выбор текста с заданием для уровня №1

        dialogStart.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT); // Растягиваем диалоговое окно
        dialogStart.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Установка прозрачного фона для диалога
        dialogStart.setCancelable(false); // Отключение системной кнопки "назад"

        dialogStart.show(); // Отображение диалога

        MaterialButton btClose = dialogStart.findViewById(R.id.bt_close_dialog);

        // Обработка нажатия на кнопку "закрыть"
        btClose.setOnClickListener(v -> onBackPressed()); // Возврат на предыдущий экран

        MaterialButton _continue = dialogStart.findViewById(R.id.bt_continue);

        // Обработка нажатия на кнопку "продолжить"
        _continue.setOnClickListener(v -> dialogStart.cancel()); // Закрытие диалога
    }

    private void initEndDialog() {
        Dialog dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE); // Отключаем название диалога
        dialogEnd.setContentView(R.layout.end_dialog); // Выбор макета для диалогового окна

        // Скрываем нижнюю панель навигации
        Window w = dialogEnd.getWindow();
        w.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ConstraintLayout constraintLayout = dialogEnd.findViewById(R.id.my_end_dialog_constraint);
        constraintLayout.setBackgroundResource(R.drawable.im_back_dialog_level); // Выбор заднего фона для диалогового окна

        TextView tvDescription = dialogEnd.findViewById(R.id.tv_description);
        tvDescription.setText(getResources().getString(R.string.interesting_fact_level2)); // Выбор текста интересного факта для уровня №1

        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT); // Растягиваем диалоговое окно
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Установка прозрачного фона для диалога
        dialogEnd.setCancelable(false); // Отключение системной кнопки "назад"

        dialogEnd.show(); // Отображение диалога

        MaterialButton btClose = dialogEnd.findViewById(R.id.bt_close_dialog);

        // Обработка нажатия на кнопку "закрыть"
        btClose.setOnClickListener(v -> finish()); // Закрытие активности

        MaterialButton _continue = dialogEnd.findViewById(R.id.bt_continue);

        // Обработка нажатия на кнопку "продолжить"
        _continue.setOnClickListener(v -> {
            Intent intent = new Intent(Level2.this, Level3.class);
            startActivity(intent); // Переход на следующий уровень
            finish(); // Закрытие текущей активности
            dialogEnd.cancel(); // Закрытие диалога
        });
    }
}
