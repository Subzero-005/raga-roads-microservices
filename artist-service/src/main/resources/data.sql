INSERT INTO artists (artist_id, artist_name, artist_role, city, state, performance_cost, available) VALUES
(1,  'Arjun',  'Singer-Male',   'Chennai',     'Tamil Nadu',   12000, true),
(2,  'Rahul',  'Singer-Male',   'Bangalore',   'Karnataka',    15000, true),
(3,  'Priya',  'Singer-Female', 'Chennai',     'Tamil Nadu',   10000, true),
(4,  'Megha',  'Singer-Female', 'Hyderabad',   'Telangana',    12000, false),
(5,  'Karthik','Guitarist',     'Bangalore',   'Karnataka',     9000, true),
(6,  'Vikram', 'Guitarist',     'Mumbai',      'Maharashtra',  11000, true),
(7,  'Rohit',  'Drummer',       'Delhi',       'Delhi',         8500, true),
(8,  'Amit',   'Drummer',       'Pune',        'Maharashtra',   9500, false),
(9,  'Manoj',  'Keyboardist',   'Hyderabad',   'Telangana',    10000, true),
(10, 'Suresh', 'Keyboardist',   'Chennai',     'Tamil Nadu',   11000, true),
(11, 'Naveen', 'Flutist',       'Mysore',      'Karnataka',    12000, true),
(12, 'Kiran',  'Flutist',       'Coimbatore',  'Tamil Nadu',   13000, true),
(13, 'Ramesh', 'Tablist',       'Mumbai',      'Maharashtra',   7000, true),
(14, 'Sanjay', 'Tablist',       'Jaipur',      'Rajasthan',     8000, true)
ON CONFLICT (artist_id) DO NOTHING;
