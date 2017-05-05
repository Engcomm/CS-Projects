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

int main()
{
  Rectangle rect(3,4);
  Rectangle rect2(5,6);
  cout << "Rect area: " << rect.area() << endl;
  cout << "Rect2 area: " << rect2.area() << endl;
  return 0;


}
