#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include "my_semaphore.h"

int my_sem_init ( my_semaphore *ms , unsigned int v){
    if(ms == NULL)
        return -1;

    ms->V = v;
    
    if(pthread_mutex_init(&ms->lock, NULL) != 0)
        return -1;

    if(pthread_cond_init(&ms->varcond, NULL) != 0){
        pthread_mutex_destroy(&ms->lock);
        return -1;
    }

    return 0;
}


int my_sem_wait ( my_semaphore *ms ){
    pthread_mutex_lock(&ms->lock);

    if(ms->V > 0)
        ms->V--;
    else
        pthread_cond_wait(&ms->varcond, &ms->lock);
    
    pthread_mutex_unlock(&ms->lock);
    return 0;
}

int my_sem_signal ( my_semaphore *ms ){
    pthread_mutex_lock(&ms->lock);
    
    if(ms->V > 0)
        ms->V++;
    else
        pthread_cond_signal(&ms->varcond);

    pthread_mutex_unlock(&ms->lock);
    return 0;    
}
    /*
int my_sem_wait(my_semaphore *ms) {
    pthread_mutex_lock(&ms->lock);

    while (ms->V == 0) { // Se il prof insiste sull'IF, usa IF, ma il WHILE è più sicuro
        pthread_cond_wait(&ms->varcond, &ms->lock);
    }
    ms->V--; // DECREMENTA SEMPRE QUI

    pthread_mutex_unlock(&ms->lock);
    return 0;
}

int my_sem_signal(my_semaphore *ms) {
    pthread_mutex_lock(&ms->lock);

    ms->V++; // INCREMENTA SEMPRE
    pthread_cond_signal(&ms->varcond); // SVEGLIA SEMPRE

    pthread_mutex_unlock(&ms->lock);
    return 0;
}
*/
int my_sem_destroy ( my_semaphore *ms ){
    if(ms == NULL)
        return -1;

    pthread_mutex_destroy(&ms->lock);
    pthread_cond_destroy(&ms->varcond);

    return 0;
}

int id[] = {0,1,2,3,4};
pthread_mutex_t bacchette[5];
my_semaphore salaAttesa;

void *func(void *ptr){
    int i =*(int*)ptr;

    for(int n=0; n<5; n++){
        printf("Filosofo %d sta pensando (giro %d)...\n", i, n+1);

        my_sem_wait(&salaAttesa);

        pthread_mutex_lock(&bacchette[i]);
        printf("Filosofo %d ha preso la sua bacchetta sinistra...\n", i);
        pthread_mutex_lock(&bacchette[(i+1)%5]);
        printf("Filosofo %d ha preso la sua bacchetta destra...\n", i);
    
        printf("Filosofo %d sta mangiando...\n", i);
        usleep(500000);

        pthread_mutex_unlock(&bacchette[(i+1)%5]);
        pthread_mutex_unlock(&bacchette[i]);
        
        printf("Filosofo %d ha lasciato le sue due bacchette e finito il pasto %d...\n", i, n);  
        
        my_sem_signal(&salaAttesa);
    }
    return NULL;
}

int main(){
    pthread_t filosofi[5];

    my_sem_init(&salaAttesa, 4);

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

    my_sem_destroy(&salaAttesa);
    for(int i=0; i<5; i++){
        pthread_mutex_destroy(&bacchette[i]);
    }

    return 0;
    
}