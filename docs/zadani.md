# SOMA Diet Tracking App

## Popis

Aplikace SOMA je komplexní a uživatelsky přívětivý nástroj určený k detailnímu sledování každodenních stravovacích návyků. Její hlavní podstatou je usnadnit uživatelům orientaci v jejich nutričním příjmu a pomoci jim dosáhnout jejich osobních cílů v oblasti zdraví a fitness. Uživatel si do aplikace jednoduše zaznamenává zkonzumovaná jídla a nápoje v průběhu dne. SOMA následně na základě rozsáhlé databáze potravin automaticky vypočítává celkový energetický příjem (v kaloriích) a detailně ho štěpí na makroživiny (bílkoviny, sacharidy, tuky) a mikroživiny (sůl, vláknina).



Kromě veřejné databáze známých potravin aplikace nabízí uživateli vytvářet své vlastní potraviny s jeho vlastními velikostmi porcí (plátek chleba = 60g). Tyto potraviny poté může přidávat do svého denního logu stejně jako potraviny veřejné.



SOMA je navržena tak, aby vyhovovala širokému spektru uživatelů, kteří mají různé motivace pro sledování svého jídelníčku. Hlavní cílové skupiny tvoří:

- **Lidé s cílem úpravy tělesné hmotnosti:** Uživatelé, kteří se snaží zhubnout budou hlavně sledovat množství zkonzumovaných kalorií za den a ocení veřejnou databázi jídel.
- **Sportovci a fitness nadšenci:** Pro tuto skupinu je užitečné sledování ostatních makroživin, zejména bílkovin. Tato skupina také ocení přidávání vlastních jídel a vlastních porcí, což jim nejen umožňuje přesnější trackování, ale také lehčí a bez nutnosti všechno vážit.
- **Osoby se specifickými dietními potřebami:** Lidé dodržující specifické diety ocení trackování jak makroživin tak mikroživin (keto = míříme na málo sacharidů).

## Entity

### Food

- vlastnosti: name, brand, image, isLiquid, servings, macronutrients, micronutrients
- macronutrients a micronutrients uloženy na 100g

#### Serving

- slouží k identifikaci porce jídla
- vlastnosti: name, size (in grams)

### LogEntry

- záznam v deníku zapsaných potravin
- vlastnosti: food, quantity, serving, timestamp
  - snědl 4x plátek toustového chleba (80g)  - food = toustový chleba, quantity = 4, serving = plátek, 20g

### Target

- pair - živina: množství
- např.: kcal=2000

## Spojení s API

Pro aplikaci mám postavený backend ve Springu, který funguje přes autentizaci pomocí Firebase. Firebase umožňuje automaticky vytvářet anonymní token pro uživatele, který lze použít pro přístup k mému backendu bez potřeby, aby se uživatel manuálně přihlašoval. Pokud tomu čas dovolí přidal bych i přihlašovací stránku přes OAuth, což umožňí propojit reálný účet s jeho záznamy.



Backend poskytuje endpointy na hledání jídel z databáze na backendu. Dále poskytuje endpointy na spravování uživatelských dat v cloudu, tudíž kdyby aplikace byla online-only, tak by nebylo potřeba jídla ukládat offline, každopádně pokud jsem to naplánoval správně, tak by se jídla měla synchronizovat s offline databází v mobilu, což znamená využití všech CRUD endpointů v databázi jídel, záznamů jídel a nutričních cílů.

### Vzorové endpointy

#### Hledání informací o konkrétním jídle

GET */api/foods/{id}*

<img src="D:\Documents\ctu\projects\soma\soma-app\docs\zadani.assets\image-20260329195917466.png" alt="image-20260329195917466"  />

- imageFilename je jméno obrázku na serveru, který jde potom načíst z /images/(small/large)_imageFilename
- barcode, type, author jsou vlastnosti, které jsou k budoucímu rozšíření (neplánuji implementovat jejich využití zatím)

#### Hledání všech jídel

GET */api/foods*

​			![image-20260329200234984](D:\Documents\ctu\projects\soma\soma-app\docs\zadani.assets\image-20260329200234984.png)  ![image-20260329200306078](D:\Documents\ctu\projects\soma\soma-app\docs\zadani.assets\image-20260329200306078.png)

- je implementováno pomocí stránkování, tudíž vrací informace o objektu stránky a slouží k postupnému načítání dat
- v content je seznam načtených food itemů a jejich konkrétní detaily

### Přehled endpointů

![image-20260329200447627](D:\Documents\ctu\projects\soma\soma-app\docs\zadani.assets\image-20260329200447627.png)