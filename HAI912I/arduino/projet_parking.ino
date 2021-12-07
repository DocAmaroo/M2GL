#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#include <ESP8266HTTPClient.h>

#include <WiFiClientSecureBearSSL.h>
// Fingerprint for demo URL, expires on June 2, 2021, needs to be updated well before this date
const uint8_t fingerprint[20] = {0x40, 0xaf, 0x00, 0x6b, 0xec, 0x90, 0x22, 0x41, 0x8e, 0xa3, 0xad, 0xfa, 0x1a, 0xe8, 0x25, 0x41, 0x1d, 0x1a, 0x54, 0xb3};
typedef struct {
  const char* id;
  const char* name;
  const float longitude;
  const float latitude;
} parking_t;

const parking_t parkings[] = {
  { "FR_MTP_ARCT","Arc de Triomphe", 3.873200750000000, 43.611002669999998},
  { "FR_MTP_TRIA","Triangle", 3.881844180000000, 43.609233840000002},
  { "FR_MTP_PITO","Pitot", 3.870191170000000, 43.612244939999997},
  { "FR_MTP_ARCE","Arceaux", 3.867490670000000, 43.611716469999998},
  { "FR_MTP_GAMB","Gambetta", 3.871374360000000, 43.606951379999998},
  { "FR_MTP_FOCH","Foch Préfecture", 3.876570840000000, 43.610749120000001},
  { "FR_MTP_COME","Comédie", 3.879761960000000, 43.608560920000002},
  { "FR_MTP_POLY","Polygone", 3.884765390000000, 43.608370960000002},
  { "FR_MTP_ANTI","Antigone", 3.888818930000000, 43.608716059999999},
  { "FR_MTP_EURO","Europa", 3.892530740000000, 43.607849710000004},
  { "FR_MTP_CORU","Corum", 3.882257730000000, 43.613888209999999},
  { "FR_MTP_GARE","Saint Roch", 3.878550720000000, 43.603291489999997},
  { "FR_MTP_CIRC","Circé Odysseum", 3.917849500000000, 43.604953770000002},
  { "FR_CAS_SABL","Notre Dame de Sablassou", 3.922295360000000, 43.634191940000001},
  { "FR_CAS_CDGA","Charles de Gaulle", 3.897762100000000, 43.628542119999999},
  { "FR_MTP_GARC","Garcia Lorca", 3.890715800000000, 43.590985089999997},
  { "FR_MTP_SABI","Sabines", 3.860224600000000, 43.583832630000003},
  { "FR_MTP_MOSS","Mosson", 3.819665540000000, 43.616237159999997},
  { "FR_MTP_MEDC","Euromédecine", 3.827723650000000, 43.638953590000000},
  { "FR_MTP_OCCI","Occitanie", 3.848597960000000, 43.634562320000001},
  { "FR_STJ_SJLC","Saint-Jean-le-Sec", 3.837931200000000, 43.570822249999999},
  { 0 } 
};

const float latitude = 43.631142;
const float longitude = 3.861489;

ESP8266WiFiMulti WiFiMulti;

void setup() {
  // Wifi Connection
  Serial.begin(115200);
  // Serial.setDebugOutput(true);

  Serial.println();
  Serial.println();
  Serial.println();

  for (uint8_t t = 4; t > 0; t--) {
    Serial.printf("[SETUP] WAIT %d...\n", t);
    Serial.flush();
    delay(1000);
  }

  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP("OniiChan!", "jag9zk1nxa6");
}

// the loop function runs over and over again forever
void loop() {
  setWifi();
}


void setWifi(){
  
  // wait for WiFi connection
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    std::unique_ptr<BearSSL::WiFiClientSecure>client(new BearSSL::WiFiClientSecure);

    // client->setFingerprint(fingerprint);
    // Or, if you happy to ignore the SSL certificate, then use the following line instead:
    client->setInsecure();
    String csvUrl = "http://data.montpellier3m.fr/sites/default/files/ressources/VilleMTP_MTP_ParkingOuv.csv"; // Url du CSV

    HTTPClient https;

     // FETCH CSV
    Serial.print("\n[i] Fetch CSV data\n");
    if (https.begin(*client, csvUrl)) {  // HTTPS

      // start connection and send HTTP header
      int httpCode = https.GET();

      // httpCode will be negative on error
      if (httpCode > 0) {
        // HTTP header has been send and Server response header has been handled
        Serial.printf("[HTTPS] GET... code: %d\n", httpCode);

        // file found at server
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          String payload = https.getString();
          Serial.printf("%s",payload);
        }
      } else {
        Serial.printf("[HTTPS] GET... failed, error: %s\n", https.errorToString(httpCode).c_str());
      }

      https.end();
    } else {
      Serial.printf("[HTTPS] Unable to connect\n");
    }

  https.end();

  }

  Serial.println("Wait 10s before next round...");
  delay(10000);
}


void fetchData(HTTPClient https, String url) {
  
}
