-- Insert a poll
INSERT INTO poll_entity (id, title, description)
VALUES (1, 'Which language do you prefer?', 'Choose your favorite from the list.');

-- Insert options for the poll
INSERT INTO option_entity (id, text, poll_id)
VALUES
    (1, 'Java', 1),
    (2, 'Kotlin', 1);