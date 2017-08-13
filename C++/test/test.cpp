#include <iostream>
#include <vector>

using namespace std;

int main() {
  vector<int> junda(5, 10);
  for (const int &c: junda) {
    cout << c << ' ';
  } 
  cout << endl;



  return 0;
}
