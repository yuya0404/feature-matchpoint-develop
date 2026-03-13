'use strict';

const Bracket = window['vue-tournament-bracket'];

const vue = new Vue({
    el: "#app",
    components: {
        bracket: Bracket
    },
    data: {
        tournaments: [],
        // 登録済み 選手情報
        teamLists: [],
        // 作成済みトーナメント
        existingMatchLists: [],
        // 登録済み 試合情報
        registeredMatchLists: [],
        matchNum: 0,
    },
    methods:{
        // トーナメント表のひな型を作る
        createTournament(tournamentNo, teamLists) {
            let teamNum = teamLists.length;
            let firstRoundNum = Math.ceil(teamNum / 2);
            this.tournaments.push(
                {
                    tournamentNo: tournamentNo,
                    rounds: [],
                }
            );
            this.createRounds(teamNum, firstRoundNum);
        },
        // ひな型を作るための再帰関数
        createRounds(teamNum, firstRoundNum) {
            teamNum = Math.ceil(teamNum / 2);
            this.tournaments[this.tournaments.length - 1].rounds.push(
                {
                    games: []
                }
            );
            for(let i = 0; i < teamNum; i++) {
                this.matchNum++;
                this.tournaments[this.tournaments.length - 1].rounds[this.tournaments[this.tournaments.length -1].rounds.length - 1].games.push(
                    {
                        gameNo: this.matchNum,
                        next: Math.ceil(this.matchNum) + firstRoundNum,
                        player1: {},
                        player2: {},
                    }
                );
            }
            if(teamNum > 1) {
                this.createRounds(teamNum);
            }
        },
        // 対戦組み合わせを参照
        allotTeam() {
            this.tournaments.forEach(tournament => {
                this.$set(
                    tournament.rounds[0],
                    'games',
                    tournament.rounds[0].games.map(game => {
                        const matchFromDb = this.existingMatchLists.find(existingMatch => game.gameNo === existingMatch.gameNo);
                            try {
                                return {
                                    gameNo: matchFromDb.gameNo,
                                    player1: {
                                        id: matchFromDb.teamIdA,
                                        name: matchFromDb.teamAPlayer2
                                            ? matchFromDb.teamAPlayer1 + "・" + matchFromDb.teamAPlayer2
                                            : matchFromDb.teamAPlayer1,
                                        winner: false,
                                    },
                                    player2: {
                                        id: matchFromDb.teamIdB,
                                        name: matchFromDb.teamBPlayer2
                                            ? matchFromDb.teamBPlayer1 + "・" + matchFromDb.teamBPlayer2
                                            : matchFromDb.teamBPlayer1,
                                        winner: false,
                                    },
                                };    
                            } catch (error) {
                                console.log(error);
                            }
                    })
                );
            });
        },
        // // 取得した登録済みの試合情報をもとに、試合ごとのwinnerプロパティをtrueにする
        // allotMatch() {
        //     this.registeredMatchLists
        // },
        // // 試合番号ボタン押下時、画面遷移
        // viewResultOrStartGame(event) {
        //     const gameNo = event.target.value;            
        //     // 試合番号でReceivedResultに検索
        //     fetch(`searchMatchByGameNo?gameNo=${gameNo}`)
        //     .then(res => {
        //         // 条件式?　その試合が登録済みなら、試合結果画面に。 : でなければ、試合設定画面に。
        //         if(res === null) {
        //             // 試合設定画面に遷移
        //             location.href =  `match_from_tournament?gameNo=${gameNo}`;
        //         } else {
        //             res.json().then(data => {
        //                 if(data.length === 1 && isNotEmptyMatch(gameNo)) {
        //                     // 試合結果画面に遷移
        //                     location.href = `registered_game_result?gameNo=${gameNo}`;
        //                 } else {
        //                     // 空試合です
        //                 }
        //             })
        //         }
        //     })
        //     .catch(error => {
        //         console.log(error);
        //     });
        // },
        // トーナメント番号に対応する選手リストを返す（奇数なら空チームを補完）
        getTeamListsForNo(no) {
            let lists = this.teamLists.filter(team => team.tournamentNo === no);
            if (lists.length % 2 === 1) {
                lists.push({
                    teamId: -no,
                    playerAName: 'empty',
                    playerBName: '',
                    tournamentNo: no
                });
            }
            return lists;
        },
    },
    created: function() {
        // トーナメント表作成済みか否か確認
        let tournamentEditStatus;
        fetch("getTournamentEditStatus")
        .then(res => res.json().then(data => {
            tournamentEditStatus = data;
            if(tournamentEditStatus === 0) {
                document.getElementById("app").innerHTML = "トーナメントは未作成です。選手登録を完了させてトーナメントを作成してください。";
            } else {
                // チーム一覧取得
                fetch('getTeamList')
                .then(res => res.json().then(data => {
                    this.teamLists = data;
                    // 実際に選手が登録されているトーナメント番号だけ生成
                    const usedNos = [...new Set(data.map(t => t.tournamentNo))].sort((a, b) => a - b);
                    usedNos.forEach(no => {
                        this.createTournament(no, this.getTeamListsForNo(no));
                    });
                    // 対戦組み合わせ一覧取得
                    fetch('getMatchList')
                    .then(res => res.json().then(data => {
                        this.existingMatchLists = data;
                        this.allotTeam();
                        // 登録された試合一覧取得
                        fetch('getRegisteredMatchLists')
                        .then(res => {
                            if(res === null) {
                                // 登録された試合なし
                            } else {
                                // // 登録済みの試合を取得
                                // res.json().then(data => {
                                //     // console.log(data);
                                //     this.registeredMatchLists = data;
                                //     this.allotMatch();
                                // })
                                // .catch(error => console.log(error));
                            }
                        })
                    }))
                    .catch(error => console.log(error));
                }))
                .catch(error => console.log(error));
            }
        }))
        .catch(error => console.log(error));
    },
})