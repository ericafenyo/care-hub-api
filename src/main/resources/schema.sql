CREATE TABLE IF NOT EXISTS cities(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    created_at DATETIME(6)  NULL,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT UK_CITY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS countries(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    created_at DATETIME(6)  NULL,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT UK_1pyiwrqimi3hnl3vtgsypj5r UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS addresses(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NULL,
    postal_code VARCHAR(255) NULL,
    street      VARCHAR(255) NULL,
    city_id     BINARY(16)   NULL,
    country_id  BINARY(16)   NULL,
    CONSTRAINT FK9fkb8qaj71tiyr9htkmn7r8y5 FOREIGN KEY (city_id) REFERENCES cities (id),
    CONSTRAINT FKn3sth7s3kur1rafwbbrqqnswt FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE IF NOT EXISTS patients(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    birth_date DATE         NULL,
    created_at DATETIME(6)  NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(50)  NULL,
    last_name  VARCHAR(50)  NULL,
    photo      VARCHAR(255) NULL,
    updated_at DATETIME(6)  NULL,
    address_id BINARY(16)   NULL,
    CONSTRAINT UK_a370hmxgv0l5c9panryr1ji7d UNIQUE (email),
    CONSTRAINT UK_pveescs3fe3p1eaabryivpydo UNIQUE (address_id),
    CONSTRAINT FKjc8017x8ae0rqi11m8jmny646 FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE IF NOT EXISTS permissions(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NOT NULL,
    description VARCHAR(100) NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    updated_at  DATETIME(6)  NOT NULL,
    CONSTRAINT UK_pnvtwliis6p05pn6i3ndjrqt2 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS recurrences(
    id            BINARY(16)                                    NOT NULL PRIMARY KEY,
    count         INT                                           NULL,
    day_of_month  INT                                           NULL,
    day_of_week   INT                                           NULL,
    frequency     ENUM ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY') NULL,
    month_of_year INT                                           NULL,
    occurrences   INT                                           NULL,
    week_of_month INT                                           NULL
);

CREATE TABLE IF NOT EXISTS roles(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NOT NULL,
    description VARCHAR(150) NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    slug        VARCHAR(50)  NOT NULL,
    updated_at  DATETIME(6)  NOT NULL,
    CONSTRAINT UK_ofx66keruapi6vyqpv6f2or37 UNIQUE (name),
    CONSTRAINT UK_sx80rwev5en94r3jv7riyoh1y UNIQUE (slug)
);

CREATE TABLE IF NOT EXISTS role_permission(
    role_id       BINARY(16) NOT NULL,
    permission_id BINARY(16) NOT NULL,
    CONSTRAINT FK2xn8qv4vw30i04xdxrpvn3bdi FOREIGN KEY (permission_id) REFERENCES permissions (id),
    CONSTRAINT FKtfgq8q9blrp0pt1pvggyli3v9 FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS users(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    birth_date DATE         NULL,
    created_at DATETIME(6)  NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(50)  NULL,
    last_name  VARCHAR(50)  NULL,
    photo      VARCHAR(255) NULL,
    updated_at DATETIME(6)  NULL,
    address_id BINARY(16)   NULL,
    CONSTRAINT UK_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT UK_hbvhqvjgmhd5omxyo67ynvbyp UNIQUE (address_id),
    CONSTRAINT FKe8vydtk7hf0y16bfm558sywbb FOREIGN KEY (address_id) REFERENCES addresses (id));

CREATE TABLE IF NOT EXISTS credentials(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    created_at DATETIME(6)  NULL,
    password   VARCHAR(255) NULL,
    updated_at DATETIME(6)  NULL,
    user_id    BINARY(16)   NULL,
    CONSTRAINT UK_ry431gkw9ueu8xq0yfbys0d1d UNIQUE (user_id),
    CONSTRAINT FKcbcgksvnqvqxrrc4dwv3qys65 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS teams(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NOT NULL,
    description VARCHAR(150) NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    updated_at  DATETIME(6)  NOT NULL,
    owner_id    BINARY(16)   NOT NULL,
    CONSTRAINT UK_a510no6sjwqcx153yd5sm4jrr UNIQUE (name),
    CONSTRAINT FKde03in0noals71lom04bmfgit FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS appointments(
    id            BINARY(16)   NOT NULL PRIMARY KEY,
    created_at    DATETIME(6)  NULL,
    description   VARCHAR(255) NULL,
    end_date      DATETIME(6)  NULL,
    location      VARCHAR(255) NULL,
    start_date    DATETIME(6)  NULL,
    title         VARCHAR(255) NULL,
    updated_at    DATETIME(6)  NULL,
    author_id     BINARY(16)   NULL,
    recurrence_id BINARY(16)   NULL,
    team_id       BINARY(16)   NULL,
    CONSTRAINT UK_nqfka0t0jb97fp4xr6u4fs5ur UNIQUE (recurrence_id),
    CONSTRAINT FK6hq8yr52y6vd5l9i1rxv1qdnj FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT FKcgg67gialdtc8fbmirhpqduu0 FOREIGN KEY (recurrence_id) REFERENCES recurrences (id),
    CONSTRAINT FKr5tnaqlrrbdxxmry19yeusqu0 FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE IF NOT EXISTS invitations(
    id         BINARY(16)                                              NOT NULL PRIMARY KEY,
    created_at DATETIME(6)                                             NOT NULL,
    email      VARCHAR(255)                                            NOT NULL,
    expires_at DATETIME(6)                                             NOT NULL,
    first_name VARCHAR(255)                                            NOT NULL,
    last_name  VARCHAR(255)                                            NOT NULL,
    status     ENUM ('PENDING', 'ACCEPTED', 'INVALIDATED', 'DECLINED') NOT NULL,
    token      VARCHAR(255)                                            NOT NULL,
    updated_at DATETIME(6)                                             NOT NULL,
    used_at    DATETIME(6)                                             NULL,
    inviter_id BINARY(16)                                              NOT NULL,
    role_id    BINARY(16)                                              NOT NULL,
    team_id    BINARY(16)                                              NOT NULL,
    CONSTRAINT UK_t4i6esv44p6yi7cxq277vlo3i UNIQUE (token),
    CONSTRAINT FK11do2m26r7o1doyjilyj1fsgm FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT FK1m1usbedadl51q5ea4vic07nv FOREIGN KEY (team_id) REFERENCES teams (id),
    CONSTRAINT FKc93ihvftpd11j547qgc9fobmc FOREIGN KEY (inviter_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS medications
(
    id           BINARY(16)   NOT NULL PRIMARY KEY,
    created_at   DATETIME(6)  NULL,
    dosage       VARCHAR(255) NULL,
    end_date     DATE         NULL,
    frequency    VARCHAR(255) NULL,
    instructions VARCHAR(255) NULL,
    name         VARCHAR(255) NULL,
    route        VARCHAR(255) NULL,
    start_date   DATE         NULL,
    updated_at   DATETIME(6)  NULL,
    team_id      BINARY(16)   NULL,
    user_id      BINARY(16)   NOT NULL,
    CONSTRAINT FKqu85h2rt9rdafsguptnna99ju FOREIGN KEY (team_id) REFERENCES teams (id),
    CONSTRAINT FKsae8ns7nscnqntu61xu8xxwl3 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS memberships
(
    id         BINARY(16)                                                       NOT NULL PRIMARY KEY,
    created_at DATETIME(6)                                                      NULL,
    status     ENUM ('ACTIVE', 'SUSPENDED', 'PENDING', 'REJECTED', 'CANCELLED') NOT NULL,
    updated_at DATETIME(6)                                                      NULL,
    role_id    BINARY(16)                                                       NULL,
    team_id    BINARY(16)                                                       NULL,
    user_id    BINARY(16)                                                       NULL,
    CONSTRAINT FKdjormybfoo7f4i4d4r803qohb FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKlxbhd8wq2idsff42jqlnc0uwb FOREIGN KEY (team_id) REFERENCES teams (id),
    CONSTRAINT FKokckx6lcp3k4fwe6qqc621jsp FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS notes
(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    content    VARCHAR(255) NULL,
    created_at DATETIME(6)  NULL,
    title      VARCHAR(255) NULL,
    updated_at DATETIME(6)  NULL,
    author_id  BINARY(16)   NOT NULL,
    team_id    BINARY(16)   NOT NULL,
    CONSTRAINT FKeequ6tj8iu98mxv7jr0nrb98n FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT FKsu2vcy6dmxg9w68u8clqojpbh FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE IF NOT EXISTS reminders
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NULL,
    description VARCHAR(255) NULL,
    location    VARCHAR(255) NULL,
    title       VARCHAR(255) NULL,
    updated_at  DATETIME(6)  NULL,
    team_id     BINARY(16)   NULL,
    CONSTRAINT FK2htdapcvh7fnu3tr5dxmsts36 FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE IF NOT EXISTS tasks
(
    id            BINARY(16)                                          NOT NULL PRIMARY KEY,
    created_at    DATETIME(6)                                         NULL,
    description   VARCHAR(255)                                        NULL,
    due_date      DATE                                                NULL,
    priority      ENUM ('LOW', 'MEDIUM', 'HIGH', 'URGENT')            NOT NULL,
    status        ENUM ('PLANNED', 'STARTED', 'COMPLETED', 'BLOCKED') NOT NULL,
    title         VARCHAR(255)                                        NOT NULL,
    updated_at    DATETIME(6)                                         NULL,
    recurrence_id BINARY(16)                                          NULL,
    team_id       BINARY(16)                                          NOT NULL,
    CONSTRAINT UK_irx097316chbjd4e3e7wsqa6e UNIQUE (recurrence_id),
    CONSTRAINT FK7ohls81a92yq2hlgcml3h1atu FOREIGN KEY (team_id) REFERENCES teams (id),
    CONSTRAINT FKdp1alhjpk99cub4cmgtf6fntf FOREIGN KEY (recurrence_id) REFERENCES recurrences (id)
);

CREATE TABLE IF NOT EXISTS task_user
(
    task_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    CONSTRAINT FK32eeu8p13crqmo7dfdtn6hncm FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKgaja271i7nlmd3qhg85coclr7 FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE IF NOT EXISTS vital_records
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    created_at  DATETIME(6)  NULL,
    notes       VARCHAR(255) NULL,
    recorded_at DATETIME(6)  NULL,
    updated_at  DATETIME(6)  NULL,
    member_id   BINARY(16)   NOT NULL,
    team_id     BINARY(16)   NOT NULL,
    CONSTRAINT FK1lbi2w2xa2t79cfqvdujo2m4y FOREIGN KEY (member_id) REFERENCES users (id),
    CONSTRAINT FKpnfl2bbvp2ywmqpqnvnh3bhr6 FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE IF NOT EXISTS vitals
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    created_at DATETIME(6) NULL,
    type       VARCHAR(50) NOT NULL,
    unit       VARCHAR(20) NOT NULL,
    updated_at DATETIME(6) NULL,
    CONSTRAINT UK_h40ut65dayf1b7thmi5wxsl UNIQUE (type)
);

CREATE TABLE IF NOT EXISTS vital_measurements
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    report_id  BINARY(16)  NOT NULL,
    vital_id   BINARY(16)  NOT NULL,
    value      VARCHAR(20) NOT NULL,
    CONSTRAINT FK9bn144umpuer8b0pwd5m8vcvh FOREIGN KEY (report_id) REFERENCES vital_records (id),
    CONSTRAINT FKpyudxgprd56gmtlq3yi7xgbq6 FOREIGN KEY (vital_id) REFERENCES vitals (id)
);
