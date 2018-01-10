package com.openyogaland.denis.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FlipCoinFragment extends Fragment implements OnClickListener
{
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
  {
    // применяем макет к фрагменту
    View view = inflater.inflate(R.layout.flip_coin_fragment, container, false);
    
    // находим кнопки по id
    Button headsButton = view.findViewById(R.id.heads_button);
    Button tailsButton = view.findViewById(R.id.tails_button);
    
    // задаём обработчик событий
    headsButton.setOnClickListener(this);
    tailsButton.setOnClickListener(this);
    
    // возвращаем фрагмент в виде элемента View
    return view;
  }
  
  @Override public void onClick(View view)
  {
    if (view instanceof Button)
    {
      switch (view.getId())
      {
        case R.id.heads_button:
          // TODO
          break;
        case R.id.tails_button:
          // TODO
          break;
      }
    }
  }
}
