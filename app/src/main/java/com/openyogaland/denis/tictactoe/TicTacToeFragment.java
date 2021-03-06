package com.openyogaland.denis.tictactoe;

import android.content.Context;
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
  // constants
  static int NW = 0;
  static int N  = 1;
  static int NE = 2;
  static int W  = 3;
  static int C  = 4;
  static int E  = 5;
  static int SW = 6;
  static int S  = 7;
  static int SE = 8;
  
  // view для отображения фрагмента
  View view;
  // слушатель события отображения счёта игроков
  OnShowScoreListener onShowScoreListener;
  // ходит ли первым игрок
  boolean isPlayerFirst;
  // символы, которыми игрок и оппонент совершают свой ход
  String playerMark, opponentMark;
  // счет игрока и оппонента
  int playerScore, opponentScore;
  // количество свободных клеток
  int freeButtonCount;
  // поле для предпочтительного хода оппонента
  int preferredOpponentMove;
  
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
  
    // устанавливаем переданные через Bundle значение
    Bundle bundle = getArguments();
    if(bundle != null)
    {
      if (playerScore != 0 && opponentScore !=0)
      {
        playerScore   = bundle.getInt("playerScore");
        opponentScore = bundle.getInt("opponentScore");
      }
    }
    
    return view;
  }
  
  // инициализируем игру
  private void gameInit(View view)
  {
    freeButtonCount = buttons.length;
    preferredOpponentMove = -1;
    
    for(int i = 0; i < buttons.length; i++)
    {
      buttons[i] = view.findViewById(fields[i]); // находим кнопки по id
      buttons[i].setOnClickListener(this);       // задаём для кнопок слушателя нажатий
      buttons[i].setText("");                    // задаём для кнопок пустую начальную маркировку
    }
    // запрещаем в начале игры нажимать на кнопки
    lockButtons();
  }
  
  public void restartGame(int playerScore, int opponentScore)
  {
    this.playerScore   = playerScore;
    this.opponentScore = opponentScore;
    gameInit(view);
  }
  
  // стартуем игру (вызывается из MainActivity)
  public void gameStart(boolean isPlayerFirst)
  {
    this.isPlayerFirst = isPlayerFirst;
    
    // стартуем игру в зависимости от того, кто ходит первым
    if (isPlayerFirst)
    {
      playerMark = getString(R.string.x);
      opponentMark = getString(R.string.o);
      // просим игрока сделать ход
      unlockButtons();
      Toast.makeText(getActivity(), R.string.player_move_text, Toast.LENGTH_SHORT).show();
    }
    else
    {
      playerMark = getString(R.string.o);
      opponentMark = getString(R.string.x);
      opponentMove();
    }
  }
  
  // при клике игрока на view
  @Override
  public void onClick(View view)
  {
    // проверяем, что клик был сделан по кнопке
    if(view instanceof Button)
    {
      // проверяем, не занято ли уже поле
      if(!playerMark.equals(((Button) view).getText()) && !opponentMark.equals(((Button) view)
          .getText()))
      {
        // устанавливаем отметку игрока
        ((Button) view).setText(playerMark);
        
        // уменьшаем количество свободных полей
        freeButtonCount--;
        
        // проверяем, выиграл ли игрок
        if(isHeWinner(playerMark))
        {
          // изменяем счёт игрока
          playerScore++;
          // выводим сообщение, что выиграл игрок
          Toast.makeText(getActivity(), R.string.win, Toast.LENGTH_SHORT).show();
          gameOver();
          return;
        }
        
        // проверяем, если не осталось свободных клеток
        if (!hasMoreFreeButtons())
        {
          // выводим сообщение о ничьей
          Toast.makeText(getActivity(), R.string.tie, Toast.LENGTH_SHORT).show();
          gameOver();
          return;
        }
        
        // передаём ход оппоненту
        opponentMove();
      }
    }
  }
  
  // есть ли свободные поля
  private boolean hasMoreFreeButtons()
  {
    return (freeButtonCount > 0);
  }
  
  // защищаем кнопки от нажатия
  void lockButtons()
  {
    for(Button button : buttons)
    {
      button.setClickable(false);      
    }
  }
  
  // делаем кнопки кликабельными
  void unlockButtons()
  {
    for(Button button : buttons)
    {
      button.setClickable(true);
    }
  }
  
  // определяем, является ли победителем
  boolean isHeWinner(String mark)
  {
    // проверяем выиграл ли игрок или оппонент
    if(checkAllRowsWin(mark) || checkAllColumnsWin(mark) || checkBothDiagonalsWin(mark))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkBothDiagonalsWin(String mark)
  {
    if(checkSlashDiagonalWin(mark) || checkBackslashDiagonalWin(mark))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkSlashDiagonalWin(String mark)
  {
    if(mark.equals(buttons[NE].getText()) && mark.equals(buttons[C].getText()) && mark.equals(buttons[SW].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkBackslashDiagonalWin(String mark)
  {
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[C].getText()) && mark.equals(buttons[SE].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  // проверка всех колонок
  boolean checkAllColumnsWin(String mark)
  {
    if(checkWestColumnWin(mark) || checkCenterColumnWin(mark) || checkEastColumnWin(mark))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkWestColumnWin(String mark)
  {
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[W].getText()) && mark.equals(buttons[SW].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkCenterColumnWin(String mark)
  {
    if(mark.equals(buttons[N].getText()) && mark.equals(buttons[C].getText()) && mark.equals(buttons[S].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkEastColumnWin(String mark)
  {
    if(mark.equals(buttons[NE].getText()) && mark.equals(buttons[E].getText()) && mark.equals(buttons[SE].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  // проверка всех строк
  boolean checkAllRowsWin(String mark)
  {
    if(checkNorthRowWin(mark) || checkCenterRowWin(mark) || checkSouthRowWin(mark))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkNorthRowWin(String mark)
  {
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[N].getText()) && mark.equals(buttons[NE].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkCenterRowWin(String mark)
  {
    if(mark.equals(buttons[W].getText()) && mark.equals(buttons[C].getText()) && mark.equals(buttons[E].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  boolean checkSouthRowWin(String mark)
  {
    if(mark.equals(buttons[SW].getText()) && mark.equals(buttons[S].getText()) && mark.equals(buttons[SE].getText()))
    {
      return true;
    }
    // по-умолчанию false
    return false;
  }
  
  // ход оппонента
  void opponentMove()
  {
    // сделал ли оппонент уже свой ход
    boolean opponentAlredyMadeMove = false;
    
    // блокируем кнопки от игрока
    lockButtons();
    
    // ищем возможность оппоненту выиграть уже на этом ходу
    preferredOpponentMove = checkOneMarkToLine(opponentMark);
    if(preferredOpponentMove != -1 &&
       !playerMark.equals(buttons[preferredOpponentMove].getText()) &&
       !opponentMark.equals(buttons[preferredOpponentMove].getText()))
    {
      // если оппонент может закрыть линию своими отметками
      buttons[preferredOpponentMove].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
    
    // ищем возможность предотвратить выигрыш игрока
    preferredOpponentMove = checkOneMarkToLine(playerMark);
    if((preferredOpponentMove != -1) && (!opponentAlredyMadeMove) &&
       !playerMark.equals(buttons[preferredOpponentMove].getText()) &&
       !opponentMark.equals(buttons[preferredOpponentMove].getText()))
    {
      // если оппонент может своей отметкой не дать закрыть линию игроку
      buttons[preferredOpponentMove].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // запускаем цикл поиска подходящего свободного поля для оппонента
    while(!opponentAlredyMadeMove)
    {
      // нужно выбрать одно поле из 25:
      // одно поле центральное с весом 9 (диапазон от 1 до 9)
      // 4 поля угловых с весом 3 (диапазон от 10 до 21)
      // 4 поля крестовых с весом 1 (диапазон от 22 до 25)
      int opponentChoice = getRandomInt(1, 25);
      
      // центр
      if (opponentChoice < 10 && opponentChoice > 0)
      {
        // проверяем занят ли центр
        if(!playerMark.equals(buttons[C].getText()) && !opponentMark
            .equals(buttons[C].getText()))
        {
          buttons[C].setText(opponentMark);
          opponentAlredyMadeMove = true;
        }
      }
      
      // углы
      if (opponentChoice < 22 && opponentChoice > 9)
      {
        if (opponentChoice > 9 && opponentChoice < 13)
        {
          // NW
          if(!playerMark.equals(buttons[NW].getText()) && !opponentMark
              .equals(buttons[NW].getText()))
          {
            buttons[NW].setText(opponentMark);
            opponentAlredyMadeMove = true;
          }
        }
        if (opponentChoice > 12 && opponentChoice < 16)
        {
          // NE
          if(!playerMark.equals(buttons[NE].getText()) && !opponentMark
              .equals(buttons[NE].getText()))
          {
            buttons[NE].setText(opponentMark);
            opponentAlredyMadeMove = true;
          }
        }
        if (opponentChoice > 15 && opponentChoice < 19)
        {
          // SW
          if(!playerMark.equals(buttons[SW].getText()) && !opponentMark
              .equals(buttons[SW].getText()))
          {
            buttons[SW].setText(opponentMark);
            opponentAlredyMadeMove = true;
          }
        }
        if (opponentChoice > 18 && opponentChoice < 22)
        {
          // SE
          if(!playerMark.equals(buttons[SE].getText()) && !opponentMark
              .equals(buttons[SE].getText()))
          {
            buttons[SE].setText(opponentMark);
            opponentAlredyMadeMove = true;
          }
        }
      }
      
      // оставшиеся четыре поля
      if (opponentChoice == 22)
      {
        // N
        if(!playerMark.equals(buttons[N].getText()) && !opponentMark
            .equals(buttons[N].getText()))
        {
          buttons[N].setText(opponentMark);
          opponentAlredyMadeMove = true;
        }
      }
      if (opponentChoice == 23)
      {
        // W
        if(!playerMark.equals(buttons[W].getText()) && !opponentMark
            .equals(buttons[W].getText()))
        {
          buttons[W].setText(opponentMark);
          opponentAlredyMadeMove = true;
        }
      }
      if (opponentChoice == 24)
      {
        // E
        if(!playerMark.equals(buttons[E].getText()) && !opponentMark
            .equals(buttons[E].getText()))
        {
          buttons[E].setText(opponentMark);
          opponentAlredyMadeMove = true;
        }
      }
      if (opponentChoice == 25)
      {
        // S
        if(!playerMark.equals(buttons[S].getText()) && !opponentMark
            .equals(buttons[S].getText()))
        {
          buttons[S].setText(opponentMark);
          opponentAlredyMadeMove = true;
        }
      }
    }
  
    // уменьшаем количество свободных полей
    freeButtonCount--;
    
    // проверяем, выиграл ли оппонент
    if(isHeWinner(opponentMark))
    {
      // изменяем счёт оппонента
      opponentScore++;
      // выводим сообщение, что выиграл оппонент
      Toast.makeText(getActivity(), R.string.lose, Toast.LENGTH_SHORT).show();
      gameOver();
      return;
    }
    
    // проверяем, если не осталось свободных клеток
    if(!hasMoreFreeButtons())
    {
      // выводим сообщение о ничьей
      Toast.makeText(getActivity(), R.string.tie, Toast.LENGTH_SHORT).show();
      gameOver();
      return;
    }
    
    // снимаем защиту с кнопок
    unlockButtons();
    // просим игрока сделать очередной ход
    Toast.makeText(getActivity(), R.string.player_move_text, Toast.LENGTH_SHORT).show();
  }
  
  // возвращает целочисленной значение константы, соответствующей
  // полю, поставив отметку в которую можно закрыть линию
  int checkOneMarkToLine(String mark)
  {
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[NE].getText()))
    {
      return N;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[S].getText()))
    {
      return N;
    }
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[SW].getText()))
    {
      return W;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[E].getText()))
    {
      return W;
    }
    if(mark.equals(buttons[NE].getText()) && mark.equals(buttons[SE].getText()))
    {
      return E;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[W].getText()))
    {
      return E;
    }
    if(mark.equals(buttons[SE].getText()) && mark.equals(buttons[SW].getText()))
    {
      return S;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[N].getText()))
    {
      return S;
    }
    if(mark.equals(buttons[N].getText()) && mark.equals(buttons[NE].getText()))
    {
      return NW;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[SE].getText()))
    {
      return NW;
    }
    if(mark.equals(buttons[W].getText()) && mark.equals(buttons[SW].getText()))
    {
      return NW;
    }
    if(mark.equals(buttons[N].getText()) && mark.equals(buttons[NW].getText()))
    {
      return NE;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[SW].getText()))
    {
      return NE;
    }
    if(mark.equals(buttons[E].getText()) && mark.equals(buttons[SE].getText()))
    {
      return NE;
    }
    if(mark.equals(buttons[S].getText()) && mark.equals(buttons[SE].getText()))
    {
      return SW;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[NE].getText()))
    {
      return SW;
    }
    if(mark.equals(buttons[W].getText()) && mark.equals(buttons[NW].getText()))
    {
      return SW;
    }
    if(mark.equals(buttons[S].getText()) && mark.equals(buttons[SW].getText()))
    {
      return SE;
    }
    if(mark.equals(buttons[C].getText()) && mark.equals(buttons[NW].getText()))
    {
      return SE;
    }
    if(mark.equals(buttons[E].getText()) && mark.equals(buttons[NE].getText()))
    {
      return SE;
    }
    if(mark.equals(buttons[W].getText()) && mark.equals(buttons[E].getText()))
    {
      return C;
    }
    if(mark.equals(buttons[S].getText()) && mark.equals(buttons[N].getText()))
    {
      return C;
    }
    if(mark.equals(buttons[NW].getText()) && mark.equals(buttons[SE].getText()))
    {
      return C;
    }
    if(mark.equals(buttons[NE].getText()) && mark.equals(buttons[SW].getText()))
    {
      return C;
    }
    // по-умолчанию -1
    return -1;
  }
  
  // завершение игры
  void gameOver()
  {
    // идщкируем кнопки от нажатия
    lockButtons();
    onShowScoreListener.onShowScore(playerScore, opponentScore);
  }
  
  // метод прикрепления фрагмента к контексту (активности)
  @Override
  public void onAttach(Context context)
  {
    super.onAttach(context);
    
    try
    {
      onShowScoreListener = (OnShowScoreListener) context;
    }
    catch(ClassCastException e)
    {
      throw new ClassCastException(context.toString() + " should implement OnShowScoreListener interface");
    }
  }
  
  // метод, возвращающий случайное целое из диапазона
  public int getRandomInt(int min, int max)
  {
    return (int) (min + Math.round(Math.random() * (max - min)));
  }
}