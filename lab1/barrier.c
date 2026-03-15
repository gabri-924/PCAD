#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include "my_barrier.h"

unsigned int pthread_my_barrier_init (my_barrier *mb, unsigned int v){
    if(v == 0) 
        return -1;

    mb->vinit = v;
    mb->val = 0;

    pthread_mutex_init(&mb->lock, NULL);
    pthread_cond_init(&mb->varcond, NULL);

    return 0;
}

unsigned int pthread_my_barrier_wait (my_barrier *mb ){
    pthread_mutex_lock(&mb->lock);
    mb->val++;

    if(mb->val == mb->vinit){
        mb->val = 0;
        pthread_cond_broadcast(&mb->varcond);
    }
    else{
        pthread_cond_wait(&mb->varcond, &mb->lock);
    }

    pthread_mutex_unlock(&mb->lock);
    return 0;
}


my_barrier bar;

void* func(void* arg) {
    int id = *(int*)arg;
    printf("Thread %i: Arrivo alla barriera...\n", id);
    
    pthread_my_barrier_wait(&bar);
    
    printf("Thread %i: Superata!\n", id);
    return NULL;
}

int main() {
    pthread_t t[3];
    pthread_my_barrier_init(&bar, 3);

    int ids[3];
    for(int i=0; i<3; i++) {
        ids[i] = i;
        pthread_create(&t[i], NULL, func, &ids[i]);
    }

    for(int i=0; i<3; i++) pthread_join(t[i], NULL);
    
    return 0;
}