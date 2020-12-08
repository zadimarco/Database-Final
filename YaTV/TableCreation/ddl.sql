
CREATE TABLE yauser
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    country VARCHAR(50),
    salt VARCHAR(50),
    passw VARCHAR(512),
    CONSTRAINT user_email_uk UNIQUE KEY (email)

);

CREATE TABLE IF NOT EXISTS app
(
    name VARCHAR(50) PRIMARY KEY,
    description VARCHAR(500)

);

CREATE TABLE IF NOT EXISTS Subscription
(
    userID INT,
    appName VARCHAR(50),
    cost FLOAT(12,2),
    expirationDate DATE,
    PRIMARY KEY (userID, appName),
    CONSTRAINT sub_user_fk FOREIGN KEY (userID) REFERENCES yauser(id),
    CONSTRAINT sub_app_fk FOREIGN KEY (appName) REFERENCES app (name)
);

CREATE TABLE IF NOT EXISTS Platform
(
    name VARCHAR(50) PRIMARY KEY,
    isMobile BOOLEAN

);

CREATE TABLE IF NOT EXISTS appplatform
(
    appName VARCHAR(50),
    platformName VARCHAR(50),
    rating FLOAT(4,2),
    version VARCHAR(12),
    PRIMARY KEY (appName, platformName),
    CONSTRAINT ap_app_fk FOREIGN KEY (appName) REFERENCES app (name),
    CONSTRAINT ap_platform_fk FOREIGN KEY (platformName) REFERENCES Platform(name)
);

CREATE TABLE IF NOT EXISTS YAShow
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    description VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS Season
(
    showID INT,
    number INT,
    PRIMARY KEY (showID, number),
    CONSTRAINT seas_show_fk FOREIGN KEY  (showID) REFERENCES YAShow(id)
);

CREATE TABLE IF NOT EXISTS Video
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    host VARCHAR(50),
    releaseDate DATE,
    isFree BOOLEAN,
    description VARCHAR(500),
    durationMS INT,
    episodeNum INT,
    showID INT,
    seasonNum INT,
    CONSTRAINT vid_season_fk FOREIGN KEY (showID, seasonNum) REFERENCES Season(showID, number),
    CONSTRAINT vid_app_fk FOREIGN KEY (host) REFERENCES app (name)
);



CREATE TABLE IF NOT EXISTS Watched
(
    userID INT,
    videoID INT,
    liked BOOLEAN,
    PRIMARY KEY (userID, videoID),
    CONSTRAINT wat_user_fk FOREIGN KEY (userID) REFERENCES yauser(id),
    CONSTRAINT wat_video_fk FOREIGN KEY (videoID) REFERENCES Video(id)
);

CREATE TABLE IF NOT EXISTS Tag
(
    videoID INT,
    tagName VARCHAR(50),
    PRIMARY KEY (videoID, tagName),
    CONSTRAINT tag_video_fk FOREIGN KEY (videoID) REFERENCES Video(id)
);

CREATE TABLE IF NOT EXISTS UserListShows
(
    userID INT,
    showID INT,
    PRIMARY KEY (userID, showID),
    CONSTRAINT uls_show_fk FOREIGN KEY (showID) REFERENCES YAShow(id),
    CONSTRAINT uls_user_fk FOREIGN KEY (userID) REFERENCES yauser(id)
);

CREATE TABLE IF NOT EXISTS UserListVideos
(
    userID INT,
    videoID INT,
    PRIMARY KEY (userID, videoID),
    CONSTRAINT ulv_vid_fk FOREIGN KEY (videoID) REFERENCES Video(id),
    CONSTRAINT ulv_user_fk FOREIGN KEY (userID) REFERENCES yauser(id)
);




