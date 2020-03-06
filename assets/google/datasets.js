const worldDataSet = [
    ['Land', 'Bestätiger', 'Tod'],
    [{v: 'CN', f: 'China'}, 80556, 3042], 
    [{v: 'KR', f: 'Süd-Korea'}, 6593, 44], 
    [{v: 'IR', f: 'Iran'}, 4747, 124], 
    [{v: 'IT', f: 'Italia'}, 3858, 148], 
    [{v: 'DE', f: 'Deutschland'}, 534, 0], 
    [{v: 'JP', f: 'Japan'}, 381, 6], 
    [{v: 'FR', f: 'Frankreich'}, 423, 7],
    [{v: 'ES', f: 'Spanien'}, 360, 5], 
    [{v: 'SG', f: 'Singapur'}, 117, 0], 
    [{v: 'HK', f: 'Honkong'}, 106, 2], 
    [{v: 'US', f: 'Vereinigte Staaten von Amerika'}, 233, 14],
    [{v: 'KW', f: 'Kuwait'}, 58, 0], 
    [{v: 'TH', f: 'Thailand'}, 47, 1], 
    [{v: 'BH', f: 'Bahrain'}, 55, 0], 
    [{v: 'TW', f: 'Taiwan'}, 44, 1], 
    [{v: 'MY', f: 'Malaysia'}, 83, 0], 
    [{v: 'AU', f: 'Australien'}, 61, 2], 
    [{v: 'AE', f: 'Vereinigte Arabische Emirate'}, 28, 0], 
    [{v: 'GB', f: 'Großbritannien'}, 116, 1], 
    [{v: 'VN', f: 'Vietnam'}, 16, 0], 
    [{v: 'CA', f: 'Kanada'}, 48, 0], 
    [{v: 'MO', f: 'Macao'}, 10, 0], 
    [{v: 'IQ', f: 'Irak'}, 40, 3], 
    [{v: 'SE', f: 'Schweden'}, 101, 0], 
    [{v: 'RU', f: 'Russland'}, 5, 0], 
    [{v: 'CH', f: 'Schweiz'}, 138, 1], 
    [{v: 'AT', f: 'Österreich'}, 47, 0], 
    [{v: 'OM', f: 'Oman'}, 16, 0], 
    [{v: 'NO', f: 'Norwegen'}, 106, 1], 
    [{v: 'IL', f: 'Israel'}, 17, 0], 
    [{v: 'GR', f: 'Griechenland'}, 45, 0], 
    [{v: 'HR', f: 'Kroatien'}, 11, 0], 
    [{v: 'IN', f: 'Indien'}, 31, 0], 
    [{v: 'PH', f: 'Philippinen'}, 5, 1], 
    [{v: 'RO', f: 'Rumänien'}, 8, 0], 
    [{v: 'LB', f: 'Libanon'}, 16, 0], 
    [{v: 'FI', f: 'Finnland'}, 15, 0], 
    [{v: 'PK', f: 'Pakistan'}, 6, 0], 
    [{v: 'NL', f: 'Niederlande'}, 82, 0], 
    [{v: 'NP', f: 'Nepal'}, 1, 0], 
    [{v: 'KH', f: 'Kambodscha'}, 1, 0], 
    [{v: 'LK', f: 'Sri Lanka'}, 1, 0], 
    [{v: 'BE', f: 'Belgien'}, 109, 0], 
    [{v: 'EG', f: 'Ägypten'}, 15, 0], 
    [{v: 'AF', f: 'Afghanistan'}, 3, 1], 
    [{v: 'DZ', f: 'Algerien'}, 17, 0], 
    [{v: 'BR', f: 'Brasilien'}, 8, 0], 
    [{v: 'MK', f: 'Nordmakedonien'}, 1, 0], 
    [{v: 'GE', f: 'Georgia'}, 9, 0], 
    [{v: 'EE', f: 'Estland'}, 10, 0], 
    [{v: 'DK', f: 'Dänemark'}, 20, 0], 
    [{v: 'SM', f: 'San Marino'}, 21, 0], 
    [{v: 'NG', f: 'Nigeria'}, 1, 0], 
    [{v: 'LT', f: 'Litauen'}, 1, 0], 
    [{v: 'NZ', f: 'Neuseeland'}, 4, 0], 
    [{v: 'BY', f: 'Weißrussland'}, 6, 0], 
    [{v: 'MX', f: 'Mexiko'}, 6, 0], 
    [{v: 'AZ', f: 'Aserbaidschan'}, 6, 0], 
    [{v: 'IS', f: 'Island'}, 37, 0], 
    [{v: 'MC', f: 'Monaco'}, 1, 0],
    [{v: 'EC', f: 'Ecuador'}, 13, 0],
    [{v: 'QA', f: 'Katar'}, 8, 0],
    [{v: 'AM', f: 'Armenien'}, 1, 0],
    [{v: 'CZ', f: 'Czech Republic'}, 12, 0],
    [{v: 'PT', f: 'Portugal'}, 9, 0],
    [{v: 'IE', f: 'Irland'}, 13, 0],
    [{v: 'SN', f: 'Senegal'}, 4, 0],
    [{v: 'PS', f: 'Palästina'}, 7, 0],
    [{v: 'CL', f: 'Chile'}, 4, 0],
    [{v: 'ID', f: 'Indonesien'}, 4, 0],
    [{v: 'MA', f: 'Marokko'}, 2, 0],
    [{v: 'SA', f: 'Saudi Arabien'}, 5, 0],
    [{v: 'HU', f: 'Ungarn'}, 4, 0],
    [{v: 'BA', f: 'Bosnien'}, 2, 0],
    [{v: 'LU', f: 'Luxemburg'}, 2, 0],
    [{v: 'DO', f: 'Dominikanische Republik'}, 1, 0],
    [{v: 'JO', f: 'Jordan'}, 1, 0],
    [{v: 'LV', f: 'Lettland'}, 1, 0],
    [{v: 'AD', f: 'Andorra'}, 1, 0],
    [{v: 'TN', f: 'Tunesien'}, 1, 0],
    [{v: 'UA', f: 'Ukraine'}, 1, 0],
    [{v: 'LI', f: 'Liechtenstein'}, 1, 0],
    [{v: 'AR', f: 'Argentinien'}, 2, 0],
    [{v: 'PL', f: 'Polen'}, 1, 0],
    [{v: 'FO', f: 'Färöer Inseln'}, 1, 0],
    [{v: 'SI', f: 'Slowenien'}, 6, 0],
    [{v: 'ZA', f: 'Südafrika'}, 1, 0],
    [{v: 'CR', f: 'Costa Rica'}, 1, 0],
    [{v: 'GI', f: 'Gibraltar'}, 1, 0],
    [{v: 'BT', f: 'Bhutan'}, 1, 0],
    [{v: 'RS', f: 'Republik Serbien'}, 1, 0],
    [{v: 'SK', f: 'Slowakei'}, 1, 0],
    [{v: 'CM', f: 'Kamerun'}, 1, 0],
    [{v: 'VA', f: 'Vatikanland'}, 1, 0],

  ];

  const germanyDataSet = [
    ['Bundesland', 'Bestätiger', 'Tod'],
    [{v: 'DE-BW', f: 'Baden-Württemberg'}, 96, 0],
    [{v: 'DE-BY', f: 'Bayern'}, 117, 0],
    [{v: 'DE-BE', f: 'Berlin'}, 19, 0],
    [{v: 'DE-BB', f: 'Brandenburg'}, 2, 0],
    [{v: 'DE-HB', f: 'Bremen'}, 4, 0], 
    [{v: 'DE-HH', f: 'Hamburg'}, 11, 0],
    [{v: 'DE-HE', f: 'Hessen'}, 16, 0],
    [{v: 'DE-MV', f: 'Mecklenburg-Vorpommern'}, 5, 0],
    [{v: 'DE-NI', f: 'Niedersachsen'}, 18, 0],
    [{v: 'DE-NW', f: 'Nordrhein-Westfalen'}, 329, 0],
    [{v: 'DE-RP', f: 'Rheinland-Pfalz'}, 10, 0],
    [{v: 'DE-SL', f: 'Saarland'}, 2, 0],
    [{v: 'DE-SN', f: 'Sachsen'}, 2, 0],
    [{v: 'DE-ST', f: 'Sachsen-Anhalt'}, 0, 0],
    [{v: 'DE-SH', f: 'Schleswig-Holstein'}, 7, 0],
    [{v: 'DE-TH', f: 'Thüringen'}, 1, 0]
  ];

    const germanyDataSetProDay = [
        ['X', 'Bestätiger', 'Tod'],
        [new Date(2020, 0, 28), 1, 0],    
        [new Date(2020, 0, 29), 4, 0],   
        [new Date(2020, 0, 30), 4, 0],  
        [new Date(2020, 0, 31), 5, 0],   
        [new Date(2020, 1, 1), 7, 0],  
        [new Date(2020, 1, 2), 8, 0],
        [new Date(2020, 1, 3), 10, 0],    
        [new Date(2020, 1, 4), 12, 0],   
        [new Date(2020, 1, 5), 12, 0],  
        [new Date(2020, 1, 6), 12, 0],   
        [new Date(2020, 1, 7), 13, 0],  
        [new Date(2020, 1, 8), 14, 0],
        [new Date(2020, 1, 9), 14, 0],    
        [new Date(2020, 1, 10), 14, 0],   
        [new Date(2020, 1, 11), 14, 0],  
        [new Date(2020, 1, 12), 16, 0],   
        [new Date(2020, 1, 13), 16, 0],  
        [new Date(2020, 1, 14), 16, 0],
        [new Date(2020, 1, 15), 16, 0],  
        [new Date(2020, 1, 16), 16, 0],
        [new Date(2020, 1, 17), 16, 0],    
        [new Date(2020, 1, 18), 16, 0],   
        [new Date(2020, 1, 19), 16, 0],  
        [new Date(2020, 1, 20), 16, 0],   
        [new Date(2020, 1, 21), 16, 0],  
        [new Date(2020, 1, 22), 16, 0],
        [new Date(2020, 1, 23), 16, 0],    
        [new Date(2020, 1, 24), 16, 0],   
        [new Date(2020, 1, 25), 16, 0],  
        [new Date(2020, 1, 26), 18, 0],   
        [new Date(2020, 1, 27), 21, 0],  
        [new Date(2020, 1, 28), 26, 0],
        [new Date(2020, 1, 29), 57, 0],
        [new Date(2020, 2, 1), 57, 0],  
        [new Date(2020, 2, 2), 129, 0],
        [new Date(2020, 2, 3), 157, 0],    
        [new Date(2020, 2, 4), 196, 0],   
        [new Date(2020, 2, 5), 400, 0]
    ];


      const germanyDataSet2 = [
    ['Bundesland', 'Bestätiger', 'Tod'],
    [{v: 'DE-BW', f: 'Baden-Württemberg'}, 73, 0],
    [{v: 'DE-BY', f: 'Bayern'}, 70, 0],
    [{v: 'DE-BE', f: 'Berlin'}, 13, 0],
    [{v: 'DE-BB', f: 'Brandenburg'}, 1, 0],
    [{v: 'DE-HB', f: 'Bremen'}, 3, 0], 
    [{v: 'DE-HH', f: 'Hamburg'}, 5, 0],
    [{v: 'DE-HE', f: 'Hessen'}, 14, 0],
    [{v: 'DE-MV', f: 'Mecklenburg-Vorpommern'}, 4, 0],
    [{v: 'DE-NI', f: 'Niedersachsen'}, 18, 0],
    [{v: 'DE-NW', f: 'Nordrhein-Westfalen'}, 181, 0],
    [{v: 'DE-RP', f: 'Rheinland-Pfalz'}, 8, 0],
    [{v: 'DE-SL', f: 'Saarland'}, 1, 0],
    [{v: 'DE-SN', f: 'Sachsen'}, 1, 0],
    [{v: 'DE-ST', f: 'Sachsen-Anhalt'}, 0, 0],
    [{v: 'DE-SH', f: 'Schleswig-Holstein'}, 7, 0],
    [{v: 'DE-TH', f: 'Thüringen'}, 1, 0]
  ];