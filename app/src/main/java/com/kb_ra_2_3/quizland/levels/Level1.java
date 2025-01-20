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

public class Level1 extends AppCompatActivity {

    Button btBack; // Кнопка "Назад"
    TextView tvLevelNumber, tvLeftCard, tvRightCard, tvLeftCardText, tvRightCardText; // Элементы интерфейса
    Slider slider; // Слайдер для отслеживания прогресса

    int leftNumCard; // Число на левой карточке
    int rightNumCard; // Число на правой карточке

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal); // Установка макета

        // Вызов стартового диалога
        initStartDialog();

        // Инициализация элементов интерфейса
        btBack = findViewById(R.id.bt_back);
        tvLevelNumber = findViewById(R.id.tv_level_number);
        tvLeftCard = findViewById(R.id.tv_left_card);
        tvRightCard = findViewById(R.id.tv_right_card);
        tvLeftCardText = findViewById(R.id.tv_left_number_text);
        tvRightCardText = findViewById(R.id.tv_right_number_text);
        slider = findViewById(R.id.slider);

        // Обработка нажатия на кнопку "назад"
        btBack.setOnClickListener(v -> onBackPressed()); // Возврат на предыдущий экран

        // Установка текста номера уровня
        tvLevelNumber.setText(R.string.level_1);

        // Метод инициализации чисел на карточках
        initCards();
    }

    /**
     * Метод работы карточек
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initCards() {
        initCardView(); // Инициализация карточек

        // Обработка нажатия на карточки
        View.OnTouchListener cardTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (v.getId() == R.id.tv_left_card) {
                        checkSelection(leftNumCard, rightNumCard, true);
                    } else if (v.getId() == R.id.tv_right_card) {
                        checkSelection(rightNumCard, leftNumCard, false);
                    }
                }
                return true;
            }
        };

        tvLeftCard.setOnTouchListener(cardTouchListener);
        tvRightCard.setOnTouchListener(cardTouchListener);
    }

    /**
     * Метод проверки выбора карточки
     */
    private void checkSelection(int selectedCard, int otherCard, boolean isLeftCard) {
        if (selectedCard > otherCard) {
            // Правильный ответ
            if (slider.getValue() < slider.getValueTo()) {
                slider.setValue(Math.min(slider.getValue() + 10, slider.getValueTo())); // Увеличиваем значение слайдера
            }
            if (isLeftCard) {
                tvLeftCard.setBackgroundResource(R.drawable.tv_style_green_40); // Успешный выбор для левой карточки
            } else {
                tvRightCard.setBackgroundResource(R.drawable.tv_style_green_40); // Успешный выбор для правой карточки
            }
        } else {
            // Неправильный ответ
            if (slider.getValue() > 1) {
                slider.setValue(slider.getValue() -  5); // Уменьшаем значение слайдера
            } else {
                slider.setValue(0); // Устанавливаем значение слайдера в 0
            }
            if (isLeftCard) {
                tvLeftCard.setBackgroundResource(R.drawable.tv_style_red_40); // Ошибочный выбор для левой карточки
            } else {
                tvRightCard.setBackgroundResource(R.drawable.tv_style_red_40); // Ошибочный выбор для правой карточки
            }
        }

        // Проверка строки прогресса уровня на заполненность
        if (slider.getValue() == slider.getValueTo()) {
            initEndDialog(); // Вызов диалога в конце уровня
        } else {
            // Пауза перед сменой значений для визуализации цвета
            tvLeftCard.postDelayed(this::initCardView, 500); // Присваиваем новые значения для карточек после задержки
        }
    }


    /**
     * Метод вызова диалога в конце уровня
     * Появляется после успешного прохождения уровня.
     */
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
        tvDescription.setText(getResources().getString(R.string.interesting_fact_level1)); // Выбор текста интересного факта для уровня №1

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
            Intent intent = new Intent(Level1.this, Level2.class);
            startActivity(intent); // Переход на следующий уровень
            finish(); // Закрытие текущей активности
            dialogEnd.cancel(); // Закрытие диалога
        });
    }

    /**
     * Метод генерации рандомных значений для карточек и вывод значений на экран
     */
    private void initCardView() {
        String[] textArrayLevel1 = getResources().getStringArray(R.array.textArrayLevel1);
        Random random = new Random();

        leftNumCard = random.nextInt(textArrayLevel1.length);
        rightNumCard = random.nextInt(textArrayLevel1.length);

        while (leftNumCard == rightNumCard) { // Если значения карточек равны, генерируем новое значение для правой карточки
            rightNumCard = random.nextInt(textArrayLevel1.length);
        }

        tvLeftCard.setText(String.valueOf(leftNumCard)); // Установка текста на левой карточке
        tvRightCard.setText(String.valueOf(rightNumCard)); // Установка текста на правой карточке

        tvLeftCardText.setText(textArrayLevel1[leftNumCard]); // Установка текста описания для левой карточки
        tvRightCardText.setText(textArrayLevel1[rightNumCard]); // Установка текста описания для правой карточки

        tvLeftCard.setBackgroundResource(R.drawable.tv_style_white_40); // Установка фона для левой карточки
        tvRightCard.setBackgroundResource(R.drawable.tv_style_white_40); // Установка фона для правой карточки
    }

    /**
     * Метод вызова старт ового диалогового окна
     * Появляется перед началом уровня
     */
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
}