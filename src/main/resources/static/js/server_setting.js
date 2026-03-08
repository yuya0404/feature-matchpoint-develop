window.addEventListener("load",function(){
	document.getElementById('playerAtext').innerHTML = document.getElementById('playerA').value
	document.getElementById('playerBtext').innerHTML = document.getElementById('playerB').value
	document.getElementById('playerCtext').innerHTML = document.getElementById('playerC').value
	document.getElementById('playerDtext').innerHTML = document.getElementById('playerD').value
	window.sessionStorage.setItem(['coat'],[0]);
})

function changeLeft(){
	let playerA = document.getElementById('playerA')
	let playerB = document.getElementById('playerB')
	let tmp = playerA.value
	playerA.value = playerB.value
	playerB.value = tmp
	document.getElementById('playerAtext').innerHTML = playerA.value
	document.getElementById('playerBtext').innerHTML = playerB.value
}

function changeRight(){
	let playerC = document.getElementById('playerC')
	let playerD = document.getElementById('playerD')
	let tmp = playerC.value
	playerC.value = playerD.value
	playerD.value = tmp
	document.getElementById('playerCtext').innerHTML = playerC.value
	document.getElementById('playerDtext').innerHTML = playerD.value
}

function changeCoat(){
	let playerA = document.getElementById('playerA')
	let playerB = document.getElementById('playerB')
	let playerC = document.getElementById('playerC')
	let playerD = document.getElementById('playerD')
	let tmp1 = playerA.value
	let tmp2 = playerB.value
	playerA.value = playerC.value
	playerC.value = tmp1
	playerB.value = playerD.value
	playerD.value = tmp2
	if(window.sessionStorage.getItem(['coat']) == 0){
		window.sessionStorage.setItem(['coat'],[1]);
	}else if(window.sessionStorage.getItem(['coat']) == 1){
		window.sessionStorage.setItem(['coat'],[0]);
	}
	document.getElementById('playerAtext').innerHTML = playerA.value
	document.getElementById('playerBtext').innerHTML = playerB.value
	document.getElementById('playerCtext').innerHTML = playerC.value
	document.getElementById('playerDtext').innerHTML = playerD.value
}

function serverSet(team){
	const playerB = document.getElementById('playerBtext');
	const playerC = document.getElementById('playerCtext');
	const labelA  = document.getElementById('serve-label-a');
	const labelB  = document.getElementById('serve-label-b');

	// プレイヤーカードのハイライトをリセット
	playerB.classList.remove('server-active');
	playerC.classList.remove('server-active');

	// サーブ権カードのハイライトをリセット
	if (labelA) { labelA.classList.remove('selected-a'); }
	if (labelB) { labelB.classList.remove('selected-b'); }

	if(team == 1){
		playerB.classList.add('server-active');
		if (labelA) labelA.classList.add('selected-a');
	} else if(team == 2){
		playerC.classList.add('server-active');
		if (labelB) labelB.classList.add('selected-b');
	}
}