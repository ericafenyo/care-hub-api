# Test data

-- Insert cities
insert into care_hub.cities (id, created_at, name)
values  (0x4F94E5DF50C544CAAA9C5D001A229F70, '2024-12-13 15:38:54.340352', 'Lyon'),
        (0xBA6217F065064B048E5ABFC8A71F17E8, '2024-12-13 15:39:37.234017', 'Paris'),
        (0xF0858BD84BFB48B9BD03F73E65CA9790, '2024-12-13 15:38:25.606809', 'Marseille'),
        (0xFBB0774DB9AC4C7C83EC4A6AD81BB731, '2024-12-13 15:39:05.499292', 'Toulouse');

-- Insert countries
insert into care_hub.countries (id, created_at, name)
values  (0x04A6DD7F672D4508B8F4A65039E60582, '2024-12-13 15:38:25.640767', 'France');

-- Insert addresses
insert into care_hub.addresses (id, created_at, postal_code, street, city_id, country_id)
values  (0x2991B3CC7AFD4CA4AE533ED0E367B011, '2024-12-13 15:39:17.225660', '69001', '15 Quai Jean Moulin', 0x4F94E5DF50C544CAAA9C5D001A229F70, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0x43A568440D4940C0BAB0D980C33065A4, '2024-12-13 15:38:42.836025', '13002', '10 Rue de la République', 0xF0858BD84BFB48B9BD03F73E65CA9790, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0x709684E58EAD4C5B98DDCC4F22021D89, '2024-12-13 15:38:54.344241', '69003', '45 Rue de Bonnel', 0x4F94E5DF50C544CAAA9C5D001A229F70, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0x995812DB7336410DABC03E94BF9670AE, '2024-12-13 15:39:27.486860', '13002', '12 Rue de la République', 0xF0858BD84BFB48B9BD03F73E65CA9790, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0xD3ACFB8D721848049704F46A91B06DFB, '2024-12-13 15:39:37.237828', '75012', '22 Rue de Bercy', 0xBA6217F065064B048E5ABFC8A71F17E8, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0xF4DDC0F948D7479CB54B47F8D6D003FC, '2024-12-13 15:39:05.503619', '31500', '30 Avenue Jean Rieux', 0xFBB0774DB9AC4C7C83EC4A6AD81BB731, 0x04A6DD7F672D4508B8F4A65039E60582),
        (0xFED4806B94EF471494E5840F3DAA787D, '2024-12-13 15:38:25.649939', '13002', '10 Rue de la République', 0xF0858BD84BFB48B9BD03F73E65CA9790, 0x04A6DD7F672D4508B8F4A65039E60582);

-- Insert users
insert into care_hub.users (id, birth_date, created_at, email, first_name, last_name, photo, updated_at, address_id)
values  (0x17E698FF56B4491DA5159F5720DF9771, '1984-03-12', '2024-12-13 15:39:37.237615', 'sophie.dubois@example.fr', 'Sophie', 'Dubois', null, '2024-12-13 15:39:37.237615', 0xD3ACFB8D721848049704F46A91B06DFB),
        (0x22595CCACA8E42EB933B2AFB909DEA5C, '1994-08-15', '2024-12-13 15:39:17.225409', 'maria.rodriguez@yopmail.fr', 'Maria', 'Rodriguez', null, '2024-12-13 15:39:17.225409', 0x2991B3CC7AFD4CA4AE533ED0E367B011),
        (0x3ADA0EEA010E457FA3B9CD33B522A823, '1957-08-30', '2024-12-13 15:39:27.486552', 'pierre.martin@example.fr', 'Pierre', 'Martin', null, '2024-12-13 15:39:27.486552', 0x995812DB7336410DABC03E94BF9670AE),
        (0x4A931A971CCF48FAAFE044E9EB41AB62, '1986-07-15', '2024-12-13 15:38:54.343936', 'didier.ndiaye@example.fr', 'Didier', 'N''Diaye', null, '2024-12-13 15:38:54.343936', 0x709684E58EAD4C5B98DDCC4F22021D89),
        (0x60C06F8B2B904A3DAED44DC6EEEB0DB2, '1959-04-20', '2024-12-13 15:38:42.835752', 'charles.ndiaye@example.fr', 'Charles', 'N''Diaye', null, '2024-12-13 15:38:42.835752', 0x43A568440D4940C0BAB0D980C33065A4),
        (0x73669C50DC104395AF1146738AF40214, '1962-11-05', '2024-12-13 15:38:25.644501', 'awa.ndiaye@example.fr', 'Awa', 'N''Diaye', null, '2024-12-13 15:38:25.644501', 0xFED4806B94EF471494E5840F3DAA787D),
        (0x987D721A07DA45E8AC471276C13F6FD9, '1989-02-10', '2024-12-13 15:39:05.503295', 'malik.ndiaye@example.fr', 'Malik', 'N''Diaye', null, '2024-12-13 15:39:05.503295', 0xF4DDC0F948D7479CB54B47F8D6D003FC);

-- Insert credentials
insert into credentials (id, created_at, password, updated_at, user_id)
values  (0x31DAA2026F2848C388CCC845A18217E5, '2024-12-13 15:38:42.916641', '$2a$10$fZRWIr67xsry3WBszUmAK.UpxgU1OuLaOsZMDlvjtKV.i1Gmvlj8a', '2024-12-13 15:38:42.916641', 0x60C06F8B2B904A3DAED44DC6EEEB0DB2),
        (0x4144272BF3164678B20F64F2685A4348, '2024-12-13 15:39:17.308490', '$2a$10$7bcKMTXd4CnHMqpZH91neujl5XnPAONGuQVpJ3lmEPUlyjdo6vsKa', '2024-12-13 15:39:17.308490', 0x22595CCACA8E42EB933B2AFB909DEA5C),
        (0x8FA23E4C4819485A9D102D0097847FA6, '2024-12-13 15:39:27.567058', '$2a$10$iAQ5W9LIRegeEBXnfj3KJeZV1ieHbVVGff7BwJZThMqHDNJkfBrxm', '2024-12-13 15:39:27.567058', 0x3ADA0EEA010E457FA3B9CD33B522A823),
        (0x92891A0B2F564A3C8689B6ED6EC4E62B, '2024-12-13 15:39:05.587820', '$2a$10$t26Dkp5/iHZiNFRwftbLMebzhT8DE5BTGfvvIIS2vSW/mUBfzPcYC', '2024-12-13 15:39:05.587820', 0x987D721A07DA45E8AC471276C13F6FD9),
        (0xA61635EB936E45D68305B4E8D42849F8, '2024-12-13 15:38:25.892495', '$2a$10$tlJuyTT95EuAuXAnOHArbe/47CPSm7kwb.OLVGRO5u4zfwEDhxx2.', '2024-12-13 15:38:25.892495', 0x73669C50DC104395AF1146738AF40214),
        (0xB30A7F89A81F47DC975D85935E6ACF04, '2024-12-13 15:38:54.436871', '$2a$10$PDKs0ECWc7Xnd2pStpJ8v.UStC4emVf2RKQAJ64x04Aps1pvX9T8q', '2024-12-13 15:38:54.436871', 0x4A931A971CCF48FAAFE044E9EB41AB62),
        (0xF4A80756087F47779B66E5124CE78EF4, '2024-12-13 15:39:37.321733', '$2a$10$d8BrQIxJg2FSI0A6208gW.asQ18hrvuY6YoCgNgP0qrFZC2VTkbha', '2024-12-13 15:39:37.321733', 0x17E698FF56B4491DA5159F5720DF9771);
