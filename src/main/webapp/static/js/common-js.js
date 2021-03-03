/* bootstrap */
document.write('<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>');
document.write('<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>');
document.write('<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>');

// input浮点数处理
function clearNoNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');//只能输入两个小数  
	//如果没有小数点，不能为类似 01、02的金额 
	if(obj.value.indexOf(".")< 0 && obj.value !=""){
	    obj.value= parseFloat(obj.value); 
	}
	//如果有小数点，不能为类似 1.10的金额  
	if(obj.value.indexOf(".")> 0  && obj.value.indexOf("0")>2){
	    obj.value= parseFloat(obj.value); 
	}
	 //如果有小数点，不能为类似 0.00的金额 
	if(obj.value.indexOf(".")> 0  && obj.value.lastIndexOf("0")>1){
	    obj.value= parseFloat(obj.value); 
	}
}
function dateFormat(fmt, date) {
    let ret;
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };
    return fmt;
}