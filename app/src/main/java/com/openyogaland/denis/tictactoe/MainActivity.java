package com.openyogaland.denis.tictactoe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements OnFlipCoinListener,
                                                               OnShowScoreListener,
                                                               OnRestartGameListener
{
  // константы
  final String TAG_RESTART    = "restart_game";
  final String TAG_FLIP       = "flip_coin";
  final String TAG_TICTACTOE  = "tictactoe";
  
  // контейнер, в котором будут заменяться фрагменты
  LinearLayout container;
  
  // транзакция фрагментов
  FragmentTransaction transaction;
  
  // фрагменты
  FlipCoinFragment    flipCoinFragment;
  TicTacToeFragment   ticTacToeFragment;
  RestartGameFragment restartGameFragment;
  
  // первый метод, вызываемый при создании андроид-активности
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    // задаём xml-файл расположения графических элементов
    setContentView(R.layout.activity_main);

    // находим контейнер для загрузки динамических фрагментов по id
    container = findViewById(R.id.container);
    
    flipCoinFragment    = new FlipCoinFragment();
    ticTacToeFragment   = new TicTacToeFragment();
    restartGameFragment = new RestartGameFragment();
    
    // при первом запуске программы
    if (savedInstanceState == null)
    {
      transaction = getSupportFragmentManager().beginTransaction();
      transaction.add(R.id.container, flipCoinFragment, TAG_FLIP);
      transaction.add(R.id.tictactoe_fragment_frame, ticTacToeFragment, TAG_TICTACTOE);
      transaction.commit();
    }
  }
  
  // случайное целое число из диапазона от minInt до maxInt включительно
  static int getRandomInt()
  {
    return (int) Math.round(Math.random());
  }
  
  // если монета подброшена, передаём результат в ticTactoeFragment
  @Override
  public void onFlipCoin(boolean isPlayerFirst)
  {
    // начинаем игру, передавая в неё информацию, кто ходит первым
    ticTacToeFragment.gameStart(isPlayerFirst);
  }
  
  // заменяем фрагмент, чтобы вывести счёт
  @Override
  public void onShowScore(int playerScore, int opponentScore)
  {
    RestartGameFragment restartGameFragment = (RestartGameFragment) getSupportFragmentManager()
        .findFragmentByTag(TAG_RESTART);
    
    if(restartGameFragment == null)
    {
      Bundle bundle = new Bundle();
      bundle.putInt("playerScore", playerScore);
      bundle.putInt("opponentScore", opponentScore);
      this.restartGameFragment.setArguments(bundle);
      transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.container, this.restartGameFragment, TAG_RESTART);
      transaction.commit();
    }
    else
    {
      // restartGameFragment уже загружен
      restartGameFragment.showScore(playerScore, opponentScore);
    }
  }
  
  // если рестартуем игру, переинициализируем игру и заменяем фрагмент во фрейме
  @Override
  public void onRestartGame(int playerScore, int opponentScore)
  {
    FlipCoinFragment flipCoinFragment = (FlipCoinFragment) getSupportFragmentManager()
        .findFragmentByTag(TAG_FLIP);
    
    TicTacToeFragment ticTacToeFragment = (TicTacToeFragment) getSupportFragmentManager()
        .findFragmentByTag(TAG_TICTACTOE);
    
    if(flipCoinFragment == null)
    {
      transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.container, this.flipCoinFragment, TAG_FLIP);
      transaction.commit();
    }
    // else flipCoinFragment уже загружен
    
    if(ticTacToeFragment == null)
    {
      Bundle bundle = new Bundle();
      bundle.putInt("playerScore", playerScore);
      bundle.putInt("opponentScore", opponentScore);
      this.ticTacToeFragment.setArguments(bundle);
      transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.tictactoe_fragment_frame, this.ticTacToeFragment, TAG_TICTACTOE);
      transaction.commit();
    }
    else
    {
      // ticTacToeFragment уже загружен
      ticTacToeFragment.restartGame(playerScore, opponentScore);
    }
  }
}