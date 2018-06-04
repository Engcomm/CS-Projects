//
//  Main.cpp
//  GameABprune
//
//  Created by Junda Lou on 2/21/18.
//  Copyright © 2018 Junda Lou. All rights reserved.
//

#include <iostream>
#include <vector>
#include <time.h>
#include "AB_prune.cpp"

int main(int argc, const char * argv[]) {
    
    int first = -1;
    vector<vector<int> > input(8, vector<int>(8));
    Board board(input);
    AB_prune ab;
    while(true) {
        cout << "Who goes first\n1. Player\n2. Opponent\nChoice: " << flush;
        cin >> first;
        if(first == 1 || first == 2)
            break;
        else
            cout << "Not a valid choice" << endl;
    }
    while(!board.is_terminate()) {
        if(first == 1)
            ab.next_move(ab.ab_prune(board, 5, numeric_limits<int>::min(), numeric_limits<int>::max(), true, clock()), board);
        else {
            board.manual_input(0);
            first = -1;
        }
        board.print_board();
        first *= -1;
    }
    
    return 0;
}
