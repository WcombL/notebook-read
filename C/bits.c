#include <stdio.h>

unsigned getbits(unsigned x, int p, int n);

int main(){
    unsigned a = getbits(8, 4, 3);

    printf("%d\n", a);
}

/*返回x中从第p位开始的n位 右起 从0开始*/
unsigned getbits(unsigned x, int p, int n){
    return (x >> (p+1-n)) & ~(~0 << n);
}
