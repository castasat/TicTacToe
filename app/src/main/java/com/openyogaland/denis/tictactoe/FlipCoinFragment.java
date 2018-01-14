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

public class FlipCoinFragment extends Fragment implements OnClickListener
{
  // переменная для хранения результата выбора игрока
  boolean isChoiceHeads;
  // переменная для записи, ходит ли игрок первым
  boolean isPlayerFirst;
  // текст во фрагменте
  TextView flipCoinTextView;
  // кнопки выбора heads и tails
  Button headsButton, tailsButton;
  // слушатель события результата броска монетки
  OnFlipCoinListener onFlipCoinListener;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
  {
    // применяем макет к фрагменту
    View view = inflater.inflate(R.layout.flip_coin_fragment, container, false);
    
    // находим текстовое поле по id
    flipCoinTextView = view.findViewById(R.id.flip_coin_textview);
    
    // находим кнопки по id
    headsButton = view.findViewById(R.id.heads_button);
    tailsButton = view.findViewById(R.id.tails_button);
    
    // задаём обработчик событий
    headsButton.setOnClickListener(this);
    tailsButton.setOnClickListener(this);
    
    // возвращаем фрагмент в виде элемента View
    return view;
  }
  
  @Override
  public void onClick(View view)
  {
    if (view instanceof Button)
    {
      switch (view.getId())
      {
        case R.id.heads_button:
          isChoiceHeads = true;
          break;
        case R.id.tails_button:
          isChoiceHeads = false;
          break;
      }
      
      // скрываем кнопки выбора heads и tails
      headsButton.setVisibility(View.INVISIBLE);
      tailsButton.setVisibility(View.INVISIBLE);
      
      // who plays first?
      whoPlaysFirst();
    }
  }
  
  // метод, определяющий, будет ли игрок, или его оппонент, ходить первым
  void whoPlaysFirst()
  {
    // игрок ходит первым, если результат подбрасывания монеты совпадает с его выбором
    isPlayerFirst = (isChoiceHeads == flipCoin());
    
    if (isPlayerFirst)
    {
      // устанавливаем сообщение, что игрок ходит первым
      flipCoinTextView.setText(R.string.player_first);
    }
    else
    {
      // устанавливаем сообщение, что игрок ходит вторым
      flipCoinTextView.setText(R.string.opponent_first);
    }
    
    // оповещаем слушаителя события броска монетки
    onFlipCoinListener.onFlipCoin(isPlayerFirst);
  }
  
  // метод подбрасывания монеты
  boolean flipCoin()
  {
    // получаем результат броска монеты в виде boolean
    int result = MainActivity.getRandomInt(0, 1);
    
    if (result == 0)
    {
      return false;
    }
    else if (result == 1)
    {
      return true;
    }
    
    // по-умолчанию - false
    return false;
  }
  
  // метод прикрепления фрагмента к контексту (активности)
  @Override
  public void onAttach(Context context)
  {
    super.onAttach(context);
    
    try
    {
      onFlipCoinListener = (OnFlipCoinListener) context;
    }
    catch(ClassCastException e)
    {
      throw new ClassCastException(context.toString() + " should implement OnFlipCoinListener interface");
    }
  }
}
