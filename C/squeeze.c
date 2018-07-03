#include <stdio.h>

void squeeze(char s[], int c);
void squeeze1(char s1[], char s2[]);

int main(){
    char s[] = "lilei wujunbo";
    squeeze(s, 117);
    printf("%s\n", s);
}

void squeeze(char s[], int c){
    int i, j;

    for(i = j = 0; s[i] != '\0'; i++ ){
        //printf("%d\t", s[i]);
        if(s[i] != c)
            s[j++] = s[i];
    }
    s[j] = '\0';
}

void squeeze1(char s1[], char s2[]){
    int i, j, k;

    i = 0;
    while (s2[i] != '\0') {
        j = 0;
        while (s1[j] != '\0') {
            if (s1[j] == s2[i]) {
                k = j;
                while ((s1[k] = s1[++k]) != '\0')
                    ;
            } else
                ++j;
        }
        ++i;
    }
}
