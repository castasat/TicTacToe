package com.openyogaland.denis.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
  // constants
  private static String PLAYER_MARKER    = "X";
  private static String OPPONENT_MARKER  = "O";
  
  // fields
  private Button northwest, north, northeast, west, center, east, southwest, south, southeast;
  private int emptySquaresLeft;
  
  // первый метод, вызываемый при создании андроид-активности
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    // задаём xml-файл расположения графических элементов
    setContentView(R.layout.activity_main);
  
    // находим кнопки по id из xml-файла
    northwest = findViewById(R.id.northwest);
    north = findViewById(R.id.north);
    northeast = findViewById(R.id.northeast);
    west = findViewById(R.id.west);
    center = findViewById(R.id.center);
    east = findViewById(R.id.east);
    southwest = findViewById(R.id.southwest);
    south = findViewById(R.id.south);
    southeast = findViewById(R.id.southeast);
  
    // задаём для кнопок слушателя нажатий, реализующего интерфейс OnClickListener
    northwest.setOnClickListener(this);
    north.setOnClickListener(this);
    northeast.setOnClickListener(this);
    west.setOnClickListener(this);
    center.setOnClickListener(this);
    east.setOnClickListener(this);
    southwest.setOnClickListener(this);
    south.setOnClickListener(this);
    southeast.setOnClickListener(this);
    
    initGame();
  }
  
  // инициализация игры
  private void initGame()
  {
    // задаём для кнопок начальную маркировку (пустую)
    northwest.setText("");
    north.setText("");
    northeast.setText("");
    west.setText("");
    center.setText("");
    east.setText("");
    southwest.setText("");
    south.setText("");
    southeast.setText("");
    
    // количество пустых клеток в начале игры
    emptySquaresLeft = 9;
  }
  
  public void onClick(View view)
  {
    // если нажата кнопка
    if(view instanceof Button)
    {
      int     buttonId    = view.getId();
      String  buttonMark  = (String) ((Button) view).getText();
  
      // если клетка ещё свободна
      // noinspection StatementWithEmptyBody
      if("".equals(buttonMark))
      {
        // проверяем какая кнопка и ставим на ней крестик
        switch(buttonId)
        {
          case R.id.northwest:
            northwest.setText(PLAYER_MARKER);
            break;
    
          case R.id.north:
            north.setText(PLAYER_MARKER);
            break;
    
          case R.id.northeast:
            northeast.setText(PLAYER_MARKER);
            break;
    
          case R.id.west:
            west.setText(PLAYER_MARKER);
            break;
    
          case R.id.center:
            center.setText(PLAYER_MARKER);
            break;
    
          case R.id.east:
            east.setText(PLAYER_MARKER);
            break;
    
          case R.id.southwest:
            southwest.setText(PLAYER_MARKER);
            break;
    
          case R.id.south:
            south.setText(PLAYER_MARKER);
            break;
    
          case R.id.southeast:
            southeast.setText(PLAYER_MARKER);
            break;
        } // конец switch()
        
        // ход игрока запускает игровой раунд
        gameRound();
        
      } // если клетка была ещё свобюодна
      else {} // если клик на занятой клетке, не предпринимаем ничего
    } // если была нажата кнопка
  } // был вызван метод onClick()
  
  // завершение игры
  private void gameOver(String string)
  {
    // выводим сообщение с результатом завершения игры
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    // стартуем новую игру
    initGame();
  }
  
  // метод описывает алгоритм одного раунда игры
  private void gameRound()
  {
    // проверяем, выиграл ли игрок (у него маркер PLAYER_MARKER)
    if(checkWinner(PLAYER_MARKER))
    {
      // если игрок выиграл, завершаем игру его выигрышем
      gameOver(getString(R.string.win));
      return;
    }
  
    // уменьшаем количество пустых клеток
    emptySquaresLeft--;
  
    // если свободных клеток не осталось
    if(emptySquaresLeft == 0)
    {
      // завершаем игру ничьёй
      gameOver(getString(R.string.tie));
    }
  
    // оппонент делает ход
    opponentMove();
  
    // проверяем, выиграл ли оппонент (у него маркер OPPONENT_MARKER)
    if(checkWinner(OPPONENT_MARKER))
    {
      // если оппонент выиграл, завершаем игру его выигрышем
      gameOver(getString(R.string.lose));
    }
  }
  
  // проверяем выиграл ли игрок, который играет данным маркером
  @SuppressWarnings("RedundantIfStatement")
  private boolean checkWinner(String marker)
  {
    // проверяем, что переданный маркер имеет допустимое значение
    if (marker.equals(PLAYER_MARKER) || marker.equals(OPPONENT_MARKER))
    {
      // проверяем верхнюю строку
      if (northwest.getText().equals(marker) && north.getText().equals(marker) &&
          northeast.getText().equals(marker))
      {
        return true;
      }
      // проверяем среднюю строку
      else if(west.getText().equals(marker) && center.getText().equals(marker) &&
         east.getText().equals(marker))
      {
        return true;
      }
      // проверяем нижнюю строку
      else if(southwest.getText().equals(marker) && south.getText().equals(marker) &&
         southeast.getText().equals(marker))
      {
        return true;
      }
      // проверяем левую колонку
      else if (northwest.getText().equals(marker) && west.getText().equals(marker) &&
          southwest.getText().equals(marker))
      {
        return true;
      }
      // проверяем среднюю колонку
      else if(north.getText().equals(marker) && center.getText().equals(marker) &&
         south.getText().equals(marker))
      {
        return true;
      }
      // проверяем правую колонку
      else if (northeast.getText().equals(marker) && east.getText().equals(marker) &&
          southeast.getText().equals(marker))
      {
        return true;
      }
      // проверяем диагональ слэш
      else if (southwest.getText().equals(marker) && center.getText().equals(marker) &&
          northeast.getText().equals(marker))
      {
        return true;
      }
      // проверяем диагональ бэкслэш
      else if (northwest.getText().equals(marker) && center.getText().equals(marker) &&
          southeast.getText().equals(marker))
      {
        return true;
      }
      // если выигрыша нет
      else
      {
        return false;
      }
    }
    // если передан неразрешённый маркер, выигрыша нет
    return false;
  }
  
  // ход оппонента
  private void opponentMove()
  {
    // применяем стратегию оппонента
    useOpponentStrategy();
    
    // проверяем, выиграл ли оппонент (у него маркер OPPONENT_MARKER)
    if(checkWinner(OPPONENT_MARKER))
    {
      // если оппонент выиграл, завершаем игру его выигрышем
      gameOver(getString(R.string.lose));
      return;
    }
 
    // уменьшаем количество пустых клеток
    emptySquaresLeft--;
  
    // если свободных клеток не осталось
    if(emptySquaresLeft == 0)
    {
      // завершаем игру ничьёй
      gameOver(getString(R.string.tie));
    }
  }
  
  // стратегия оппонента
  private void useOpponentStrategy()
  {
    // если не удаётся применить выигрышную стратегию оппонента
    if(!applyWinningOpponentStrategy())
    {
      // если неприменима беспроигрышная стратегия оппонента
      if(!applyNonLoseOpponentStrategy())
      {
        // используем стратегию наибольших возможностей
        applyMaxPossibilitiesStrategy();
      }
    }
  }
  
  // выигрышная стратегия оппонента
  private boolean applyWinningOpponentStrategy()
  {
    // если свободен центр
    if(center.getText().equals(""))
    {
      // если маркер в центре принесёт выигрыш оппоненту
      if((north.getText().equals(OPPONENT_MARKER) && south.getText().equals(OPPONENT_MARKER)) ||
         (west.getText().equals(OPPONENT_MARKER) && east.getText().equals(OPPONENT_MARKER)) ||
         (northwest.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER))
         || (northeast.getText().equals(OPPONENT_MARKER) && southwest.getText().equals
          (OPPONENT_MARKER)))
      {
        // он ставит свой маркер в центре
        center.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен северо-запад
    else if(northwest.getText().equals(""))
    {
      // если маркер в северо-западе принесёт выигрыш оппоненту
      if((north.getText().equals(OPPONENT_MARKER) && northeast.getText().equals(OPPONENT_MARKER))
         || (west.getText().equals(OPPONENT_MARKER) && southwest.getText().equals(OPPONENT_MARKER))
         || (center.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер в северо-запад
        northwest.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен северо-восток
    else if(northeast.getText().equals(""))
    {
      // если маркер в северо-востоке принесёт выигрыш оппоненту
      if((north.getText().equals(OPPONENT_MARKER) && northwest.getText().equals(OPPONENT_MARKER))
         || (east.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER))
         || (center.getText().equals(OPPONENT_MARKER) && southwest.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер в северо-восток
        northeast.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен юго-запад
    else if(southwest.getText().equals(""))
    {
      // если маркер в юго-западе принесёт выигрыш оппоненту
      if((south.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER))
         || (west.getText().equals(OPPONENT_MARKER) && northwest.getText().equals(OPPONENT_MARKER))
         || (center.getText().equals(OPPONENT_MARKER) && northeast.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер в юго-запад
        southwest.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен юго-восток
    else if(southeast.getText().equals(""))
    {
      // если маркер в юго-востоке принесёт выигрыш оппоненту
      if((south.getText().equals(OPPONENT_MARKER) && southwest.getText().equals(OPPONENT_MARKER))
         || (east.getText().equals(OPPONENT_MARKER) && northeast.getText().equals(OPPONENT_MARKER))
         || (center.getText().equals(OPPONENT_MARKER) && northwest.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер в юго-восток
        southeast.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен север
    else if(north.getText().equals(""))
    {
      // если маркер на севере принесёт выигрыш оппоненту
      if((northwest.getText().equals(OPPONENT_MARKER) && northeast.getText().equals(OPPONENT_MARKER)
          || (center.getText().equals(OPPONENT_MARKER)) && south.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер на севере
        north.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен запад
    else if(west.getText().equals(""))
    {
      // если маркер на западе принесёт выигрыш оппоненту
      if((northwest.getText().equals(OPPONENT_MARKER) && southwest.getText().equals(OPPONENT_MARKER)
          || (center.getText().equals(OPPONENT_MARKER)) && east.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер на западе
        west.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен восток
    else if(east.getText().equals(""))
    {
      // если маркер на востоке принесёт выигрыш оппоненту
      if((northeast.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER)
          || (center.getText().equals(OPPONENT_MARKER)) && west.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер на востоке
        east.setText(OPPONENT_MARKER);
        return true;
      }
    }
    
    // если свободен юг
    else if(south.getText().equals(""))
    {
      // если маркер на юге принесёт выигрыш оппоненту
      if((southwest.getText().equals(OPPONENT_MARKER) && southeast.getText().equals(OPPONENT_MARKER)
          || (center.getText().equals(OPPONENT_MARKER)) && north.getText().equals(OPPONENT_MARKER)))
      {
        // он ставит свой маркер на юге
        south.setText(OPPONENT_MARKER);
        return true;
      }
    }
    // если не удалось применить выигрышную стратегию, поскольку не совпали условия
    return false;
  }
  
  // беспроигрышная стратегия оппонента
  private boolean applyNonLoseOpponentStrategy()
  {
    // если свободен центр
    if(center.getText().equals(""))
    {
      // если маркер в центре спасёт оппонента от проигрыша
      if((north.getText().equals(PLAYER_MARKER) && south.getText().equals(PLAYER_MARKER)) || (
          west.getText().equals(PLAYER_MARKER) && east.getText().equals(PLAYER_MARKER)) || (
             northwest.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER))
         || (northeast.getText().equals(PLAYER_MARKER) && southwest.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер в центре
        center.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен северо-запад
    else if(northwest.getText().equals(""))
    {
      // если маркер в северо-западе спасёт оппонента от проигрыша
      if((north.getText().equals(PLAYER_MARKER) && northeast.getText().equals(PLAYER_MARKER))
         || (west.getText().equals(PLAYER_MARKER) && southwest.getText().equals(PLAYER_MARKER))
         || (center.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер в северо-запад
        northwest.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен северо-восток
    else if(northeast.getText().equals(""))
    {
      // если маркер в северо-востоке спасёт оппонента от проигрыша
      if((north.getText().equals(PLAYER_MARKER) && northwest.getText().equals(PLAYER_MARKER))
         || (east.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER))
         || (center.getText().equals(PLAYER_MARKER) && southwest.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер в северо-восток
        northeast.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен юго-запад
    else if(southwest.getText().equals(""))
    {
      // если маркер в юго-западе спасёт оппонента от проигрыша
      if((south.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER))
         || (west.getText().equals(PLAYER_MARKER) && northwest.getText().equals(PLAYER_MARKER))
         || (center.getText().equals(PLAYER_MARKER) && northeast.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер в юго-запад
        southwest.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен юго-восток
    else if(southeast.getText().equals(""))
    {
      // если маркер в юго-востоке спасёт оппонента от проигрыша
      if((south.getText().equals(PLAYER_MARKER) && southwest.getText().equals(PLAYER_MARKER))
         || (east.getText().equals(PLAYER_MARKER) && northeast.getText().equals(PLAYER_MARKER))
         || (center.getText().equals(PLAYER_MARKER) && northwest.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер в юго-восток
        southeast.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен север
    else if(north.getText().equals(""))
    {
      // если маркер на севере спасёт оппонента от проигрыша
      if((northwest.getText().equals(PLAYER_MARKER) && northeast.getText().equals(PLAYER_MARKER)
          || (center.getText().equals(PLAYER_MARKER)) && south.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер на севере
        north.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен запад
    else if(west.getText().equals(""))
    {
      // если маркер на западе спасёт оппонента от проигрыша
      if((northwest.getText().equals(PLAYER_MARKER) && southwest.getText().equals(PLAYER_MARKER)
          || (center.getText().equals(PLAYER_MARKER)) && east.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер на западе
        west.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен восток
    else if(east.getText().equals(""))
    {
      // если маркер на востоке спасёт оппонента от проигрыша
      if((northeast.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER)
          || (center.getText().equals(PLAYER_MARKER)) && west.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер на востоке
        east.setText(OPPONENT_MARKER);
        return true;
      }
    }
  
    // если свободен юг
    else if(south.getText().equals(""))
    {
      // если маркер на юге спасёт оппонента от проигрыша
      if((southwest.getText().equals(PLAYER_MARKER) && southeast.getText().equals(PLAYER_MARKER)
          || (center.getText().equals(PLAYER_MARKER)) && north.getText().equals(PLAYER_MARKER)))
      {
        // он ставит свой маркер на юге
        south.setText(OPPONENT_MARKER);
        return true;
      }
    }
    // если неприменима беспроигрышная стратегия, поскольку не совпали условия
    return false;
  }
  
  // стратегия максимальных возможностей для оппонента
  private void applyMaxPossibilitiesStrategy()
  {
    // если центр свободен
    if (center.getText().equals(""))
    {
      center.setText(OPPONENT_MARKER);
    }
    // если северо-запад свободен
    else if(northwest.getText().equals(""))
    {
      northwest.setText(OPPONENT_MARKER);
    }
    // если северо-восток свободен
    else if(northeast.getText().equals(""))
    {
      northeast.setText(OPPONENT_MARKER);
    }
    // если юго-запад свободен
    else if(southwest.getText().equals(""))
    {
      southwest.setText(OPPONENT_MARKER);
    }
    // если юго-восток свободен
    else if(southeast.getText().equals(""))
    {
      southeast.setText(OPPONENT_MARKER);
    }
    // если север свободен
    else if(north.getText().equals(""))
    {
      north.setText(OPPONENT_MARKER);
    }
    // если запад свободен
    else if(west.getText().equals(""))
    {
      west.setText(OPPONENT_MARKER);
    }
    // если восток свободен
    else if(east.getText().equals(""))
    {
      east.setText(OPPONENT_MARKER);
    }
    // если юг свободен
    else if(south.getText().equals(""))
    {
      south.setText(OPPONENT_MARKER);
    }
  }
}