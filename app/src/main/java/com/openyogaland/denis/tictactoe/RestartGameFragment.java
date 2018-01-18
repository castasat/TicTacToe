package com.openyogaland.denis.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RestartGameFragment extends Fragment implements OnClickListener
{
  View view;
  // счет игрока и оппонента
  int playerScore, opponentScore;
  // текст во фрагменте
  TextView playerScoreText, opponentScoreText;
  // кнопка во фрагменте
  Button restartGameButton;
  // слушатель события отображения счёта игроков
  OnRestartGameListener onRestartGameListener;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
  {
    // применяем макет к фрагменту
    view = inflater.inflate(R.layout.restart_game_fragment, container, false);
  
    // находим текстовые поля по id
    playerScoreText = view.findViewById(R.id.player_score);
    opponentScoreText = view.findViewById(R.id.opponent_score);
    
    // находим кнопку по id
    restartGameButton = view.findViewById(R.id.restart_game_button);
    // задаём обработчик событий
    restartGameButton.setOnClickListener(this);
    
    // устанавливаем переданные через Bundle значение
    Bundle bundle = getArguments();
    if(bundle != null)
    {
      playerScore   = bundle.getInt("playerScore");
      opponentScore = bundle.getInt("opponentScore");
      showScore(playerScore, opponentScore);
    }
    
    // возвращаем фрагмент в виде элемента View
    return view;
  }
  
  @Override
  public void onClick(View view)
  {
    if(view.getId() == R.id.restart_game_button)
    {
      // рестарт игры
      onRestartGameListener.onRestartGame(playerScore, opponentScore);
    }
  }
  
  // метод прикрепления фрагмента к контексту (активности)
  @Override
  public void onAttach(Context context)
  {
    super.onAttach(context);
    
    try
    {
      onRestartGameListener = (OnRestartGameListener) context;
    }
    catch(ClassCastException e)
    {
      throw new ClassCastException(context.toString() + " should implement OnRestartGameListener interface");
    }
  }
  
  public void showScore(int playerScore, int opponentScore)
  {
    this.playerScore = playerScore;
    this.opponentScore = opponentScore;
    playerScoreText.setText(String.valueOf(playerScore));
    opponentScoreText.setText(String.valueOf(opponentScore));
  }
}
