<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약페이지</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<!-- jQuery UI 국제화 대응을 위한 라이브러리 (다국어) -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/i18n/jquery-ui-i18n.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/i18n/datepicker-ko.js"></script>

<style>
#main {
	display: flex;
}

#title {
	font-size: 30px;
	color: #696969;
	border-bottom: 1px solid black;
	width: 50%;
	padding-bottom: 20px;
	margin-bottom: 10px;
}

/* .detail_img { */
/* 	width: 500px; */
/* 	height: 300px; */
/* } */

#info {
	display: flex;
}

#addr {
	float: right;
	font-size: 13px;
	justify-content: center;
	align-items: center;
}

#show_petsitter {
	margin-right: 20px;
}

#pay {
	border: 1px solid black;
	padding: 10px;
}

.date{
	margin-bottom: 10px;
	height: 25px;
}

.date1{
	height: 25px;
}

.petname{
	height: 25px;
	margin-bottom: 10px;
}
#pet_choice{
	height: 30px;

}
#price_div{
	position:relative;
	right: 0;
}
.price{
	text-align: right;
	margin-bottom: 10px;
	height: 25px;
	border: none;
}
</style>
</head>
<body>
	
	<%-- <div id="header">
 		<jsp:include page="header.jsp" /> 
	</div> --%>
	
	<h1>예약상세페이지</h1>
			<form action="reservation" method='post'>
	<div id='main'>
	
		<div id='show_petsitter'></div>
		<div id='pay'>
			<input type="text" class='date' name='start_day' placeholder='예약시작일' /> 부터
			<input type="text" class='date1'name='end_day' placeholder='예약종료일' /> <br />
			반려견 : <input type="text" class='petname' id='pet' name='pet' readOnly  />
			<input type="hidden" id='pet_no' name='pet_no'  />
		</div>
	</div>
				</form>
	<div id="review" style="width: 1129px; height: 400px; border: 1px solid black; margin: 10, 10, 10, 10;">
	<h3>후기</h3>
	<div style="border: 1px solid black;"></div>
	</div>



<script>
$(".date1").click(function(){
	alert("시작일값은 : "+$(".date").val());
	
});

</script>

	<script>
	var json_pet=${json_pet};
	console.log("json_pet ="+json_pet);
	console.log("-----------------------------");
	
	var str1="";
		str1+="<select id='pet_choice'>";
		str1+="<option value=''>선택하세요</option>";
	$.each(json_pet, function(key,val) {
		console.log("json_pet = "+json_pet[key].PET_NAME);	
		console.log("json_pet = "+json_pet[key].PET_NO);	
		str1+="<option value="+json_pet[key].PET_NO+">"+json_pet[key].PET_NAME+"</option>";
	
	});
	 	str1+="</select> <br />";
	 	
		 /* $.each(json_pet, function(key,val) {
			str1+="<input type='hidden' id='pet_no' name='pet_no' value="+json_pet[key].PET_NO+" />";
		});   */
		 
	$("#pay").append(str1);
	
	$("#pet_choice").change(function() {
		$("#pet_choice option:selected").each(function() {
				$("#pet").val($(this).text()); //선택값 입력 
				$("#pet_no").val($(this).val()); //선택값 입력 
			});
		});
// 	var jb = jQuery.noConflict();
		/*  	$(function() {
				$.datepicker.setDefaults($.datepicker.regional['ko']); //datepicker 한국어로 사용하기 위한 언어설정
				$('.date').datepicker({dateFormat: 'yy년 mm월 dd일',
										minDate: 0});
			});  */
	
		
			
			$('.date').datepicker({
				dateFormat : 'yy-mm-dd',
				minDate : 0,
				onClose : function(a) {
					console.log("시작일  "+ a);//시작일
					$('.date1').datepicker({
						dateFormat : 'yy-mm-dd',
						minDate : a,
						
					});

				}

			});


		var json = ${json_detail};
		var str = "";
		var pay = "";
		console.log(json);
		console.log(json.SITTER_TITLE);
		str += "<div id='title'>" + json.SITTER_TITLE + "</div>";
		str += "<div><img style='width: 500px; height: 300px;' id='detail_img' src='img/"+json.SITTER_PHOTO+"' alt='펫시터 이미지입니다.' /></div>";
		str += "<div id='info'>"
		str += "<div id='name' style='font-size: 20px'>" + json.US_NAME + " (" + json.SITTER_ID
				+ ")&nbsp;&nbsp;</div>";
		str += "<div id='addr' style='font-size: 20px'><div> ◎" + json.US_ADDRESS + "</div></div>";
		str += "</div>"
		str += "<div id='contents' style='font-size: 20px'>" + json.SITTER_BODY + "</div>";
		$("#show_petsitter").append(str);
		pay +="<div id='price_div'>";
		pay += "1박 : <input type='text' style='width:330px' class='price' readOnly value='"+json.SITTER_PRICE+"'/>원<br />";
		pay += "최종 금액 : <input type='text' style='width:285px'  id='f_pirce' class='price'  name='price' readOnly value='"+json.SITTER_PRICE+"'/>원";
		pay += "</div>";
		pay+="<input type='submit' value='최종 예약' />";
		pay+="<input type='hidden' name='sit_id' value='"+json.SITTER_ID+"' />";
		pay+="<input type='hidden' name='sit_addr' value='"+json.US_ADDRESS+"' />";

		$("#pay").append(pay);
	</script>
	
	<script>
		/* var json = ${json_review};
		var srt = "";
		console.log(json);
		console.log(json.res_no);
		
		$(.review).append(str); */
	</script>
</body>
</html>