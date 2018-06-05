#include <stdio.h>

int main(){
    int c;

    printf("EOF is %d\n", EOF);
    c = getchar();
    
    while(c != EOF){
        putchar(c);
        c = getchar();
    }
}
