DROP VIEW IF EXISTS received_result;
DROP VIEW IF EXISTS match_team;
DROP TABLE IF EXISTS score;
DROP TABLE IF EXISTS game_info;
DROP TABLE IF EXISTS match;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS comp;
DROP TABLE IF EXISTS management;

CREATE TABLE management (
  login_id character varying(50) not null,
  password character varying(50) not null,
  constraint management_PKC primary key (login_id)
);

CREATE TABLE comp (
  comp_id serial not null,
  comp_login_id character varying(10) not null,
  comp_name character varying(100) not null,
  comp_date date not null,
  comp_place varchar(100),
  game_type integer not null,
  tournament_count integer not null,
  tournament_edit_status integer default 0 not null,
  memo character varying(255),
  constraint comp_PKC primary key (comp_id)
);

CREATE TABLE team (
  team_id serial not null,
  comp_id integer not null,
  player_a_name character varying(30) not null,
  player_b_name character varying(30),
  tournament_no integer not null,
  constraint team_PKC primary key (team_id)
);

CREATE TABLE match (
  match_id serial not null,
  comp_id integer not null,
  game_no integer not null,
  team_id_a integer,
  team_id_b integer,
  constraint match_PKC primary key (match_id)
);

CREATE TABLE score (
  score_id serial not null,
  game_info_id integer not null,
  set_no integer not null,
  team_a_score integer not null,
  team_b_score integer not null,
  constraint score_PKC primary key (score_id)
);

CREATE TABLE game_info (
  game_info_id serial not null,
  match_id integer not null,
  coat_no integer,
  judge_name character varying(50),
  record_status integer not null,
  record_date timestamp,
  max_point integer not null,
  game_count integer not null,
  constraint game_info_PKC primary key (game_info_id)
);

CREATE VIEW received_result AS
SELECT m.match_id, m.comp_id, g.game_info_id, m.game_no, g.coat_no, g.judge_name,
 g.record_status, g.record_date, g.max_point, g.game_count, t.tournament_no,
 m.team_id_a, t.player_a_name as team_a_player1, t.player_b_name as team_a_player2,
 m.team_id_b, te.player_a_name as team_b_player1, te.player_b_name as team_b_player2
FROM match m
JOIN game_info g ON g.match_id = m.match_id
JOIN team t ON t.team_id = m.team_id_a
JOIN team te ON te.team_id = m.team_id_b;

CREATE VIEW match_team AS
SELECT m.match_id, m.comp_id, m.game_no, t.tournament_no,
 m.team_id_a, t.player_a_name as team_a_player1, t.player_b_name as team_a_player2,
 m.team_id_b, te.player_a_name as team_b_player1, te.player_b_name as team_b_player2
FROM match m
JOIN team t ON t.team_id = m.team_id_a
JOIN team te ON te.team_id = m.team_id_b;
