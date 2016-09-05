// Taken from Unix Network Programming: The Sockets Networking API, Volume 1,
// Third Edition, by W. Richard Stevens, Bill Fenner, and Andrew M. Rudoff

// Page 700

// This program demonstrates how to use a semaphore to solve the
// synchronization problem.

#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>

#include <iostream>

using namespace std;

struct count {
    int randomNum;
    sem_t sem;
};

void *doit(void *);

int
main(int argc, char **argv)
{
    pthread_t tidA, tidB;
    // counter in shared memory
    struct count c;

    c.randomNum = 0;

    // initialize semaphore
    sem_init(&c.sem,0,1);

    srandom(1000);

    srand(time(NULL));

    // create two threads
    pthread_create(&tidA, NULL, &doit, &c);
    pthread_create(&tidB, NULL, &doit, &c);

    // wait for both threads to terminate
    pthread_join(tidA, NULL);
    pthread_join(tidB, NULL);

    exit(0);
}

void *
doit(void *vptr)
{
    int i, val;
    long r;
    struct count* c;

    c = (struct count*) vptr;

    // Each thread fetches, prints, and increments the counter 100 times.
    // We use sleep to represent work being done in the meantime.
    sem_wait(&c->sem);
    val = c->randomNum;
    r = random() % 100;
    usleep(r);
    if(c->randomNum == 0)
    {
        c->randomNum = rand() % 100 + 1;
		cout<< "storing ";
		cout<< c->randomNum;
		cout<< endl;
    }
    else {
        cout << pthread_self() << " " << val << endl;
    }
    sem_post(&c->sem);
    r = random() % 100;
    usleep(r);
}
