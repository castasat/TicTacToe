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
    return view;
  }
  
  // инициализируем игру
  private void gameInit(View view)
  {
    playerScore   = 0;
    opponentScore = 0;
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
  
    // делаем наиболее выгодный ход, исходя из расположения
    // проверяем занят ли центр
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[C].getText()) && !opponentMark
        .equals(buttons[C].getText()))
    {
      buttons[C].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // проверяем, заняты ли углы
    // NW
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[NW].getText()) && !opponentMark
        .equals(buttons[NW].getText()))
    {
      buttons[NW].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // NE
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[NE].getText()) && !opponentMark
        .equals(buttons[NE].getText()))
    {
      buttons[NE].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // SW
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[SW].getText()) && !opponentMark
        .equals(buttons[SW].getText()))
    {
      buttons[SW].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // SE
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[SE].getText()) && !opponentMark
        .equals(buttons[SE].getText()))
    {
      buttons[SE].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
    
    // проверяем, заняты ли поля креста
    // N
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[N].getText()) && !opponentMark
        .equals(buttons[N].getText()))
    {
      buttons[N].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // W
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[W].getText()) && !opponentMark
        .equals(buttons[W].getText()))
    {
      buttons[W].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // E
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[E].getText()) && !opponentMark
        .equals(buttons[E].getText()))
    {
      buttons[E].setText(opponentMark);
      opponentAlredyMadeMove = true;
    }
  
    // S
    if(!opponentAlredyMadeMove && !playerMark.equals(buttons[S].getText()) && !opponentMark
        .equals(buttons[S].getText()))
    {
      buttons[S].setText(opponentMark);
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
    lockButtons();
    // TODO отображение счёта игроков
    // TODO рестарт игры
  }
}