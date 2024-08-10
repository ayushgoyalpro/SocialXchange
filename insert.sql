-- Insert data into the Influencer table
INSERT INTO Influencer (ig_username, name, email, mobile_number, password, followers, address, lat, lon, dob,
                        date_created)
VALUES ('john_doe', 'John Doe', 'john@example.com', '555-1234', 'pass123', 10000, '123 Main St, Springfield', 37.7749,
        -122.4194, '1990-01-01', '2023-08-10'),
       ('jane_smith', 'Jane Smith', 'jane@example.com', '555-5678', 'pass456', 50000, '456 Elm St, Springfield',
        40.7128, -74.0060, '1992-02-02', '2023-08-10'),
       ('sam_jones', 'Sam Jones', 'sam@example.com', '555-9876', 'pass789', 7500, '789 Oak St, Springfield', 34.0522,
        -118.2437, '1985-03-03', '2023-08-10'),
       ('emma_watson', 'Emma Watson', 'emma@example.com', '555-4321', 'pass321', 25000, '321 Pine St, Springfield',
        51.5074, -0.1278, '1995-04-04', '2023-08-10'),
       ('alex_king', 'Alex King', 'alex@example.com', '555-6543', 'pass654', 12000, '654 Maple St, Springfield',
        48.8566, 2.3522, '1988-05-05', '2023-08-10');

-- Insert data into the Business table
INSERT INTO Business (name, email, password, business_type, ig_username, address, lat, lon, mobile_number, qr,
                      date_created, status)
VALUES ('TechWorld', 'info@techworld.com', 'techpass123', 'Technology', 'techworld_ig',
        '101 Innovation Way, Silicon Valley', 37.3875, -122.0575, '555-1111', 'qr_techworld', '2023-08-10', 'active'),
       ('FashionHub', 'contact@fashionhub.com', 'fashionpass456', 'Fashion', 'fashionhub_ig',
        '202 Fashion St, New York', 40.7128, -74.0060, '555-2222', 'qr_fashionhub', '2023-08-10', 'active'),
       ('Foodies', 'support@foodies.com', 'foodpass789', 'Food', 'foodies_ig', '303 Gourmet Blvd, Los Angeles', 34.0522,
        -118.2437, '555-3333', 'qr_foodies', '2023-08-10', 'active'),
       ('FitnessPro', 'hello@fitnesspro.com', 'fitpass321', 'Fitness', 'fitnesspro_ig', '404 Health Ave, Miami',
        25.7617, -80.1918, '555-4444', 'qr_fitnesspro', '2023-08-10', 'active'),
       ('TravelLife', 'explore@travellife.com', 'travelpass654', 'Travel', 'travellife_ig', '505 Adventure Rd, Paris',
        48.8566, 2.3522, '555-5555', 'qr_travellife', '2023-08-10', 'active');

-- Insert data into the SoCo Card table
INSERT INTO SoCo_Card (influencer_id, phy_card_id, status, timestamp)
VALUES (1, 1001, 'active', CURRENT_TIMESTAMP),
       (2, 1002, 'active', CURRENT_TIMESTAMP),
       (3, 1003, 'inactive', CURRENT_TIMESTAMP),
       (4, 1004, 'active', CURRENT_TIMESTAMP),
       (5, 1005, 'active', CURRENT_TIMESTAMP);

-- Insert data into the Payment_Card table
INSERT INTO Payment_Card (influencer_id, stripe_card_id, last4, brand, exp_month, exp_year, created_at)
VALUES (1, 'card_123abc', '4242', 'Visa', 12, 2024, CURRENT_TIMESTAMP),
       (2, 'card_456def', '1111', 'MasterCard', 11, 2025, CURRENT_TIMESTAMP),
       (3, 'card_789ghi', '2222', 'Amex', 10, 2026, CURRENT_TIMESTAMP),
       (4, 'card_012jkl', '3333', 'Discover', 9, 2027, CURRENT_TIMESTAMP),
       (5, 'card_345mno', '4444', 'Visa', 8, 2028, CURRENT_TIMESTAMP);

-- Insert data into the SoCo Transaction table
INSERT INTO SoCo_Transaction (influencer_id, business_id, soco_amount, balance_remaining, soco_card_id, payment_type,
                              timestamp)
VALUES (1, 1, 50.00, 10.00, 1, 'qr', CURRENT_TIMESTAMP),
       (2, 2, 100.00, 20.00, 2, 'card', CURRENT_TIMESTAMP),
       (3, 3, 75.00, 15.00, 3, 'qr', CURRENT_TIMESTAMP),
       (4, 4, 120.00, 30.00, 4, 'card', CURRENT_TIMESTAMP),
       (5, 5, 90.00, 25.00, 5, 'qr', CURRENT_TIMESTAMP);

-- Insert data into the Transaction table
INSERT INTO Transaction (soco_t_id, stripe_payment_intent_id, amount, fee, status, captured_amount, created_at,
                         updated_at)
VALUES (1, 'pi_001', 50.00, 2.50, 'authorised', 47.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'pi_002', 100.00, 3.00, 'captured', 97.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'pi_003', 75.00, 2.25, 'cancelled', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'pi_004', 120.00, 4.00, 'captured', 116.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'pi_005', 90.00, 2.70, 'released', 87.30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the Balances table
INSERT INTO Balances (influencer_id, current_balance, timestamp)
VALUES (1, 500.00, CURRENT_TIMESTAMP),
       (2, 1000.00, CURRENT_TIMESTAMP),
       (3, 750.00, CURRENT_TIMESTAMP),
       (4, 1200.00, CURRENT_TIMESTAMP),
       (5, 900.00, CURRENT_TIMESTAMP);

-- Insert data into the Activity table
INSERT INTO Activity (influencer_id, business_id, content_published, amount, date_created)
VALUES (1, 1, 'Posted a review of TechWorld gadgets.', 100.00, CURRENT_TIMESTAMP),
       (2, 2, 'Promoted FashionHub clothing line.', 200.00, CURRENT_TIMESTAMP),
       (3, 3, 'Shared a Foodies restaurant experience.', 150.00, CURRENT_TIMESTAMP),
       (4, 4, 'Reviewed FitnessPro workout gear.', 180.00, CURRENT_TIMESTAMP),
       (5, 5, 'Posted about TravelLife destinations.', 130.00, CURRENT_TIMESTAMP);

-- Insert data into the Business_Transfer table
INSERT INTO Business_Transfer (transaction_id, stripe_transfer_id, business_id, transfer_amount, created_at)
VALUES (1, 'tr_001', 1, 47.50, CURRENT_TIMESTAMP),
       (2, 'tr_002', 2, 97.00, CURRENT_TIMESTAMP),
       (3, 'tr_003', 3, 0.00, CURRENT_TIMESTAMP),
       (4, 'tr_004', 4, 116.00, CURRENT_TIMESTAMP),
       (5, 'tr_005', 5, 87.30, CURRENT_TIMESTAMP);

-- Insert data into the Bank_Details table
INSERT INTO Bank_Details (business_id, bank_account_details, date_created)
VALUES (1, 'acct_123abc', CURRENT_DATE),
       (2, 'acct_456def', CURRENT_DATE),
       (3, 'acct_789ghi', CURRENT_DATE),
       (4, 'acct_012jkl', CURRENT_DATE),
       (5, 'acct_345mno', CURRENT_DATE);

-- Insert data into the Category table
INSERT INTO Category (name, follower_count)
VALUES ('Diamond', 1000000),
       ('Platinum', 500000),
       ('Gold', 100000),
       ('Silver', 50000),
       ('Bronze', 10000);

-- Insert data into the Spend_Cap table
INSERT INTO Spend_Cap (business_id, category_id, amount)
VALUES (1, 1, 5000.00),
       (1, 2, 10000.00),
       (2, 3, 7500.00),
       (2, 4, 9000.00),
       (3, 5, 100.00);

-- Insert data into the Stats table
INSERT INTO Stats (business_id, influencer_count, promotions_count, audience_reached)
VALUES (1, 5, 10, 500000),
       (2, 10, 20, 1000000),
       (3, 8, 15, 750000),
       (4, 6, 12, 600000),
       (5, 4, 8, 400000);

-- Insert data into the Business_Transfer table
INSERT INTO Business_Transfer (transaction_id, stripe_transfer_id, business_id, transfer_amount, created_at)
VALUES (1, 'tr_001', 1, 47.50, CURRENT_TIMESTAMP),
       (2, 'tr_002', 2, 97.00, CURRENT_TIMESTAMP),
       (3, 'tr_003', 3, 0.00, CURRENT_TIMESTAMP),
       (4, 'tr_004', 4, 116.00, CURRENT_TIMESTAMP),
       (5, 'tr_005', 5, 87.30, CURRENT_TIMESTAMP);
