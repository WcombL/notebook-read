#include <stdio.h>

int main(){
    double nc = 0;
    int c;
    while((c = getchar()) != EOF)
        if(c == ' ')
            ++nc;
    printf("%.0f\n", nc);
}
