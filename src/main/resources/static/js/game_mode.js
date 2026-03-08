const vue = new Vue({
	el: "#app",
	data:{
		gameNo: document.getElementById('gameNo').value,
		team: [],
		teamAPlayer1: '',
		teamAPlayer2: '',
		teamBPlayer1: '',
		teamBPlayer2: '',
	},
	methods:{
		teamSet() {
			fetch('searchMember?gameNo=' + this.gameNo)
			.then(res => res.json()
			.then(data => {
				this.team = data
				if(this.team.matchId != null){
					this.teamAPlayer1 = this.team.teamAPlayer1 || ''
					this.teamAPlayer2 = this.team.teamAPlayer2 || ''
					this.teamBPlayer1 = this.team.teamBPlayer1 || ''
					this.teamBPlayer2 = this.team.teamBPlayer2 || ''
				}else{
					this.teamAPlayer1 = 'メンバーがいません'
					this.teamAPlayer2 = ''
					this.teamBPlayer1 = 'メンバーがいません'
					this.teamBPlayer2 = ''
				}
			}))
		},
	},
	created:
		function() {
			fetch('searchMember?gameNo=' + this.gameNo)
			.then(res => res.json()
			.then(data => {
				this.team = data
				if(this.team.matchId != null){
					this.teamAPlayer1 = this.team.teamAPlayer1 || ''
					this.teamAPlayer2 = this.team.teamAPlayer2 || ''
					this.teamBPlayer1 = this.team.teamBPlayer1 || ''
					this.teamBPlayer2 = this.team.teamBPlayer2 || ''
				}else{
					this.teamAPlayer1 = 'メンバーがいません'
					this.teamAPlayer2 = ''
					this.teamBPlayer1 = 'メンバーがいません'
					this.teamBPlayer2 = ''
				}
			}))
		},
})