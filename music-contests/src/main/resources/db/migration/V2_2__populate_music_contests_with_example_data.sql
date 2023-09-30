insert into address (address_id, country, city, postal_code, street, building_number, additional_info) values
('a76accd6-c94c-42a8-8fad-d6e063396bab', 'admin', 'admin', '00-000', 'admin', 'admin', 'tylko do użytku administratora'),
('79b4f4ce-1d82-48c4-81ef-b28ef85b04f0', 'Polska', 'Warszawa', '01-376', 'Jabłońska', '20/15', 'Wejście koło żabki'),
('7a63bf62-7099-441d-bb87-1a6c4b057c61', 'Polska', 'Gdańsk', '80-253', 'Dworcowa', '2', 'Wejście od tyłu'),
('25551862-30eb-4f62-b881-557dbfa5013a', 'Polska', 'Kołobrzeg', '78-100', 'Jana Pawła II', '50/12', ''),
('47c66804-7e69-45ef-b67d-aee3c4f87faa', 'Polska', 'Poznań', '60-778', 'Zygmuntowska', '15', ''),
('64e980bd-c010-4c82-b652-d0f8d1f84568', 'Polska', 'Katowice', '40-036', 'Juliusza Ligonia', '7', '');

insert into music_school (music_school_id, name, patron, primary_degree, secondary_degree, address_id) values
('de5afa41-a858-4a33-9015-08f080180dea', 'admin', 'admin admin', true, true, 'a76accd6-c94c-42a8-8fad-d6e063396bab'),
('cec42d7b-6d45-474f-b69e-d13786ab7ac5', 'Szkoła Muzyczna1', '', true, true, '79b4f4ce-1d82-48c4-81ef-b28ef85b04f0'),
('0e618e04-1ac8-4d94-ad84-5f12b897c50f', 'Szkoła Muzyczna2', 'Wojciech Kilar', true, false, '7a63bf62-7099-441d-bb87-1a6c4b057c61'),
('95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4', 'Szkoła Muzyczna3', 'Amadeusz Mozart', false, true, '25551862-30eb-4f62-b881-557dbfa5013a'),
('9a0d8162-7818-4332-88ec-3a30d7077d38', 'Szkoła Muzyczna4', 'Marek Jankowski', true, false, '47c66804-7e69-45ef-b67d-aee3c4f87faa');

insert into headmaster (headmaster_id, name, surname, email, pesel, music_school_id) values
('4d79b7eb-9465-42de-b7dd-e16285264679', 'admin', 'admin', 'admin@music-contests.pl', '$2a$12$pCXbGnUzgqExHW/BjzZkw.R7bviaeCTOdrG9zq/yTQ2dEhRpHIr1q', 'de5afa41-a858-4a33-9015-08f080180dea'), --00000000000
('79a07cd8-9c49-4a84-9a8e-cddca4f7f7bf', 'Dyrektor1', 'Dyrektorski1', 'dyrektor1@mejl.com', '$2a$12$btLyh6//KkL33FUcf3MU7Obi2Ex1gvVlCEHPegFHuW093GQ.nT/sW', 'cec42d7b-6d45-474f-b69e-d13786ab7ac5'), --00000000001
('d82ef282-d566-41fa-a9fc-a1167830a385', 'Dyrektor2', 'Dyrektorski2', 'dyrektor2@mejl.com', '$2a$12$XuSkJ.zqFuAPazkxeDtBaOJWtV//FlHVAJTFQv7jAgS1goIA33VN.', '0e618e04-1ac8-4d94-ad84-5f12b897c50f'), --00000000002
('7aa719c5-269b-4629-8102-2319d25714da', 'DyrektorNauczyciel3', 'Dyrektorski3', 'dyrektornauczyciel3@mejl.com', '$2a$12$iRCevu9EYX2lFSCOjeGgD.15Oel4V//mYvMOOOW6.rPgd.WCfDwK2', '95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4'), --00000000003
('bf69a445-24c8-4749-9ccc-3f4fd9d3be2b', 'DyrektorNauczyciel4', 'Dyrektorski4', 'dyrektornauczyciel4@mejl.com', '$2a$12$O5cXYnY6nOpg5VonUe1dW.nPh4/WyTy0oaHNOdglA.0GC/jO5ltG.', '9a0d8162-7818-4332-88ec-3a30d7077d38'); --00000000004

insert into teacher (teacher_id, name, surname, email, pesel, instrument, music_school_id) values
('c37ca26e-653b-4b07-95aa-4c5f42e5a84b', 'admin', 'admin', 'admin@music-contests.pl', '$2a$12$pCXbGnUzgqExHW/BjzZkw.R7bviaeCTOdrG9zq/yTQ2dEhRpHIr1q', 'fortepian', 'de5afa41-a858-4a33-9015-08f080180dea'), --00000000000
('23840207-d3d3-4d52-bcb8-3f977fa2bc8b', 'Nauczyciel1', 'Nauczycielski1', 'nauczyciel1@mejl.com', '$2a$12$KKgtul5ON.ytpMXEKswHaeY9erI3Lq9KiR0G6huBdtjOQS.exCfgW', 'skrzypce', 'cec42d7b-6d45-474f-b69e-d13786ab7ac5'), --00000000011
('658a5272-633c-49a7-8239-fea73ba530f8', 'Nauczyciel2', 'Nauczycielski2', 'nauczyciel2@mejl.com', '$2a$12$eUpsNLJcFah3sv6yEfivxeTbsAgdhIIqAy6wFl5PdmaYn4U4Hh/Am', 'akordeon', '0e618e04-1ac8-4d94-ad84-5f12b897c50f'), --00000000022
('89af8865-9ff5-4949-8d70-03dfebdd116b', 'Nauczyciel3', 'Nauczycielski3', 'nauczyciel3@mejl.com', '$2a$12$xfGDHVT//AsYpxVbHPr52u3pxz7HCYEDNRCNb4DNsu5D1W1Pba4Gi', 'wibrafon', '95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4'), --00000000033
('9fff444d-8ac0-431c-bfe1-049938ef78a1', 'DyrektorNauczyciel3', 'Dyrektorski3', 'dyrektornauczyciel3@mejl.com', '$2a$12$iRCevu9EYX2lFSCOjeGgD.15Oel4V//mYvMOOOW6.rPgd.WCfDwK2', 'gong', '95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4'), --00000000003
('8b180ade-ec53-43f3-af8b-10583a150f61', 'DyrektorNauczyciel4', 'Dyrektorski4', 'dyrektornauczyciel4@mejl.com', '$2a$12$O5cXYnY6nOpg5VonUe1dW.nPh4/WyTy0oaHNOdglA.0GC/jO5ltG.', 'trąbka', '9a0d8162-7818-4332-88ec-3a30d7077d38'); --00000000004

insert into student (student_id, name, surname, email, pesel, class, education_duration, music_school_degree, music_school_id, main_instrument, second_instrument, teacher_id) values
('5c6e5f7b-a856-4d76-a493-a10f0d1ff6b2', 'admin', 'admin', 'admin@music-contests.pl', '$2a$12$pCXbGnUzgqExHW/BjzZkw.R7bviaeCTOdrG9zq/yTQ2dEhRpHIr1q', 'FIRST', 'FOUR_YEAR', 'FIRST', 'de5afa41-a858-4a33-9015-08f080180dea', 'fortepian', 'fortepian', 'c37ca26e-653b-4b07-95aa-4c5f42e5a84b'), --00000000000
(uuid_generate_v4(), 'Uczeń1', 'Uczniowski1', 'uczeń1@mejl.com', '$2a$12$/ntdOEgZJUBbJZGBWyyGG.tFoSGSWlOba8FrgLjVXkl6TGAg3ZQxW', 'SECOND', 'FOUR_YEAR', 'FIRST', 'cec42d7b-6d45-474f-b69e-d13786ab7ac5', 'tuba', 'wiolonczela', '23840207-d3d3-4d52-bcb8-3f977fa2bc8b'), --00000000111
(uuid_generate_v4(), 'Uczeń2', 'Uczniowski2', 'uczeń2@mejl.com', '$2a$12$SYqEpw/eIBbilFmKA7bBg.XVkmCAnKnMoW8itUjVUZNV.K9NziGOK', 'FOURTH', 'FOUR_YEAR', 'SECOND', 'cec42d7b-6d45-474f-b69e-d13786ab7ac5', 'flet', '', '23840207-d3d3-4d52-bcb8-3f977fa2bc8b'), --00000000222
(uuid_generate_v4(), 'Uczeń3', 'Uczniowski3', 'uczeń3@mejl.com', '$2a$12$JptEhWcVL82qP0RJHJ5zp.LuVFAfWR1.tdA46Of7IUCR9hCy/K.ie', 'FOURTH', 'SIX_YEAR', 'FIRST', 'cec42d7b-6d45-474f-b69e-d13786ab7ac5', 'waltornia', 'skrzypce', '23840207-d3d3-4d52-bcb8-3f977fa2bc8b'), --00000000333
(uuid_generate_v4(), 'Uczeń4', 'Uczniowski4', 'uczeń4@mejl.com', '$2a$12$bJTBI38J7fxX/gmO1BnXDeMyGatvLlw1cM3dRBGMosJSNBBvm1WSK', 'FIRST', 'FOUR_YEAR', 'FIRST', '0e618e04-1ac8-4d94-ad84-5f12b897c50f', 'puzon', 'kontrafagot', '658a5272-633c-49a7-8239-fea73ba530f8'), --00000000444
(uuid_generate_v4(), 'Uczeń5', 'Uczniowski5', 'uczeń5@mejl.com', '$2a$12$UwHQ6jGvPDLnZMrPWzZCk.4Lwyp1DOBVM2Us4.De9hOULYWQc0tLm', 'FIRST', 'FOUR_YEAR', 'SECOND', '0e618e04-1ac8-4d94-ad84-5f12b897c50f', 'lutnia', '', '658a5272-633c-49a7-8239-fea73ba530f8'), --00000000555
(uuid_generate_v4(), 'Uczeń6', 'Uczniowski6', 'uczeń6@mejl.com', '$2a$12$PkaqHAhhCwUKnwVNOUknRejHRYCkaSkxGnCd9iPgpDm5yEc5K3IK6', 'THIRD', 'SIX_YEAR', 'SECOND', '0e618e04-1ac8-4d94-ad84-5f12b897c50f', 'kontrabas', '', '89af8865-9ff5-4949-8d70-03dfebdd116b'), --00000000666
(uuid_generate_v4(), 'Uczeń7', 'Uczniowski7', 'uczeń7@mejl.com', '$2a$12$UMlbtO8q3MVUf88eURobueL3rjA1gylkOqegrN9AOcPDmcWrdiTMi', 'THIRD', 'FOUR_YEAR', 'FIRST', '95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4', 'skrzypce', 'grzechotka', '89af8865-9ff5-4949-8d70-03dfebdd116b'), --00000000777
(uuid_generate_v4(), 'Uczeń8', 'Uczniowski8', 'uczeń8@mejl.com', '$2a$12$EjnTQtI3p546k63Vx/r8B.aNVtI5SgDKtzR7xKleotrDZuPQsFbTC', 'SECOND', 'SIX_YEAR', 'FIRST', '95a5c9bc-a756-4122-9fbc-f87bbd0a3ed4', 'tuba', 'kastaniety', '9fff444d-8ac0-431c-bfe1-049938ef78a1'), --00000000888
(uuid_generate_v4(), 'Uczeń9', 'Uczniowski9', 'uczeń9@mejl.com', '$2a$12$HzWDgqAuxlNLhmmZGsWtgeNEFlIJtXnhA7n/Hsp6eVq1syzbSWzne', 'FIRST', 'SIX_YEAR', 'SECOND', '9a0d8162-7818-4332-88ec-3a30d7077d38', 'skrzypce', 'kontrafagot', '8b180ade-ec53-43f3-af8b-10583a150f61'), --00000000999
(uuid_generate_v4(), 'Uczeń10', 'Uczniowski10', 'uczeń10@mejl.com', '$2a$12$H38KYsyCHTF48YS8pgTlNumm2gHpHApFxfU4qWaiihaDl/iGHvXsa', 'FIRST', 'SIX_YEAR', 'SECOND', '9a0d8162-7818-4332-88ec-3a30d7077d38', 'gitara', 'saksofon', '8b180ade-ec53-43f3-af8b-10583a150f61'), --00000001999
(uuid_generate_v4(), 'Uczeń11', 'Uczniowski11', 'uczeń11@mejl.com', '$2a$12$PIO6uguO0AAwCFBRfpIi6.SJ1r9l/0/9NvuPYMxGU9/2UgvvzG18q', 'FIRST', 'SIX_YEAR', 'SECOND', '9a0d8162-7818-4332-88ec-3a30d7077d38', 'obój', '', '8b180ade-ec53-43f3-af8b-10583a150f61'); --00000002999

insert into competition_location (competition_location_id, name, address_id) values
('8ee25de2-92af-453e-b56b-bffeca89b8b6', 'Szkoła Muzyczna1', '79b4f4ce-1d82-48c4-81ef-b28ef85b04f0'),
('0d0b3f11-72e3-4d6c-97be-4cf2a0e6f920', 'Regionalne Centrum Kultury', '64e980bd-c010-4c82-b652-d0f8d1f84568');

insert into competition (competition_id, name, instrument, online, primary_degree, secondary_degree, beginning_date_time, end_date_time, result_announcement, application_deadline, requirements_description, organizer_id, location_id, finished) values
(uuid_generate_v4(), 'Konkurs Muzyczny1', 'strunowe', true, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '79a07cd8-9c49-4a84-9a8e-cddca4f7f7bf', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny2', 'gitara', true, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '79a07cd8-9c49-4a84-9a8e-cddca4f7f7bf', '0d0b3f11-72e3-4d6c-97be-4cf2a0e6f920', false),
(uuid_generate_v4(), 'Konkurs Muzyczny3', 'altówka', true, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '79a07cd8-9c49-4a84-9a8e-cddca4f7f7bf', '0d0b3f11-72e3-4d6c-97be-4cf2a0e6f920', false),
(uuid_generate_v4(), 'Konkurs Muzyczny4', 'fagot', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'd82ef282-d566-41fa-a9fc-a1167830a385', '0d0b3f11-72e3-4d6c-97be-4cf2a0e6f920', false),
(uuid_generate_v4(), 'Konkurs Muzyczny5', 'saksofon', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'd82ef282-d566-41fa-a9fc-a1167830a385', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny6', 'obój', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'd82ef282-d566-41fa-a9fc-a1167830a385', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny7', 'organy', false, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '7aa719c5-269b-4629-8102-2319d25714da', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny8', 'waltornia', false, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '7aa719c5-269b-4629-8102-2319d25714da', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny9', 'kotły', false, false, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', '7aa719c5-269b-4629-8102-2319d25714da', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny10', 'czelesta', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'bf69a445-24c8-4749-9ccc-3f4fd9d3be2b', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny11', 'talerze', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'bf69a445-24c8-4749-9ccc-3f4fd9d3be2b', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false),
(uuid_generate_v4(), 'Konkurs Muzyczny12', 'grzechotka', false, true, true, '2023-09-05 05:05:00Z', '2023-09-08 05:05:00Z', '2023-09-20 05:05:00Z', '2023-09-04 05:05:00Z', 'Wymagania, utwory do zagrania i inne informacje', 'bf69a445-24c8-4749-9ccc-3f4fd9d3be2b', '8ee25de2-92af-453e-b56b-bffeca89b8b6', false);

insert into music_contests_user (user_id, user_name, password, active, role_id) values
(uuid_generate_v4(), 'dyrektor1@mejl.com', '$2a$12$btLyh6//KkL33FUcf3MU7Obi2Ex1gvVlCEHPegFHuW093GQ.nT/sW', true, 'c6a4911f-32ba-4450-8153-53a29e881618'),
(uuid_generate_v4(), 'dyrektor2@mejl.com', '$2a$12$XuSkJ.zqFuAPazkxeDtBaOJWtV//FlHVAJTFQv7jAgS1goIA33VN.', true, 'c6a4911f-32ba-4450-8153-53a29e881618'),
(uuid_generate_v4(), 'dyrektornauczyciel3@mejl.com', '$2a$12$iRCevu9EYX2lFSCOjeGgD.15Oel4V//mYvMOOOW6.rPgd.WCfDwK2', true, 'c6a4911f-32ba-4450-8153-53a29e881618'),
(uuid_generate_v4(), 'dyrektornauczyciel4@mejl.com', '$2a$12$O5cXYnY6nOpg5VonUe1dW.nPh4/WyTy0oaHNOdglA.0GC/jO5ltG.', true, 'c6a4911f-32ba-4450-8153-53a29e881618'),
(uuid_generate_v4(), 'nauczyciel1@mejl.com', '$2a$12$KKgtul5ON.ytpMXEKswHaeY9erI3Lq9KiR0G6huBdtjOQS.exCfgW', true, 'ed269ef4-994d-4c42-8b10-c1f145d2b388'),
(uuid_generate_v4(), 'nauczyciel2@mejl.com', '$2a$12$eUpsNLJcFah3sv6yEfivxeTbsAgdhIIqAy6wFl5PdmaYn4U4Hh/Am', true, 'ed269ef4-994d-4c42-8b10-c1f145d2b388'),
(uuid_generate_v4(), 'nauczyciel3@mejl.com', '$2a$12$xfGDHVT//AsYpxVbHPr52u3pxz7HCYEDNRCNb4DNsu5D1W1Pba4Gi', true, 'ed269ef4-994d-4c42-8b10-c1f145d2b388'),
(uuid_generate_v4(), 'uczeń1@mejl.com', '$2a$12$/ntdOEgZJUBbJZGBWyyGG.tFoSGSWlOba8FrgLjVXkl6TGAg3ZQxW', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń2@mejl.com', '$2a$12$SYqEpw/eIBbilFmKA7bBg.XVkmCAnKnMoW8itUjVUZNV.K9NziGOK', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń3@mejl.com', '$2a$12$JptEhWcVL82qP0RJHJ5zp.LuVFAfWR1.tdA46Of7IUCR9hCy/K.ie', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń4@mejl.com', '$2a$12$bJTBI38J7fxX/gmO1BnXDeMyGatvLlw1cM3dRBGMosJSNBBvm1WSK', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń5@mejl.com', '$2a$12$UwHQ6jGvPDLnZMrPWzZCk.4Lwyp1DOBVM2Us4.De9hOULYWQc0tLm', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń6@mejl.com', '$2a$12$PkaqHAhhCwUKnwVNOUknRejHRYCkaSkxGnCd9iPgpDm5yEc5K3IK6', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń7@mejl.com', '$2a$12$UMlbtO8q3MVUf88eURobueL3rjA1gylkOqegrN9AOcPDmcWrdiTMi', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń8@mejl.com', '$2a$12$EjnTQtI3p546k63Vx/r8B.aNVtI5SgDKtzR7xKleotrDZuPQsFbTC', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń9@mejl.com', '$2a$12$HzWDgqAuxlNLhmmZGsWtgeNEFlIJtXnhA7n/Hsp6eVq1syzbSWzne', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń10@mejl.com', '$2a$12$H38KYsyCHTF48YS8pgTlNumm2gHpHApFxfU4qWaiihaDl/iGHvXsa', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de'),
(uuid_generate_v4(), 'uczeń11@mejl.com', '$2a$12$PIO6uguO0AAwCFBRfpIi6.SJ1r9l/0/9NvuPYMxGU9/2UgvvzG18q', true, '67a52ba2-5f0d-4582-ba1b-081a2b6f16de');