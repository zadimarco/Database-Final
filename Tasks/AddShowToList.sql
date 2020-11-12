-- This task is quite odd. The only unique identifier for a show is the ID
-- This means that we need the user to input the showID to exactly identify the show
-- The user, however, can be identified through their email

SET @user = ':userEmail' COLLATE utf8mb4_general_ci;
SET @show = ':showID';

INSERT INTO userlistshows
VALUES
	((SELECT u.id FROM yauser u WHERE u.email = @user), @show);