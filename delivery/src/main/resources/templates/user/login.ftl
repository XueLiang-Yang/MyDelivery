<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/js/vue.js"></script>
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/axios.js"></script>
    <script src="/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="/css/form.css" />
    <link rel="stylesheet" type="text/css" href="/css/bootstrapValidator.min.css" />
    <script>
        $(function(){
            $('.message a').click(function () {
                $('form').animate({
                    height: 'toggle',
                    opacity: 'toggle'
                }, 'slow');
            });
        })
    </script>
</head>
<body>
<div id="wrapper" class="login-page">
    <div id="login_form" class="form">
        <form id="register-form" class="register-form" action="javascript:void(0)">
            <div class="form-group">
                <input type="text" placeholder="用户名" id="r_user_name" name="r_user_name" v-model="r_user_name"/>
            </div>
            <div class="form-group">
                <input type="password" placeholder="密码" id="r_password" name="r_password" v-model="r_password"/>
            </div>
            <div class="form-group">
                <input type="password" placeholder="请确认密码" id="sure_password" name="sure_password" v-model="sure_password"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="请输入您的学号" id="stu_num" name="stu_num" v-model="stu_num"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="请输入您的学生姓名" id="stu_name" name="stu_name" v-model="stu_name"/>
            </div>
            <div class="form-group">
                性别：
                男<label for="r_male"><input type="radio" id="r_male" name="r_sex" v-model="r_sex" name="r_sex" value="男"/></label>
                女<label for="r_female"><input type="radio" id="r_female" name="r_sex" v-model="r_sex" name="r_sex" value="女"/></label>
            </div>
            <div class="form-group">
                <input type="text" placeholder="请输入您的班级" id="r_className" name="r_className" v-model="r_className"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="电子邮件" id="r_email" name="r_email" v-model="r_email"/>
            </div>
            <button id="create" @click="register()">创建账户</button>
            <p class="message">已经有了一个账户? <a href="javascript:void(0)">立刻登录</a></p>
        </form>
        <form id="loginForm" class="login-form" action="javascript:void(0)">
            <div class="form-group">
                <input type="text" placeholder="用户名" id="user_name" class="form-control" name="user_name"  v-model="user_name"/>
            </div>
            <div class="form-group">
                <input type="password" placeholder="密码" id="password" class="form-control" name="password" v-model="password"/>
            </div>
            <button id="login" @click="login()">登　录</button>
            <p class="message">还没有账户? <a href="javascript:void(0)">立刻创建</a></p>
        </form>
    </div>
</div>
<script>
    $(function () {
        $('#loginForm').bootstrapValidator({

            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                user_name: {
                    message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }
            }
        });
        $('#register-form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                r_user_name: {
                    message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },remote:{//ajax验证
                            url:'/user/checkUserName',
                            message:'用户已存在',
                            delay:3000,
                            type:'POST',
                        }
                    }
                }, r_password: {
                    message: '密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }, sure_password: {
                    message: '请再次输入密码',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }, identical: {
                            field: 'r_password',
                            message: '两次输入的密码不相符'
                        }
                    }
                }, stu_num: {
                    message: '学生学号验证失败',
                    validators: {
                        notEmpty: {
                            message: '学生学号不能为空'
                        }
                    }
                }, stu_name: {
                    message: '学生姓名验证失败',
                    validators: {
                        notEmpty: {
                            message: '学生姓名不能为空'
                        }
                    }
                },r_sex: {
                    message: '性别验证失败',
                    validators: {
                        notEmpty: {
                            message: '性别不能为空'
                        }
                    }
                }, r_className: {
                    message: '班级验证失败',
                    validators: {
                        notEmpty: {
                            message: '班级不能为空'
                        }
                    }
                }, r_email: {
                    validators: {
                        notEmpty: {
                            message: '邮箱地址不能为空'
                        },emailAddress:{
                            message: '邮箱地址格式不正确'
                        }
                    }
                }
            }
        });
    });
    new Vue({
        el:login_form,
        data:{
            r_user_name:"",
            r_password:"",
            sure_password:"",
            r_email:"",
            stu_num:"",
            stu_name:"",
            r_sex:"",
            r_className:"",
            user_name:"",
            password:"",
        },
        methods:{
            login:function () {
                var url = "/user/doLogin";
                axios.post(url,{
                    username:this.user_name,
                    password:this.password
                }).then(function(res){
                    if(res.data != null){
                        console.log(res.data);
                    }else{
                        alert("用户名或密码错误，请重试！！！");
                    }
                })
            },
            register:function () {
                var url = "/user/register";
                var password = this.sure_password;
                axios.post(url,{
                    nickname:this.r_user_name,
                    password:this.r_password,
                    eMail:this.r_email,
                    stuNum:this.stu_num,
                    stuName:this.stu_name,
                    stuSex:this.r_sex,
                    className:this.r_className,
                }).then(function(res){
                    if(res.data == "success"){
                        alert("注册成功！！！");
                    }else{
                        alert("注册失败，请重试！！！");
                    }
                });
            }
        }
    });

</script>
</body>
</html>