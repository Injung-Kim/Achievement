$(document).ready(function(){
	$('#sumbit').click( function(){
		//console.log($('#key').value)
		if($('#key').val() == ""){
			//console.log("입력값 없음")
			alert("검색어를 입력하세요")
		}else if($('#key').val() != undefined){			
			$('#form').submit()
		}
	})
	
})
