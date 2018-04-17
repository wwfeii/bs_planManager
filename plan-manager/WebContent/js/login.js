function freshImg(){
	document.getElementById('codeImg').src="http://localhost:8080/plan-manager/user/getValidateImg.action?date="+new Date().getTime();
}