const playerA = document.getElementById('playerA').value
const playerB = document.getElementById('playerB').value
const playerC = document.getElementById('playerC').value
const playerD = document.getElementById('playerD').value

const textA = document.getElementById('playerAtext')
const textB = document.getElementById('playerBtext')
const textC = document.getElementById('playerCtext')
const textD = document.getElementById('playerDtext')

const point1 = document.getElementById('team1_point')
const point2 = document.getElementById('team2_point')

const pointText1 = document.getElementById('team1_point_text')
const pointText2 = document.getElementById('team2_point_text')

const server = document.getElementById('server')
let first_server
const server_set = document.getElementById('server_select')

const tasuBtn1 = document.getElementById('team1tasu')
const tasuBtn2 = document.getElementById('team2tasu')

window.addEventListener("load",function(){
	document.getElementById('playerAtext').innerHTML = document.getElementById('playerA').value
	document.getElementById('playerBtext').innerHTML = document.getElementById('playerB').value
	document.getElementById('playerCtext').innerHTML = document.getElementById('playerC').value
	document.getElementById('playerDtext').innerHTML = document.getElementById('playerD').value
	if(server.value == 'playerB'){
		server.value = document.getElementById('playerB').value
		first_server = server.value
		document.getElementById('playerBtext').setAttribute('style', 'color: red;')
	}else if(server.value == 'playerC'){
		server.value = document.getElementById('playerC').value
		first_server = server.value
		document.getElementById('playerCtext').setAttribute('style', 'color: red;')
	}
	document.getElementById('team1_point_text').innerHTML = 0
	document.getElementById('team2_point_text').innerHTML = 0
	document.getElementById('team1_point').value = document.getElementById('team1_point_text').innerHTML
	document.getElementById('team2_point').value = document.getElementById('team2_point_text').innerHTML
})

function serverSet(team, btn){
	let tmp
	var maxPoint = document.getElementById('max_point')
	console.log(first_server)
	if(btn == 'tasu'){
		if(team == 1){
			if(server.value == playerA || server.value == playerB){
				tmp = textA.innerHTML
				textA.innerHTML = textB.innerHTML
				textB.innerHTML = tmp
			}else if(server.value == playerC){
				if(first_server == playerB){
					server.value = playerB
					server_set.value = 'playerB'
				}else{
					server.value = playerA
					server_set.value = 'playerA'
				}
			}else if(server.value == playerD){
				if(first_server == playerB){
					server.value = playerA
					server_set.value = 'playerA'
				}else{
					server.value = playerB
					server_set.value = 'playerB'
				}
			}
			if(window.sessionStorage.getItem(['coat']) == null || window.sessionStorage.getItem(['coat']) == 0){
				pointText1.innerHTML = Number(pointText1.innerHTML) + 1
				point1.value = pointText1.innerHTML
			}else if(window.sessionStorage.getItem(['coat']) == 1){
				pointText1.innerHTML = Number(pointText1.innerHTML) + 1
				point2.value = pointText1.innerHTML
			}
		}else if(team == 2){
			if(server.value == playerC || server.value == playerD){
				tmp = textC.innerHTML
				textC.innerHTML = textD.innerHTML
				textD.innerHTML = tmp
			}else if(server.value == playerA){
				if(first_server == playerB){
					server.value = playerC
					server_set.value = 'playerC'
				}else {
					server.value = playerD
					server_set.value = 'playerD'
				}
				
			}else if(server.value == playerB){
				if(first_server == playerB){
					server.value = playerD
					server_set.value = 'playerD'
				}else{
					server.value = playerC
					server_set.value = 'playerC'
				}
				
			}
			if(window.sessionStorage.getItem(['coat']) == null || window.sessionStorage.getItem(['coat']) == 0){
				pointText2.innerHTML = Number(pointText2.innerHTML) + 1
				point2.value = pointText2.innerHTML
			}else if(window.sessionStorage.getItem(['coat']) == 1){
				pointText2.innerHTML = Number(pointText2.innerHTML) + 1
				point1.value = pointText2.innerHTML
			}
		}
		textA.setAttribute('style', '')
		textB.setAttribute('style', '')
		textC.setAttribute('style', '')
		textD.setAttribute('style', '')
		if(server.value == textA.innerHTML){
			textA.setAttribute('style', 'color: red;')
		}else if(server.value == textB.innerHTML){
			textB.setAttribute('style', 'color: red;')
		}else if(server.value == textC.innerHTML){
			textC.setAttribute('style', 'color: red;')
		}else if(server.value == document.getElementById('playerDtext').innerHTML){
			textD.setAttribute('style', 'color: red;')
		}
	}else if (btn == 'hiku'){
		if(team == 1){
			if(window.sessionStorage.getItem(['coat']) == null || window.sessionStorage.getItem(['coat']) == 0){
				if(Number(pointText1.innerHTML) > 0){
					pointText1.innerHTML = Number(pointText1.innerHTML) - 1
					point1.value = pointText1.innerHTML
				}
			}else if(window.sessionStorage.getItem(['coat']) == 1){
				if(Number(pointText1.innerHTML) > 0){
					pointText1.innerHTML = Number(pointText1.innerHTML) - 1
					point2.value = pointText1.innerHTML
				}
			}
		}else if(team == 2){
			if(window.sessionStorage.getItem(['coat']) == null || window.sessionStorage.getItem(['coat']) == 0){
				if(Number(pointText2.innerHTML) > 0){
					pointText2.innerHTML = Number(pointText2.innerHTML) - 1
					point2.value = pointText2.innerHTML
				}
			}else if(window.sessionStorage.getItem(['coat']) == 1){
				if(Number(pointText2.innerHTML) > 0){
					pointText2.innerHTML = Number(pointText2.innerHTML) - 1
					point1.value = pointText2.innerHTML
				}
			}
		}
	}
	
	if(Number(pointText1.innerHTML) >= Number(maxPoint.value)){
		if(Number(pointText1.innerHTML) - Number(pointText2.innerHTML) >= 2){
			tasuBtn1.style.visibility = "hidden"
		}else {
			tasuBtn1.style.visibility = "visible"
		}
		
	}else { 
		tasuBtn1.style.visibility = "visible"
	}
	
	if(Number(pointText2.innerHTML) >= Number(maxPoint.value)){
		if(Number(pointText2.innerHTML) - Number(pointText1.innerHTML) >= 2){
			tasuBtn2.style.visibility = "hidden"
		}else {
			tasuBtn2.style.visibility = "visible"
		}
		
	}else { 
		tasuBtn2.style.visibility = "visible"
	}
	
	if(Number(pointText1.innerHTML) >= Number(maxPoint.value) + 9 || Number(pointText2.innerHTML) >= Number(maxPoint.value) + 9) {
		tasuBtn1.style.visibility = "hidden"
		tasuBtn2.style.visibility = "hidden"
	}
}

function serverChange(){
	textA.setAttribute('style', '')
	textB.setAttribute('style', '')
	textC.setAttribute('style', '')
	textD.setAttribute('style', '')
	if(server_set.value == 'playerA'){
		server.value = playerA
	}else if(server_set.value == 'playerB'){
		server.value = playerB
	}else if(server_set.value == 'playerC'){
		server.value = playerC
	}else if(server_set.value == 'playerD'){
		server.value = playerD
	}
	
	if(server.value == textA.innerHTML){
		textA.setAttribute('style', 'color: red;')
	}else if(server.value == textB.innerHTML){
		textB.setAttribute('style', 'color: red;')
	}else if(server.value == textC.innerHTML){
		textC.setAttribute('style', 'color: red;')
	}else if(server.value == document.getElementById('playerDtext').innerHTML){
		textD.setAttribute('style', 'color: red;')
	}
}