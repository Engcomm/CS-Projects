//
//  Board.cpp
//  GameABprune
//
//  Created by Junda Lou on 2/24/18.
//  Copyright © 2018 Junda Lou. All rights reserved.
//

#include <stdio.h>
#include <vector>
#include <iostream>
using namespace std;

class Board {
    
    vector<vector<int> > board;
    const int SIZE = 8, PLAYER = 1, OPPO = 2, MAX = 1, MIN = 0;
    
public:
    
    Board() : board() { }
    Board(vector<vector<int> > input) : board(input) { }
    
    bool is_terminate() {
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j <= SIZE - 4; ++j) {
                if(board[i][j] != 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3])
                    return true;
            }
        }
        for(int i = 0; i <= SIZE; ++i) {
            for(int j = 0; j <= SIZE - 4; ++j) {
                if(board[j][i] != 0 && board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i])
                    return true;
            }
        }
        return false;
    }
    
    vector<Board> get_neighbors(bool min_or_max) {
        vector<Board> result;
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j < SIZE; ++j) {
                if(board[i][j] == 0) {
                    Board neighbor;
                    vector<vector<int> > tmp;
                    for(int k = 0; k < SIZE; ++k) {
                        tmp.push_back(board[k]);
                    }
                    if(min_or_max)
                        tmp[i][j] = PLAYER;
                    else
                        tmp[i][j] = OPPO;
                    neighbor.set_input(tmp);
                    result.push_back(neighbor);
                }
            }
        }
        return result;
    }
    
    int evaluate(bool min_or_max) {
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j <= SIZE - 4; j++) {
                if(min_or_max) {
                    if(board[i][j] == PLAYER && board[i][j + 1] == PLAYER && board[i][j + 2] == PLAYER && board[i][j + 3] == PLAYER)
                        return 5000;
                    else if(board[i][j] == PLAYER && board[i][j + 1] == PLAYER && board[i][j + 2] == PLAYER && ((j != 0 && board[i][j - 1] == 0) || board[i][j + 3] == 0))
                        return 1000;
                    else if(board[i][j] == PLAYER && board[i][j + 1] == PLAYER && ((j != 0 && board[i][j - 1] == 0) || board[i][j + 2] == 0)) {
                        if(board[i][j + 3] == 0)
                            return 500;
                        else
                            return 300;
                    } else if (board[i][j] == PLAYER && ((j != 0 && board[i][j - 1] == 0) || board[i][j + 1] == 0)) {
                        if(board[i][j + 2] == 0 && board[i][j + 3])
                            return 100;
                        else if (board[i][j + 2] == 0)
                            return 50;
                        else
                            return 10;
                    }
                    
                } else {
                    if(board[i][j] == OPPO && board[i][j + 1] == OPPO && board[i][j + 2] == OPPO && board[i][j + 3] == OPPO)
                        return -5000;
                    else if(board[i][j] == OPPO && board[i][j + 1] == OPPO && board[i][j + 2] == OPPO && ((j != 0 && board[i][j - 1] == 0) || board[i][j + 3] == 0))
                        return -1000;
                    else if(board[i][j] == OPPO && board[i][j + 1] == OPPO && ((j != 0 && board[i][j - 1] == 0) || board[i][j + 2] == 0))
                        return -500;
                }
            }
        }
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j <= SIZE - 4; j++) {
                if(min_or_max) {
                    if(board[j][i] == PLAYER && board[j + 1][i] == PLAYER && board[j + 2][i] == PLAYER && board[j + 3][i] == PLAYER)
                        return 5000;
                    else if(board[j][i] == PLAYER && board[j + 1][i] == PLAYER && board[j + 2][i] == PLAYER && ((j != 0 && board[j - 1][i] == 0) || board[j + 3][i] == 0))
                        return 1000;
                    else if(board[j][i] == PLAYER && board[j + 1][i] == PLAYER && ((j != 0 && board[j - 1][i] == 0) || board[j + 2][i] == 0)) {
                        if(board[j + 3][i] == 0)
                            return 500;
                        else
                            return 300;
                    } else if(board[j][i] == PLAYER && ((j != 0 && board[j - 1][i] == 0) || board[j + 1][i] == 0)) {
                        if(board[j + 2][i] == 0 && board[j + 3][i] == 0)
                            return 100;
                        else if(board[j + 2][i] == 0)
                            return 50;
                        else
                            return 10;
                    }
                } else {
                    if(board[j][i] == OPPO && board[j + 1][i] == OPPO && board[j + 2][i] == OPPO && board[j + 3][i] == OPPO)
                        return -5000;
                    else if(board[j][i] == OPPO && board[j + 1][i] == OPPO && board[j + 2][i] == OPPO && ((j != 0 && board[j - 1][i] == 0) || board[j + 3][i] == 0))
                        return -1000;
                    else if(board[j][i] == OPPO && board[j + 1][i] == OPPO && ((j != 0 && board[j - 1][i] == 0) || board[j + 2][i] == 0))
                        return -500;
                }
            }
        }
        return 0;
    }
    
    void manual_input(int player_or_oppo) {
        string position;
        int row = -1, col = -1;
        while(true) {
            if(player_or_oppo)
                cout << "Choose where to input manually: " << flush;
            else
                cout << "Choose opponent's next move: " << flush;
            cin >> position;
            if(position[0] >= 'A' && position[0] <= 'Z')
                row = (int)(tolower(position[0]) - 'A');
            else if(position[0] >= 'a' && position[0] <= 'z')
                row = (int)(position[0] - 'a');
            if(position[1] >= '0' && position[1] <= '9')
                col = (int)(position[1] - '0') - 1;
            if(row < 0 || row >= SIZE || col < 0 || col >= SIZE || position.length() > 2 || board[row][col] != 0)
                cout << "Unvalid inputs, please retry" << endl;
            else
                break;
        }
        if(player_or_oppo)
            board[row][col] = PLAYER;
        else
            board[row][col] = OPPO;
    }
    
    void print_board() {
        char row = 'A';
        cout << "  ";
        for(int i = 0; i < SIZE; ++i)
            cout << i + 1 << " ";
        cout << endl;
        for(int i = 0; i < SIZE; ++i) {
            cout << row << " " << flush;
            for(int j = 0; j < SIZE; ++j) {
                if(board[i][j] == 1)
                    cout << "O " << flush;
                else if(board[i][j] == 2)
                    cout << "X " << flush;
                else
                    cout << "- " << flush;
            }
            cout << endl;
            row = (char)((int)row + 1);
        }
    }
    
    void set_input(vector<vector<int> > input) {
        board = input;
    }
    
    vector<vector<int> > get_input() {
        return board;
    }
    
    void set_board(int row, int col, int value) {
        board[row][col] = value;
    }
    
};
