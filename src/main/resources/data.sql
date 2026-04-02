-- ================================================
-- Dummy Data for DoggyApp Registry
-- ================================================

-- Organizations (3)
INSERT INTO organizations (name, email, password) VALUES
('Pawsome Rescue',       'pawsome@rescue.com',          'OrgPass1!'),
('Happy Tails Shelter',  'info@happytails.com',          'OrgPass2!'),
('Best Friends Kennel',  'contact@bestfriends.com',      'OrgPass3!');

-- Owners (30 total, 10 per organization)
INSERT INTO owners (name, email, phone_number) VALUES
-- Org 1
('Alice Johnson',   'alice.j@email.com',    '+12025550101'),
('Bob Smith',       'bob.smith@email.com',  '+12025550102'),
('Carol White',     'carol.w@email.com',    '+12025550103'),
('David Brown',     'david.b@email.com',    '+12025550104'),
('Emma Davis',      'emma.d@email.com',     '+12025550105'),
('Frank Miller',    'frank.m@email.com',    '+12025550106'),
('Grace Wilson',    'grace.w@email.com',    '+12025550107'),
('Henry Moore',     'henry.m@email.com',    '+12025550108'),
('Iris Taylor',     'iris.t@email.com',     '+12025550109'),
('Jack Anderson',   'jack.a@email.com',     '+12025550110'),
-- Org 2
('Karen Thomas',    'karen.t@email.com',    '+12025550111'),
('Liam Jackson',    'liam.j@email.com',     '+12025550112'),
('Mia Harris',      'mia.h@email.com',      '+12025550113'),
('Noah Martin',     'noah.m@email.com',     '+12025550114'),
('Olivia Lee',      'olivia.l@email.com',   '+12025550115'),
('Paul Walker',     'paul.w@email.com',     '+12025550116'),
('Quinn Hall',      'quinn.h@email.com',    '+12025550117'),
('Rachel Allen',    'rachel.a@email.com',   '+12025550118'),
('Sam Young',       'sam.y@email.com',      '+12025550119'),
('Tina King',       'tina.k@email.com',     '+12025550120'),
-- Org 3
('Uma Scott',       'uma.s@email.com',      '+12025550121'),
('Victor Green',    'victor.g@email.com',   '+12025550122'),
('Wendy Adams',     'wendy.a@email.com',    '+12025550123'),
('Xander Baker',    'xander.b@email.com',   '+12025550124'),
('Yara Nelson',     'yara.n@email.com',     '+12025550125'),
('Zoe Carter',      'zoe.c@email.com',      '+12025550126'),
('Aaron Mitchell',  'aaron.m@email.com',    '+12025550127'),
('Bella Perez',     'bella.p@email.com',    '+12025550128'),
('Carlos Roberts',  'carlos.r@email.com',   '+12025550129'),
('Diana Turner',    'diana.t@email.com',    '+12025550130');

-- Dogs (30 total, 10 per organization)
-- Columns: dog_name, breed, age, weight, image, organization_id, owner_id
INSERT INTO dogs (dog_name, breed, age, weight, image, organization_id, owner_id) VALUES
-- Org 1 (dogs 1-10, owners 1-10)
('Buddy',   'Labrador Retriever',   3, 65, null, 1,  1),
('Max',     'German Shepherd',      5, 75, null, 1,  2),
('Luna',    'Golden Retriever',     2, 55, null, 1,  3),
('Charlie', 'Bulldog',              4, 50, null, 1,  4),
('Daisy',   'Poodle',               1, 15, null, 1,  5),
('Rocky',   'Beagle',               6, 25, null, 1,  6),
('Bella',   'Rottweiler',           3, 90, null, 1,  7),
('Cooper',  'Yorkshire Terrier',    2,  7, null, 1,  8),
('Molly',   'Boxer',                4, 65, null, 1,  9),
('Tucker',  'Dachshund',            5, 20, null, 1, 10),
-- Org 2 (dogs 11-20, owners 11-20)
('Duke',    'Siberian Husky',       3, 55, null, 2, 11),
('Sadie',   'Border Collie',        4, 45, null, 2, 12),
('Bear',    'Shih Tzu',             2, 12, null, 2, 13),
('Chloe',   'Great Dane',           1,100, null, 2, 14),
('Zeus',    'Australian Shepherd',  5, 60, null, 2, 15),
('Penny',   'Doberman',             3, 80, null, 2, 16),
('Bruno',   'Cocker Spaniel',       6, 30, null, 2, 17),
('Stella',  'Chihuahua',            2,  6, null, 2, 18),
('Diesel',  'Maltese',              4,  8, null, 2, 19),
('Lola',    'Pomeranian',           3,  7, null, 2, 20),
-- Org 3 (dogs 21-30, owners 21-30)
('Atlas',   'Labrador Retriever',   2, 70, null, 3, 21),
('Nova',    'Golden Retriever',     1, 50, null, 3, 22),
('Rex',     'German Shepherd',      7, 80, null, 3, 23),
('Zoe',     'Boxer',                3, 60, null, 3, 24),
('Jasper',  'Beagle',               4, 28, null, 3, 25),
('Ruby',    'Poodle',               2, 18, null, 3, 26),
('Hunter',  'Siberian Husky',       5, 58, null, 3, 27),
('Pepper',  'Border Collie',        3, 42, null, 3, 28),
('Ace',     'Bulldog',              4, 52, null, 3, 29),
('Willow',  'Dachshund',            6, 22, null, 3, 30);

-- Users (15 total, 5 per organization)
-- Password for all: UserPass1!  (meets: uppercase U, lowercase, digit 1, special !)
INSERT INTO users (first_name, last_name, email, password, organization_id) VALUES
-- Org 1
('Tom',  'Baker',  'tom.baker@pawsome.com',   'UserPass1!', 1),
('Sara', 'Clark',  'sara.clark@pawsome.com',  'UserPass1!', 1),
('Mike', 'Evans',  'mike.evans@pawsome.com',  'UserPass1!', 1),
('Lisa', 'Foster', 'lisa.foster@pawsome.com', 'UserPass1!', 1),
('Jake', 'Grant',  'jake.grant@pawsome.com',  'UserPass1!', 1),
-- Org 2
('Amy',  'Hughes', 'amy.hughes@happytails.com', 'UserPass1!', 2),
('Ben',  'Ingram', 'ben.ingram@happytails.com', 'UserPass1!', 2),
('Cara', 'Jones',  'cara.jones@happytails.com', 'UserPass1!', 2),
('Dan',  'Knox',   'dan.knox@happytails.com',   'UserPass1!', 2),
('Eve',  'Lopez',  'eve.lopez@happytails.com',  'UserPass1!', 2),
-- Org 3
('Fred', 'Mason',  'fred.mason@bestfriends.com',  'UserPass1!', 3),
('Gina', 'Nash',   'gina.nash@bestfriends.com',   'UserPass1!', 3),
('Hank', 'Owen',   'hank.owen@bestfriends.com',   'UserPass1!', 3),
('Iris', 'Price',  'iris.price@bestfriends.com',  'UserPass1!', 3),
('Joel', 'Quinn',  'joel.quinn@bestfriends.com',  'UserPass1!', 3);

-- Vaccines (3 per dog: Bordetella, Rabies, DHPP — 90 total)
INSERT INTO vaccines (vaccine, expire_date, dog_id) VALUES
-- Dog 1 Buddy
('Bordetella', '2026-06-01', 1), ('Rabies', '2027-01-15', 1), ('DHPP', '2026-09-01', 1),
-- Dog 2 Max
('Bordetella', '2026-07-01', 2), ('Rabies', '2027-03-20', 2), ('DHPP', '2026-11-01', 2),
-- Dog 3 Luna
('Bordetella', '2026-05-15', 3), ('Rabies', '2027-05-10', 3), ('DHPP', '2026-08-15', 3),
-- Dog 4 Charlie
('Bordetella', '2026-08-01', 4), ('Rabies', '2027-02-28', 4), ('DHPP', '2026-12-01', 4),
-- Dog 5 Daisy
('Bordetella', '2026-09-01', 5), ('Rabies', '2027-06-15', 5), ('DHPP', '2027-01-01', 5),
-- Dog 6 Rocky
('Bordetella', '2026-04-15', 6), ('Rabies', '2026-11-30', 6), ('DHPP', '2026-07-15', 6),
-- Dog 7 Bella
('Bordetella', '2026-10-01', 7), ('Rabies', '2027-04-10', 7), ('DHPP', '2027-02-01', 7),
-- Dog 8 Cooper
('Bordetella', '2026-06-15', 8), ('Rabies', '2027-01-20', 8), ('DHPP', '2026-09-15', 8),
-- Dog 9 Molly
('Bordetella', '2026-11-01', 9), ('Rabies', '2027-07-05', 9), ('DHPP', '2027-03-01', 9),
-- Dog 10 Tucker
('Bordetella', '2026-05-01', 10), ('Rabies', '2026-12-25', 10), ('DHPP', '2026-08-01', 10),
-- Dog 11 Duke
('Bordetella', '2026-06-01', 11), ('Rabies', '2027-01-15', 11), ('DHPP', '2026-09-01', 11),
-- Dog 12 Sadie
('Bordetella', '2026-07-15', 12), ('Rabies', '2027-02-10', 12), ('DHPP', '2026-10-15', 12),
-- Dog 13 Bear
('Bordetella', '2026-08-20', 13), ('Rabies', '2027-03-15', 13), ('DHPP', '2026-11-20', 13),
-- Dog 14 Chloe
('Bordetella', '2026-09-10', 14), ('Rabies', '2027-04-20', 14), ('DHPP', '2026-12-10', 14),
-- Dog 15 Zeus
('Bordetella', '2026-10-05', 15), ('Rabies', '2027-05-25', 15), ('DHPP', '2027-01-05', 15),
-- Dog 16 Penny
('Bordetella', '2026-05-20', 16), ('Rabies', '2026-12-10', 16), ('DHPP', '2026-08-20', 16),
-- Dog 17 Bruno
('Bordetella', '2026-11-15', 17), ('Rabies', '2027-06-30', 17), ('DHPP', '2027-02-15', 17),
-- Dog 18 Stella
('Bordetella', '2026-06-25', 18), ('Rabies', '2027-01-30', 18), ('DHPP', '2026-09-25', 18),
-- Dog 19 Diesel
('Bordetella', '2026-12-01', 19), ('Rabies', '2027-07-15', 19), ('DHPP', '2027-03-01', 19),
-- Dog 20 Lola
('Bordetella', '2026-04-20', 20), ('Rabies', '2026-11-10', 20), ('DHPP', '2026-07-20', 20),
-- Dog 21 Atlas
('Bordetella', '2026-07-01', 21), ('Rabies', '2027-02-15', 21), ('DHPP', '2026-10-01', 21),
-- Dog 22 Nova
('Bordetella', '2026-08-10', 22), ('Rabies', '2027-03-20', 22), ('DHPP', '2026-11-10', 22),
-- Dog 23 Rex
('Bordetella', '2026-05-05', 23), ('Rabies', '2026-12-01', 23), ('DHPP', '2026-08-05', 23),
-- Dog 24 Zoe
('Bordetella', '2026-09-20', 24), ('Rabies', '2027-04-25', 24), ('DHPP', '2026-12-20', 24),
-- Dog 25 Jasper
('Bordetella', '2026-10-15', 25), ('Rabies', '2027-05-30', 25), ('DHPP', '2027-01-15', 25),
-- Dog 26 Ruby
('Bordetella', '2026-06-10', 26), ('Rabies', '2027-01-05', 26), ('DHPP', '2026-09-10', 26),
-- Dog 27 Hunter
('Bordetella', '2026-11-20', 27), ('Rabies', '2027-06-10', 27), ('DHPP', '2027-02-20', 27),
-- Dog 28 Pepper
('Bordetella', '2026-07-25', 28), ('Rabies', '2027-02-20', 28), ('DHPP', '2026-10-25', 28),
-- Dog 29 Ace
('Bordetella', '2026-12-15', 29), ('Rabies', '2027-07-20', 29), ('DHPP', '2027-03-15', 29),
-- Dog 30 Willow
('Bordetella', '2026-04-05', 30), ('Rabies', '2026-11-01', 30), ('DHPP', '2026-07-05', 30);

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
-- NOTE: "like" is a reserved SQL keyword — quoted here for H2 compatibility
INSERT INTO likes ("like", dog_id) VALUES
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

-- Events (15 total, all within Mon Apr 6 – Sun Apr 12, 2026)
-- Dog 1 (Buddy) has 3 events; User 1 (Tom Baker) has 3 events — all overlapping.
-- FK columns: users_user_id, dogs_dog_id (JPA default naming)
INSERT INTO events (event, description, start_time, end_time, users_user_id, dogs_dog_id) VALUES
-- Dog 1 / User 1 — 3 events (satisfies both requirements)
('Vet Appointment',  'Annual wellness exam for Buddy',         '2026-04-06 09:00:00', '2026-04-06 10:00:00',  1,  1),
('Grooming Session', 'Full groom and nail trim for Buddy',     '2026-04-08 14:00:00', '2026-04-08 15:30:00',  1,  1),
('Training Class',   'Basic obedience refresher for Buddy',   '2026-04-10 11:00:00', '2026-04-10 12:00:00',  1,  1),
-- Remaining 12 events spread across other users and dogs
('Vet Appointment',  'Dental check for Luna',                  '2026-04-06 10:30:00', '2026-04-06 11:30:00',  2,  3),
('Playdate',         'Off-leash play session for Daisy',       '2026-04-07 09:00:00', '2026-04-07 10:00:00',  3,  5),
('Vet Appointment',  'Dental cleaning for Cooper',             '2026-04-07 14:00:00', '2026-04-07 15:00:00',  4,  8),
('Playdate',         'Dog park outing for Molly',              '2026-04-08 10:00:00', '2026-04-08 11:00:00',  5,  9),
('Training Class',   'Agility training for Sadie',             '2026-04-08 16:00:00', '2026-04-08 17:00:00',  6, 12),
('Vaccine Booster',  'Bordetella booster for Duke',            '2026-04-09 09:30:00', '2026-04-09 10:00:00',  7, 11),
('Vet Appointment',  'Eye drop check-up for Zeus',             '2026-04-09 13:00:00', '2026-04-09 14:00:00',  8, 15),
('Grooming Session', 'Ear cleaning for Bruno',                 '2026-04-09 16:00:00', '2026-04-09 17:00:00',  9, 17),
('Training Class',   'Scent work session for Jasper',          '2026-04-10 13:00:00', '2026-04-10 14:00:00', 10, 25),
('Vet Appointment',  'Cardiac check for Zoe',                  '2026-04-10 15:00:00', '2026-04-10 16:00:00', 11, 24),
('Playdate',         'Socialization session for Chloe',        '2026-04-11 10:00:00', '2026-04-11 11:00:00', 12, 14),
('Vet Appointment',  'Senior wellness check for Willow',       '2026-04-12 11:00:00', '2026-04-12 12:30:00', 13, 30);
