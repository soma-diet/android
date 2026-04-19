# SOMA - Sledování stravy (Android)

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)

Nativní Android aplikace pro rychlé logování kalorií a živin. Podporuje offline používání.

## Ukázky

<div align="center">

|                     Deník                     |                     Hledání                      |                       Cíle                        |
| :-------------------------------------------: | :----------------------------------------------: | :-----------------------------------------------: |
| <img src="./docs/readme/log.jpg" width="250"> | <img src="./docs/readme/search.jpg" width="250"> | <img src="./docs/readme/targets.jpg" width="250"> |

</div>

<div align="center">

|                Detail potraviny                |                 Zápis do deníku                 |
| :--------------------------------------------: | :---------------------------------------------: |
| <img src="./docs/readme/food.jpg" width="250"> | <img src="./docs/readme/entry.jpg" width="250"> |

</div>

## Funkce

- **Deník:** Přehled záznamů s podporou swipování pro rychlé smazání nebo editaci.
- **Vyhledávání:** Kombinuje vlastní lokální databázi s externím API.
- **Cíle:** Nastavení denních limitů pro různé makro a mikroživiny.
- **Upozornění:** Nastavitelné systémové připomínky pro pravidelný zápis jídla.
- **Vlastní data:** Tvorba vlastních potravin včetně nahrávání fotek z mobilu.
- **Offline:** Plná funkčnost deníku a lokálního katalogu i bez připojení k síti.

## Technologie

- **UI:** Jetpack Compose (Material 3).
- **Database:** Room (SQLite) pro lokální perzistenci.
- **Network:** Retrofit & OkHttp.
- **Auth:** Firebase Authentication.
- **Storage:** DataStore pro nastavení a preference.
- **Architektura:** MVVM se StateFlow.
