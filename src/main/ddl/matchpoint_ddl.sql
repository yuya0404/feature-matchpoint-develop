--データベース作成
create database matchpoint;

-- 対戦組み合わせ
create table match (
  match_id serial not null
  , comp_id integer not null
  , game_no integer not null
  , team_id_a integer
  , team_id_b integer
  , constraint match_PKC primary key (match_id)
) ;

-- スコア
create table score (
  score_id serial not null
  , game_info_id integer not null
  , set_no integer not null
  , team_a_score integer not null
  , team_b_score integer not null
  , constraint score_PKC primary key (score_id)
) ;

-- 試合情報
create table game_info (
  game_info_id serial not null
  , match_id integer not null
  , coat_no integer
  , judge_name character varying(50)
  , record_status integer not null
  , record_date timestamp
  , max_point integer not null
  , game_count integer not null
  , constraint game_info_PKC primary key (game_info_id)
) ;

-- チーム
create table team (
  team_id serial not null
  , comp_id integer not null
  , player_a_name character varying(30) not null
  , player_b_name character varying(30)
  , tournament_no integer not null
  , constraint team_PKC primary key (team_id)
) ;

-- 大会
create table comp (
  comp_id serial not null
  , comp_login_id character varying(10) not null
  , comp_name character varying(100) not null
  , comp_date date not null
  , comp_place varchar(100)
  , game_type integer not null
  , tournament_count integer not null
  , tournament_edit_status integer default 0 not null
  , memo character varying(255)
  , constraint comp_PKC primary key (comp_id)
) ;

-- 運営管理
create table management (
  login_id character varying(50) not null
  , password character varying(50) not null
  , constraint management_PKC primary key (login_id)
) ;

comment on table match is '対戦組み合わせ';
comment on column match.match_id is '対戦組み合わせID';
comment on column match.comp_id is '大会ID';
comment on column match.game_no is '試合番号';
comment on column match.team_id_a is 'チームID1';
comment on column match.team_id_b is 'チームID2';

comment on table score is 'スコア';
comment on column score.score_id is 'スコアID';
comment on column score.game_info_id is '試合情報ID';
comment on column score.set_no is 'セット番号';
comment on column score.team_a_score is 'チーム1スコア';
comment on column score.team_b_score is 'チーム2スコア';

comment on table game_info is '試合情報';
comment on column game_info.game_info_id is '試合情報ID';
comment on column game_info.match_id is '対戦組み合わせID';
comment on column game_info.coat_no is 'コート番号';
comment on column game_info.judge_name is '審判名';
comment on column game_info.record_status is '登録ステータス';
comment on column game_info.record_date is '登録日';
comment on column game_info.max_point is '最大得点';
comment on column game_info.game_count is 'ゲーム数';

comment on table team is 'チーム';
comment on column team.team_id is 'チームID';
comment on column team.comp_id is '大会ID';
comment on column team.player_a_name is '選手名前1';
comment on column team.player_b_name is '選手名前2';
comment on column team.tournament_no is 'トーナメント番号';

comment on table comp is '大会';
comment on column comp.comp_id is '大会ID';
comment on column comp.comp_login_id is '大会ログインID';
comment on column comp.comp_name is '大会名';
comment on column comp.comp_date is '開催日';
comment on column comp.comp_place is '場所';
comment on column comp.game_type is '試合形式';
comment on column comp.tournament_count is 'トーナメント数';
comment on column comp.tournament_edit_status is 'トーナメント編集ステータス';
comment on column comp.memo is '備考';

comment on table management is '運営管理';
comment on column management.login_id is 'ログインID';
comment on column management.password is 'パスワード';


--insert文
insert into management values ('admin', 'admin');
insert into comp (comp_login_id, comp_name,comp_date, comp_place, game_type, tournament_count, memo) values
('comp1','2022年度6月大会' , '2022-06-11', '那覇市市民体育館', 2, 4, ''),
('comp2','2022年度9月大会' , '2022-09-21', '沖縄市市民体育館', 1, 2, 'シングルス大会'),
('comp3','2022年度12月大会' , '2022-12-25', '宜野湾市立体育館', 2, 2, ''),
('comp4','2023年度3月大会' , '2023-03-15', '那覇市市民体育館', 1, 2, 'シングルス大会'),
('comp5','2023年度6月大会' , '2023-06-1', '沖縄市市民体育館', 2, 4, '');

insert into team (comp_id,player_a_name, player_b_name, tournament_no) values 
(1,'山田太郎', '山田花子', 1),
(1,'桃田賢斗', '常山幹太', 1),
(1,'西本拳太', '渡邉航貴', 1),
(1,'奈良岡功大', '保木卓朗', 1),
(1,'山口茜', '奥原希望', 1),
(1,'髙橋沙也加', '髙橋明日香', 1),
(1,'大堀彩', '福島由紀', 1),
(1,'廣田彩花', '永原和可那', 1),
(1,'山下恭平', '金子祐樹', 2),
(1,'渡辺勇大', '松居圭一郎', 2),
(1,'齋藤太一', '竹内義憲', 2),
(1,'小林優吾', '古賀輝', 2),
(1,'篠谷菜留', '松友美佐紀', 2),
(1,'東野有紗', '岩永鈴', 2),
(1,'中西 貴映', '松山奈未', 2),
(1,'松本麻佑', '志田千陽', 2),
(1,'古賀穂', '秦野陸', 3),
(1,'田中湧士', '高橋洸士', 3),
(1,'森口航士朗', '井上拓斗', 3),
(1,'三橋健也', '岡村洋輝', 3),
(1,'川上紗恵奈', '仁平菜月', 3),
(1,'水井ひらり', '大家夏稀', 3),
(1,'郡司莉子', '宮浦玲奈', 3),
(1,'保原彩夏', '櫻本絢子', 3),
(1,'井上誠也', '大垣空也', 4),
(1,'川邊悠陽', '大田隼也', 4),
(1,'岩野滉也', '齋藤駿', 4),
(1,'石川心菜', '吉川天乃', 4),
(1,'室屋奏乃', '岩城杏奈', 4),

(2,'山田太郎','', 1),
(2,'桃田賢斗','', 1),
(2,'西本拳太','', 1),
(2,'奈良岡功大','', 1),
(2,'山口茜','', 1),
(2,'髙橋沙也加','', 1),
(2,'大堀彩','', 1),
(2,'廣田彩花','', 1),
(2,'山下恭平','', 2),
(2,'渡辺勇大','', 2),
(2,'齋藤太一','',  2),
(2,'小林優吾','', 2),
(2,'篠谷菜留','',  2),
(2,'東野有紗','', 2),
(2,'中西 貴映','',  2),
(2,'松本麻佑', '',2),

(3,'山田太郎', '山田花子', 1),
(3,'桃田賢斗', '常山幹太', 1),
(3,'西本拳太', '渡邉航貴', 1),
(3,'奈良岡功大', '保木卓朗', 1),
(3,'山口茜', '奥原希望', 1),
(3,'髙橋沙也加', '髙橋明日香', 1),
(3,'大堀彩', '福島由紀', 1),
(3,'廣田彩花', '永原和可那', 1),
(3,'山下恭平', '金子祐樹', 2),
(3,'渡辺勇大', '松居圭一郎', 2),
(3,'齋藤太一', '竹内義憲', 2),
(3,'小林優吾', '古賀輝', 2),
(3,'篠谷菜留', '松友美佐紀', 2),
(3,'東野有紗', '岩永鈴', 2),
(3,'中西 貴映', '松山奈未', 2),
(3,'松本麻佑', '志田千陽', 2);

insert into match (comp_id, game_no, team_id_a, team_id_b) values 
(1,1,1,2),
(1,2,3,4),
(1,3,5,6),
(1,4,7,8),
(1,5,2,4),
(1,6,6,8),
(1,7,4,8),
(1,8,9,10),
(1,9,11,12),
(1,10,13,14),
(1,11,15,16),
(1,12,10,12),
(1,13,14,16),
(1,14, 12,16),
(1,15,17,18),
(1,16,19,20),
(1,17,21,22),
(1,18,23,24),
(1,19,18,20),
(1,20,22,24),
(1,21,20,24),
(1,22,25,26),
(1,23,27,28),
(1,24,29,30),
(1,25,31,32),
(1,26,26,28),
(1,27,30,32),
(1,28,28,32);


insert into game_info (match_id, coat_no, judge_name, record_status, record_date, max_point, game_count) values
(1,1,'佐藤裕子', 2,'2022/06/18 13:00:57',11,1),
(1,1,'佐藤太郎', 1,'2022/06/18 13:20:57',11,3),
(2,2,'鈴木', 0,'2022/06/18 13:00:57',21,3),
(3,3,'佐藤裕子',0 ,'2022/06/18 13:11:57',21,3),
(4,4,'新垣', 0,'2022/06/18 13:22:57',21,3);

insert into score (game_info_id, set_no, team_a_score, team_b_score) values
(1,1,11,8),
(2,1,1,11),
(2,2,21,15),
(2,3,18,21),
(3,1,21,18),
(3,2,17,21),
(3,3,14,21),
(4,1,21,13),
(4,2,18,21),
(4,3,18,21),
(5,1,15,21),
(5,2,21,19),
(5,3,16,21);

select * from management;
select * from comp;
select * from team;
select * from match;
select * from game_info;
select * from score;

/*ビュー作成*/
create view received_result as
SELECT m.match_id, m.comp_id,  g.game_info_id,m.game_no, g.coat_no, g.judge_name,
 g.record_status, g.record_date, g.max_point, g.game_count, t.tournament_no,
 m.team_id_a, t.player_a_name as team_a_player1, t.player_b_name as team_a_player2, 
 m.team_id_b, te.player_a_name as team_b_player1,  te.player_b_name as team_b_player2
from match m
join game_info g on g.match_id = m.match_id
join team t on t.team_id = m.team_id_a
join team te on te.team_id = m.team_id_b;

create view match_team as
SELECT m.match_id, m.comp_id,m.game_no, t.tournament_no,
 m.team_id_a, t.player_a_name as team_a_player1, t.player_b_name as team_a_player2, 
 m.team_id_b, te.player_a_name as team_b_player1,  te.player_b_name as team_b_player2
from match m
join team t on t.team_id = m.team_id_a
join team te on te.team_id = m.team_id_b;


/*ビュー削除*/
drop view received_result;
drop view match_team;

/*追加カラム*/
insert into match (comp_id, game_no, team_id_a, team_id_b) values 
(2,1,30,31),
(2,2,32,33),
(2,3,34,35),
(2,4,36,37),
(2,8,38,39),
(2,9,40,41),
(2,10,42,43),
(2,1,44,45),
(3,1,46,47),
(3,2,48,49),
(3,3,50,51),
(3,4,52,53),
(3,8,54,55),
(3,9,56,57),
(3,10,58,59),
(3,11,60,61),
(3,5,46,48),
(3,6,50,52),
(3,7,48,50),
(3,12,54,56),
(3,13,58,60),
(3,14,56,58);
