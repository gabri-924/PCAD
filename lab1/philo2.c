#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

int id[] = {0,1,2,3,4};
pthread_mutex_t bacchette[5];

void *func(void *ptr){
    int i =*(int*)ptr;

    for(int n=0; n<5; n++){
        printf("Filosofo %d sta pensando (giro %d)...\n", i, n+1);

        if(i==4){
            pthread_mutex_lock(&bacchette[(i+1)%5]);
            printf("Filosofo %d ha preso la sua bacchetta sinistra...\n", i);
            pthread_mutex_lock(&bacchette[i]);
            printf("Filosofo %d ha preso la sua bacchetta destra...\n", i);
        }
        else{
            pthread_mutex_lock(&bacchette[i]);
            printf("Filosofo %d ha preso la sua bacchetta sinistra...\n", i);
            pthread_mutex_lock(&bacchette[(i+1)%5]);
            printf("Filosofo %d ha preso la sua bacchetta destra...\n", i);
        }
       

        printf("Filosofo %d sta mangiando...\n", i);
        //sleep(100000);

        pthread_mutex_unlock(&bacchette[(i+1)%5]);
        pthread_mutex_unlock(&bacchette[i]);
          
        
        printf("Filosofo %d ha lasciato le sue due bacchette e finito il pasto %d...\n", i, n);
    }
    return NULL;
}

int main(){
    pthread_t filosofi[5];

    for(int i=0; i<5; i++){
        pthread_mutex_init(&bacchette[i], NULL);
    }

    pthread_t th1, th2, th3, th4, th5;
    for(int i=0; i<5; i++){
        pthread_create(&filosofi[i],NULL, func, (id+i));
    }

    for(int i = 0; i < 5; i++) {
        pthread_join(filosofi[i], NULL);
    }

    return 0;
    
}