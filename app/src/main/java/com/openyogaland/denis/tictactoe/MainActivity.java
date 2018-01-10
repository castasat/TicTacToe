package com.openyogaland.denis.tictactoe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
  // транзакция фрагментов
  FragmentTransaction transaction;
  
  // фрагменты
  FlipCoinFragment  flipCoinFragment;
  TicTacToeFragment ticTacToeFragment;
  
  // первый метод, вызываемый при создании андроид-активности
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    // задаём xml-файл расположения графических элементов
    setContentView(R.layout.activity_main);
    
    flipCoinFragment  = new FlipCoinFragment();
    ticTacToeFragment = new TicTacToeFragment();
    
    transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.flipcoin_fragment_frame, flipCoinFragment);
    transaction.add(R.id.tictactoe_fragment_frame, ticTacToeFragment);
    transaction.commit();
  }
  
}