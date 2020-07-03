$(function(){

    var checked={}

    // 用户名
    $('#username').blur(function(event) {
        checked['username'] = checkUsername()
        checkBtn()
    });

    //密码
    $('#password').blur(function(event) {
        checked['password'] = checkPassword()
        checkBtn()
    });

    //确认密码
    $('#password2').blur(function(event) {
        checked['password2'] = checkPassword2()
        checkBtn()
    });

    //图形验证码
    $('#imageCode').blur(function(event) {
        checked['imageCode'] = checkImageCode()
        checkBtn()
    });

    //手机号
    // $('#mobileNumber').blur(function(event) {
    //     checked['mobileNumber'] = checkMobileNumber()
    //     checkBtn()
    // });
    //
    // //短信验证码
    // $('#smsCode').blur(function(event) {
    //     checked['smsCode'] = checkSmsCode()
    //     checkBtn()
    // });

    //协议
    // $('#agree').blur(function(event) {
    //     checked['agree'] = checkIAgree()
    //     checkBtn()
    // });


    function checkBtn()
    {
        var i = 0
        $(".reg-btn").addClass("off")
        .attr("disabled",true);
        Object.keys(checked).forEach(function(key){
            if((key,checked[key])===true){
                i++
            }
        });
        if(i===4){
            $(".reg-btn").removeClass("off")
            .attr("disabled",false)
        }
        return i===4
    }


    //校验用户名
    function checkUsername(){
        var username = $.trim($('#username').val());
        if (username === '') {
            $('.username-err').removeClass('hide').find("em").text('请输入用户名');
            return false;
        } else {
            // 远程校验用户名
            var status = true
             $.ajax({
                url: '/users/isExist/'+username,
                type: 'get',
                dataType: 'json',
                async: false,
                success:function(data){
                    if(data.isExist){
                        $('.username-err').removeClass('hide').find("em").text('用户名已存在');
                        status = false;
                    }else {
                        $('.username-err').addClass('hide');
                        status = true
                    }
                },
                error:function(data){
                    $('.username-err').removeClass('hide').find("em").text(data.message);
                    status = false;
                }
            });
            // $('.username-err').addClass('hide');
            return status;
        }
    }

    //校验密码
    function checkPassword(){
        var password = $.trim($('#password').val());
        if (password === '') {
            $('.password-err').removeClass('hide').find("em").text('请输入密码');
            return false;
        } else {
            $('.password-err').addClass('hide');
            return true;
        }
    }

    //校验确认密码
    function checkPassword2(){
        var password = $.trim($('#password').val());
        var password2 = $.trim($('#password2').val());
        if (password2 === '') {
            $('.password2-err').removeClass('hide').find("em").text('请确认密码');
            return false;
        } else if(password !== password2){
            $('.password2-err').removeClass('hide').find("em").text('两次密码不一致');
            return false
        }else {
            $('.password2-err').addClass('hide');
            return true;
        }
    }


    //校验图形验证码
    function checkImageCode(){
        var imageCode = $.trim($('#imageCode').val());
        if (imageCode === '') {
            $('.imageCode-err').removeClass('hide').find("em").text('请输入验证码');
            return false;
        } else {
            $('.imageCode-err').addClass('hide');
            return true;
        }
    }

    // //校验手机号
    // function checkMobileNumber(){
    //     var mobileNumber = $.trim($('#mobileNumber').val());
    //     var status = true;
    //     if (mobileNumber === '') {
    //         $('.mobileNumber-err').removeClass('hide').find("em").text('请输入手机号');
    //         return false;
    //     }
    //     var param = /^1[34578]\d{9}$/;
    //     if (!param.test(mobileNumber)) {
    //         $('.mobileNumber-err').removeClass('hide').find("em").text('手机号不合法，请重新输入');
    //         return false;
    //     }
    //     // 远程校验
    //     // $.ajax({
    //     //     url: '/checkMobileNumber',
    //     //     type: 'post',
    //     //     dataType: 'json',
    //     //     async: false,
    //     //     data: {mobileNumber:mobileNumber,type:"login"},
    //     //     success:function(data){
    //     //         $('.imageCode-err').addClass('hide');
    //     //     },
    //     //     error:function(data){
    //     //         $('.mobileNumber-err').removeClass('hide').text(data.message);
    //     //         $(".reg-btn").addClass("off");
    //     //         return false;
    //     //     }
    //     // });
    //     $('.mobileNumber-err').addClass('hide');
    //     return status;
    // }
    //
    // //校验短信验证码
    // function checkSmsCode(){
    //     var smsCode = $.trim($('#smsCode').val());
    //     if (smsCode === '') {
    //         $('.smsCode-err').removeClass('hide').find("em").text('请输入验证码');
    //         return false;
    //     } else {
    //         $('.smsCode-err').addClass('hide');
    //         return true;
    //     }
    // }


    function checkIAgree(){
        var agree = $.trim($('#agree').val());
        if (agree === '') {
            return false;
        } else {
            return true;
        }
    }

    // 注册点击事件
    $(".reg-btn").click(function(){
        var username = $.trim($('#username').val()); //用户名
        var password = $.trim($('#password').val()); //密码
        var user = {username:username,password:password}; //转换成Json
        if (!$('.code').hasClass('hide')) { //需要验证码
            var code = $.trim($('#imageCode').val());//验证码
            if (!checkImageCode(code)) {//检测验证码
                return false;
            }
            user.code = code;//将验证码加入到Json
        }
        $.ajax({ //发送请求
            url: '/users',
            type: 'post',
            // dataType: 'json',
            contentType: "application/json;charset=utf-8",
            // async: true,
            data: JSON.stringify(user),
            success:function(){
                $('.globalInfoTip').removeClass('hide').text("注册成功!")
                //window.setTimeout(window.location.href = '/login.html', 3000);
                return true
            },
            error:function(data){ //ajax错误
                console.log(data)
                data = data.responseJSON
                if(data.code === '1'){//账户错误
                    $(".reg-btn").off('click').addClass("off");
                    $('.username-err').removeClass('hide').find('em').text(data.message)
                    .find('i').attr('class', 'icon-warn').css("color","#d9585b");
                    $('.code').find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                    return false;
                } else if(data.code == '2') { //密码错误
                    $(".reg-btn").off('click').addClass("off");
                    $('.password-err').removeClass('hide').find('em').text(data.message)
                    .find('i').attr('class', 'icon-warn').css("color","#d9585b");
                    $('.code').removeClass('hide')
                    .find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                    return false;
                } else if(data.code == '3') {//验证码错误
                    $(".reg-btn").off('click').addClass("off");
                    $('.imageCode-err').removeClass('hide').find('em').text(data.message)
                    .find('i').attr('class', 'icon-warn').css("color","#d9585b");
                    $('.code').removeClass('hide')
                    .find('img').attr('src','/code/imageCode?width=100&height=40&r='+Math.random());
                    return false;
                }
            }
        });
    });

    // 注册的回车事件
    $(window).keydown(function(event) {
        if (event.keyCode === 13) {
            if(checkBtn()){
                $('.reg-btn').trigger('click');
            }
        }
    });


    //发送短信验证码
    // $(".form-data").delegate(".send","click",function () {
    //     var mobileNumber = $.trim($('#mobileNumber').val()); //手机号
    //     if (checkMobileNumber()) {
    //         $.ajax({
    //             url: '/code/smsCode',
    //             type: 'get',
    //             dataType: 'json',
    //             async: true,
    //             data: {mobileNumber:mobileNumber},
    //             success:function(data){
    //
    //             },
    //             error:function(data){
    //                 console.log(data)
    //                 data = data.responseJSON
    //                 alert(data.message)
    //             }
    //         });
    //
    //         var oTime = $(".form-data .time"),
    //             oSend = $(".form-data .send"),
    //             num = parseInt(oTime.text()),
    //             oEm = $(".form-data .time em");
    //         $(this).hide();
    //         oTime.removeClass("hide");
    //         var timer = setInterval(function () {
    //             var num2 = num-=1;
    //             oEm.text(num2);
    //             if(num2===0){
    //                 clearInterval(timer);
    //                 oSend.text("重新发送验证码");
    //                 oSend.show();
    //                 oEm.text("30");
    //                 oTime.addClass("hide");
    //             }
    //         },1000);
    //     }
    // });

//	图形验证码点击事件
    $('.code').find('img').click(function(event) {
        $(this).attr('src', '/code/imageCode?width=100&height=40&r='+Math.random());
    });
});