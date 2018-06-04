//
//  AB_prune.cpp
//  GameABprune
//
//  Created by Junda Lou on 2/21/18.
//  Copyright © 2018 Junda Lou. All rights reserved.
//

#include <stdio.h>
#include <vector>
#include <string>
#include <iostream>
#include <cmath>
#include <limits>
#include <time.h>
#include "Board.cpp"

using namespace std;

class AB_prune {
    
    const int PLAYER = 1, OPPO = 2, MAX = 1, MIN = 0;
    
public:
    AB_prune() { }
    
    int ab_prune(Board node, int depth, int a, int b, bool min_or_max, clock_t limit) {
        int heuristic = 0;
        if(depth == 0 || node.is_terminate() || ((float)(clock() - limit)) / CLOCKS_PER_SEC >= 30)
            return node.evaluate(min_or_max);
        if(min_or_max) {
            heuristic = numeric_limits<int>::min();
            for(Board child : node.get_neighbors(min_or_max)) {
                heuristic = max(heuristic, ab_prune(child, depth - 1, a, b, MIN, limit));
                a = max(a, heuristic);
                if(b <= a)
                    break;
            }
            return heuristic;
        } else {
            heuristic = numeric_limits<int>::max();
            for(Board child : node.get_neighbors(min_or_max)) {
                heuristic = min(heuristic, ab_prune(child, depth - 1, a, b, MAX, limit));
                b = min(b, heuristic);
                if(b <= a)
                    break;
            }
            return heuristic;
        }
    }
    
    void next_move(int heuristic, Board &board) {
        for(Board child : board.get_neighbors(1)) {
            if(child.evaluate(1) == heuristic) {
                board.set_input(child.get_input());
                break;
            }
        }
    }
    
};
