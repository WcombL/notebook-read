#include <stdio.h>

int mian(){
    int c, b1=0, t1=0, n1=0;

    printf("Input some characters, then pass Ctril+D.\n");
    while((c = getchar()) != EOF){
        if(c == ' '){
            ++b1;
        }else if(c == '\t'){
            ++t1;
        }else if(c == '\n'){
            ++n1;
        }
    }
    printf("Count of blanks is %d, count of tabs is %d, count of newlines is %d.\n", b1, t1, n1);

    return 0;
}
