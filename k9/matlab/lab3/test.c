#include <stdio.h>

int main() {
    int i;
    unsigned int b = 1;
    for(i = 0; i < 10; ++i) {
        if(b == 16) {
            b = 1;
        }
        b = b << 1u;
        printf("%d ", b);
    }
}
