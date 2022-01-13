#include <Arduino.h>
#include <WiFi.h>
#include <WebServer.h>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <cmath>

// Wifi parameters
#define WIFI_SSID "OniiChan!"
#define WIFI_PWD "jag9zk1nxa6"

// Coordonnée de la Faculté des Sciences
#define FDS_LATITUDE 43.631142f
#define FDS_LONGITUDE 3.861489f

#define MONTPELLIER3M_BASE_URL "https://data.montpellier3m.fr/"
#define MONTPELLIER3M_API_PATH_PREFIX "sites/default/files/ressources/"
#define MONTPELLIER3M_API_PATH_SUFFIX ".xml"

typedef struct {
  String date_time;
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

String _buildURL(const char *id) {
  String res = MONTPELLIER3M_BASE_URL MONTPELLIER3M_API_PATH_PREFIX;
  res += id;
  res += MONTPELLIER3M_API_PATH_SUFFIX;
  return res;
}

// Very quick and dirty XML parsing
String _getXmlValue(const String line, const String &tag) {
  String res = "";
  size_t b1 = line.indexOf('<');
  if (b1 != -1) {
    size_t e1 = line.indexOf('>', b1);
    if ((e1 != -1) && (e1 - b1 - 1 == tag.length()) && !line.compareTo(tag.substring(b1 + 1, e1 - b1 - 1))) {
      size_t e2 = line.indexOf('>');
      if (e2 != -1) {
        size_t b2 = line.indexOf('<', e2);
        if ((b2 != -1) && (e2 - b2 - 1 == (tag.length() + 1))
            && (line[b2 + 1] == '/') && !line.compareTo(tag.substring(b2 + 2, e2 - b2 - 2))) {
          res = line.substring(e1 + 1, b2 - e1 - 1);
        }
      }
    }
  }
  return res;
}

// Set web server port number to 80
WebServer server(80);

// Beginning of the Web page
void handleRoot() {
  String page = "<!DOCTYPE html>";
  page += "<html>";

  page += "<head>";
  page += " <title>The closest parking</title>";
  page += " <meta http-equiv='refresh' content='60' name='viewport' content='width=device-width, initial-scale=1' charset='utf-8'/>";
  // page += " <link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>";
  page += "</head>";

  page += "<body>";
  page += " <p>Vous chercher des places de parkings chaudes de votre régions ??";
  page += " <h4>Entrer vos coordonnées</h4>";
  page += " <form>";
  page += "   <div>";
  page += "     <label for='latitude'>Latitude</label>";
  page += "     <input type='text' name='latitude' id='latitude' required>";
  page += "   </div>";
  page += "   <div>";
  page += "     <label for='longitude'>Longitude</label>";
  page += "     <input type='text' name='longitude' id='longitude' required>";
  page += "   </div>";
  page += "   <div>";
  page += "     <input type='submit' value='Rechercher !'>";
  page += "   </div>";
  page += " </form>";
  page += "</body>";

  page += "</html>";

  server.send(200, "text/html", page);
}

// Page Not found
void handleNotFound(){
  server.send(404, "text/plain","404: Not found");
}


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

  client->setInsecure();
  HTTPClient https;

  // Print it
  for(int i=0; i < n; i++) {
    // Appel à l'API    
    String url = buildUrl(ptrCpy->id);
    if (https.begin(*client, url)) {
      int httpCode = https.GET();

      // on success
      if (httpCode > 0) {
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          String payload = https.getString();
          Serial.printf("%s",payload);

          // Check si le parking est ouvert
          
          // Check si le parking à une/des places disponibles
        }
      } else {
        Serial.printf("[HTTPS] GET... failed, error: %s\n", https.errorToString(httpCode).c_str());
      }

      https.end();
    } else {
      Serial.printf("[HTTPS] Unable to connect\n");
    }

    // Afficher le parking
    Serial.println("[i] Name: %s | distance: %f\n", ptrCpy[i].name, ptrCpy[i].distance);
  }

  https.end();
}


void setup() {
  Serial.begin(115200);
  delay(3000);
  Serial.println("\n");

  // Connect to WiFi
  Serial.print("Connecting to ");
  Serial.println(WIFI_SSID);
  WiFi.persistent(false);
  WiFi.begin(WIFI_SSID, WIFI_PWD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  // Print local IP address and start web server
  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  server.on("/", handleRoot);   // Homepage loading
  server.onNotFound(handleNotFound);  // Not found page loading
  
  server.begin();
  
  Serial.println("Serveur web actif");
}

void loop() {
  server.handleClient();
}
