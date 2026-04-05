-- ================================================
-- Dummy Data for DoggyApp Registry
-- ================================================

-- Organizations (3)
-- plaintext passwords: OrgPass1! / OrgPass2! / OrgPass3!
INSERT INTO organizations (name, email, password, subscription_start, subscription_expiration) VALUES
('Pawsome Rescue',       'pawsome@rescue.com',     '$2b$10$kdvnLLUItTZxoLk6pJchZeRXOYpL.21Rg4.sxqlNbfrr4ZHB87Lea', '2026-04-04', '2026-04-04'),  -- OrgPass1! active
('Happy Tails Shelter',  'info@happytails.com',    '$2b$10$AJ/sTESnSAjwTUwYM4JDOuh6zlDLq6W1T4KNWtmZsexwRV.a9PHHG', '2025-03-15', '2026-03-15'),  -- OrgPass2! expiring soon
('Best Friends Kennel',  'contact@bestfriends.com','$2b$10$OcwLr6gyXupXp/jY6U8gk.EIsnJphaIb5nje4og85FK0askqhHDtu', '2026-01-10', '2027-01-10');  -- OrgPass3! recently renewed

-- Owners (12 total, 4 per organization — only owners tied to dogs)
INSERT INTO owners (name, email, phone_number) VALUES
-- Org 1: Pawsome Rescue clients (ids 1–4)
('Alice Johnson',   'alice.j@email.com',    '+12025550101'),  -- id=1,  client of Org 1
('Bob Smith',       'bob.smith@email.com',  '+12025550102'),  -- id=2,  client of Org 1
('Carol White',     'carol.w@email.com',    '+12025550103'),  -- id=3,  client of Org 1
('David Brown',     'david.b@email.com',    '+12025550104'),  -- id=4,  client of Org 1
-- Org 2: Happy Tails Shelter clients (ids 5–8)
('Karen Thomas',    'karen.t@email.com',    '+12025550111'),  -- id=5,  client of Org 2
('Liam Jackson',    'liam.j@email.com',     '+12025550112'),  -- id=6,  client of Org 2
('Mia Harris',      'mia.h@email.com',      '+12025550113'),  -- id=7,  client of Org 2
('Noah Martin',     'noah.m@email.com',     '+12025550114'),  -- id=8,  client of Org 2
-- Org 3: Best Friends Kennel clients (ids 9–12)
('Uma Scott',       'uma.s@email.com',      '+12025550121'),  -- id=9,  client of Org 3
('Victor Green',    'victor.g@email.com',   '+12025550122'),  -- id=10, client of Org 3
('Wendy Adams',     'wendy.a@email.com',    '+12025550123'),  -- id=11, client of Org 3
('Xander Baker',    'xander.b@email.com',   '+12025550124');  -- id=12, client of Org 3

-- Dogs (30 total, 10 per organization)
-- Columns: dog_name, breed, age, weight, image, organization_id, owner_id
-- Owner distribution per org:
--   Org 1: owner 1 → 3 dogs, owner 2 → 4 dogs, owner 3 → 2 dogs, owner 4 → 1 dog
--   Org 2: owner 5 → 2 dogs, owner 6 → 4 dogs, owner 7 → 3 dogs, owner 8 → 1 dog
--   Org 3: owner 9 → 3 dogs, owner 10 → 4 dogs, owner 11 → 2 dogs, owner 12 → 1 dog
INSERT INTO dogs (dog_name, breed, age, weight, image, organization_id, owner_id) VALUES
-- Org 1
('Buddy',   'Labrador Retriever',   3, 65, 'https://images.dog.ceo/breeds/labrador/n02099712_4323.jpg',                  1,  1),
('Max',     'German Shepherd',      5, 75, 'broken',                                                                        1,  1),  -- intentional broken: test missing-image UI
('Luna',    'Golden Retriever',     2, 55, 'https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg',              1,  1),
('Charlie', 'Bulldog',              4, 50, 'https://images.dog.ceo/breeds/bulldog-english/jager-1.jpg',                      1,  2),
('Daisy',   'Poodle',               1, 15, 'https://images.dog.ceo/breeds/poodle-toy/n02113624_2784.jpg',                    1,  2),
('Rocky',   'Beagle',               6, 25, 'https://images.dog.ceo/breeds/beagle/n02088364_11136.jpg',                       1,  2),
('Bella',   'Rottweiler',           3, 90, 'https://images.dog.ceo/breeds/rottweiler/n02106550_4920.jpg',                    1,  2),
('Cooper',  'Yorkshire Terrier',    2,  7, 'https://images.dog.ceo/breeds/terrier-yorkshire/IMG_3001.jpg',                   1,  3),
('Molly',   'Boxer',                4, 65, 'https://images.dog.ceo/breeds/boxer/n02108089_3162.jpg',                         1,  3),
('Tucker',  'Dachshund',            5, 20, 'https://images.dog.ceo/breeds/dachshund/dachshund-5.jpg',                        1,  4),
-- Org 2
('Duke',    'Siberian Husky',       3, 55, 'broken',                                                                        2,  5),  -- intentional broken: test missing-image UI
('Sadie',   'Border Collie',        4, 45, 'https://images.dog.ceo/breeds/collie-border/n02106166_2685.jpg',                 2,  5),
('Bear',    'Shih Tzu',             2, 12, 'https://images.dog.ceo/breeds/shihtzu/n02086240_788.jpg',                        2,  6),
('Chloe',   'Great Dane',           1,100, 'https://images.dog.ceo/breeds/dane-great/n02109047_1005.jpg',                    2,  6),
('Zeus',    'Australian Shepherd',  5, 60, 'https://images.dog.ceo/breeds/australian-shepherd/pepper2.jpg',                 2,  6),
('Penny',   'Doberman',             3, 80, 'https://images.dog.ceo/breeds/doberman/n02107142_4017.jpg',                      2,  6),
('Bruno',   'Cocker Spaniel',       6, 30, 'https://images.dog.ceo/breeds/spaniel-cocker/ekko1.jpg',                        2,  7),
('Stella',  'Chihuahua',            2,  6, 'https://images.dog.ceo/breeds/chihuahua/n02085620_7613.jpg',                     2,  7),
('Diesel',  'Maltese',              4,  8, 'https://images.dog.ceo/breeds/maltese/n02085936_4245.jpg',                       2,  7),
('Lola',    'Pomeranian',           3,  7, 'https://images.dog.ceo/breeds/pomeranian/n02112018_11641.jpg',                   2,  8),
-- Org 3
('Atlas',   'Labrador Retriever',   2, 70, 'broken',                                                                        3,  9),  -- intentional broken: test missing-image UI
('Nova',    'Golden Retriever',     1, 50, 'https://images.dog.ceo/breeds/retriever-golden/n02099601_7771.jpg',              3,  9),
('Rex',     'German Shepherd',      7, 80, 'https://images.dog.ceo/breeds/german-shepherd/n02106662_18268.jpg',              3,  9),
('Zoe',     'Boxer',                3, 60, 'https://images.dog.ceo/breeds/boxer/n02108089_10901.jpg',                        3, 10),
('Jasper',  'Beagle',               4, 28, 'https://images.dog.ceo/breeds/beagle/n02088364_12124.jpg',                      3, 10),
('Ruby',    'Poodle',               2, 18, 'https://images.dog.ceo/breeds/poodle-standard/n02113799_2280.jpg',               3, 10),
('Hunter',  'Siberian Husky',       5, 58, 'https://images.dog.ceo/breeds/husky/n02110185_1469.jpg',                         3, 10),
('Pepper',  'Border Collie',        3, 42, 'https://images.dog.ceo/breeds/collie-border/n02106166_90.jpg',                   3, 11),
('Ace',     'Bulldog',              4, 52, 'https://images.dog.ceo/breeds/bulldog-english/jager-2.jpg',                      3, 11),
('Willow',  'Dachshund',            6, 22, 'https://images.dog.ceo/breeds/dachshund/Daschund-2.jpg',                         3, 12);

-- Users (15 total, 5 per organization)
-- All users share the same password for testing: UserPass1!
INSERT INTO users (first_name, last_name, email, password, organization_id) VALUES
-- Org 1
('Tom',  'Baker',  'tom.baker@pawsome.com',   '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 1),  -- UserPass1!
('Sara', 'Clark',  'sara.clark@pawsome.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 1),  -- UserPass1!
('Mike', 'Evans',  'mike.evans@pawsome.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 1),  -- UserPass1!
('Lisa', 'Foster', 'lisa.foster@pawsome.com', '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 1),  -- UserPass1!
('Jake', 'Grant',  'jake.grant@pawsome.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 1),  -- UserPass1!
-- Org 2
('Amy',  'Hughes', 'amy.hughes@happytails.com', '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 2),  -- UserPass1!
('Ben',  'Ingram', 'ben.ingram@happytails.com', '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 2),  -- UserPass1!
('Cara', 'Jones',  'cara.jones@happytails.com', '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 2),  -- UserPass1!
('Dan',  'Knox',   'dan.knox@happytails.com',   '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 2),  -- UserPass1!
('Eve',  'Lopez',  'eve.lopez@happytails.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 2),  -- UserPass1!
-- Org 3
('Fred', 'Mason',  'fred.mason@bestfriends.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 3),  -- UserPass1!
('Gina', 'Nash',   'gina.nash@bestfriends.com',   '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 3),  -- UserPass1!
('Hank', 'Owen',   'hank.owen@bestfriends.com',   '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 3),  -- UserPass1!
('Iris', 'Price',  'iris.price@bestfriends.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 3),  -- UserPass1!
('Joel', 'Quinn',  'joel.quinn@bestfriends.com',  '$2b$10$otw3dDWlH/ccJqOdQ7Q1cOS7MG6ROqIIlaG5mtWBtT0bdyTNTKh..', 3);  -- UserPass1!

-- Vaccines (3 per dog: Bordetella, Rabies, DHPP — 90 total)
INSERT INTO vaccines (vaccine_name, expiration_date, dog_id, standard) VALUES
-- Dog 1 Buddy
('Bordetella', '2026-06-01', 1, true), ('Rabies', '2027-01-15', 1, true), ('DHPP', '2026-09-01', 1, false),
-- Dog 2 Max
('Bordetella', '2026-07-01', 2, true), ('Rabies', '2027-03-20', 2, true), ('DHPP', '2026-11-01', 2, false),
-- Dog 3 Luna
('Bordetella', '2026-05-15', 3, true), ('Rabies', '2027-05-10', 3, true), ('DHPP', '2026-08-15', 3, false),
-- Dog 4 Charlie
('Bordetella', '2026-08-01', 4, true), ('Rabies', '2027-02-28', 4, true), ('DHPP', '2026-12-01', 4, false),
-- Dog 5 Daisy
('Bordetella', '2026-09-01', 5, true), ('Rabies', '2027-06-15', 5, true), ('DHPP', '2027-01-01', 5, false),
-- Dog 6 Rocky
('Bordetella', '2026-04-15', 6, true), ('Rabies', '2026-11-30', 6, true), ('DHPP', '2026-07-15', 6, false),
-- Dog 7 Bella
('Bordetella', '2026-10-01', 7, true), ('Rabies', '2027-04-10', 7, true), ('DHPP', '2027-02-01', 7, false),
-- Dog 8 Cooper
('Bordetella', '2026-06-15', 8, true), ('Rabies', '2027-01-20', 8, true), ('DHPP', '2026-09-15', 8, false),
-- Dog 9 Molly
('Bordetella', '2026-11-01', 9, true), ('Rabies', '2027-07-05', 9, true), ('DHPP', '2027-03-01', 9, false),
-- Dog 10 Tucker
('Bordetella', '2026-05-01', 10, true), ('Rabies', '2026-12-25', 10, true), ('DHPP', '2026-08-01', 10, false),
-- Dog 11 Duke
('Bordetella', '2026-06-01', 11, true), ('Rabies', '2027-01-15', 11, true), ('DHPP', '2026-09-01', 11, false),
-- Dog 12 Sadie
('Bordetella', '2026-07-15', 12, true), ('Rabies', '2027-02-10', 12, true), ('DHPP', '2026-10-15', 12, false),
-- Dog 13 Bear
('Bordetella', '2026-08-20', 13, true), ('Rabies', '2027-03-15', 13, true), ('DHPP', '2026-11-20', 13, false),
-- Dog 14 Chloe
('Bordetella', '2026-09-10', 14, true), ('Rabies', '2027-04-20', 14, true), ('DHPP', '2026-12-10', 14, false),
-- Dog 15 Zeus
('Bordetella', '2026-10-05', 15, true), ('Rabies', '2027-05-25', 15, true), ('DHPP', '2027-01-05', 15, false),
-- Dog 16 Penny
('Bordetella', '2026-05-20', 16, true), ('Rabies', '2026-12-10', 16, true), ('DHPP', '2026-08-20', 16, false),
-- Dog 17 Bruno
('Bordetella', '2026-11-15', 17, true), ('Rabies', '2027-06-30', 17, true), ('DHPP', '2027-02-15', 17, false),
-- Dog 18 Stella
('Bordetella', '2026-06-25', 18, true), ('Rabies', '2027-01-30', 18, true), ('DHPP', '2026-09-25', 18, false),
-- Dog 19 Diesel
('Bordetella', '2026-12-01', 19, true), ('Rabies', '2027-07-15', 19, true), ('DHPP', '2027-03-01', 19, false),
-- Dog 20 Lola
('Bordetella', '2026-04-20', 20, true), ('Rabies', '2026-11-10', 20, true), ('DHPP', '2026-07-20', 20, false),
-- Dog 21 Atlas
('Bordetella', '2026-07-01', 21, true), ('Rabies', '2027-02-15', 21, true), ('DHPP', '2026-10-01', 21, false),
-- Dog 22 Nova
('Bordetella', '2026-08-10', 22, true), ('Rabies', '2027-03-20', 22, true), ('DHPP', '2026-11-10', 22, false),
-- Dog 23 Rex
('Bordetella', '2026-05-05', 23, true), ('Rabies', '2026-12-01', 23, true), ('DHPP', '2026-08-05', 23, false),
-- Dog 24 Zoe
('Bordetella', '2026-09-20', 24, true), ('Rabies', '2027-04-25', 24, true), ('DHPP', '2026-12-20', 24, false),
-- Dog 25 Jasper
('Bordetella', '2026-10-15', 25, true), ('Rabies', '2027-05-30', 25, true), ('DHPP', '2027-01-15', 25, false),
-- Dog 26 Ruby
('Bordetella', '2026-06-10', 26, true), ('Rabies', '2027-01-05', 26, true), ('DHPP', '2026-09-10', 26, false),
-- Dog 27 Hunter
('Bordetella', '2026-11-20', 27, true), ('Rabies', '2027-06-10', 27, true), ('DHPP', '2027-02-20', 27, false),
-- Dog 28 Pepper
('Bordetella', '2026-07-25', 28, true), ('Rabies', '2027-02-20', 28, true), ('DHPP', '2026-10-25', 28, false),
-- Dog 29 Ace
('Bordetella', '2026-12-15', 29, true), ('Rabies', '2027-07-20', 29, true), ('DHPP', '2027-03-15', 29, false),
-- Dog 30 Willow
('Bordetella', '2026-04-05', 30, true), ('Rabies', '2026-11-01', 30, true), ('DHPP', '2026-07-05', 30, false);

-- Alerts (2 per dog)
INSERT INTO alerts (alert, dog_id) VALUES
('Allergic to chicken', 1),               ('Needs heartworm medication monthly', 1),
('Fear of loud noises', 2),               ('Do not approach from behind', 2),
('Sensitive stomach, special diet only', 3), ('Pulls hard on leash', 3),
('Brachycephalic, avoid overheating', 4), ('Snoring is normal', 4),
('Needs regular grooming every 6 weeks', 5), ('Separation anxiety', 5),
('Prone to obesity, limit treats', 6),    ('Escape artist, check fences', 6),
('Strong prey drive', 7),                 ('Not suitable with small children', 7),
('Needs dental cleaning soon', 8),        ('Cold-sensitive due to small size', 8),
('Hip dysplasia, limit stairs', 9),       ('Needs joint supplement daily', 9),
('Prone to back injuries, no jumping', 10), ('Keep weight under 25 lbs', 10),
('Requires daily brushing', 11),          ('High energy, needs 2 hrs exercise daily', 11),
('Herding instinct, may nip at heels', 12), ('Needs mental stimulation daily', 12),
('Brachycephalic, monitor breathing', 13), ('Keep away from hot temperatures', 13),
('Growth plates still developing', 14),   ('Requires slow feeding bowl', 14),
('Eye condition, needs drops daily', 15), ('Check coat for mats weekly', 15),
('Aggressive toward male dogs', 16),      ('Needs assertive experienced handler', 16),
('Recurring ear infections', 17),         ('Keep ears dry after baths', 17),
('Fragile due to small size', 18),        ('No rough play with large dogs', 18),
('Skin allergies, hypoallergenic shampoo only', 19), ('Monthly skin check required', 19),
('Luxating patella, avoid excessive jumping', 20), ('Needs warmth in winter', 20),
('Allergic to wheat', 21),                ('Needs anti-anxiety medication', 21),
('Hypothyroidism, daily medication required', 22), ('Weight gain tendency', 22),
('Degenerative myelopathy monitoring', 23), ('Regular neurological checks needed', 23),
('Heart murmur, low-intensity exercise only', 24), ('Annual cardiac exam required', 24),
('Food motivated, strict portion control', 25), ('Sneaks under fences', 25),
('Gets car sick', 26),                    ('Needs slow introduction to new dogs', 26),
('Hot spots on skin, monitor coat', 27),  ('Cannot tolerate high heat', 27),
('Herding breed, needs a job', 28),       ('Will chase livestock if off-leash', 28),
('Brachycephalic, no extended time outdoors in heat', 29), ('Sleep apnea possible', 29),
('Intervertebral disc disease risk', 30), ('Must use ramp, no stairs', 30);

-- Notes (1 per dog)
INSERT INTO notes (note, dog_id) VALUES
('Buddy responds well to clicker training. Loves fetch and water.', 1),
('Max is fully trained in basic obedience. Needs firm but gentle handling.', 2),
('Luna is gentle with children and seniors. Great for family placement.', 3),
('Charlie requires moderate exercise. Keep cool in warm weather.', 4),
('Daisy is fully house-trained. Enjoys agility courses.', 5),
('Rocky is scent-driven and should always be on leash outdoors.', 6),
('Bella is loyal but needs an experienced owner. Good guard dog.', 7),
('Cooper is a lap dog and bonds quickly with one person.', 8),
('Molly is playful and social with other dogs.', 9),
('Tucker loves burrowing under blankets. Calm and low-energy indoors.', 10),
('Duke loves the cold and needs significant daily outdoor time.', 11),
('Sadie excels at agility and frisbee. Very intelligent.', 12),
('Bear is calm and good for apartments. Minimal exercise needs.', 13),
('Chloe is still a puppy and currently in socialization training.', 14),
('Zeus is great with other dogs. Herding instinct is strong.', 15),
('Penny is highly trainable and eager to please.', 16),
('Bruno loves the water and retrieves naturally.', 17),
('Stella does best as an only pet. Bonds closely with one owner.', 18),
('Diesel is hypoallergenic and good for allergy-prone families.', 19),
('Lola is very vocal. Needs a patient owner.', 20),
('Atlas is a working dog and does best with structured tasks.', 21),
('Nova is perfect for first-time dog owners. Very gentle.', 22),
('Rex has a working dog background. Needs a structured environment.', 23),
('Zoe is energetic and loves tug-of-war and wrestling.', 24),
('Jasper is an excellent nose work candidate.', 25),
('Ruby is recently groomed and up to date on dental cleaning.', 26),
('Hunter has a thick double coat that needs weekly brushing.', 27),
('Pepper was a farm dog and still has strong herding instincts.', 28),
('Ace is gentle despite his size and loves cuddles.', 29),
('Willow is a senior dog who prefers calm, quiet environments.', 30);

-- Likes (2 per dog)
INSERT INTO likes (like_text, dog_id) VALUES
('Playing fetch', 1),              ('Swimming', 1),
('Tug of war', 2),                 ('Long hikes', 2),
('Running in open fields', 3),     ('Belly rubs', 3),
('Snuggling on the couch', 4),     ('Short walks', 4),
('Agility courses', 5),            ('Learning new tricks', 5),
('Sniffing trails', 6),            ('Chasing squirrels', 6),
('Roughhousing', 7),               ('Chewing on bones', 7),
('Sitting in laps', 8),            ('Watching TV', 8),
('Playing with other dogs', 9),    ('Chasing balls', 9),
('Burrowing under blankets', 10),  ('Sunny naps', 10),
('Running in snow', 11),           ('Howling along to music', 11),
('Frisbee', 12),                   ('Herding games', 12),
('Being carried', 13),             ('Sleeping in sunbeams', 13),
('Exploring new places', 14),      ('Meeting new people', 14),
('Playing in sprinklers', 15),     ('Chasing a flirt pole', 15),
('Obedience drills', 16),          ('Car rides', 16),
('Dock diving', 17),               ('Fetching sticks from water', 17),
('Burrowing in blankets', 18),     ('Sitting in owner lap', 18),
('Sunbathing', 19),                ('Gentle play', 19),
('Barking at birds', 20),          ('Running in circles', 20),
('Retrieving bumpers', 21),        ('Pulling a sled', 21),
('Gentle walks', 22),              ('Playing with children', 22),
('Protection sport drills', 23),   ('Tracking scents', 23),
('Sprinting', 24),                 ('Wrestling', 24),
('Nose work puzzles', 25),         ('Digging', 25),
('Running on the beach', 26),      ('Learning tricks', 26),
('Running through snow', 27),      ('Pulling activities', 27),
('Rounding up animals', 28),       ('Chasing a ball across the yard', 28),
('Napping on soft surfaces', 29),  ('Being petted', 29),
('Sunbathing on the porch', 30),   ('Slow leisurely walks', 30);

-- Locations (4 per organization: 1-4 = Org 1, 5-8 = Org 2, 9-12 = Org 3)
-- offsite=false for on-premises rooms/fields; offsite=true for off-location entries
INSERT INTO locations (location_name, org_id, address, offsite) VALUES
('Boarding Room',  1, '101 Pawsome Lane, Springfield, IL 62701',        false),
('Training Field', 1, '101 Pawsome Lane, Springfield, IL 62701',        false),
('Play Area',      1, '101 Pawsome Lane, Springfield, IL 62701',        false),
('Off Location',   1, '101 Pawsome Lane, Springfield, IL 62701',        true),
('Boarding Room',  2, '55 Happy Tails Blvd, Austin, TX 73301',          false),
('Training Field', 2, '55 Happy Tails Blvd, Austin, TX 73301',          false),
('Play Area',      2, '55 Happy Tails Blvd, Austin, TX 73301',          false),
('Off Location',   2, '55 Happy Tails Blvd, Austin, TX 73301',          true),
('Boarding Room',  3, '200 Best Friends Rd, Portland, OR 97201',        false),
('Training Field', 3, '200 Best Friends Rd, Portland, OR 97201',        false),
('Play Area',      3, '200 Best Friends Rd, Portland, OR 97201',        false),
('Off Location',   3, '200 Best Friends Rd, Portland, OR 97201',        true);

-- Events (15 total, all within Mon Apr 6 – Sun Apr 12, 2026)
-- Dog 1 (Buddy) has 3 events; User 1 (Tom Baker) has 3 events — all overlapping.
-- FK columns: users_user_id, dogs_dog_id (JPA default naming)
-- Location IDs: Org1→ 1=Boarding Room, 2=Training Field, 3=Play Area, 4=Off Location
--               Org2→ 5=Boarding Room, 6=Training Field, 7=Play Area, 8=Off Location
--               Org3→ 9=Boarding Room, 10=Training Field, 11=Play Area, 12=Off Location
INSERT INTO events (event, description, location_id, start_time, end_time, users_user_id, dogs_dog_id) VALUES
-- Dog 1 / User 1 (Org 1) — 3 events
('Vet Appointment',  'Annual wellness exam for Buddy',         4,  '2026-04-06 09:00:00', '2026-04-06 10:00:00',  1,  1),
('Grooming Session', 'Full groom and nail trim for Buddy',     1,  '2026-04-08 14:00:00', '2026-04-08 15:30:00',  1,  1),
('Training Class',   'Basic obedience refresher for Buddy',   2,  '2026-04-10 11:00:00', '2026-04-10 12:00:00',  1,  1),
-- Remaining 12 events spread across other users and dogs
('Vet Appointment',  'Dental check for Luna',                  4,  '2026-04-06 10:30:00', '2026-04-06 11:30:00',  2,  3),
('Playdate',         'Off-leash play session for Daisy',       3,  '2026-04-07 09:00:00', '2026-04-07 10:00:00',  3,  5),
('Grooming Session', 'Dental cleaning for Cooper',             1,  '2026-04-07 14:00:00', '2026-04-07 15:00:00',  4,  8),
('Playdate',         'Dog park outing for Molly',              3,  '2026-04-08 10:00:00', '2026-04-08 11:00:00',  5,  9),
('Training Class',   'Agility training for Sadie',             6,  '2026-04-08 16:00:00', '2026-04-08 17:00:00',  6, 12),
('Vaccine Booster',  'Bordetella booster for Duke',            5,  '2026-04-09 09:30:00', '2026-04-09 10:00:00',  7, 11),
('Vet Appointment',  'Eye drop check-up for Zeus',             8,  '2026-04-09 13:00:00', '2026-04-09 14:00:00',  8, 15),
('Grooming Session', 'Ear cleaning for Bruno',                 5,  '2026-04-09 16:00:00', '2026-04-09 17:00:00',  9, 17),
('Training Class',   'Scent work session for Jasper',          6,  '2026-04-10 13:00:00', '2026-04-10 14:00:00', 10, 25),
('Vet Appointment',  'Cardiac check for Zoe',                  12, '2026-04-10 15:00:00', '2026-04-10 16:00:00', 11, 24),
('Playdate',         'Socialization session for Chloe',        11, '2026-04-11 10:00:00', '2026-04-11 11:00:00', 12, 14),
('Training Class',   'Senior wellness check for Willow',       10, '2026-04-12 11:00:00', '2026-04-12 12:30:00', 13, 30);
