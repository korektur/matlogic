#include <iostream>

extern "C" int __cdecl factorial(int);

int main() {
	int n;
	std::cin >> n;
	std::cout << factorial(n) << std::endl;
	system("pause");
	return 0;
}