#include "functions.h"

int main(){
	FILE* f = freopen("out.txt", "w", stdout);
        print_out<PLOG>(2, 32);
	fclose(f);
	return 0;
}
