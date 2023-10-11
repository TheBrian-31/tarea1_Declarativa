%Gasolineras
coordenadas(uno,13.673590405234433,-89.30026884964994).
coordenadas(texaco,13.672337005703938, -89.29746646998609).
coordenadas(pumaEnergy,13.673741750836976, -89.29809018907693).
coordenadas(texacoSERVITEX,13.671844617795232, -89.27581557904251).
coordenadas(dlc,13.671716339506927, -89.28181245691182).

%Centro de estudio
coordenadas(itca,13.672882771653159, -89.28002725209757).
coordenadas(colegio_SantaInes,13.671475248217835, -89.28594158313732).
coordenadas(colegio_Fatima,13.671869329105517, -89.2841582042338).
coordenadas(jose_damian_villacorta,13.673039263887832, -89.28321852819083).
coordenadas(colegio_Champagnat,13.678052265395262, -89.2832514814886).

%centros de salud
coordenadas(hospital_SanRafael,13.671683256670143, -89.27898162548787).
coordenadas(hospital_Climosal,13.671398971249756, -89.29241966660045).
coordenadas(unidadDeSalud_ElPital,13.672563837295582, -89.29262709072745).
coordenadas(clinica_SagradaFamilia,13.67458769330064, -89.29029306254928).
coordenadas(clinica_Dental,13.671769716310365, -89.27934583448305).

%Parques
coordenadas(parque_DanielaHernandez,13.67367207390962, -89.28861966311094).
coordenadas(parque_SanMartin,13.673613246823143, -89.28518777948871).
coordenadas(parque_LaFamilia,13.682124606684614, -89.28292835893274).
coordenadas(cafetalon,13.67629764324958, -89.28331288662865).
coordenadas(parque_ElEspino,13.699968447024647, -89.27863048370791).

%Centros comerciales
coordenadas(centroComercial_SantaRosa,13.678783322658784, -89.30039327121855).
coordenadas(centroComercial_Holanda,13.673778950533743, -89.29421900488472).
coordenadas(plaza_Merliot,13.678753762183995, -89.27612915154727).
coordenadas(plaza_ElCarmen,13.674890042479959, -89.28808984720588).
coordenadas(la_Skina,13.677817764381556, -89.29755396648788).

%Calles
coordenadas_lugar(n2_Calle_Ote,13.672963495146654, -89.28239868905219).
coordenadas_lugar(n9_Avenida_Sur_2-1, 13.669643377756415, -89.28255566589242).
coordenadas_lugar(n3_avenida_norte_y_8_Calle_te_14,13.669765876693948, -89.28591543977916).
coordenadas_lugar(C. Paralela a Sta. Rosa,13.6780985541056, -89.29781395753207).

lugar(itca).
lugar(n2_Calle_Ote).
lugar(n9_Avenida_Sur_2-1).
lugar(n3_avenida_norte_y_8_Calle_te_14).
lugar(colegio_SantaInes).

%Relaciones calles
conecta_con(itca,n2_Calle_Ote).
conecta_con(n2_Calle_Ote, n9_Avenida_Sur_2-1).
conecta_con(n9_Avenida_Sur_2-1,n3_avenida_norte_y_8_Calle_te_14).
conecta_con(n3_avenida_norte_y_8_Calle_te_14,colegio_SantaInes).

%Ruta de un lugar a otro
ir_hacia(X, Y, Ruta) :-
    abolish(visitado, 1),
    assert(visitado(X)),
    ir_hacia_rec(X, Y, [X], Ruta).

ir_hacia_rec(X, Y, Visitados, Ruta):-
    conecta_con(X, Y),
    reverse([Y|Visitados], Ruta).

ir_hacia_rec(X, Y, Visitados, Ruta):-
    conecta_con(X, Z),
    not(visitado(Z)),
    assert(visitado(Z)),
    ir_hacia_rec(Z, Y, [Z|Visitados], Ruta).

ir_hacia_rec(X, Y, Visitados, Ruta):-
    conecta_con(X, Z),
    visitado(Z),
    fail.

%regla que asocia los lugares con las coordenadas de ubicacion de estos.
% Regla para obtener las coordenadas de un lugar dado
obtener_coordenadas_lugar(Lugar, X, Y) :- coordenadas(Lugar, X, Y).

% Reglas para obtener solo el nombre de los lugares
obtener_nombre_lugar(Lugar) :- coordenadas(Lugar, _, _).





