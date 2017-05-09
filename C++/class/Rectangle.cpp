#include <iostream>
#include <cstdlib>
using namespace std;

class Rectangle
{
  int width, height;
public:
  Rectangle(int, int);
  int area() {return(width*height);}

};

Rectangle::Rectangle(int a, int b) 
{
  width = a;
  height = b;
}

