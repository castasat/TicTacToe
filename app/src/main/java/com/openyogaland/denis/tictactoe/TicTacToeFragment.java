package com.openyogaland.denis.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TicTacToeFragment extends Fragment implements OnClickListener
{
  // view для отображения фрагмента
  View view;
  // символы, которыми игрок и оппонент совершают свой ход
  String playerMark, opponentMark;
  // ходит ли первым игрок
  boolean isPlayerFirst;
  
  // массив int, содержащий id кнопок игровых полей
  int[] fields = {R.id.NW, R.id.N, R.id.NE, R.id.W, R.id.C, R.id.E, R.id.SW, R.id.S, R.id.SE};
  
  // массив кнопок игровых полей
  Button buttons[] = new Button[fields.length];
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
  {
    // применяем макет к фрагменту
    view = inflater.inflate(R.layout.tictactoe_fragment, container, false);
    gameInit(view);
    
    // возвращаем фрагмент в виде элемента View
    return view;
  }
  
  @Override
  public void onClick(View view)
  {
    if(view instanceof Button)
    {
      switch(view.getId())
      {
        case R.id.C:
          // TODO
          break;
        case R.id.N:
          // TODO
          break;
        case R.id.NW:
          // TODO
          break;
      }
      // TODO move String value to resources
      gameOver("Restarting game");
    }
  }
  
  private void gameInit(View view)
  {
    for(int i = 0; i < buttons.length; i++)
    {
      buttons[i] = view.findViewById(fields[i]); // находим кнопки по id
      buttons[i].setOnClickListener(this);       // задаём для кнопок слушателя нажатий
      buttons[i].setText("");                    // задаём для кнопок пустую начальную маркировку
      buttons[i].setClickable(false);            // запрещаем в начале игры нажимать на поля игры
    }
  }
  
  // завершение игры
  private void gameOver(String string)
  {
    // выводим сообщение с результатом завершения игры
    Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
  }
  
  public void gameStart(boolean isPlayerFirst)
  {
    this.isPlayerFirst = isPlayerFirst;
    // TODO стартовать игру в зависимости от того, кто ходит первым
  }
}