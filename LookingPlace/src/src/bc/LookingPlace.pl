child_of(joe, ralf).
child_of(mary, joe).
child_of(steve, joe).
descendent_of(X, Y) :-
    child_of(X, Y).
descendent_of(X, Y) :-
    child_of(Z, Y),
    descendent_of(X, Z).

%Gasolineras
lugares(uno).
lugares(texaco).
lugares(pumaEnergy).
lugares(texacoSERVITEX).
lugares(dlc).

%Centro de estudio
lugares(itca).
lugares(colegio_SantaInes).
lugares(colegio_Fatima).
lugares(jose_damian_villacorta).
lugares(colegio_Champagnat).

%centros de salud
lugares(hospital_SanRafael).
lugares(hospital_Climosal).
lugares(unidadDeSalud_ElPital).




