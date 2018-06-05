#include <stdio.h>

int main(){
    int step = 20, lower = 0, upper = 40;

    int celsius = lower;
    int fahr;
    while(celsius <= upper){
        fahr = 9/5 * celsius + 32;
        printf("%6d %6d\n", fahr, celsius);
        celsius = celsius + 1;
    }
}
