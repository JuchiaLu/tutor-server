function doBinding(){
    $.ajax({
        url: '/social/binding',
        type: 'post',
        // async: true,
        success:function(data){
            console.log(data)
            console.log("成功")
            alert("成功")
            return true
        },
        error:function(data){
            console.log(data)
            console.log("失败")
            alert("失败")
            return false
        }
    })
}

$(function(){
	var tab = 'account_number';
	// 用户名密码登录选项卡切换
	$(".account_number").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
        $(this).addClass("on");
        $(".message").removeClass("on");
        $(".form2").addClass("hide");
        $(".form1").removeClass("hide");
    });
	// 手机号登录选项卡切换
	$(".message").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
		$(this).addClass("on");
        $(".account_number").removeClass("on");
		$(".form2").removeClass("hide");
		$(".form1").addClass("hide");
    });

    // 以下是输入框输入文字时把提示的错误隐藏
    
    // 用户名
	$('#num').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

    //密码
	$('#pass').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

    //图形验证码
	$('#veri').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

    //手机号
	$('#num2').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

    //短信验证码
	$('#veri-code').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});



	// 登录按钮是否可点击
	function checkBtn()
	{
		$(".log-btn").off('click');
		if (tab == 'account_number') {
			var inp = $.trim($('#num').val());
			var pass = $.trim($('#pass').val());
			if (inp != '' && pass != '') {
				if (!$('.code').hasClass('hide')) {
					code = $.trim($('#veri').val());
					if (code == '') {
						$(".log-btn").addClass("off");
					} else {
						$(".log-btn").removeClass("off");
						sendBtn();
					}
				} else {
					$(".log-btn").removeClass("off");
						sendBtn();
				}
			} else {
				$(".log-btn").addClass("off");
			}
		} else {
			var phone = $.trim($('#num2').val());
			var code2 = $.trim($('#veri-code').val());
			if (phone != '' && code2 != '') {
				$(".log-btn").removeClass("off");
				sendBtn();
			} else {
				$(".log-btn").addClass("off");
			}
		}
	}

    //校验用户名
	function checkAccount(username){
		if (username == '') {
			$('.num-err').removeClass('hide').find("em").text('请输入账户');
			return false;
		} else {
			$('.num-err').addClass('hide');
			return true;
		}
	}

    //校验密码
	function checkPass(pass){
		if (pass == '') {
			$('.pass-err').removeClass('hide').text('请输入密码');
			return false;
		} else {
			$('.pass-err').addClass('hide');
			return true;
		}
	}

    //校验图形验证码
	function checkCode(code){
		if (code == '') {
			$('.tel-warn').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			$('.tel-warn').addClass('hide');
			return true;
		}
	}

    //校验手机号
	function checkPhone(phone){
		var status = true;
		if (phone == '') {
			$('.num2-err').removeClass('hide').find("em").text('请输入手机号');
			return false;
		}
		var param = /^1[34578]\d{9}$/;
		if (!param.test(phone)) {
			// globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
			$('.num2-err').removeClass('hide');
			$('.num2-err').text('手机号不合法，请重新输入');
			return false;
		}
		// $.ajax({
        //     url: '/checkPhone',
        //     type: 'post',
        //     dataType: 'json',
        //     async: false,
        //     data: {phone:phone,type:"login"},
        //     success:function(data){
        //         if (data.code == '0') {
        //             $('.num2-err').addClass('hide');
        //             // console.log('aa');
        //             // return true;
        //         } else {
        //             $('.num2-err').removeClass('hide').text(data.msg);
        //             // console.log('bb');
		// 			status = false;
		// 			// return false;
        //         }
        //     },
        //     error:function(){
        //     	status = false;
        //         // return false;
        //     }
        // });
		return status;
	}

    //校验短信验证码
	function checkPhoneCode(pCode){
		if (pCode == '') {
			$('.error').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			$('.error').addClass('hide');
			return true;
		}
	}

	// 登录点击事件
	function sendBtn(){
		if (tab == 'account_number') {
			$(".log-btn").click(function(){
				// var type = 'phone';
				var inp = $.trim($('#num').val()); //用户名
				var pass =$.trim($('#pass').val()); //密码
				if (checkAccount(inp) && checkPass(pass)) { //检查用户名密码
					var ldata = {username:inp,password:pass}; //转换成Json
					if (!$('.code').hasClass('hide')) { //需要验证码
						code = $.trim($('#veri').val());//验证码
						if (!checkCode(code)) {//检测验证码
							return false;
						}
						ldata.code = code;//将验证码加入到Json
					}
					$.ajax({ //发送请求
			            url: '/auth/form',
			            type: 'post',
			            dataType: 'json',
			            async: true,
			            data: ldata,
			            success:function(data){
                            console.log(data)
                            doBinding()
                            window.location.href = data.redirectUrl
                            return true
			            },
			            error:function(data){ //ajax错误
			                console.log(data)
                            data = data.responseJSON
                            if(data.code == '1'){//账户错误
                                $(".log-btn").off('click').addClass("off");
                                $('.num-err').removeClass('hide').find('em').text(data.message);
                                $('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
                                $('.code').find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                                return false;
                            } else if(data.code == '2') { //密码错误
                                $(".log-btn").off('click').addClass("off");
                                $('.pass-err').removeClass('hide').find('em').text(data.message);
                                $('.pass-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
                                $('.code').removeClass('hide');
                                $('.code').find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                                return false;
                            } else if(data.code == '3') {//验证码错误
                                $(".log-btn").off('click').addClass("off");
                                $('.img-err').removeClass('hide').find('em').text(data.message);
                                $('.img-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
                                $('.code').removeClass('hide');
                                $('.code').find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                                return false;
                            }
			            }
			        });
				} else {
					return false;
				}
			});
		} else {
			$(".log-btn").click(function(){ //手机号登录
				// var type = 'phone';
				var phone = $.trim($('#num2').val()); //手机号码
				var pcode = $.trim($('#veri-code').val()); //短信验证码
				if (checkPhone(phone) && checkPass(pcode)) {//检查手机号 和 短信验证码
					$.ajax({ //发送 ajax请求
			            url: '/auth/mobile',
			            type: 'post',
			            dataType: 'json',
			            async: true,
			            data: {mobileNumber:phone,code:pcode},
			            success:function(data){
                            console.log(data)
                            doBinding()
                            window.location.href = data.redirectUrl
                            return true
			            },
			            error:function(data){
                            console.log(data)
                            data = data.responseJSON
                           if(data.code == '1') { //帐号错误
                                $(".log-btn").off('click').addClass("off");
                                $('.num2-err').removeClass('hide').text(data.message);
                                return false;
                            } else if(data.code == '2') { //验证码错误
                                $(".log-btn").off('click').addClass("off");
                                $('.error').removeClass('hide').text(data.message);
                                return false;
                            }
			            }
			        });
				} else {
					$(".log-btn").off('click').addClass("off");
					// $('.tel-warn').removeClass('hide').text('登录失败');
					return false;
				}
			});
		}
	}

	// 登录的回车事件
	$(window).keydown(function(event) {
    	if (event.keyCode == 13) {
    		$('.log-btn').trigger('click');
    	}
    });


    //发送短信验证码
	$(".form-data").delegate(".send","click",function () {
		var phone = $.trim($('#num2').val()); //手机号
		if (checkPhone(phone)) {
				$.ajax({
		            url: '/code/smsCode',
		            type: 'get',
		            dataType: 'json',
		            async: true,
		            data: {mobileNumber:phone},
		            success:function(data){

		            },
		            error:function(data){
                        console.log(data)
                        data = data.responseJSON
                        alert(data.message)
		            }
		        });

            var oTime = $(".form-data .time"),
                oSend = $(".form-data .send"),
                num = parseInt(oTime.text()),
                oEm = $(".form-data .time em");
            $(this).hide();
            oTime.removeClass("hide");
            var timer = setInterval(function () {
                var num2 = num-=1;
                oEm.text(num2);
                if(num2==0){
                    clearInterval(timer);
                    oSend.text("重新发送验证码");
                    oSend.show();
                    oEm.text("30");
                    oTime.addClass("hide");
                }
            },1000);
		}
    });

//	图形验证码点击事件
    $('.code').find('img').click(function(event) {
        $(this).attr('src', '/code/imageCode?width=100&height=40&r='+Math.random());
    });

    // 图标变成第三头像
    $.ajax({
        url: '/socialUser',
        type: 'get',
        dataType: 'json',
        async: true,
        success:function(data){
            $('.head-logo').attr('src',data.headImage)
            $('.wenzi').text('Hi '+data.nickname +' 欢迎你的到来~~')
        },
        error:function(data){

        }
    })

});