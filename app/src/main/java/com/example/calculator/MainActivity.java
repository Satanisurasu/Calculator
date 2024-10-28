package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText numberField; // Поле для ввода числа и отображения выражения
    StringBuilder operationBuilder; // Для хранения текущего ввода операции

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberField = findViewById(R.id.numberField);
        operationBuilder = new StringBuilder();

        findViewById(R.id.add).setOnClickListener((view) -> onOperationClick("+"));
        findViewById(R.id.sub).setOnClickListener((view) -> onOperationClick("-"));
        findViewById(R.id.mul).setOnClickListener((view) -> onOperationClick("*"));
        findViewById(R.id.div).setOnClickListener((view) -> onOperationClick("/"));
        findViewById(R.id.eq).setOnClickListener((view) -> onEqualClick());
        findViewById(R.id.clear).setOnClickListener((view) -> clearCalculator());

        findViewById(R.id.n0).setOnClickListener((view) -> onNumberClick("0"));
        findViewById(R.id.n1).setOnClickListener((view) -> onNumberClick("1"));
        findViewById(R.id.n2).setOnClickListener((view) -> onNumberClick("2"));
        findViewById(R.id.n3).setOnClickListener((view) -> onNumberClick("3"));
        findViewById(R.id.n4).setOnClickListener((view) -> onNumberClick("4"));
        findViewById(R.id.n5).setOnClickListener((view) -> onNumberClick("5"));
        findViewById(R.id.n6).setOnClickListener((view) -> onNumberClick("6"));
        findViewById(R.id.n7).setOnClickListener((view) -> onNumberClick("7"));
        findViewById(R.id.n8).setOnClickListener((view) -> onNumberClick("8"));
        findViewById(R.id.n9).setOnClickListener((view) -> onNumberClick("9"));
        findViewById(R.id.comma).setOnClickListener((view) -> onNumberClick("."));
    }

    // Обработка нажатия на числовую кнопку
    public void onNumberClick(String number) {
        operationBuilder.append(number);
        numberField.setText(operationBuilder.toString());
    }

    // Обработка нажатия на кнопку операции
    public void onOperationClick(String op) {
        int length = operationBuilder.length();

        // Проверяем, есть ли уже знак в конце строки
        if (length > 0) {
            char lastChar = operationBuilder.charAt(length - 1);

            if (isOperator(lastChar)) {
                // Заменяем текущий оператор на новый, если он уже есть
                operationBuilder.setCharAt(length - 1, op.charAt(0));
            } else {
                // Добавляем оператор, если предыдущий символ не является оператором
                operationBuilder.append(op);
            }
        } else if (length == 0 && op.equals("-")) {
            // Разрешаем минус для начала выражения, чтобы указать отрицательное число
            operationBuilder.append(op);
        }

        numberField.setText(operationBuilder.toString());
    }

    // Обработка нажатия на "="
    public void onEqualClick() {
        // Удаляем любой оператор в конце выражения перед вычислением
        if (operationBuilder.length() > 0 && isOperator(operationBuilder.charAt(operationBuilder.length() - 1))) {
            operationBuilder.deleteCharAt(operationBuilder.length() - 1);
        }

        if (operationBuilder.length() > 0) {
            String expression = operationBuilder.toString().replace(',', '.');
            try {
                Double result = Calculator.evaluateExpression(expression);
                operationBuilder.setLength(0);  // Очистка для нового ввода
                operationBuilder.append(result.toString().replace('.', ','));
                numberField.setText(operationBuilder.toString());
            } catch (Exception e) {
                // В случае ошибки показываем сообщение об ошибке
                numberField.setText("Ошибка");
                operationBuilder.setLength(0); // Очистка строки
            }
        }
    }

    // Метод для очистки калькулятора
    private void clearCalculator() {
        operationBuilder.setLength(0); // Очищаем строку операций
        numberField.setText("");
    }

    // Метод для проверки, является ли символ оператором
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
