#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>

const int giri = 5;
const int N = 12;
const int C = 4;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t salire = PTHREAD_COND_INITIALIZER;
pthread_cond_t pieno = PTHREAD_COND_INITIALIZER;
pthread_cond_t scendere = PTHREAD_COND_INITIALIZER;

int passeggeriSaliti = 0;

typedef enum{LIBERO, PIENO, ARRIVATO} stato_bus_t;
stato_bus_t statoBus = LIBERO;

void* bus_thread(void* arg){
    while(1){
        pthread_mutex_lock(&mutex);

        while(passeggeriSaliti < C){
            pthread_cond_wait(&pieno, &mutex);
        }
        statoBus = PIENO;
        printf("Posti finiti, il bus sta per partire\n");
        pthread_mutex_unlock(&mutex);

        sleep(5);

        pthread_mutex_lock(&mutex);

        printf("Signori siamo arrivati, siete pregati di scendere\n");
        statoBus = ARRIVATO;

        pthread_cond_broadcast(&scendere);

        while(passeggeriSaliti > 0){
            pthread_cond_wait(&scendere, &mutex);
        }
        statoBus = LIBERO;
        printf("Bus vuoto, pronto per un altro viaggio\n");

        pthread_cond_broadcast(&salire);
        pthread_mutex_unlock(&mutex);
    }
    return NULL;
}

void* passeggeri_thread(void *arg){
    int id = *(int*)arg;
    
    while(1){
        pthread_mutex_lock(&mutex);

        while(statoBus != LIBERO || passeggeriSaliti == C){
            pthread_cond_wait(&salire, &mutex);
        }

        passeggeriSaliti++;
        printf("E' salito il passeggero %d\n", id);
        printf("Passeggeri saliti (%d/%d)\n", passeggeriSaliti, C);
        
        if (passeggeriSaliti == C) {
            pthread_cond_signal(&pieno);
        }

        while(statoBus != ARRIVATO){
            pthread_cond_wait(&scendere, &mutex);
        }
       
        passeggeriSaliti--;
        printf("E' sceso il passeggero %d\n", id);

        if(passeggeriSaliti == 0){
            pthread_cond_signal(&scendere);
        }

        pthread_mutex_unlock(&mutex);

        sleep(rand() % 5 +1);
    }
    return NULL;
}

int main(){
    pthread_t passeggeri[N];
    pthread_t bus;

    srand(time(NULL));


    pthread_create(&bus, NULL, bus_thread, NULL);
    
    for(int i=0; i<N; i++){
        int *id = malloc(sizeof(int));
        *id = i +1;
        pthread_create(&passeggeri[i], NULL, passeggeri_thread, id);
    }
    
    pthread_join(bus, NULL);

    return 0;
}