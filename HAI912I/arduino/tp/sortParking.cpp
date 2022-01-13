#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <cmath>

using namespace std;

typedef struct {
  string date_time;
  bool open;
  unsigned int free;
  unsigned int total;
} parking_data_t;

typedef struct {
  const char* id;
  const char* name;
  float longitude;
  float latitude;
  double distance;
} parking_t;

// Coordonnée de la Faculté des Sciences
#define FDS_LATITUDE 43.631142f
#define FDS_LONGITUDE 3.861489f

const parking_t parkings[] = {
  { "FR_MTP_ARCT","Arc de Triomphe", 3.873200750000000, 43.611002669999998, -1},
  { "FR_MTP_TRIA","Triangle", 3.881844180000000, 43.609233840000002, -1},
  { "FR_MTP_PITO","Pitot", 3.870191170000000, 43.612244939999997, -1},
  { "FR_MTP_ARCE","Arceaux", 3.867490670000000, 43.611716469999998, -1},
  { "FR_MTP_GAMB","Gambetta", 3.871374360000000, 43.606951379999998, -1},
  { "FR_MTP_FOCH","Foch Préfecture", 3.876570840000000, 43.610749120000001, -1},
  { "FR_MTP_COME","Comédie", 3.879761960000000, 43.608560920000002, -1},
  { "FR_MTP_POLY","Polygone", 3.884765390000000, 43.608370960000002, -1},
  { "FR_MTP_ANTI","Antigone", 3.888818930000000, 43.608716059999999, -1},
  { "FR_MTP_EURO","Europa", 3.892530740000000, 43.607849710000004, -1},
  { "FR_MTP_CORU","Corum", 3.882257730000000, 43.613888209999999, -1},
  { "FR_MTP_GARE","Saint Roch", 3.878550720000000, 43.603291489999997, -1},
  { "FR_MTP_CIRC","Circé Odysseum", 3.917849500000000, 43.604953770000002, -1},
  { "FR_CAS_SABL","Notre Dame de Sablassou", 3.922295360000000, 43.634191940000001, -1},
  { "FR_CAS_CDGA","Charles de Gaulle", 3.897762100000000, 43.628542119999999, -1},
  { "FR_MTP_GARC","Garcia Lorca", 3.890715800000000, 43.590985089999997, -1},
  { "FR_MTP_SABI","Sabines", 3.860224600000000, 43.583832630000003, -1},
  { "FR_MTP_MOSS","Mosson", 3.819665540000000, 43.616237159999997, -1},
  { "FR_MTP_MEDC","Euromédecine", 3.827723650000000, 43.638953590000000, -1},
  { "FR_MTP_OCCI","Occitanie", 3.848597960000000, 43.634562320000001, -1},
  { "FR_STJ_SJLC","Saint-Jean-le-Sec", 3.837931200000000, 43.570822249999999, -1},
  { 0 } 
};


static int compareDistanceStruct (const void * a, const void * b) {
  const parking_t *fa = (parking_t *) a;
  const parking_t *fb = (parking_t *) b;

  if (fa->distance > fb->distance) return 1;
  else if (fa->distance < fb->distance) return -1;
  else return 0;  
}


double getDistance(float x1, float y1, float x2, float y2) {
  return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
}

void getNearestParking(int n)
{
  const parking_t *ptr = parkings;

  // Copy the parkings value;
  parking_t *ptrCpy = (parking_t *) malloc(sizeof(*ptr));
  mempcpy(ptrCpy, ptr, sizeof(parkings));

  // Get the distance between us
  int i=0;
  while(ptr->id) {
    double distance = getDistance(FDS_LONGITUDE, FDS_LATITUDE, ptr->longitude, ptr->latitude);
    ptrCpy[i].distance = distance;
    ++ptr;
    i++;
  }

  // Ordered from nearest to farthest
  qsort(ptrCpy, i, sizeof(parking_t), compareDistanceStruct);

  // Print it
  for(int i=0; i < n; i++) {
    // Check si le parking est ouvert
    // Check si le parking à une/des places disponibles
    printf("[i] Name: %s | distance: %f\n", ptrCpy[i].name, ptrCpy[i].distance);
  }
}



int main() {
  int n = 5;

  printf("[i] Program start -----------------------------------------------\n");
  
  printf("\n[i] Best distances ------\n");
  getNearestParking(n);

  printf("[i] Program stop\n");

  return 0;
}


// Archives -------------------
// void displayDistance() {

//   const parking_t *ptr = parkings;

//   while(ptr->id) {
//     double distance = getDistance(FDS_LONGITUDE, FDS_LATITUDE, ptr->longitude, ptr->latitude);
//     printf("[i] Name: %s | distance: %f\n", ptr->name, distance);
//     ++ptr;
//   }
// }
// --- In main
//printf("[i] All distances ------\n");
//displayDistance();