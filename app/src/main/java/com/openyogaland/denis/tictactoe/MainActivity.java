package com.openyogaland.denis.tictactoe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnFlipCoinListener
{
  // транзакция фрагментов
  FragmentTransaction transaction;
  
  // фрагменты
  FlipCoinFragment  flipCoinFragment;
  TicTacToeFragment ticTacToeFragment;
  // TODO add restart game fragment
  
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
  
  // случайное целое число из диапазона от minInt до maxInt включительно
  static int getRandomInt(int min, int max)
  {
    return (int) (min + (Math.round(Math.random()) * (max - min)));
  }
  
  // если монета подброшена, передаём результат в ticTactoeFragment
  @Override
  public void onFlipCoin(boolean isPlayerFirst)
  {
    // начинаем игру, передавая в неё информацию, кто ходит первым
    ticTacToeFragment.gameStart(isPlayerFirst);
  }
}