$(function(){
	var tab = 'account_number';
	// 用户名密码登录选项卡切换
	$(".account_number").click(function () {
		$('.tel-warn').addClass('hide'); //隐藏错误提示
		tab = $(this).attr('class').split(' ')[0]; //设置当前为tab = account_number
		checkBtn();
        $(this).addClass("on"); //account_number 添加 on类

        $(".message").removeClass("on"); //移除手机登录 on
        $(".email").removeClass("on");//移除邮箱登录 on
        $(".form2").addClass("hide");
        $(".form3").addClass("hide");

        $(".form1").removeClass("hide");
    });
	// 手机号登录选项卡切换
	$(".message").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
		$(this).addClass("on");

        $(".account_number").removeClass("on");
        $(".email").removeClass("on");
		$(".form1").addClass("hide");
        $(".form3").addClass("hide");

        $(".form2").removeClass("hide");
    });

    // 邮箱号登录选项卡切换
    $(".email").click(function () {
        $('.tel-warn').addClass('hide'); //隐藏错误提示
        tab = $(this).attr('class').split(' ')[0];
        checkBtn();
        $(this).addClass("on");

        $(".account_number").removeClass("on"); //移除账号登录 on 类
        $(".message").removeClass("on"); //移除手机登录 on 类
        $(".form1").addClass("hide");
        $(".form2").addClass("hide");

        $(".form3").removeClass("hide");
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

    //邮箱号
    $('#num3').keyup(function(event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    //短信验证码
	$('#veri-code').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

    //邮箱验证码
    $('#veri-code3').keyup(function(event) {
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
		} else if(tab == 'message'){ //
			var phone = $.trim($('#num2').val());
			var code2 = $.trim($('#veri-code').val());
			if (phone != '' && code2 != '') {
				$(".log-btn").removeClass("off");
				sendBtn();
			} else {
				$(".log-btn").addClass("off");
			}
		}
		else {
            var email = $.trim($('#num3').val());
            var code3 = $.trim($('#veri-code3').val());
            if (email != '' && code3 != '') {
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
			$('.img-err').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			$('.img-err').addClass('hide');
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

    function checkEmail(email){
        var status = true;
        if (email == '') {
            $('.num3-err').removeClass('hide').find("em").text('请输入邮箱号');
            return false;
        }
        var param = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (!param.test(email)) {
            // globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
            $('.num3-err').removeClass('hide');
            $('.num3-err').text('邮箱号不合法，请重新输入');
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

    //校验邮箱验证码
    function checkEmailCode(pCode){
        if (pCode == '') {
            $('.error3').removeClass('hide').text('请输入验证码');
            return false;
        } else {
            $('.error3').addClass('hide');
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
		} else if (tab == 'message'){ //
			$(".log-btn").click(function(){ //手机号登录
				// var type = 'phone';
				var phone = $.trim($('#num2').val()); //手机号码
				var pcode = $.trim($('#veri-code').val()); //短信验证码
				if (checkPhone(phone) && checkPhoneCode(pcode)) {//检查手机号 和 短信验证码
					$.ajax({ //发送 ajax请求
			            url: '/auth/mobile',
			            type: 'post',
			            dataType: 'json',
			            async: true,
			            data: {mobileNumber:phone,code:pcode},
			            success:function(data){
                            console.log(data)
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
		else if (tab == 'email'){
            $(".log-btn").click(function(){ //邮箱登录
                // var type = 'phone';
                var email = $.trim($('#num3').val()); //邮箱号
                var ecode = $.trim($('#veri-code3').val()); //邮箱验证码
                if (checkEmail(email) && checkEmailCode(ecode)) {//检查邮箱 和 邮箱验证码
                    $.ajax({ //发送 ajax请求
                        url: '/auth/email',
                        type: 'post',
                        dataType: 'json',
                        async: true,
                        data: {email:email,code:ecode},
                        success:function(data){
                            console.log(data)
                            window.location.href = data.redirectUrl
                            return true
                        },
                        error:function(data){
                            console.log(data)
                            data = data.responseJSON
                            if(data.code == '1') { //帐号错误
                                $(".log-btn").off('click').addClass("off");
                                $('.num3-err').removeClass('hide').text(data.message);
                                return false;
                            } else if(data.code == '2') { //验证码错误
                                $(".log-btn").off('click').addClass("off");
                                $('.error3').removeClass('hide').text(data.message);
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
                    oEm.text("60");
                    oTime.addClass("hide");
                }
            },1000);
		}
    });

    //发送邮箱验证码
    $(".form-data").delegate(".send3","click",function () {
        var email = $.trim($('#num3').val()); //邮箱
        if (checkEmail(email)) {
            $.ajax({
                url: '/code/emailCode',
                type: 'get',
                dataType: 'json',
                async: true,
                data: {email:email},
                success:function(data){

                },
                error:function(data){
                    console.log(data)
                    data = data.responseJSON
                    alert(data.message)
                }
            });

            var oTime = $(".form-data .time3"),
                oSend = $(".form-data .send3"),
                num = parseInt(oTime.text()),
                oEm = $(".form-data .time3 em");
            $(this).hide();
            oTime.removeClass("hide");
            var timer = setInterval(function () {
                var num2 = num-=1;
                oEm.text(num2);
                if(num2==0){
                    clearInterval(timer);
                    oSend.text("重新发送验证码");
                    oSend.show();
                    oEm.text("60");
                    oTime.addClass("hide");
                }
            },1000);
        }
    });

//	图形验证码点击事件
    $('.code').find('img').click(function(event) {
        $(this).attr('src', '/code/imageCode?width=100&height=40&r='+Math.random());
    });
});