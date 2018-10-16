package com.siswadi.sudoku;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.siswadi.sudoku.View.BoardGridView;
import com.siswadi.sudoku.View.CustomCellTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by esisw on 12/26/2017.
 */

public class SudokuEngine {
    /*Fields*/
    private Random rand = new Random();
    private int[][] board;
    private int[][] gameBoard;
    private int[][] userBoard;

    private Set<Integer> notAllowedSet = new TreeSet<Integer>();
    private Set<Integer> allowedSet = new TreeSet<Integer>();
    //deletedPoints are the modifiable cells
    public List<Point> deletedPointSet = new ArrayList<Point>();

    public SudokuEngine()
    {
        board = new int[9][9];
        gameBoard = new int[9][9];
        userBoard = new int[9][9];
    }

    public void generateBoard()
    {
        int n;
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                //System.out.println("ROW: " + row + " COL: " + col);
                createNotAllowedSet(row, col);
                createAllowedSet(row, col);
                //printNotA();
                if(allowedSet.isEmpty()) col = -1;//if a possible number does not exist
                    //-------------------------------------------------------------------------
                else//if a possible number exist, check if n is allowed or not
                {
                    n = getNum();
                    //System.out.print("n --> " + n + "\n");
                    //when n is allowed, set it to that cell
                    board[row][col] = n;
                }
                notAllowedSet.clear();
                allowedSet.clear();
            }
        }
    }
    private void createAllowedSet(int row, int col)
    {
        //create allowed set
        for(int i = 1; i <= 9; i++)
        {
            if(!notAllowedSet.contains(i))
            {
                allowedSet.add(i);
            }
        }
    }
    private void createNotAllowedSet(int row, int col)
    {
        /**CHECKKUPLEFT**/
        for(int i = 0; i < row; i++)
        {
            notAllowedSet.add(board[i][col]);
        }
        for(int i = 0; i < col; i++)
        {
            notAllowedSet.add(board[row][i]);
        }

        /***CHECK3by3***/
        /****CHECK LEFT****/
        //if col is 0,3,6 no need to check left
        if(col == 1 || col == 4 || col == 7)
        {
            notAllowedSet.add(board[row][col-1]);
        }
        if(col == 2 || col == 5 || col == 8)
        {
            notAllowedSet.add(board[row][col-1]);
            notAllowedSet.add(board[row][col-2]);
        }
        /****CHECK UP****/
        //if col is 0,3,6 no need to check up
        int startRow, startCol;

        if(col < 3) startCol = 0;
        else if(col < 6 && col > 2) startCol = 3;
        else startCol = 6;

        if(row == 1 || row == 4 || row == 7)
        {
            startRow = row - 1;

            notAllowedSet.add(board[startRow][startCol]);
            notAllowedSet.add(board[startRow][startCol + 1]);
            notAllowedSet.add(board[startRow][startCol + 2]);
        }
        if(row == 2 || row == 5 || row == 8)
        {
            startRow = row - 2;

            notAllowedSet.add(board[startRow][startCol]);
            notAllowedSet.add(board[startRow][startCol + 1]);
            notAllowedSet.add(board[startRow][startCol + 2]);

            notAllowedSet.add(board[startRow + 1][startCol]);
            notAllowedSet.add(board[startRow + 1][startCol + 1]);
            notAllowedSet.add(board[startRow + 1][startCol + 2]);
        }
    }
    public int getNum()
    {
        int a = -99;
        int n = rand.nextInt(allowedSet.size());
        int stop = 0;
        for(int x : allowedSet)
        {
            if(stop == n) a = x;
            stop++;
        }
        return a;
    }

    /* Store the answers */
    public void duplicateBoard()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                gameBoard[i][j] = board[i][j];
                userBoard[i][j] = board[i][j];
            }
        }
    }
    //delete some cells
    public void prepareGameBoard(int difficulty)
    {
        //store the answer first in userBoard before deleting
        this.duplicateBoard();
        int a, b, c, d, e, f,g;
        for(int i = 0; i < 9; i++)
        {
            a = rand.nextInt(9);
            b = rand.nextInt(9);
            c = rand.nextInt(9);
            d = rand.nextInt(9);
            e = rand.nextInt(9);
            f = rand.nextInt(9);
            g = rand.nextInt(9);

            for(int j = 0; j < 9; j++)
            {
                boolean difficultyLevel;
                if(difficulty == 0)//-----beginner
                    difficultyLevel = (j == a || j == b);
                else if(difficulty == 1)//intermediate
                    difficultyLevel = (j == a || j == b|| j == c || j == d);
                else //-------------------extreme
                    difficultyLevel = (j == a || j == b|| j == c || j == d || j == e || j == f || j == g);

                if(difficultyLevel)
                {
                    gameBoard[i][j] = -99;

                    Point x = new Point(i, j);
                    deletedPointSet.add(x);
                }
            }
        }
    }

    public void printGameBoard(GridView gridView)
    {
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                CustomCellTextView selectedCell = (CustomCellTextView)gridView.getChildAt(getPos(row, col));
                if(gameBoard[row][col] == -99)
                {
                    selectedCell.setText("");

                }
                else
                {
                    selectedCell.setNumber(gameBoard[row][col]);
                }
            }
        }
    }

    public void printAnswerBoard(GridView gridView)
    {
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                CustomCellTextView selectedCell = (CustomCellTextView)gridView.getChildAt(getPos(row, col));
                selectedCell.setNumber(board[row][col]);
            }
        }
    }

    //rows&cols to position in gridView
    private int getPos(int row, int col)
    {
        return 9*row + col;
    }

    public boolean deletedPointSetContains(int pos)
    {
        boolean toReturn = false;
        Point p = new Point(pos/9, pos%9);
        for(Point px : deletedPointSet)
        {
            if(px.col == p.col && px.row == p.row) toReturn = true;
        }
        return toReturn;
    }


    public boolean verify(GridView board)
    {
        //Copy board to userBoard array
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                String cell = (String)((CustomCellTextView)board.getChildAt(getPos(i, j))).getText();
                int n;

                if(cell.equals(""))
                {
                    n = -99;
                }
                else
                {
                    n = Integer.parseInt(cell);
                }
                userBoard[i][j] = n;
            }
        }

        //General Idea is to check if the sum of each rows and cols are 45
        int sumH = 0;
        int sumV = 0;
        int[] check = new int[18];
        //fill check array with 0
        for(int i = 0; i < 18; i++)
        {
            check[i] = 0;
        }

        //add rows check to 45
        //add columns check to 45
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                sumH += userBoard[i][j];
                sumV += userBoard[j][i];
            }
            if(sumH == 45) check[i] = 1;
            if(sumV == 45) check[i + 9] = 1;
            sumH = 0;
            sumV = 0;
        }
        int totalSum = 0;
        for(int i = 0; i < 18; i++)
        {
            totalSum += check[i];
        }

        if(totalSum == 18) return true;
        else return false;
    }

    private class Point
    {
        private int row;
        private int col;

        public Point(int x, int y)
        {
            row = x;
            col = y;
        }
    }


}
